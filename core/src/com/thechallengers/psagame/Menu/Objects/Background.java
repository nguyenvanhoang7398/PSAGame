package com.thechallengers.psagame.Menu.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/13/2017.
 */

public class Background extends Actor {
    private boolean isMovingToGameScreen = false;
    private Texture background;
    private TextureRegion upper_bar, lower_bar;
    private int starting_x, starting_y, end_x, end_y, offset_x, offset_y;
    private int top_bottom_compensate;

    public Background() {
        super();

        background = new Texture(Gdx.files.internal("textures/new_Menu_background.png"));

        //top_bottom_compensate = (Gdx.graphics.getHeight() - 1440) / 2;
        //upper_bar = new TextureRegion(new Texture(Gdx.files.internal("textures/upper_bar.png")), 0, 0, 1080, top_bottom_compensate);
        //lower_bar = new TextureRegion(new Texture(Gdx.files.internal("textures/lower_bar.png")), 0, 0, 1080, top_bottom_compensate);

        starting_x = 0; starting_y = 0; end_x = 2160; end_y = 3840;
        offset_x = 0; offset_y = 0;


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(background, 0, top_bottom_compensate, 1080, 1920);

        batch.setColor(Color.WHITE); // reset the color
//
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


       AssetLoader.updateGameBackGroundDetails(starting_x, starting_y, end_x, end_y);
    }

    public void moveToGameScreen() {
        isMovingToGameScreen = true;
    }
}
