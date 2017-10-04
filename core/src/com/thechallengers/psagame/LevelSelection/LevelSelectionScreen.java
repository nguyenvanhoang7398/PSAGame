package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by name on 3/10/2017.
 */

public class LevelSelectionScreen implements Screen {

    private LevelWorld level_world;
    private LevelRender level_renderer;
    private float runTime = 0;

    //constructors
    public LevelSelectionScreen() {
        AssetLoader.loadLevelSelectionTexture();
        level_world = new LevelWorld();
        level_renderer = new LevelRender(level_world);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(level_world.getStage());
        runTime += delta;
        level_world.update(delta);
        level_renderer.render(runTime);
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
        AssetLoader.disposeLevelSelectionTexture();
        level_world.getStage().dispose();

    }
}
