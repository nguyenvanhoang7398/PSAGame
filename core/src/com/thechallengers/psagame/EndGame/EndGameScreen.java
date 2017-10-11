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

    public EndGameScreen() {
        world = new EndGameWorld();
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
