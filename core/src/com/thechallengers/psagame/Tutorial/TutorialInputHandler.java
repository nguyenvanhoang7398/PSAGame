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
                    if (screenX > 455 && screenX < 507 && screenY > 1775 && screenY < 1827) {
                        inputForNormalGame(475, 1800);
                        world.clicked();
                        world.getStateQueue().removeFirst();
                        world.createOnScreenInstructions();
                        break;
                    }
                    else return false;
                case TILTED:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    break;
                case TILTED_INDICATOR:
                    if (screenX > 515 && screenX < 568 && screenY > 1713 && screenY < 1766) {
                        inputForNormalGame(540, 1738);
                        world.clicked();
                        world.getStateQueue().removeFirst();
                        world.createOnScreenInstructions();
                        break;
                    }
                    else return false;
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

            return true;
        }

        inputForNormalGame(screenX, screenY);

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

    public void inputForNormalGame(int screenX, int screenY) {
        float box2DWorld_x = screenX / 100f;
        float box2DWorld_y = (1920 - screenY * 1920 / Gdx.graphics.getHeight()) / 100f;

        if (!world.box2DWorld.destroyMode) {
            if (!((CraneData) world.box2DWorld.getCrane().getUserData()).isMoving) {
                world.box2DWorld.moveCrane(box2DWorld_x, box2DWorld_y);
            }
        } else if (world.box2DWorld.cooldown <= 0) {
            world.box2DWorld.destroyBlock(box2DWorld_x, box2DWorld_y);
            world.box2DWorld.destroyMode = false;
        }
    }
}
