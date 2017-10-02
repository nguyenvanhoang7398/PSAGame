package Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class ShopScreen implements Screen {
    private PSAGame game;
    private ShopWorld shopWorld;
    private ShopRenderer shopRenderer;
    private float runTime = 0;

    //constructor
    public ShopScreen(PSAGame game) {
        AssetLoader.loadShopTexture();
        this.game = game;
        shopWorld = new ShopWorld();
        shopRenderer = new ShopRenderer(shopWorld);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(shopWorld.getStage());
        runTime += delta;
        shopWorld.update(delta);
        shopRenderer.render(runTime);
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
    public void hide() { dispose();}

    @Override
    public void dispose() {
        AssetLoader.disposeShopTexture();
    }
}
