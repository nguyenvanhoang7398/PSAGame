package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by name on 3/10/2017.
 */

public class StarLevel extends Actor{
    private int starLevel = 0;
    private Texture texture = AssetLoader.star0;
    private int posX, posY;

    public StarLevel(int posX, int posY, int starLevel) {
        this.posX = posX;
        this.posY = posY;
        this.starLevel = starLevel;
        switch (starLevel) {
            case 1: texture = AssetLoader.star1;
                break;
            case 2: texture = AssetLoader.star2;
                break;
            case 3: texture = AssetLoader.star3;
                break;
            default: break;
        }
    }
    public void draw(Batch batch, float alpha){
        batch.draw(texture,posX,posY);
    }
}
