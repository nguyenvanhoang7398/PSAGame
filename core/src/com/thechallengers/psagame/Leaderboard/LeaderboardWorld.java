package com.thechallengers.psagame.Leaderboard;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

/**
 * Created by VanHoang on 10/12/2017.
 */

public class LeaderboardWorld implements ScreenWorld {
    private Stage stage;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;
    private LeaderboardBackground leaderboard_background;
    private PSAGame game;
    private ImageButton leaderboard_title;
    private ImageButton.ImageButtonStyle leaderboard_title_style;
    private ImageButton add_highscore;
    private ImageButton.ImageButtonStyle add_highscore_style;

    public LeaderboardWorld(PSAGame game) {
        this.game = game;
        stage = new Stage();
        leaderboard_background = new LeaderboardBackground();
        createBackButton();
        createTitle();
        createAddHighscore();

        stage.addActor(leaderboard_background);
        stage.addActor(back_button);
        stage.addActor(leaderboard_title);
        stage.addActor(add_highscore);
    }

    public void update(float delta) { stage.act(delta);}

    public void createBackButton() {
        back_button_style = new TextButton.TextButtonStyle();
        back_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_backButton));
        back_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_backButton));
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

    public void createTitle() {
        leaderboard_title_style = new ImageButton.ImageButtonStyle();
        leaderboard_title_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_title));
        leaderboard_title_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_title));
        leaderboard_title = new ImageButton(leaderboard_title_style);
        leaderboard_title.setPosition(100, 1400);
    }

    public void createAddHighscore() {
        add_highscore_style = new ImageButton.ImageButtonStyle();
        add_highscore_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_addButton));
        add_highscore_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_addButton));
        add_highscore = new ImageButton(add_highscore_style);
        add_highscore.setPosition(100, 950);
    }

    public Stage getStage() {return stage;}
}
