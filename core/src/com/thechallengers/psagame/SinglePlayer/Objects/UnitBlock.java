package com.thechallengers.psagame.SinglePlayer.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by VanHoang on 9/29/2017.
 */

public class UnitBlock extends Actor {
    Texture unitBlockTexture = new Texture("textures/unitBlockSmall.png");
    float blockX, blockY;
    public static int SCREEN_HEIGHT = 1920;
    public static int SCREEN_WIDTH = 1080;

    public UnitBlock(float x, float y) {
        blockX = x;
        blockY = y;
        setBounds(blockX, blockY, unitBlockTexture.getWidth(), unitBlockTexture.getHeight());
        toBack();
    }

    public UnitBlock() {
        blockX = SCREEN_WIDTH/2 - unitBlockTexture.getWidth()/2;
        blockY = SCREEN_HEIGHT/2 - unitBlockTexture.getHeight()/2;
        setBounds(blockX, blockY, unitBlockTexture.getWidth(), unitBlockTexture.getHeight());
        setZIndex(0);
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        batch.getColor().a *= parentAlpha;

        batch.draw(unitBlockTexture, blockX, blockY);
    }

}
