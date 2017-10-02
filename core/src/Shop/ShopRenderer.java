package Shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class ShopRenderer extends ScreenRenderer {
    private ShopWorld shopWorld;

    public ShopRenderer(ShopWorld shopWorld) {
        super();
        this.shopWorld = shopWorld;
        this.stage = shopWorld.getStage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);

    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
}
