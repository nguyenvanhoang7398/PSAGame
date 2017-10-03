package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.thechallengers.psagame.SinglePlayer.Objects.CraneData;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class PhysicsInputHandler implements InputProcessor {
    private SinglePlayerGameWorld world;

    public PhysicsInputHandler(SinglePlayerGameWorld world) {
        this.world = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float box2DWorld_x = screenX / 100f;
        float box2DWorld_y = (1920 - screenY * 1920 / Gdx.graphics.getHeight()) / 100f;
        System.out.printf("%f %f\n", box2DWorld_x, box2DWorld_y);

        //neu muon input vao box2DWorld dung reference world.box2DWorld
        if (!world.box2DWorld.destroyMode) {
            if (!((CraneData) world.box2DWorld.getCrane().getUserData()).isMoving) {
                world.box2DWorld.moveCrane(box2DWorld_x, box2DWorld_y);
            }
        } else if (world.box2DWorld.cooldown <= 0) {
            world.box2DWorld.destroyBlock(box2DWorld_x, box2DWorld_y);
            world.box2DWorld.destroyMode = false;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
