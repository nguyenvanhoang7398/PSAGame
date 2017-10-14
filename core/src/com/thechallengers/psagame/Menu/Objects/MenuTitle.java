package com.thechallengers.psagame.Menu.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by VanHoang on 10/14/2017.
 */

public class MenuTitle extends Actor {


    private final float HEIGHT = 1920;
    private final float WIDTH = 1080;
    private final float SCALE = 1f;
    private final float MENU_TITLE_OFFSET_Y = HEIGHT*0.65f;

    private Texture menu_title;
    private float starting_x, starting_y, width, height;

    public MenuTitle() {
        super();

        menu_title = new Texture(Gdx.files.internal("textures/new_Menu_title.png"));
        width = menu_title.getWidth()*SCALE;
        height = menu_title.getHeight()*SCALE;
        starting_x = WIDTH/2 - width/2;
        starting_y = MENU_TITLE_OFFSET_Y;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(menu_title, starting_x, starting_y, width, height);

        batch.setColor(Color.WHITE); // reset the color
//
    }
}
