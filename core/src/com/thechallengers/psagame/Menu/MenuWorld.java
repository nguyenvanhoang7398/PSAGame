package com.thechallengers.psagame.Menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private TextButton exit_button, sp_button, mp_button, setting_button, tutorial_button, shopp_button, leaderboard_button, play_button;
    private TextButton.TextButtonStyle play_button_style, tutorial_button_style;
    private MenuCrane menu_crane;
    private Background background;
    private Stage stage;
    private int zoomTime = 0;

    private boolean isInTransition = false;
    private Runnable next_activity = null;

    //constructor
    public MenuWorld() {
        stage = new Stage();

        //play button
        createPlayButtonStyle();
        createPlayButton();
        addListenerToPlayButton();

        //tutorial button
        createTutorialButtonStyle();
        createTutorialButton();

        //Menu Crane
        menu_crane = new MenuCrane(440, LONG_EDGE, 200, 920, exit_button, sp_button, mp_button, leaderboard_button, tutorial_button, shopp_button);

        //Background
        background = new Background();
        //add all actors to stage

        stage.addActor(background);
        stage.addActor(menu_crane);
        stage.addActor(play_button);
        stage.addActor(tutorial_button);

        //clouds and containers
        createClouds();
        createContainers();
        //lookAt();

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

        /*
        if (zoomTime < 57) {
            translate();
            zoomIn();
            zoomTime ++;
            System.out.println(zoomTime);
        }
        */

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

    public void createPlayButtonStyle() {
        play_button_style = new TextButton.TextButtonStyle();
        play_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.play_button));
        play_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.play_button_pressed));
        play_button_style.font = AssetLoader.arial;
        //play_button.setPosition();
    }

    public void createPlayButton() {
        play_button = new TextButton("", play_button_style);
        play_button.setPosition(180, 1920 - 1726);
    }

    public void createTutorialButtonStyle() {
        tutorial_button_style = new TextButton.TextButtonStyle();
        tutorial_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.tutorial_button));
        tutorial_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.tutorial_button_pressed));
        tutorial_button_style.font = AssetLoader.arial;
    }

    public void createTutorialButton() {
        tutorial_button = new TextButton("", tutorial_button_style);
        tutorial_button.setPosition(670, 1920 - 1726);
    }

    public void addListenerToPlayButton() {
        play_button.addListener(new ClickListener() {
           @Override
            public void clicked(InputEvent event, float x, float y) {
               System.out.println("A");
           }
        });
    }

    public void zoomIn() {
        ((OrthographicCamera) stage.getCamera()).zoom -= 0.01f;
    }

    public void translate() {
        stage.getCamera().translate(-2.75f, 1.5f, 0);
    }

    public void lookAt() {
        stage.getCamera().lookAt(540, 960, 0);
    }
}
