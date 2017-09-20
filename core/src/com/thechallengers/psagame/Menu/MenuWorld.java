package com.thechallengers.psagame.Menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.Menu.Objects.Background;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.Menu.Objects.MenuCrane;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
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

        //exit button
        exit_button = new TextButton("Exit", menu_button_style);
        exit_button.setPosition(580, 400);
        exit_button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isInTransition = true;
                menu_crane.setLowering();
                next_activity = new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                };
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //SP button
        sp_button = new TextButton("SP", menu_button_style);
        sp_button.setPosition(300, 800);
        sp_button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isInTransition = true;
                menu_crane.setLowering();

                /*
                for (int i = 0; i < stage.getActors().size; i++) {
                    if (stage.getActors().get(i) instanceof Background) {System.out.println("AAAAAAAAAAA");}
                    else stage.getActors().get(i).addAction(fadeOut(2f));
                }
                */

                background.moveToGameScreen();

                stage.addAction(sequence(delay(2.5f),run(new Runnable() {
                    @Override
                    public void run() {
                        CURRENT_SCREEN = PSAGame.Screen.DummyGameScreen;
                    }
                })));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //MP button
        mp_button = new TextButton("MP", menu_button_style);
        mp_button.setPosition(580, 800);
        mp_button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //Setting button
        setting_button = new TextButton("", setting_button_style);
        setting_button.setPosition(SHORT_EDGE - 240, LONG_EDGE - 240);
        setting_button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //Tutorial button
        tutorial_button = new TextButton("Tutorial", menu_button_style);
        tutorial_button.setPosition(300, 400);
        tutorial_button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //Leaderboard Button
        leaderboard_button = new TextButton("LBD", menu_button_style);
        leaderboard_button.setPosition(580, 600);
        leaderboard_button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //shop button
        shopp_button = new TextButton("Sho", menu_button_style);
        shopp_button.setPosition(300, 600);
        shopp_button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //Menu Crane
        menu_crane = new MenuCrane(440, LONG_EDGE, 200, 920, exit_button, sp_button, mp_button, leaderboard_button, tutorial_button, shopp_button);

        //Background
        background = new Background();
        //add all actors to stage
        stage = new Stage();
        stage.addActor(background);
        stage.addActor(menu_crane);
        stage.addActor(sp_button);
        stage.addActor(exit_button);
        stage.addActor(mp_button);
        stage.addActor(leaderboard_button);
        stage.addActor(setting_button);
        stage.addActor(shopp_button);
        stage.addActor(tutorial_button);

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
            setting_button.clearListeners();
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


}
