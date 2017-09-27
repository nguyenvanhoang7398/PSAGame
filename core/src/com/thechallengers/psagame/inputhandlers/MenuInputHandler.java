package com.thechallengers.psagame.inputhandlers;

import com.badlogic.gdx.InputProcessor;
import com.thechallengers.psagame.SinglePlayer.SinglePlayerGameWorld;

/**
 * Created by Phung Tuan Hoang on 9/6/2017.
 */

public class MenuInputHandler implements InputProcessor{
    private SinglePlayerGameWorld menu_world;

    public MenuInputHandler(SinglePlayerGameWorld world) {
        menu_world = world;
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
