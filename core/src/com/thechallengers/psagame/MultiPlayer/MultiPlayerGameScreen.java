package com.thechallengers.psagame.MultiPlayer;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.thechallengers.psagame.SinglePlayer.*;
import com.thechallengers.psagame.game.PSAGame;

import org.json.JSONObject;

/**
 * Created by Asus on 10/11/2017.
 */

public class MultiPlayerGameScreen extends  SinglePlayerGameScreen {
    private float runTime = 0;

    public MultiPlayerGameScreen(PSAGame game) {
        super(1);
        WarpController.renderer = this.renderer;
    }

    @Override
    public void render(float delta) {
        if (WarpController.getInstance().STATE != WarpController.WAITING) {
            runTime += delta;
            world.update(delta);
            renderer.render(runTime);
            sendInfo(this.world.box2DWorld.getPercentageOverlap());
        } else {
//            System.out.println("Waiting for another user");
        }
    }

    public void sendInfo(float percentOverlap) {
        try {
            JSONObject data = new JSONObject();
            data.put("overlapPercentageOpponent", percentOverlap);
            WarpController.getInstance().sendGameUpdate(data.toString());
            System.out.println("Info sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
