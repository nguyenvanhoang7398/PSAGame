package com.thechallengers.psagame.Dummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.inputhandlers.MenuInputHandler;

/**
 * Created by Phung Tuan Hoang on 9/9/2017.
 */

public class DummyGameScreen implements Screen {
    private PSAGame game;
    private DummyGameWorld world;
    private DummyGameRenderer renderer;
    private float runTime = 0;

    public DummyGameScreen(PSAGame game) {
        this.game = game;
        world = new DummyGameWorld();
        renderer = new DummyGameRenderer(world);
        Gdx.input.setInputProcessor(new MenuInputHandler(world));
        AssetLoader.loadGameTexture();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
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
