package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 10/1/2017.
 */

public class TutorialScreen implements Screen{
    private PSAGame game;
    private TutorialInputHandler tutorialInputHandler;
    private TutorialWorld world;
    private TutorialRenderer renderer;
    private float runTime = 0;

    public TutorialScreen(PSAGame game) {
        AssetLoader.loadGameTexture();
        AssetLoader.loadMenuTexture();
        AssetLoader.loadTutorialTexture();
        this.game = game;
        world = new TutorialWorld();
        renderer = new TutorialRenderer(world);
        this.tutorialInputHandler = new TutorialInputHandler(world);
        //NEED ASSETLOADER LOAD
        Gdx.input.setInputProcessor(tutorialInputHandler);
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
