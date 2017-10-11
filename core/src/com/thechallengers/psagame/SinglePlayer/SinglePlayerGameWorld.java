package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.thechallengers.psagame.EndGame.EndGameWorld;
import com.thechallengers.psagame.SinglePlayer.Objects.Worker;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;
import static com.thechallengers.psagame.game.PSAGame.LONG_EDGE;
import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class SinglePlayerGameWorld implements ScreenWorld {
    final float TIMER_FONT_SIZE = 7;
    final float PERCENTAGE_THRESHOLD = 0.7f;
    private Body crane;
    float CRANE_SPEED = 8f;
    private Stage stage;
    private World world;
    public Array<Body> bodyArray = new Array<Body>();
    private Worker worker;
    private float gameTime;
    private float worldTime;
    public boolean hasStarted = false;

    private Label countdownLabel;

    //touchpad-related variables
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin skin; //store the skin for user interface: touchpad and release button
    private Drawable touchBackground, touchKnob;

    //release button-related variables
    private TextButton releaseButton;
    private TextButton.TextButtonStyle releaseStyle;
    private TextButton destroyButton;
    private TextButton.TextButtonStyle destroyStyle;
    private Drawable releaseBG;
    private Drawable destroyBG;

    //worker-related variables

    public Box2DWorld box2DWorld;


    public SinglePlayerGameWorld(int level) {
        worldTime = 300;
        gameTime = 0;
        worker = new Worker();
        stage = new Stage();
        //stage.addActor(touchpad);
        // stage.addActor(releaseButton);
        //stage.addActor(worker);

        // Gdx.input.setInputProcessor(stage);

        box2DWorld = new Box2DWorld(level);
        this.world = box2DWorld.getWorld();
    }

    //create everything considering user interface

    public void addListenerToReleaseButton() {
        releaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Release button selected");
            }
        });
    }

    public void addListenerToDestroyButton() {
        destroyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Destroy button selected");
                if (box2DWorld.cooldown <= 0) {
                    box2DWorld.destroyMode = true;
                }
            }
        });
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        box2DWorld.update(delta);
        world.getBodies(bodyArray);
        if (box2DWorld.cooldown > 0) box2DWorld.cooldown -= delta;
        if (hasStarted) {
            gameTime += delta;
            worldTime -= delta;
        }

        if (box2DWorld.getPercentageOverlap() > PERCENTAGE_THRESHOLD) {
            AssetLoader.winningBG = ScreenUtils.getFrameBufferTexture();
            EndGameWorld.star = 3;
            CURRENT_SCREEN = PSAGame.Screen.EndGameScreen;
        }

        if (worldTime < 0) {
            AssetLoader.losingBG = ScreenUtils.getFrameBufferTexture();
            EndGameWorld.star = 0;
            CURRENT_SCREEN = PSAGame.Screen.EndGameScreen;
        }

        float xGrav = Gdx.input.getAccelerometerX() / 9.81f;
        float yGrav = Gdx.input.getAccelerometerY() / 9.81f;
        float zGrav = Gdx.input.getAccelerometerZ() / 9.81f;

        // gForce will be close to 1 when there is no movement.
        float gForce = (float)Math.sqrt((xGrav * xGrav) + (yGrav * yGrav) + (zGrav * zGrav));

        if (gForce > 2 && box2DWorld.cooldown <= 0) {
            box2DWorld.destroyMode = true;
        }

        System.out.println(box2DWorld.getCrane().getPosition().y);
    }

    public Stage getStage() {
        return stage;
    }

    public World getWorld() {
        return world;
    }

    public Worker getWorker() { return worker;}

    public float getWorldTime() {
        return worldTime;
    }
}
