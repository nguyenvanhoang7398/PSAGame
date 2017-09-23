package com.thechallengers.psagame.Menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.Menu.Objects.Background;
import com.thechallengers.psagame.Menu.Objects.Cloud;
import com.thechallengers.psagame.Menu.Objects.Containers;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.Menu.Objects.MenuCrane;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;
import static com.thechallengers.psagame.game.PSAGame.LONG_EDGE;
import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

/**
 * Created by Phung Tuan Hoang on 9/6/2017.
 */

public class MenuWorld implements ScreenWorld {
    private TextButton exit_button, sp_button, mp_button, setting_button, tutorial_button, shopp_button, leaderboard_button;
    private TextButton.TextButtonStyle menu_button_style, setting_button_style;
    private MenuCrane menu_crane;
    private Background background;
    private Stage stage;

    private boolean isInTransition = false;
    private Runnable next_activity = null;

    //constructor
    public MenuWorld() {
        //style for menu button
        menu_button_style = new TextButton.TextButtonStyle();
        menu_button_style.up = new TextureRegionDrawable(AssetLoader.button_up);
        menu_button_style.down = new TextureRegionDrawable(AssetLoader.button_down);
        menu_button_style.font = AssetLoader.arial;

        //style for setting button
        setting_button_style = new TextButton.TextButtonStyle();
        setting_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_button_texture, 0, 0, 240, 240));
        setting_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_button_texture, 240, 0, 240, 240));
        setting_button_style.font = AssetLoader.arial;

        //Menu Crane
        menu_crane = new MenuCrane(440, LONG_EDGE, 200, 920, exit_button, sp_button, mp_button, leaderboard_button, tutorial_button, shopp_button);

        //Background
        background = new Background();
        //add all actors to stage
        stage = new Stage();
        stage.addActor(background);
        stage.addActor(menu_crane);
        createClouds();
        createContainers();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);

        if (isInTransition) {
            exit_button.clearListeners();
            sp_button.clearListeners();
            mp_button.clearListeners();
            leaderboard_button.clearListeners();
            tutorial_button.clearListeners();
            shopp_button.clearListeners();
        }

        if (menu_crane.getY() > LONG_EDGE * 2 - 400) {
            menu_crane.stop();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setNext_activity(Runnable next_activity) {
        this.next_activity = next_activity;
    }

    public void createClouds() {
        for (int i = 0; i < 5; i++) {
            Cloud cloud = new Cloud();
            stage.addActor(cloud);
        }
    }

    public void createContainers() {
        stage.addActor(new Containers());
    }
}
