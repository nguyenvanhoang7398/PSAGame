package Shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.Menu.Objects.Background;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class ShopWorld implements ScreenWorld {
    private Stage stage;
    private Table table;
    private ShopBackground background;
    private TextButton shop_backButton;
    private TextButton.TextButtonStyle shop_backButton_style;

    public ShopWorld() {
        createBackButton();
        stage = new Stage();
        background = new ShopBackground();
        stage.addActor(background);
        stage.addActor(shop_backButton);

        table = new Table();

        createItem1();
    }



    //BACK BUTTON TO MENU
    public void createBackButtonStyle() {
        shop_backButton_style = new TextButton.TextButtonStyle();
        shop_backButton_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.shop_backButton));
        shop_backButton_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.shop_backButton));
        shop_backButton_style.font = AssetLoader.arial;
    }

    public void createBackButton() {
        createBackButtonStyle();

        shop_backButton = new TextButton("", shop_backButton_style);
        shop_backButton.setPosition(0, 1700);
        addListenerToBackButton();
    }

    public void addListenerToBackButton() {
        shop_backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shop_backButton.addAction(sequence(fadeOut(0.6f), fadeIn(0.6f)));
                //CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
            }
        });
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
