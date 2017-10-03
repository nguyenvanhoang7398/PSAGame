package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.Menu.MenuScreen;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by name on 3/10/2017.
 */

class LevelWorld implements ScreenWorld {
    private PSAGame game;
    private Stage stage;
    private LevelSelectionBackground background;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;

    //constructor
    public LevelWorld (PSAGame game) {
        this.game = game;
        stage = new Stage();
        background = new LevelSelectionBackground();
        createBackButton();
        stage.addActor(background);
        stage.addActor(back_button);

    }
    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    public void createBackButton() {
        back_button_style = new TextButton.TextButtonStyle();
        back_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.level_backButton));
        back_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.level_backButton));
        back_button_style.font = AssetLoader.arial;

        back_button = new TextButton("", back_button_style);
        back_button.setPosition(50, 1700);
        addListenerToBackButton();
    }

    public void addListenerToBackButton() {
        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back_button.addAction(sequence(fadeOut(0.6f), fadeIn(0.6f)));
                game.setScreen(new MenuScreen(game));
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
