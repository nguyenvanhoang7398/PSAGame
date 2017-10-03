package com.thechallengers.psagame.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/6/2017.
 */

public class MenuScreen implements Screen {
    private PSAGame game;
    private MenuWorld menu_world;
    private MenuRenderer menu_renderer;
    private float runTime = 0;

    //constructor
    public MenuScreen(PSAGame game) {
        AssetLoader.loadMenuTexture();
        this.game = game;
        menu_world = new MenuWorld(game);
        menu_renderer = new MenuRenderer(menu_world);
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(menu_world.getStage());
        runTime += delta;
        menu_world.update(delta);
        menu_renderer.render(runTime);

    }

    @Override
    public void show() {

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
        AssetLoader.disposeMenuTexture();
        menu_world.getStage().dispose();
    }
}
