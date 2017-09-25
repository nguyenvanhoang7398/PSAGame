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
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;
import static com.thechallengers.psagame.game.PSAGame.LONG_EDGE;
import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

/**
 * Created by Phung Tuan Hoang on 9/6/2017.
 */

public class MenuWorld implements ScreenWorld {
    private TextButton tutorial_button, play_button, single_player_button, multi_player_button, overlay_button, setting_button, setting_overlay, setting_box;
    private TextButton.TextButtonStyle play_button_style, tutorial_button_style, single_player_button_style, multi_player_button_style, overlay_button_style, setting_button_style, setting_overlay_style, setting_box_style;
    private MenuCrane menu_crane;
    private Background background;
    private Containers containers;
    private Stage stage;
    private int zoomTime = 0;
    private boolean isZooming = false;

    private boolean isInTransition = false;
    private Runnable next_activity = null;

    //constructor
    public MenuWorld() {
        stage = new Stage();

        //play button
        createPlayButton();

        createSinglePlayerButton();
        createMultiPlayerButton();
        createOverlayButton();

        //tutorial button
        createTutorialButton();

        //create setting button
        createSettingButton();

        //Background
        background = new Background();
        //add all actors to stage

        stage.addActor(background);
        //stage.addActor(menu_crane);
        stage.addActor(play_button);
        stage.addActor(tutorial_button);
        stage.addActor(setting_button);

        //clouds and containers
        createClouds();
        createContainers();
        //lookAt();

    }

    @Override
    public void update(float delta) {
        stage.act(delta);
            System.out.println(stage.getActors().size);

//        if (menu_crane.getY() > LONG_EDGE * 2 - 400) {
  //          menu_crane.stop();
    //    }


        if (isZooming && zoomTime < 57) {
            translate();
            zoomIn();
            zoomTime ++;
            System.out.println(zoomTime);
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
        containers = new Containers();
        stage.addActor(containers);
    }

    //PLAY BUTTON
    public void createPlayButtonStyle() {
        play_button_style = new TextButton.TextButtonStyle();
        play_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.play_button));
        play_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.play_button_pressed));
        play_button_style.font = AssetLoader.arial;
    }

    public void createPlayButton() {
        createPlayButtonStyle();

        play_button = new TextButton("", play_button_style);
        play_button.setPosition(180, 1920 - 1726);
        addListenerToPlayButton();

        addListenerToPlayButton();
    }

    public void addListenerToPlayButton() {
        play_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addActor(overlay_button);
                stage.addActor(single_player_button);
                stage.addActor(multi_player_button);
            }
        });
    }

    //TUTORIAL BUTTON
    public void createTutorialButtonStyle() {
        tutorial_button_style = new TextButton.TextButtonStyle();
        tutorial_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.tutorial_button));
        tutorial_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.tutorial_button_pressed));
        tutorial_button_style.font = AssetLoader.arial;
    }

    public void createTutorialButton() {
        createTutorialButtonStyle();

        tutorial_button = new TextButton("", tutorial_button_style);
        tutorial_button.setPosition(670, 1920 - 1726);
    }

    //SINGLE PLAYER BUTTON
    public void createSinglePlayerButtonStyle() {
        single_player_button_style = new TextButton.TextButtonStyle();
        single_player_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.single_player_button));
        single_player_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.single_player_button_pressed));
        single_player_button_style.font = AssetLoader.arial;
    }

    public void createSinglePlayerButton() {
        createSinglePlayerButtonStyle();

        single_player_button = new TextButton("", single_player_button_style);
        single_player_button.setPosition(180, 1920 - 1326);
        single_player_button.getColor().a = 0;
        single_player_button.addAction(fadeIn(0.1f));

        addListenerToSinglePlayerButton();
    }

    public void addListenerToSinglePlayerButton() {
        single_player_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removePlayOptions();

                //move to single player code here
                containers.addAction(fadeOut(1f));
                isZooming = true;
            }
        });
    }

    //MULTIPLAYER BUTTON
    public void createMultiPlayerButtonStyle() {
        multi_player_button_style = new TextButton.TextButtonStyle();
        multi_player_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.multi_player_button));
        multi_player_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.multi_player_button_pressed));
        multi_player_button_style.font = AssetLoader.arial;
    }

    public void createMultiPlayerButton() {
        createMultiPlayerButtonStyle();

        multi_player_button = new TextButton("", multi_player_button_style);
        multi_player_button.setPosition(670, 1920 - 1326);
        multi_player_button.getColor().a = 0;
        multi_player_button.addAction(fadeIn(0.1f));

        addListenerToMultiPlayerButton();
    }

    public void addListenerToMultiPlayerButton() {
        multi_player_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removePlayOptions();

                //move to multi player code here
            }
        });
    }

    //OVERLAY (BACKGROUND FOR SP AND MP)
    public void createOverlayButtonStyle() {
        overlay_button_style = new TextButton.TextButtonStyle();
        overlay_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.overlay_button));
        overlay_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.overlay_button_pressed));
        overlay_button_style.font = AssetLoader.arial;
    }

    public void createOverlayButton() {
        createOverlayButtonStyle();

        overlay_button = new TextButton("", overlay_button_style);
        overlay_button.setPosition(0, 0);
        overlay_button.getColor().a = 0;
        overlay_button.addAction(fadeIn(0.1f));

        addListenerToOverlayButton();
    }

    public void addListenerToOverlayButton() {
        overlay_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removePlayOptions();
            }
        });
    }

    //SETTING BUTTON
    public void createSettingButtonStyle() {
        setting_button_style = new TextButton.TextButtonStyle();
        setting_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_button));
        setting_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_button_pressed));
        setting_button_style.font = AssetLoader.arial;
    }

    public void createSettingButton() {
        createSettingButtonStyle();

        setting_button = new TextButton("", setting_button_style);
        setting_button.setPosition(1080 - 20 - 100, 1900 - 100);

        addListenerToSettingButton();
    }

    public void addListenerToSettingButton() {
        setting_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createSettingOverlay();
                createSettingBox();

                stage.addActor(setting_overlay);
                stage.addActor(setting_box);
            }
        });
    }

    //OVERLAY (FOR SETTING)
    public void createSettingOverlay() {
        setting_overlay_style = new TextButton.TextButtonStyle();
        setting_overlay_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_overlay));
        setting_overlay_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_overlay));
        setting_overlay_style.font = AssetLoader.arial;

        setting_overlay = new TextButton("", setting_overlay_style);
        setting_overlay.setPosition(0, 0);

        setting_overlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeSettingOptions();
            }
        });
    }

    //SETTING BOX - BASICALLY BOX THAT CONTAINS SETTINGS OPTIONS
    public void createSettingBox() {
        setting_box_style = new TextButton.TextButtonStyle();
        setting_box_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_box));
        setting_box_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.setting_box));
        setting_box_style.font = AssetLoader.arial;

        setting_box = new TextButton("", setting_box_style);
        setting_box.setPosition(165, 510);
        setting_box.getColor().a = 0;
        setting_box.addAction(fadeIn(0.1f));
    }
    //
    public void removeSettingOptions() {
        setting_box.addAction(Actions.sequence(fadeOut(0.1f), removeActor()));;
        setting_overlay.addAction(Actions.sequence(fadeOut(0.1f), removeActor()));;
    }

    //
    public void removePlayOptions() {
        single_player_button.addAction(sequence(fadeOut(0.1f), removeActor(), fadeIn(0.1f)));
        multi_player_button.addAction(sequence(fadeOut(0.1f), removeActor(), fadeIn(0.1f)));
        overlay_button.addAction(sequence(fadeOut(0.1f), removeActor(), fadeIn(0.1f)));
    }

    public void zoomIn() {
        ((OrthographicCamera) stage.getCamera()).zoom -= 0.01f;
    }

    public void translate() {
        stage.getCamera().translate(-2.75f, 1.5f, 0);
    }
}
