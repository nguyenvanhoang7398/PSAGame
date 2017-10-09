package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.Menu.MenuScreen;
import com.thechallengers.psagame.SinglePlayer.SinglePlayerGameScreen;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by name on 3/10/2017.
 */

class LevelWorld implements ScreenWorld {
    private Stage stage;
    private LevelSelectionBackground background;
    private LevelTitle title;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;
    private TextButton.TextButtonStyle level1_style;
    private TextButton level1_button;
    private ImageButton level1_star;
    private ImageButton.ImageButtonStyle level1_star_style;

    //constructor
    public LevelWorld () {
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
                CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
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
        level1_star_style = new ImageButton.ImageButtonStyle();
        int star_Level = Gdx.app.getPreferences("prefs").getInteger("level1_starLevel");
        Drawable style;
        switch(star_Level) {
            case 0: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star0));
                break;
            case 1: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star1));
                break;
            case 2: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star2));
                break;
            case 3: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star3));
                break;
            default: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star0));
                break;
        }
        level1_star_style.imageUp = style;
        level1_star_style.imageDown = style;
        level1_star = new ImageButton(level1_star_style);
        level1_star.setPosition(100, 1000 - 100);
        addListenerToLevel1Button();
        addListenerToLevel1Star();
    }

    private void addListenerToLevel1Star() {
        level1_star.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int star_Level = Gdx.app.getPreferences("prefs").getInteger("level1_starLevel");
                star_Level ++;
                if (star_Level > 3) star_Level = 0;
                Drawable style;
                switch(star_Level) {
                    case 0: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star0));
                        break;
                    case 1: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star1));
                        break;
                    case 2: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star2));
                        break;
                    case 3: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star3));
                        break;
                    default: style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star0));
                        break;
                }
                ImageButton.ImageButtonStyle star_style = level1_star.getStyle();
                star_style.imageUp = style;
                star_style.imageDown = style;
                Gdx.app.getPreferences("prefs").putInteger("level1_starLevel", star_Level).flush();

            }
        });
    }
//
    public void addListenerToLevel1Button() {
        level1_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                level1_button.addAction(sequence(fadeOut(0.6f), fadeIn(0.6f)));
                CURRENT_SCREEN = PSAGame.Screen.SinglePlayerGameScreen;
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
