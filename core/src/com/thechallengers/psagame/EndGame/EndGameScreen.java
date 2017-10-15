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
        else if (END_SCREEN_PERCENT < 0.35f) {
            END_SCREEN_TIME = 300;
            star = 1;
        }
        else if (END_SCREEN_PERCENT < 0.7) {
            END_SCREEN_TIME = 300;
            star = 2;
        }

        //Handling star currency and level unlock
        Preferences prefs = Gdx.app.getPreferences("prefs");
        prefs.putInteger("star", prefs.getInteger("star") + star);
        prefs.flush();
        if (star > 0 && prefs.getInteger("level") == END_SCREEN_LEVEL) {
            prefs.putInteger("level", prefs.getInteger("level") + 1);
        }
        prefs.flush();

        prefs.putInteger("moneyBalance", prefs.getInteger("moneyBalance") + star).flush();

        int previousStar = prefs.getInteger("level" + String.valueOf(END_SCREEN_LEVEL) + "star");
        if (star > previousStar) prefs.putInteger("level" + String.valueOf(END_SCREEN_LEVEL) + "star", star);
        prefs.flush();

        String previousBestTime = prefs.getString("best_time_level" + END_SCREEN_LEVEL);
        String updatedBestTime = getTimeString(END_SCREEN_TIME);
        boolean newBestTime = false;

        if (updatedBestTime.compareTo(previousBestTime) > 0) {
            prefs.putString("best_time_level" + END_SCREEN_LEVEL, updatedBestTime).flush();
            newBestTime = true;
        }

        world = new EndGameWorld(star, END_SCREEN_TIME, END_SCREEN_LEVEL, newBestTime);
        Gdx.input.setInputProcessor(world.getStage());
    }

    public static String getTimeString(float time) {
        int minute = (int) (time / 60f);
        int seconds = (int) (time - minute * 60);
        String secondsString;
        if (seconds < 10) secondsString = "0" + String.valueOf(seconds);
        else secondsString = String.valueOf(seconds);
        String timeString = String.valueOf(minute) + ":" +secondsString;

        return timeString;
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
