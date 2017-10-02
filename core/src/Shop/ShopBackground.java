package Shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thechallengers.psagame.Menu.Objects.Background;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by name on 3/10/2017.
 */

public class ShopBackground extends Actor {
    private Texture background;


    public ShopBackground() {
        super();
        background = AssetLoader.shop_background;
    }

    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        // always make sure to only multiply by the parent alpha
        batch.getColor().a *= parentAlpha;

        // do your drawing
        batch.draw(background, 0, 0, 1080, 1920);

        batch.setColor(Color.WHITE); // reset the color
    }
}
