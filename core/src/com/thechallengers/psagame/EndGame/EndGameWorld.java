package com.thechallengers.psagame.EndGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.EndGame.Objects.ActorWithTexture;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Phung Tuan Hoang on 10/4/2017.
 */

public class EndGameWorld implements ScreenWorld {
    public static float time;
    public static float level;
    public static int star;
    private TextButton mainMenu;
    private TextButton nextLevel;
    private Stage stage;
    private ActorWithTexture background;
    private ActorWithTexture actual;
    private ActorWithTexture tips;
    private ActorWithTexture starBackground;
    private Array<ActorWithTexture> stars = new Array<ActorWithTexture>();


    public EndGameWorld() {
        stage = new Stage();

        background = new ActorWithTexture(AssetLoader.winningBG, 0, 0);
        background.addAction(fadeIn(0.1f));
        background.addAction(fadeOut(2.9f));
        stage.addActor(background);
//
        actual = new ActorWithTexture(AssetLoader.actual_1, 0, 0);
        actual.addAction(sequence(fadeIn(3f), run(new Runnable() {
            @Override
            public void run() {
                showTheRest();
            }
        })));
        actual.getColor().a = 0;
        stage.addActor(actual);
    }

    public void showTheRest() {

        //STAR BACKGROUND
        starBackground = new ActorWithTexture(AssetLoader.star_background, 0, 0);
        starBackground.addAction(fadeIn(0.1f));
        stage.addActor(starBackground);

        //stars
        for (int i = 0; i < star; i++) {
            stars.add(new ActorWithTexture(AssetLoader.star.get(i), 0, 0));
            stars.get(i).addAction(sequence(delay(i * 0.5f), fadeIn(0.1f)));
            stage.addActor(stars.get(i));
        }

        //TIPS
        tips = new ActorWithTexture(AssetLoader.tips_1, 0, 0);
        tips.addAction(fadeIn(3f));
        stage.addActor(tips);

        //NEXT LEVEL
        TextButton.TextButtonStyle nextLevelStyle = new TextButton.TextButtonStyle();
        nextLevelStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.next_button));
        nextLevelStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.next_button));
        nextLevelStyle.font = AssetLoader.arial;

        TextButton nextLevel = new TextButton("", nextLevelStyle);
        nextLevel.setPosition(730, 1920 - 1726);
        nextLevel.addAction(fadeIn(8f));
        nextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PSAGame.LEVEL++;
                PSAGame.CURRENT_SCREEN = PSAGame.Screen.SinglePlayerGameScreen;
            }
        });

        stage.addActor(nextLevel);


        //MAIN MENU
        TextButton.TextButtonStyle mainMenuStyle = new TextButton.TextButtonStyle();
        mainMenuStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.menu_button));
        mainMenuStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.menu_button));
        mainMenuStyle.font = AssetLoader.arial;

        TextButton mainMenu = new TextButton("", mainMenuStyle);
        mainMenu.setPosition(150, 1920 - 1726);
        mainMenu.addAction(fadeIn(8f));
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PSAGame.CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
            }
        });
        stage.addActor(mainMenu);



        //REPLAY BUTTOn
        TextButton.TextButtonStyle replayStyle = new TextButton.TextButtonStyle();
        replayStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.replay_button));
        replayStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.replay_button));
        replayStyle.font = AssetLoader.arial;

        TextButton replay = new TextButton("", replayStyle);
        replay.setPosition(450, 1920 - 1726 + 390);
        replay.addAction(fadeIn(8f));
        replay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PSAGame.CURRENT_SCREEN = PSAGame.Screen.SinglePlayerGameScreen;
            }
        });
        stage.addActor(replay);
    }

    @Override
    public void update(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    public Stage getStage() {

        return stage;
    }
}
