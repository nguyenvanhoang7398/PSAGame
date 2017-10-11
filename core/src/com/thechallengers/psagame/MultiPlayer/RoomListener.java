package com.thechallengers.psagame.MultiPlayer;

/**
 * Created by Asus on 10/11/2017.
 */

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

public class RoomListener implements RoomRequestListener{


    private WarpController callBack;

    public RoomListener(WarpController callBack) {
        this.callBack = callBack;
    }

    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent event) {
        if(event.getResult()==WarpResponseResultCode.SUCCESS){
            callBack.onGetLiveRoomInfo(event.getJoinedUsers());
        }else{
            callBack.onGetLiveRoomInfo(null);
        }
    }

    public void onJoinRoomDone(RoomEvent event) {
        callBack.onJoinRoomDone(event);
    }

    public void onLeaveRoomDone(RoomEvent arg0) {

    }

    public void onSetCustomRoomDataDone(LiveRoomInfoEvent arg0) {

    }

    public void onSubscribeRoomDone(RoomEvent event) {
        if(event.getResult()==WarpResponseResultCode.SUCCESS){
            callBack.onRoomSubscribed(event.getData().getId());
        }else{
            callBack.onRoomSubscribed(null);
        }
    }

    public void onUnSubscribeRoomDone(RoomEvent arg0) {

    }

    public void onUpdatePropertyDone(LiveRoomInfoEvent arg0) {

    }

    @Override
    public void onLockPropertiesDone (byte result) {

    }

    @Override
    public void onUnlockPropertiesDone (byte arg0) {

    }

    public void onJoinAndSubscribeRoomDone(RoomEvent roomEvent) {

    }

    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {

    }

}

