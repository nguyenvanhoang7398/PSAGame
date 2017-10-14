package com.thechallengers.psagame.MultiPlayer;

/**
 * Created by Asus on 10/11/2017.
 */

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class ConnectionListener implements ConnectionRequestListener {

    WarpController callBack;

    public ConnectionListener(WarpController callBack){
        this.callBack = callBack;
    }

    public void onConnectDone(ConnectEvent e) {
        for(int i = 0; i < 1; i ++) {
            System.out.println("REason " + e.getResult());
        }
        if(e.getResult()==WarpResponseResultCode.SUCCESS){
            callBack.onConnectDone(true);
        }else{
            callBack.onConnectDone(false);
        }
    }

    public void onDisconnectDone(ConnectEvent e) {

    }

    @Override
    public void onInitUDPDone (byte result) {
        if(result==WarpResponseResultCode.SUCCESS){
            callBack.isUDPEnabled = true;
        }
    }

}

