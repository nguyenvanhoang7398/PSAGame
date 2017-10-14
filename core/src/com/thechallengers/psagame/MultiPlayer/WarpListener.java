package com.thechallengers.psagame.MultiPlayer;

/**
 * Created by Asus on 10/11/2017.
 */

public interface WarpListener {

    public void onWaitingStarted(String message);

    public void onError(String message);

    public void onGameStarted(String message);

    public void onGameFinished(int code, boolean isRemote);

    public void onGameUpdateReceived(String message);

}

