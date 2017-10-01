package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by Phung Tuan Hoang on 10/1/2017.
 */

public class TutorialInputHandler implements InputProcessor {
    private TutorialWorld world;

    public TutorialInputHandler(TutorialWorld world) {
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
        switch (world.getStateQueue().first()) {
            case WELCOME: {
                world.getStateQueue().removeFirst();
                world.getInstructor().clicked();
                world.getBalloon().clicked();
                break;
            }
            case AIM:
                break;
            case DROP:
                break;
            case TILTED:
                break;
            case AFTER_TILTED:
                break;
            case PROGRESS:
                break;
            case DESTROY:
                break;
            case GOODLUCK:
                break;
        }
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
