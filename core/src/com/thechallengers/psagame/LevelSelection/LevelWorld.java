package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
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
import com.thechallengers.psagame.helpers.SoundLoader;

import java.util.logging.Level;

import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.LEVEL;

/**
 * Created by name on 3/10/2017.
 */

class LevelWorld implements ScreenWorld {
    private Stage stage;
    private LevelSelectionBackground background;
    private LevelTitle title;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;
    private final int LevelNum = 5;
    private TextButton.TextButtonStyle[] level_style = new TextButton.TextButtonStyle[LevelNum];
    private TextButton[] level_button = new TextButton[LevelNum];
    private ImageButton[] level_star = new ImageButton[LevelNum];
    private ImageButton.ImageButtonStyle[] level_star_style = new ImageButton.ImageButtonStyle[LevelNum];
    private Texture[] levelButton = {AssetLoader.level1_Button, AssetLoader.level2_Button,
                                    AssetLoader.level3_Button, AssetLoader.level4_Button,
                                    AssetLoader.level5_Button};
    private Texture[] levelButton_down = AssetLoader.levelButton_down;
    private int[] x_coord = {225, 475, 725, 350, 600};
    private int[] y_coord = {1000, 1000, 1000, 600, 600};
    private int playerLevel = Gdx.app.getPreferences("prefs").getInteger("level");

    //constructor
    public LevelWorld () {
        stage = new Stage();
        background = new LevelSelectionBackground();
        title = new LevelTitle();
        createBackButton();
        createLevelButton();
        stage.addActor(background);
        stage.addActor(title);
        stage.addActor(back_button);
        for(int i = 0; i < LevelNum; i ++) {
            stage.addActor(level_button[i]);
            stage.addActor(level_star[i]);
        }
    }
    @Override
    public void update(float delta) {
        stage.act(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
        }
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

    public void createLevelButton() {
        for(int i = 0; i < LevelNum; i ++) {
            level_style[i] = new TextButton.TextButtonStyle();
            level_style[i].up = new TextureRegionDrawable(new TextureRegion(levelButton[i]));
            level_style[i].down = new TextureRegionDrawable(new TextureRegion(levelButton_down[i]));
            level_style[i].font = AssetLoader.arial;

            level_button[i] = new TextButton("", level_style[i]);
            level_button[i].setPosition(x_coord[i], y_coord[i]);
            level_star_style[i] = new ImageButton.ImageButtonStyle();
            int star_Level = Gdx.app.getPreferences("prefs").getInteger("level" + Integer.toString(i+1)+"_starLevel");
            Drawable style;
            switch (star_Level) {
                case 0:
                    style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star0));
                    break;
                case 1:
                    style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star1));
                    break;
                case 2:
                    style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star2));
                    break;
                case 3:
                    style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star3));
                    break;
                default:
                    style = new TextureRegionDrawable(new TextureRegion(AssetLoader.star0));
                    break;
            }
            level_star_style[i].imageUp = style;
            level_star_style[i].imageDown = style;
            level_star[i] = new ImageButton(level_star_style[i]);
            level_star[i].setPosition(x_coord[i] - 20, y_coord[i] - 110);
            if((i + 1) <= playerLevel) {
                addListenerToLevelButton(i);
                addListenerToLevelStar(i);
            } else {
                level_button[i].getColor().a = 0.5f;
            }
        }
    }

    private void addListenerToLevelStar(final int i) {
        level_star[i].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int star_Level = Gdx.app.getPreferences("prefs").getInteger("level" + Integer.toString(i+1)+"_starLevel");
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
                ImageButton.ImageButtonStyle star_style = level_star[i].getStyle();
                star_style.imageUp = style;
                star_style.imageDown = style;
                Gdx.app.getPreferences("prefs").putInteger("level" + Integer.toString(i+1)+"_starLevel", star_Level).flush();

            }
        });
    }
//
    public void addListenerToLevelButton(final int i) {
        level_button[i].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                level_button[i].addAction(sequence(fadeOut(0.6f), fadeIn(0.6f)));
                CURRENT_SCREEN = PSAGame.Screen.SinglePlayerGameScreen;
                LEVEL = i + 1;
                SoundLoader.soundHashtable.get("click.wav").play();
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
