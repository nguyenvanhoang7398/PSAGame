package com.thechallengers.psagame.Leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

/**
 * Created by VanHoang on 10/12/2017.
 */

public class LeaderboardWorld implements ScreenWorld, Input.TextInputListener {

    private HashMap<Integer, ArrayList<HighscoreEntry>> test_highscore_table;
    private HashMap<Integer, ArrayList<HighscoreEntry>> highscore_table;
    private Stage stage;
    private TextButton.TextButtonStyle back_button_style;
    private TextButton back_button;
    private LeaderboardBackground leaderboard_background;
    private PSAGame game;
    private ImageButton leaderboard_title;
    private ImageButton.ImageButtonStyle leaderboard_title_style;
    private ImageButton add_highscore;
    private ImageButton.ImageButtonStyle add_highscore_style;
    private TextButton.TextButtonStyle highscore_text_style;
    private ImageButton no_button;
    private ImageButton.ImageButtonStyle no_button_style;
    private TextButton.TextButtonStyle level1_style;
    private TextButton level1_button;
    private TextButton.TextButtonStyle level2_style;
    private TextButton level2_button;
    private LeaderboardWorld self = this;
    private ArrayDeque<Actor> displaying_highscores;

    public LeaderboardWorld(PSAGame game) {
        this.game = game;
        stage = new Stage();
        leaderboard_background = new LeaderboardBackground();
        highscore_table = new HashMap<Integer, ArrayList<HighscoreEntry>>();
        test_highscore_table = new HashMap<Integer, ArrayList<HighscoreEntry>>();
        displaying_highscores = new ArrayDeque<Actor>();
        createBackButton();
        createTitle();
        createAddHighscore();
        createLevel1Button();
        createLevel2Button();
        initTestHighscoreTable();
        fetchHighscoreTable();

        stage.addActor(leaderboard_background);
        stage.addActor(back_button);
        stage.addActor(leaderboard_title);
        stage.addActor(add_highscore);
        stage.addActor(level1_button);
        stage.addActor(level2_button);
    }

    private void initTestHighscoreTable() {
        ArrayList<HighscoreEntry> level1_highscore = new ArrayList<HighscoreEntry>();
        level1_highscore.add(new HighscoreEntry("Hoang", 1, 100f));
        level1_highscore.add(new HighscoreEntry("Quang", 1, 90f));
        Collections.sort(level1_highscore);

        ArrayList<HighscoreEntry> level2_highscore = new ArrayList<HighscoreEntry>();
        level2_highscore.add(new HighscoreEntry("Quang", 2, 100f));
        level2_highscore.add(new HighscoreEntry("Hoang", 2, 90f));
        Collections.sort(level2_highscore);

        test_highscore_table.put(1, level1_highscore);
        test_highscore_table.put(2, level2_highscore);
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
        add_highscore.setPosition(850, 100);
        addListenerToAddHighscoreButton();
    }

    public void addListenerToAddHighscoreButton() {
        add_highscore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.getTextInput(self, "New highscore", "", "your highscore");
            }
        });
    }

    public void createLevel1Button() {
        level1_style = new TextButton.TextButtonStyle();
        level1_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.level1_Button));
        level1_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.level1_Button));
        level1_style.font = AssetLoader.arial;
        level1_button = new TextButton("", level1_style);
        level1_button.setPosition(100, 1000);
        addListenerToLevel1Button();
    }

    public void addListenerToLevel1Button() {
        level1_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createLeaderboardTable(1);
            }
        });
    }

    public void createLevel2Button() {
        level2_style = new TextButton.TextButtonStyle();
        level2_style.up = new TextureRegionDrawable(new TextureRegion(AssetLoader.level2_Button));
        level2_style.down = new TextureRegionDrawable(new TextureRegion(AssetLoader.level2_Button));
        level2_style.font = AssetLoader.arial;
        level2_button = new TextButton("", level2_style);
        level2_button.setPosition(450, 1000);
        addListenerToLevel2Button();
    }

    public void addListenerToLevel2Button() {
        level2_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createLeaderboardTable(2);
            }
        });
    }

    public void createLeaderboardTable(int level) {
        final float MARGIN_Y = 60f;
        final float FONT_SCALE = 3.5f;
        final float OFFSET_X = 540f;
        final float NO_BUTTON_OFFSET_Y = 100f;
        final float MAX_COUNT = 10;

        float offset_y = 850f;
        int i = 0;
        ArrayList<HighscoreEntry> highscore_of_this_level = highscore_table.get(level);
        Collections.sort(highscore_of_this_level);
        highscore_text_style = new TextButton.TextButtonStyle();
        highscore_text_style.font = AssetLoader.consolas_15;
        highscore_text_style.fontColor = Color.BLACK;
        highscore_text_style.font.getData().setScale(FONT_SCALE, FONT_SCALE);


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

        no_button_style = new ImageButton.ImageButtonStyle();
        no_button_style.imageUp = new TextureRegionDrawable(new TextureRegion(AssetLoader.no_button));
        no_button_style.imageDown = new TextureRegionDrawable(new TextureRegion(AssetLoader.no_button));
        no_button = new ImageButton(no_button_style);
        no_button.setPosition(OFFSET_X, NO_BUTTON_OFFSET_Y);

        stage.addActor(no_button);
        no_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                while (displaying_highscores.size() > 0) {
                    Actor actor = displaying_highscores.poll();
                    actor.remove();
                }
                no_button.remove();
            }
        });
    }

    public void fetchHighscoreTable() {
        highscore_table = test_highscore_table;
    }

    public Stage getStage() {return stage;}

    @Override
    public void input(String text) {
        String[] tokens = text.split(" ");
        String name = tokens[0];
        Float new_highscore = Float.parseFloat(tokens[1]);
        Integer level = Integer.parseInt(tokens[2]);
    }

    @Override
    public void canceled() {

    }
}
