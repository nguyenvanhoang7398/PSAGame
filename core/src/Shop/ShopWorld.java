package Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class ShopWorld implements ScreenWorld {
    private Stage stage;
    private Table table;

    public ShopWorld() {
        stage = new Stage();

        table = new Table();

        createItem1();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    public void createItem1() {
    }

    public Stage getStage() {
        return stage;
    }
}
