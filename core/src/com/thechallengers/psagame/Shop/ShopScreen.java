package com.thechallengers.psagame.Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.helpers.SoundLoader;


/**
 * Created by name on 3/10/2017.
 */

public class ShopScreen implements Screen {
    private PSAGame game;
    private ShopWorld shop_world;
    private ShopRender shop_renderer;
    private float runTime = 0;

    public ShopScreen(PSAGame game) {
        AssetLoader.loadShopTexture();
        this.game = game;
        shop_world = new ShopWorld(game);
        shop_renderer = new ShopRender(shop_world);
        SoundLoader.musicHashtable.get("shop_bgm.mp3").play();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(shop_world.getStage());
        runTime += delta;
        shop_world.update(delta);
        shop_renderer.render(runTime);
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
    public void hide() { }

    @Override
    public void dispose() {
        AssetLoader.disposeShopTexture();
        shop_world.getStage().dispose();
        SoundLoader.musicHashtable.get("shop_bgm.mp3").stop();
    }
}
