package com.thechallengers.psagame.MultiPlayer;

import java.util.HashMap;
import java.util.Hashtable;

import org.json.JSONObject;
import com.badlogic.gdx.utils.Json;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.thechallengers.psagame.SinglePlayer.SinglePlayerGameRenderer;

public class WarpController {

    private static WarpController instance;
    public static SinglePlayerGameRenderer renderer;

    private boolean showLog = true;

    private final String apiKey = "03ab0ca46a7680fbadaa05e80b7064cfdb3c95d4498b2617978b2ce12b1ff888";

    private final String secretKey = "095046a9904b572b81c906924b14b814551f7d05c3f63f4897af9d581c150742";

    private WarpClient warpClient;

    private String localUser;
    private String roomId;

    private boolean isConnected = false;
    boolean isUDPEnabled = false;

    private WarpListener warpListener ;

    public int STATE;

    // Game state constants
    public static final int WAITING = 1;
    public static final int STARTED = 2;
    public static final int COMPLETED = 3;
    public static final int FINISHED = 4;

    // Game completed constants
    public static final int GAME_WIN = 5;
    public static final int GAME_LOOSE = 6;
    public static final int ENEMY_LEFT = 7;

    public WarpController() {
        initAppwarp();
        warpClient.addConnectionRequestListener(new ConnectionListener(this));
        warpClient.addChatRequestListener(new ChatListener(this));
        warpClient.addZoneRequestListener(new ZoneListener(this));
        warpClient.addRoomRequestListener(new RoomListener(this));
        warpClient.addNotificationListener(new NotificationListener(this));
        STATE = WAITING;
        WarpClient.enableTrace(true);
    }

    public static WarpController getInstance(){
        if(instance == null){
            instance = new WarpController();
        }
        return instance;
    }

    public void startApp(String localUser){
        this.localUser = localUser;
        warpClient.connectWithUserName(localUser);
    }

    public void setListener(WarpListener listener){
        this.warpListener = listener;
    }

    public void stopApp(){
        if(isConnected){
            warpClient.unsubscribeRoom(roomId);
            warpClient.leaveRoom(roomId);
        }
        warpClient.disconnect();
    }

    private void initAppwarp(){
        try {
            WarpClient.initialize(apiKey, secretKey);
            WarpClient . setRecoveryAllowance ( 120 );
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendGameUpdate(String msg){
        if(isConnected){
            if(isUDPEnabled){
                warpClient.sendUDPUpdatePeers((localUser+"#@"+msg).getBytes());
            }else{
                warpClient.sendUpdatePeers((localUser+"#@"+msg).getBytes());
            }
        }
    }

    public void updateResult(int code, String msg){
        if(isConnected){
            STATE = COMPLETED;
            HashMap<String, Object> properties = new HashMap<String, Object>();
            properties.put("result", code);
            warpClient.lockProperties(properties);
        }
    }

    public void onConnectDone(boolean status){
        for(int i = 0; i < 5000; i ++) {
//            System.out.println("Status is " + status);
        }
        if(status){
            warpClient.initUDP();
            warpClient.joinRoomInRange(1, 1, false);
        }else{
            isConnected = false;
            handleError();
        }
    }

    public void onDisconnectDone(boolean status){

    }

    public void onRoomCreated(String roomId){
        if(roomId!=null){
            warpClient.joinRoom(roomId);
        }else{
            handleError();
        }
    }

    public void onJoinRoomDone(RoomEvent event){
        log("onJoinRoomDone: "+event.getResult());
        if(event.getResult()==WarpResponseResultCode.SUCCESS){// success case
            this.roomId = event.getData().getId();
            warpClient.subscribeRoom(roomId);
        }else if(event.getResult()==WarpResponseResultCode.RESOURCE_NOT_FOUND){// no such room found
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("result", "");
            warpClient.createRoom("superjumper", "shephertz", 2, data);
        }else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onRoomSubscribed(String roomId){
        log("onSubscribeRoomDone: "+roomId);
        if(roomId!=null){
            isConnected = true;
            warpClient.getLiveRoomInfo(roomId);
        }else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onGetLiveRoomInfo(String[] liveUsers){
        log("onGetLiveRoomInfo: "+liveUsers.length);
        if(liveUsers!=null){
            if(liveUsers.length==2){
                startGame();
            }else{
                waitForOtherUser();
            }
        }else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onUserJoinedRoom(String roomId, String userName){
		/*
		 * if room id is same and username is different then start the game
		 */
        if(localUser.equals(userName)==false){
            startGame();
        }
    }

    public void onSendChatDone(boolean status){
        log("onSendChatDone: "+status);
    }

    public void onGameUpdateReceived(String message){
//		log("onMoveUpdateReceived: message"+ message );
        String userName = message.substring(0, message.indexOf("#@"));
        String data = message.substring(message.indexOf("#@")+2, message.length());
        System.out.println("Ye");
        if(!localUser.equals(userName)){
            warpListener.onGameUpdateReceived(data);
            try {
                JSONObject obj = new JSONObject(message);
                float x = (float)obj.getDouble("overlapPercentageOpponent");
                System.out.println("The percentage of op: " + userName + " IS " + x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onResultUpdateReceived(String userName, int code){
        if(localUser.equals(userName)==false){
            STATE = FINISHED;
            warpListener.onGameFinished(code, true);
        }else{
            warpListener.onGameFinished(code, false);
        }
    }

    public void onUserLeftRoom(String roomId, String userName){
        log("onUserLeftRoom "+userName+" in room "+roomId);
        if(STATE==STARTED && !localUser.equals(userName)){// Game Started and other user left the room
            warpListener.onGameFinished(ENEMY_LEFT, true);
        }
    }

    public int getState(){
        return this.STATE;
    }

    private void log(String message){
        if(showLog){
            System.out.println(message);
        }
    }

    private void startGame(){
        STATE = STARTED;
        warpListener.onGameStarted("Start the Game");
    }

    private void waitForOtherUser(){
        STATE = WAITING;
        warpListener.onWaitingStarted("Waiting for other user");
    }

    private void handleError(){
        if(roomId!=null && roomId.length()>0){
            warpClient.deleteRoom(roomId);
        }
        disconnect();
    }

    public void handleLeave(){
        if(isConnected){
            warpClient.unsubscribeRoom(roomId);
            warpClient.leaveRoom(roomId);
            if(STATE!=STARTED){
                warpClient.deleteRoom(roomId);
            }
            warpClient.disconnect();
        }
    }

    private void disconnect(){
        warpClient.removeConnectionRequestListener(new ConnectionListener(this));
        warpClient.removeChatRequestListener(new ChatListener(this));
        warpClient.removeZoneRequestListener(new ZoneListener(this));
        warpClient.removeRoomRequestListener(new RoomListener(this));
        warpClient.removeNotificationListener(new NotificationListener(this));
        warpClient.disconnect();
    }
}
