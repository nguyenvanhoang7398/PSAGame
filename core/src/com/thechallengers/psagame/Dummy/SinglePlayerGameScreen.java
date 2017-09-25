package com.thechallengers.psagame.Dummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/9/2017.
 */

public class SinglePlayerGameScreen implements Screen {
    private PSAGame game;
    private SinglePlayerGameWorld world;
    private SinglePlayerGameRenderer renderer;
    private float runTime = 0;

    public SinglePlayerGameScreen(PSAGame game) {
        this.game = game;
        world = new SinglePlayerGameWorld();
        renderer = new SinglePlayerGameRenderer(world);
        Gdx.input.setInputProcessor(new GestureDetector(new SinglePlayerGameInputHandler(world)));
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
