package com.thechallengers.psagame.Menu.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.Random;

/**
 * Created by Phung Tuan Hoang on 9/13/2017.
 */

public class Background extends Actor {
    private boolean isMovingToGameScreen = false;
    private TextureRegion background;
    private int starting_x, starting_y, end_x, end_y, offset_x, offset_y;

    public Background() {
        super();
        starting_x = 0; starting_y = 0; end_x = 2160; end_y = 3840;
        offset_x = 0; offset_y = 0;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(background, 0, 0, 1080, 1920);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isMovingToGameScreen) {
            offset_x = Math.round(delta / 2.5f * 1080 / 2);
            offset_y = Math.round(delta / 2.5f * 1920 / 2);
            starting_x += offset_x;
            starting_y += offset_y;
            end_x -= offset_x;
            end_y -= offset_y;
        }

        background = new TextureRegion(AssetLoader.background_texture, starting_x, starting_y, end_x - starting_x, end_y - starting_y);
       AssetLoader.updateGameBackGroundDetails(starting_x, starting_y, end_x, end_y);
    }

    public void moveToGameScreen() {
        isMovingToGameScreen = true;
    }
}
