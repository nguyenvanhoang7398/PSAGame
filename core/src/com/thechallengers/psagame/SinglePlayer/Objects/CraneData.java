package com.thechallengers.psagame.SinglePlayer.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Phung Tuan Hoang on 9/29/2017.
 */

public class CraneData {
    public boolean isMoving;
    public Body cranedBody;
    public float velocity;
    public enum STATE {
        STOP, UP, LEFT, RIGHT, DOWN
    }
    public STATE state;
    public Vector2 destination;

    public CraneData() {
        isMoving = false;
        cranedBody = null;
        velocity = 0;
        state = STATE.STOP;
        destination = new Vector2(5.4f, 14.4f);
    }
}
