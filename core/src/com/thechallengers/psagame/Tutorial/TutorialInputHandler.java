package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.thechallengers.psagame.SinglePlayer.Objects.CraneData;

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
        if (world.getStateQueue().size != 0) {
            switch (world.getStateQueue().first()) {
                case WELCOME:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case AIM:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case DROP:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case DROP_INDICATOR:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case TILTED:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case TILTED_INDICATOR:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case AFTER_TILTED:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case AFTER_TILTED_INDICATOR:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case PROGRESS:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case DESTROY:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case DESTROY_INDICATOR:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case GOODLUCK:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                default:
            }
        }

        else {
            if (!((CraneData) world.box2DWorld.getCrane().getUserData()).isMoving) {
                float box2DWorld_x = screenX / 100f;
                float box2DWorld_y = (1920 - screenY * 1920 / Gdx.graphics.getHeight()) / 100f;
                System.out.printf("%f %f\n", box2DWorld_x, box2DWorld_y);

                world.box2DWorld.moveCrane(box2DWorld_x, box2DWorld_y);
            }
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
