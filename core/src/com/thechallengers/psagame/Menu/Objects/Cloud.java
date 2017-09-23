package com.thechallengers.psagame.Menu.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.badlogic.gdx.scenes.scene2d.Action.*;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Phung Tuan Hoang on 9/23/2017.
 */

public class Cloud extends Actor {
    private Texture cloud;
    private int x_position, y_position;
    private int speed;

    public Cloud() {
        Random r = new Random();

        int type = r.nextInt(2);
        if (type == 0) speed = 75;
        else speed = -75;

        if (r.nextInt(2) == 0) cloud = AssetLoader.cloud_1;
        else cloud = AssetLoader.cloud_2;

        if (type == 0) x_position = r.nextInt((int) Gdx.graphics.getWidth() / 6);
        else x_position = r.nextInt((int) Gdx.graphics.getWidth() / 6) + (int) (5 / 6f * Gdx.graphics.getWidth());
        y_position = r.nextInt((int) Gdx.graphics.getHeight() / 2) + (int) (Gdx.graphics.getHeight() / 2);

        this.setPosition(x_position, y_position);

        this.addAction(sequence(fadeIn(5f), fadeOut(5f), run(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        })));

        this.getColor().a = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(cloud, (int) this.getX(), (int) this.getY());

        batch.setColor(Color.WHITE); // reset the color

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.setPosition(this.getX() + speed * delta, this.getY());
        System.out.println(this.getY());

        if (this.getX() > 1080 || this.getX() < 0) reset();
    }

    public void reset() {
        Random r = new Random();

        int type = r.nextInt(2);
        if (type == 0) speed = 75;
        else speed = -75;

        if (r.nextInt(2) == 0) cloud = AssetLoader.cloud_1;
        else cloud = AssetLoader.cloud_2;

        if (type == 0) x_position = r.nextInt((int) Gdx.graphics.getWidth() / 6);
        else x_position = r.nextInt((int) Gdx.graphics.getWidth() / 6) + (int) (5 / 6f * Gdx.graphics.getWidth());
        y_position = r.nextInt((int) Gdx.graphics.getHeight() / 2) + (int) (Gdx.graphics.getHeight() / 2);

        this.setPosition(x_position, y_position);

        this.addAction(sequence(fadeIn(5f), fadeOut(5f), run(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        })));

        this.getColor().a = 0f;
    }
}
