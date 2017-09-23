package com.thechallengers.psagame.Menu.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/23/2017.
 */

public class Containers extends Actor {
    private Texture containers;

    public Containers() {
        this.setPosition(160, 1920 - 1240);
        containers = AssetLoader.containers;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(containers, (int) this.getX(), (int) this.getY());

        batch.setColor(Color.WHITE); // reset the color

    }

}
