package Shop;

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
    }

    public void render() {

    }
}
