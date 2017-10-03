package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by name on 3/10/2017.
 */

public class LevelSelectionBackground extends Actor {
    Texture texture = new Texture(Gdx.files.internal("textures/level_selection_background.png"));
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,0,0);
    }
}
