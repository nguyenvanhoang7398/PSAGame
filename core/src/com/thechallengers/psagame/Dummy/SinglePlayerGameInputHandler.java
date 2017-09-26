package com.thechallengers.psagame.Dummy;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.thechallengers.psagame.Dummy.Physics.PhysicsInputHandler;

/**
 * Created by Phung Tuan Hoang on 9/20/2017.
 */

public class SinglePlayerGameInputHandler implements InputProcessor {
    PhysicsInputHandler physicsInputHandler;

    public SinglePlayerGameInputHandler(PhysicsInputHandler physicsInputHandler) {
        this.physicsInputHandler = physicsInputHandler;
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
        physicsInputHandler.touchDown(screenX, screenY + 319, pointer, button);
        return true;
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
