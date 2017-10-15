package com.thechallengers.psagame.Leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.internal.LinkedTreeMap;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

/**
 * Created by VanHoang on 10/12/2017.
 */

public class LeaderboardWorld implements ScreenWorld, Input.TextInputListener {

    private final String REST_API_URL = "https://psagame.herokuapp.com/leaderboard";
    private final int LevelNum = 5;
    private final float LEADERBOARD_OVERLAY_Y_OFFSET = 210f;
    private final float WIDTH = 1080f;
    private final float HEIGHT = 1920f;

    private HashMap<Integer, ArrayList<HighscoreEntry>> test_highscore_table;
    private Stage stage;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;
    private LeaderboardBackground leaderboard_background;
    private PSAGame game;
    private ImageButton add_highscore;
    private ImageButton.ImageButtonStyle add_highscore_style;
    private TextButton.TextButtonStyle highscore_text_style;
    private ImageButton no_button;
    private ImageButton.ImageButtonStyle no_button_style;
    private LeaderboardWorld self = this;
    private ArrayDeque<Actor> displaying_highscores;
    private ImageButton.ImageButtonStyle leaderboard_overlay_style;
    private ImageButton leaderboard_overlay;
    private TextButton[] level_button = new TextButton[LevelNum];
    private TextButton.TextButtonStyle[] level_style = new TextButton.TextButtonStyle[LevelNum];
    private Texture[] levelButton = {AssetLoader.level1_Button, AssetLoader.level2_Button,
            AssetLoader.level3_Button, AssetLoader.level4_Button,
            AssetLoader.level5_Button};
    private int[] x_coord = {200, 450, 700, 325, 575};
    private int[] y_coord = {1000, 1000, 1000, 600, 600};


    public LeaderboardWorld(PSAGame game) {
        this.game = game;
        stage = new Stage();
        leaderboard_background = new LeaderboardBackground();
        test_highscore_table = new HashMap<Integer, ArrayList<HighscoreEntry>>();
        displaying_highscores = new ArrayDeque<Actor>();
        initTestHighscoreTable();
        createBackButton();
        createAddHighscore();
        createLevelButton();

        stage.addActor(leaderboard_background);
        for(int i = 0; i < LevelNum; i ++) {
            stage.addActor(level_button[i]);
        }
        stage.addActor(back_button);
        // stage.addActor(add_highscore);
    }

    private void initTestHighscoreTable() {
        ArrayList<HighscoreEntry> level1_highscore = new ArrayList<HighscoreEntry>();
        level1_highscore.add(new HighscoreEntry("Hoang", 1, "3:03"));
        level1_highscore.add(new HighscoreEntry("Quang", 1, "2:54"));
        Collections.sort(level1_highscore);

        ArrayList<HighscoreEntry> level2_highscore = new ArrayList<HighscoreEntry>();
        level2_highscore.add(new HighscoreEntry("Quang", 2, "2:21"));
        level2_highscore.add(new HighscoreEntry("Hoang", 2, "2:42"));
        Collections.sort(level2_highscore);

        test_highscore_table.put(1, level1_highscore);
        test_highscore_table.put(2, level2_highscore);
    }

    public void update(float delta) { stage.act(delta);}

    public void createBackButton() {
        back_button_style = new TextButton.TextButtonStyle();
        back_button_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_backButton));
        back_button_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_backButton_down));
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

    public void createAddHighscore() {
        add_highscore_style = new ImageButton.ImageButtonStyle();
        add_highscore_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_addButton));
        add_highscore_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_addButton));
        add_highscore = new ImageButton(add_highscore_style);
        add_highscore.setPosition(850, 100);
        addListenerToAddHighscoreButton();
    }

    public void addListenerToAddHighscoreButton() {
        add_highscore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.getTextInput(self, "New highscore", "", "<name(String)> <score(float)> <level(integer)>");
            }
        });
    }

    public void createLeaderboardTable(int level) {
        System.out.println("Level " + level);
        final float MARGIN_Y = 75f;
        final float FONT_SCALE = 4.2f;
        final float OFFSET_X = 540f;
        final float MAX_COUNT = 10;

        float offset_y = 1100f;
        int i = 0;
        // ArrayList<HighscoreEntry> highscore_of_this_level = test_highscore_table.get(level);
        ArrayList<HighscoreEntry> highscore_of_this_level = new ArrayList<HighscoreEntry>();
        boolean result= queryHighscoreLevelFromDatabase(highscore_of_this_level, level);

        Collections.sort(highscore_of_this_level);
        highscore_text_style = new TextButton.TextButtonStyle();
        highscore_text_style.font = AssetLoader.consolas_60;
        highscore_text_style.fontColor = Color.YELLOW;
        //highscore_text_style.font.getData().setScale(FONT_SCALE, FONT_SCALE);

        if (!result) {
            TextButton error_line1 = new TextButton("", highscore_text_style);
            error_line1.setText("Cannot");
            error_line1.setPosition(OFFSET_X, offset_y);
            displaying_highscores.addLast(error_line1);

            TextButton error_line2 = new TextButton("", highscore_text_style);
            error_line2.setText("receive leaderboard");
            error_line2.setPosition(OFFSET_X, offset_y - MARGIN_Y);
            displaying_highscores.addLast(error_line2);

            TextButton error_line3 = new TextButton("", highscore_text_style);
            error_line3.setText("Please check");
            error_line3.setPosition(OFFSET_X, offset_y - MARGIN_Y*2);
            displaying_highscores.addLast(error_line3);

            TextButton error_line4 = new TextButton("", highscore_text_style);
            error_line4.setText("your connection");
            error_line4.setPosition(OFFSET_X, offset_y - MARGIN_Y*3);
            displaying_highscores.addLast(error_line4);

            stage.addActor(error_line1);
            stage.addActor(error_line2);
            stage.addActor(error_line3);
            stage.addActor(error_line4);
        }

        for (HighscoreEntry highscoreEntry : highscore_of_this_level) {
            TextButton highscore_text = new TextButton("", highscore_text_style);
            highscore_text.setText(highscoreEntry.getUserName() + " " + highscoreEntry.getScore());
            highscore_text.setPosition(OFFSET_X, offset_y);
            offset_y -= MARGIN_Y;
            displaying_highscores.addLast(highscore_text);
            stage.addActor(highscore_text);
            i++;
            if (i >= MAX_COUNT) {
                break;
            }
        }
    }

    public boolean queryHighscoreLevelFromDatabase(final ArrayList<HighscoreEntry> high_scores, int level) {
        final String REST_API_URL_LEVEL = REST_API_URL + "/" + Integer.toString(level);
        final ArrayList<Boolean> finished = new ArrayList<Boolean>();

        Map<String, String> parameters = new HashMap<String, String>();
        Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGet.setUrl(REST_API_URL_LEVEL);
        httpGet.setContent(HttpParametersUtils.convertHttpParameters(parameters));

        try {
            Gdx.net.sendHttpRequest(httpGet, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    String result = httpResponse.getResultAsString();
                    HashMap<String, Object> parsed_object = JSONParser.parse(result);
                    ArrayList<Object> high_score_list = new ArrayList<Object>((List) parsed_object.get("result"));

                    for (Object high_score : high_score_list) {
                        LinkedTreeMap<String, String> high_score_hm = (LinkedTreeMap<String, String>) high_score;
                        String name = high_score_hm.get("name");
                        int level = Integer.parseInt(high_score_hm.get("level"));
                        String score = high_score_hm.get("score");
                        high_scores.add(new HighscoreEntry(name, level, score));
                    }

                    finished.add(true);
                }

                @Override
                public void failed(Throwable t) {
                    finished.add(false);
                }

                @Override
                public void cancelled() {
                }
            });
        } catch (Exception e) {
            return false;
        }
        try {
            Thread.sleep(2000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (finished.isEmpty()) {
            return false;
        }
        return finished.get(0);
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
        try {
            Thread.sleep(3000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void createLevelButton() {
        for(int i = 0; i < LevelNum; i ++) {
            level_style[i] = new TextButton.TextButtonStyle();
            level_style[i].up = new TextureRegionDrawable(new TextureRegion(levelButton[i]));
            level_style[i].down = new TextureRegionDrawable(new TextureRegion(levelButton[i]));
            level_style[i].font = AssetLoader.arial;

            level_button[i] = new TextButton("", level_style[i]);
            level_button[i].setPosition(x_coord[i], y_coord[i]);
            addListenerToLevelButton(i+1);
        }
    }

    public void addListenerToLevelButton(final int i) {
        level_button[i-1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createLeaderboardOverlay();
                createLeaderboardTable(i);
            }
        });
    }

    public void createLeaderboardOverlayStyle() {
        leaderboard_overlay_style = new ImageButton.ImageButtonStyle();
        leaderboard_overlay_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_overlay));
        leaderboard_overlay_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.leaderboard_overlay));
    }

    public void createLeaderboardOverlay() {
        createLeaderboardOverlayStyle();
        leaderboard_overlay = new ImageButton(leaderboard_overlay_style);
        leaderboard_overlay.setPosition(WIDTH/2-leaderboard_overlay.getWidth()/2, LEADERBOARD_OVERLAY_Y_OFFSET);
        leaderboard_overlay.addAction(fadeIn(0.1f));
        stage.addActor(leaderboard_overlay);
        addListenerToLeaderboardOverlay();
    }

    public void addListenerToLeaderboardOverlay() {
        leaderboard_overlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                while (displaying_highscores.size() > 0) {
                    Actor actor = displaying_highscores.poll();
                    actor.remove();
                }
                leaderboard_overlay.remove();
            }
        });
    }

    public Stage getStage() {return stage;}

    @Override
    public void input(String text) {
        String[] tokens = text.split(" ");
        sendHighscoreToDatabase(tokens[0], tokens[1], tokens[2]);
    }

    @Override
    public void canceled() {

    }
}
