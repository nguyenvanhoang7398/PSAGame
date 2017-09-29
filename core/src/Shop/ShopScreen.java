package Shop;

import com.badlogic.gdx.Screen;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class ShopScreen implements Screen {
    private ShopWorld shopWorld;
    private ShopRenderer shopRenderer;
    private float runTime = 0;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        shopWorld.update(delta);
        shopRenderer.render();
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
