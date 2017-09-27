package com.thechallengers.psagame.SinglePlayer.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/18/2017.
 */

public class Crane extends Actor {
    private Vector2 velocity;
    private Vector2 acceleration;
    private Body crane_body;

    public Crane() {

    }

    @Override
    public void act(float delta) {
        velocity.x += acceleration.x * delta;
        velocity.y += acceleration.y * delta;

        this.setX(this.getX() + velocity.x * delta);
        this.setY(this.getY() + velocity.y * delta);
    }
}
