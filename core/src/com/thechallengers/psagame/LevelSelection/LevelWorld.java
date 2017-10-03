package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.Menu.MenuScreen;
import com.thechallengers.psagame.SinglePlayer.SinglePlayerGameScreen;
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
    private LevelTitle title;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;
    private TextButton.TextButtonStyle level1_style;
    private TextButton level1_button;
    private StarLevel level1_star;

    //constructor
    public LevelWorld (PSAGame game) {
        this.game = game;
        stage = new Stage();
        background = new LevelSelectionBackground();
        title = new LevelTitle();
        createBackButton();
        createLevel1Button();
        stage.addActor(background);
        stage.addActor(title);
        stage.addActor(back_button);
        stage.addActor(level1_button);
        stage.addActor(level1_star);

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

    public void createLevel1Button() {
        level1_style = new TextButton.TextButtonStyle();
        level1_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.level1_Button));
        level1_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.level1_Button));
        level1_style.font = AssetLoader.arial;

        level1_button = new TextButton("", level1_style);
        level1_button.setPosition(100, 1000);
        level1_star = new StarLevel(100, 1000 - 100, 1);
        addListenerToLevel1Button();

    }

    public void addListenerToLevel1Button() {
        level1_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                level1_button.addAction(sequence(fadeOut(0.6f), fadeIn(0.6f)));
                game.setScreen(new SinglePlayerGameScreen(game));
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
