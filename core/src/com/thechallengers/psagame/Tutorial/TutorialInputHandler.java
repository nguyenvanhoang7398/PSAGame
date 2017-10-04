package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.thechallengers.psagame.SinglePlayer.Objects.CraneData;

import static com.thechallengers.psagame.game.PSAGame.LONG_EDGE;
import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

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
                    if (screenX > 455 && screenX < 507 && screenY > 1675 && screenY < 1727) {
                        inputForNormalGame(475, 1700);
                        world.clicked();
                        world.getStateQueue().removeFirst();
                        world.createOnScreenInstructions();
                        break;
                    }
                    else return false;
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
                case DESTROY_INDICATOR_1:
                    if (screenX > SHORT_EDGE-(30+200) && screenX < SHORT_EDGE-(30) && screenY >1920 - (LONG_EDGE-(15)) && screenY < 1920 - (LONG_EDGE-(15+200))) {
                        world.clicked();
                        world.getStateQueue().removeFirst();
                        world.createOnScreenInstructions();
                        world.box2DWorld.destroyMode = true;
                        return false;
                    }
                    return false;
                case DESTROY_INDICATOR_2:
                    if (screenX > 455 && screenX < 507 && screenY > 1775 && screenY < 1827) {
                        inputForNormalGame(475, 1800);
                        world.clicked();
                        world.getStateQueue().removeFirst();
                        world.createOnScreenInstructions();
                        break;
                    }
                    else return false;
                case GOODLUCK:
                    world.clicked();
                    world.getStateQueue().removeFirst();
                    world.createOnScreenInstructions();
                    world.addListenerToDestroyButton();
                    InputMultiplexer multiplexer = new InputMultiplexer();
                    multiplexer.addProcessor(world.getStage());
                    multiplexer.addProcessor(this);
                    Gdx.input.setInputProcessor(multiplexer);
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
