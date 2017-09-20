package com.thechallengers.psagame.Dummy.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/18/2017.
 */

public class Crane extends Actor {
    private Vector2 position, matrix_position;
    private boolean isCarrying;
    private Texture crane_texture;
    public enum DIRECTION {UP, DOWN, LEFT, RIGHT};
    private int[][] matrix;

    public Crane() {
        position = new Vector2(1080-100, 1500);
        isCarrying = false;
    }

    @Override
    public void act(float delta) {

    }

    public void move(DIRECTION direction) {
        switch (direction) {
            case LEFT: {
                position.x -= 50;
                break;
            }

            case RIGHT: {
                position.x += 50;
                break;
            }
            case DOWN: {
                position.y -= 50;
                break;
            }
            case UP: {
                position.y += 50;
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(AssetLoader.game_crane, position.x, position.y);
    }
}
