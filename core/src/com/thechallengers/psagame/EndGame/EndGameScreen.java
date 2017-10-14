package com.thechallengers.psagame.EndGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;

/**
 * Created by Phung Tuan Hoang on 10/4/2017.
 */

public class EndGameScreen implements Screen {
    private EndGameWorld world;
    public static int END_SCREEN_LEVEL = 0;
    public static float END_SCREEN_TIME = 0;
    public static float END_SCREEN_PERCENT = 0;

    public EndGameScreen() {
        int star = 3;

        if (END_SCREEN_PERCENT == 0f) star = 0;
        else if (END_SCREEN_PERCENT < 0.2) {
            END_SCREEN_TIME = 300;
            star = 1;
        }
        else if (END_SCREEN_PERCENT < 0.4) {
            END_SCREEN_TIME = 300;
            star = 2;
        }

        //Handling star currency and level unlock
        Preferences prefs = Gdx.app.getPreferences("prefs");
        prefs.putInteger("star", prefs.getInteger("star") + star);
        prefs.flush();
        if (star > 0) prefs.putInteger("level", prefs.getInteger("level") + 1);
        prefs.flush();

        world = new EndGameWorld(star, END_SCREEN_TIME);
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
