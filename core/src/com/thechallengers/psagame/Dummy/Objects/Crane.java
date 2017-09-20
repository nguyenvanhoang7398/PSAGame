package com.thechallengers.psagame.Dummy.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Phung Tuan Hoang on 9/18/2017.
 */

public class Crane extends Actor {
    private Vector2 position, matrix_position;
    private boolean isCarrying;
    private Texture crane_texture;
    public enum DIRECTION {UP, DOWN, LEFT, RIGHT};
    private int[][] matrix;

    public Crane(int[][] matrix) {
        matrix_position = new Vector2(4, 0);
        isCarrying = false;
        this.matrix = matrix;
    }

    @Override
    public void act(float delta) {

    }

    public void move(DIRECTION direction) {
        switch (direction) {
            case LEFT: {
                matrix_position.x--;
                if (matrix_position.x < 0) matrix_position.x = 0;
                break;
            }

            case RIGHT: {
                matrix_position.x++;
                if (matrix_position.x < 0) matrix_position.x = 0;
                break;
            }

            case DOWN: {

            }
        }
    }
}
