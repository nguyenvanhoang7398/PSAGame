package com.thechallengers.psagame.EndGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;

/**
 * Created by Phung Tuan Hoang on 10/4/2017.
 */

public class EndGameScreen implements Screen {
    private EndGameWorld world;
    private EndGameRender renderer;
    public static int END_SCREEN_LEVEL = 0;
    public static float END_SCREEN_TIME = 0;
    public static float END_SCREEN_PERCENT = 0;

    public EndGameScreen() {
        int star = 3;

        if (END_SCREEN_PERCENT < 0.3) {
            END_SCREEN_TIME = 300;
            star = 1;
        }
        else if (END_SCREEN_PERCENT < 0.6) {
            END_SCREEN_TIME = 300;
            star = 2;
        }

        world = new EndGameWorld(star, END_SCREEN_TIME);
        renderer = new EndGameRender(world);
        Gdx.input.setInputProcessor(world.getStage());
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
