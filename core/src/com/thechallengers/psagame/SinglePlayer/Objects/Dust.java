package com.thechallengers.psagame.SinglePlayer.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 10/15/2017.
 */

public class Dust extends Actor {
    private Texture texture;
    private int direction; //left is -1, right is 1
    private float velocity;

    public Dust(int direction, float x, float y) {
        if (direction == -1) texture = AssetLoader.dust_left;
        else texture = AssetLoader.dust_right;
        this.direction = direction;
        if (direction == -1) this.setX(x - 85);
        else this.setX(x);
        this.setY(y);
        this.velocity = 15f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.setX(this.getX() + velocity * direction * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(texture, (int) this.getX(), (int) this.getY());

        batch.setColor(Color.WHITE); // reset the color
    }
}
