package com.thechallengers.psagame.EndGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.EndGame.Objects.ActorWithTexture;
import com.thechallengers.psagame.Leaderboard.JSONParser;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_LEVEL;
import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_TIME;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;
import static com.thechallengers.psagame.game.PSAGame.playSound;

/**
 * Created by Phung Tuan Hoang on 10/4/2017.
 */

public class EndGameWorld implements ScreenWorld, Input.TextInputListener {

    private final String REST_API_URL = "https://psagame.herokuapp.com/leaderboard";

    private TextButton mainMenu;
    private TextButton nextLevel;
    private Stage stage;
    private ActorWithTexture winningBG, background;
    private ActorWithTexture actual;
    private ActorWithTexture tips;
    private ActorWithTexture starBackground;
    private Array<ActorWithTexture> stars = new Array<ActorWithTexture>();
    private EndGameWorld self = this;
    private int star;
    private float time;
    private int level;


    public EndGameWorld(int star, float time, int level, boolean newBestTime) {
        this.star = star;
        this.time = time;
        this.level = level;

        stage = new Stage();

        winningBG = new ActorWithTexture(AssetLoader.winningBG, 0, 0);
        winningBG.addAction(fadeOut(3f));
        winningBG.getColor().a = 1;
        stage.addActor(winningBG);
//
        actual = new ActorWithTexture(AssetLoader.actualArrayList.get(END_SCREEN_LEVEL - 1), 0, 0);
        actual.addAction(sequence(fadeIn(3f), delay(2f)));
        actual.getColor().a = 0;
        stage.addActor(actual);

        background = new ActorWithTexture(AssetLoader.endGameBackground, 0, 0);
        background.addAction(sequence(delay(5f), fadeIn(0.5f), run(new Runnable() {
            @Override
            public void run() {
                showTheRest();
            }
        })));
        background.getColor().a = 0;
        stage.addActor(background);

        if (newBestTime) {
            Gdx.input.getTextInput(self, "Congrats on your new best time!", "", "Enter your name");
        }
    }

    public void showTheRest() {
        //get time in String
        float time = END_SCREEN_TIME;
        int minute = (int) (time / 60f);
        int seconds = (int) (time - minute * 60);
        String secondsString;
        if (seconds < 10) secondsString = "0" + String.valueOf(seconds);
        else secondsString = String.valueOf(seconds);
        String timeString = "Time: " + String.valueOf(minute) + ":" +secondsString;

        //time
        TextButton.TextButtonStyle timeStyle = new TextButton.TextButtonStyle();
        timeStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.time_end_screen));
        timeStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.time_end_screen));
        timeStyle.font = AssetLoader.consolas_60;

        TextButton timeIndicator = new TextButton(timeString, timeStyle);
        timeIndicator.setPosition(390, 1400);
        timeIndicator.addAction(fadeIn(0.5f));
        timeIndicator.getColor().a = 0;
        stage.addActor(timeIndicator);

        //stars
        for (int i = 0; i < star; i++) {
            stars.add(new ActorWithTexture(AssetLoader.star.get(i), 0, 0));
            stars.get(i).addAction(sequence(delay(0.5f + i * 0.5f), run(new Runnable() {
                @Override
                public void run() {
                    PSAGame.playSound("star_appearance.mp3");
                }
            }),fadeIn(0.1f)));
            stage.addActor(stars.get(i));
        }

        //TIPS
        tips = new ActorWithTexture(AssetLoader.tips[END_SCREEN_LEVEL - 1], 0, 0);
        tips.addAction(sequence(delay(2.5f - (3 - star) * 0.5f), fadeIn(0.3f)));
        stage.addActor(tips);

        //NEXT LEVEL
        TextButton.TextButtonStyle nextLevelStyle = new TextButton.TextButtonStyle();
        nextLevelStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.next_button));
        nextLevelStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.next_button_down));
        nextLevelStyle.font = AssetLoader.arial;

        TextButton nextLevel = new TextButton("", nextLevelStyle);
        nextLevel.setPosition(750, 50);
        nextLevel.addAction(sequence(delay(3.5f - (3 - star) * 0.5f), fadeIn(0.3f)));
        nextLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSound("click.wav");
                PSAGame.LEVEL++;
                if (PSAGame.LEVEL <= PSAGame.AVAILABLE_LEVEL) {
                    CURRENT_SCREEN = PSAGame.Screen.SinglePlayerGameScreen;
                } else {
                    CURRENT_SCREEN = PSAGame.Screen.LevelSelectionScreen;
                }
            }
        });
        nextLevel.getColor().a = 0;
        stage.addActor(nextLevel);

        //Level Selection
        TextButton.TextButtonStyle levelSelectionStyle = new TextButton.TextButtonStyle();
        levelSelectionStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.level_selection));
        levelSelectionStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.level_selection_down));
        levelSelectionStyle.font = AssetLoader.arial;

        TextButton levelSelection = new TextButton("", levelSelectionStyle);
        levelSelection.setPosition(430, 50);
        levelSelection.addAction(sequence(delay(3.5f - (3 - star) * 0.5f), fadeIn(0.3f)));
        levelSelection.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSound("click.wav");
                CURRENT_SCREEN = PSAGame.Screen.LevelSelectionScreen;
            }
        });
        levelSelection.getColor().a = 0;
        stage.addActor(levelSelection);


        //MAIN MENU
        TextButton.TextButtonStyle mainMenuStyle = new TextButton.TextButtonStyle();
        mainMenuStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.menu_button));
        mainMenuStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.menu_button_down));
        mainMenuStyle.font = AssetLoader.arial;

        TextButton mainMenu = new TextButton("", mainMenuStyle);
        mainMenu.setPosition(120, 50);
        mainMenu.addAction(sequence(delay(3.5f - (3 - star) * 0.5f, fadeIn(0.3f))));
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSound("click.wav");
                CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
            }
        });
        mainMenu.getColor().a = 0;
        stage.addActor(mainMenu);



        //REPLAY BUTTOn
        TextButton.TextButtonStyle replayStyle = new TextButton.TextButtonStyle();
        replayStyle.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.replay_button));
        replayStyle.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.replay_button_down));
        replayStyle.font = AssetLoader.arial;

        TextButton replay = new TextButton("", replayStyle);
        replay.setPosition(430, 320);
        replay.addAction(sequence(delay(3.5f - (3 - star) * 0.5f), fadeIn(0.3f)));
        replay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSound("click.wav");
                CURRENT_SCREEN = PSAGame.Screen.SinglePlayerGameScreen;
            }
        });
        replay.getColor().a = 0;
        stage.addActor(replay);
    }

    public void sendHighscoreToDatabase(String name, String score, String level) {

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("name", name);
        parameters.put("score", score);
        parameters.put("level", level);
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setHeader("Content-Type", "application/json");
        request.setContent(JSONParser.toJSON(parameters));
        request.setUrl(REST_API_URL);
        System.out.println(request.getContent());
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("Status code ", "" + httpResponse.getStatus().getStatusCode());
                Gdx.app.log("Result ", httpResponse.getResultAsString());
            }
            @Override
            public void failed(Throwable t) {
                Gdx.app.log("Failed ", t.getMessage());
            }
            @Override
            public void cancelled() {
                Gdx.app.log("Cancelled ", "User cancelled");
            }
        });
    }

    @Override
    public void update(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
    }

    public Stage getStage() {

        return stage;
    }

    @Override
    public void input(String text) {
        sendHighscoreToDatabase(text, EndGameScreen.getTimeString(time), Integer.toString(level));
    }

    @Override
    public void canceled() {

    }
}
