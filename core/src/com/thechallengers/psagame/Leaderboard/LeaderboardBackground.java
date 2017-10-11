package com.thechallengers.psagame.Leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by VanHoang on 10/12/2017.
 */

public class LeaderboardBackground extends Actor {
    Texture texture = new Texture(Gdx.files.internal("textures/leaderboard_background.png"));
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,0,0);
    }
}
