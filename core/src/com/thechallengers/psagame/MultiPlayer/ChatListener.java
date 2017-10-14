package com.thechallengers.psagame.MultiPlayer;

/**
 * Created by Asus on 10/11/2017.
 */
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;



public class ChatListener implements ChatRequestListener{

    WarpController callBack;

    public ChatListener(WarpController callBack) {
        this.callBack = callBack;
    }

    public void onSendChatDone(byte result) {
        if(result==WarpResponseResultCode.SUCCESS){
            callBack.onSendChatDone(true);
        }else{
            callBack.onSendChatDone(false);
        }
    }

    @Override
    public void onSendPrivateChatDone (byte arg0) {
        // TODO Auto-generated method stub

    }

}

