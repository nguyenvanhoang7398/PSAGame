package com.thechallengers.psagame.Menu;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.thechallengers.psagame.Menu.Objects.Background;
import com.thechallengers.psagame.Menu.Objects.Cloud;
import com.thechallengers.psagame.Menu.Objects.Containers;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.Menu.Objects.MenuCrane;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

import sun.rmi.runtime.Log;

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
    private TextButton.TextButtonStyle play_button_style, tutorial_button_style, single_player_button_style, multi_player_button_style,
            overlay_button_style, setting_button_style, setting_overlay_style, setting_box_style;
    private Slider.SliderStyle music_slider_style, sfx_slider_style;
    private Slider music_slider, sfx_slider;
    private MenuCrane menu_crane;
    private Background background;
    private Containers containers;
    private Stage stage;
    private int zoomTime = 0;
    private boolean isZooming = false;
    private Array<Cloud> cloudArray;

    private boolean isInTransition = false;

    //for shop
    private TextButton shop_button;
    private TextButton.TextButtonStyle shop_button_style;

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

        //shop button
        createShopButton();

        stage.addActor(background);
        //stage.addActor(menu_crane);
        stage.addActor(play_button);
        stage.addActor(tutorial_button);
        stage.addActor(setting_button);
        stage.addActor(shop_button);

        //clouds and containers
        cloudArray = new Array<Cloud>();
        createClouds();
        createContainers();
        //lookAt();

    }

    @Override
    public void update(float delta) {
        stage.act(delta);

        if (zoomTime == 57) {
            CURRENT_SCREEN = PSAGame.Screen.SinglePlayerGameScreen;
        }

        if (isZooming && zoomTime < 57) {
            translate();
            zoomIn();
            zoomTime ++;
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void createClouds() {
        for (int i = 0; i < 5; i++) {
            Cloud cloud = new Cloud();
            stage.addActor(cloud);
            cloudArray.add(cloud);
        }
    }

    public void createContainers() {
        containers = new Containers();
        stage.addActor(containers);
    }

    //SHOP BUTTON
    public void createShopButtonStyle() {
        shop_button_style = new TextButton.TextButtonStyle();
        shop_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.shop_button));
        shop_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.shop_button));
        shop_button_style.font = AssetLoader.arial;
    }

    public void createShopButton() {
        createShopButtonStyle();

        shop_button = new TextButton("", shop_button_style);
        shop_button.setPosition(700, 1700);
        addListenerToShopButton();
    }

    public void addListenerToShopButton() {
        shop_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shop_button.addAction(sequence(fadeOut(0.6f), fadeIn(0.6f)));

            }
        });
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

        //addListenerToPlayButton();
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
                containers.addAction(fadeOut(0.6f));

                for (int i = 0; i < cloudArray.size; i++) {
                    cloudArray.get(i).clearActions();
                    cloudArray.get(i).addAction(fadeOut(0.6f));
                }

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
                createSFXSlider();
                createMusicSlider();

                stage.addActor(setting_overlay);
                stage.addActor(setting_box);
                stage.addActor(music_slider);
                stage.addActor(sfx_slider);
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

    //MUSIC SLIDER
    public void createMusicSlider() {
        music_slider_style = new Slider.SliderStyle(new TextureRegionDrawable(new TextureRegion(AssetLoader.slider_bg)),
                                                    new TextureRegionDrawable(new TextureRegion(AssetLoader.slider_knob)));
        music_slider = new Slider(0f, 1f, 0.01f, false, music_slider_style);
        music_slider.setHeight(88);
        music_slider.setWidth(588);
        music_slider.setPosition(165 + 78, 1177);
        music_slider.setValue(Gdx.app.getPreferences("prefs").getFloat("music volume"));
        music_slider.getColor().a = 0;
        music_slider.addAction(fadeIn(0.1f));

        music_slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.getPreferences("prefs").putFloat("music volume", music_slider.getValue()).flush();
            }
        });
    }

    public void createSFXSlider() {
        sfx_slider_style = new Slider.SliderStyle(new TextureRegionDrawable(new TextureRegion(AssetLoader.slider_bg)),
                           new TextureRegionDrawable(new TextureRegion(AssetLoader.slider_knob)));
        sfx_slider = new Slider(0f, 1f, 0.01f, false, sfx_slider_style);
        sfx_slider.setHeight(88);
        sfx_slider.setWidth(588);
        sfx_slider.setPosition(165 + 78, 983);
        sfx_slider.setValue(Gdx.app.getPreferences("prefs").getFloat("sfx volume"));
        sfx_slider.getColor().a = 0;
        sfx_slider.addAction(fadeIn(0.1f));

        sfx_slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.getPreferences("prefs").putFloat("sfx volume", sfx_slider.getValue()).flush();
            }
        });
    }

    //
    public void removeSettingOptions() {
        setting_box.addAction(Actions.sequence(fadeOut(0.1f), removeActor()));
        setting_overlay.addAction(Actions.sequence(fadeOut(0.1f), removeActor()));
        music_slider.addAction(Actions.sequence(fadeOut(0.1f), removeActor()));
        sfx_slider.addAction(Actions.sequence(fadeOut(0.1f), removeActor()));
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
        stage.getCamera().translate(-2.75f, +2f, 0);
    }
}
