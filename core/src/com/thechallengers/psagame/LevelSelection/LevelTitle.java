package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by name on 3/10/2017.
 */

public class LevelTitle extends Actor {
    Texture texture = AssetLoader.level_title;
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,100,1400);
    }
}
