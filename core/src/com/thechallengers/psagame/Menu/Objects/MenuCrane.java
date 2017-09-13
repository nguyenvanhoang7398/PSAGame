package com.thechallengers.psagame.Menu.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class MenuCrane extends Actor {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration = new Vector2(0, 0);
    private enum State {UP, DOWN, STOP};
    private State current_state = State.STOP;
    private float wait_time = 0;
    private Button sp, mp, shop, leader, exit, tut;
    private Texture crane_texture;

    public MenuCrane(float x, float y, float width, float height, Button sp, Button mp, Button shop, Button leader, Button exit, Button tut) {
        super();
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        this.setWidth(width);
        this.setHeight(height);
        this.sp = sp;
        this.mp = mp;
        this.tut = tut;
        this.shop = shop;
        this.leader = leader;
        this.exit = exit;
        this.crane_texture = AssetLoader.menu_crane_texture;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (position.y <= 1000 && current_state != State.UP) {
            velocity.y = 0;
            wait_time += delta;
            if (wait_time >= 0.5f) {
                wait_time = 0;
                current_state = State.UP;
            }
            else return;
        }

        if (current_state == State.STOP) {
        }
        else if (current_state == State.DOWN) {
            acceleration.y = -3000f;
            velocity.add(acceleration.cpy().scl(delta));
            position.add(velocity.cpy().scl(delta));
        }
        else if (current_state == State.UP) {
            acceleration.y = +3000f;
            velocity.add(acceleration.cpy().scl(delta));
            position.add(velocity.cpy().scl(delta));

            //updates buttons positions as well
            exit.setPosition(exit.getX(), exit.getY() + this.velocity.y * delta);
            shop.setPosition(shop.getX(), shop.getY() + this.velocity.y * delta);
            sp.setPosition(sp.getX(), sp.getY() + this.velocity.y * delta);
            mp.setPosition(mp.getX(), mp.getY() + this.velocity.y * delta);
            tut.setPosition(tut.getX(), tut.getY() + this.velocity.y * delta);
            leader.setPosition(leader.getX(), leader.getY() + this.velocity.y * delta);
        }

        this.setX(position.x);
        this.setY(position.y);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * alpha);
        batch.draw(crane_texture, position.x, position.y);
    }

    public void stop() {
        velocity.y = 0; acceleration.y = 0; current_state = State.STOP;
    }

    public void setLowering() {
        current_state = State.DOWN;
    }
}
