package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.thechallengers.psagame.EndGame.EndGameScreen;
import com.thechallengers.psagame.EndGame.EndGameWorld;
import com.thechallengers.psagame.SinglePlayer.Objects.Worker;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.SinglePlayer.Physics.Frame;
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
    private Body crane;
    float CRANE_SPEED = 8f;
    private Stage stage;
    private World world;
    public Array<Body> bodyArray = new Array<Body>();
    private Worker worker;


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


    public SinglePlayerGameWorld() {
        createUI();
        worker = new Worker();
        stage = new Stage();
        //stage.addActor(touchpad);
        // stage.addActor(releaseButton);
        //stage.addActor(worker);
        stage.addActor(destroyButton);
        // Gdx.input.setInputProcessor(stage);

        box2DWorld = new Box2DWorld();
        this.world = box2DWorld.getWorld();


    }

    //create everthing considering user interface
    public void createUI() {
        //create the skin library for touchpad and release Button
        skin = new Skin();
        skin.add("destroyButton", new Texture("textures/dynamite.png"));

        destroyStyle = new TextButton.TextButtonStyle();
        destroyBG = skin.getDrawable("destroyButton");
        destroyStyle.up = destroyBG;
        destroyStyle.down = destroyBG;
        destroyStyle.font = AssetLoader.arial;
        destroyButton = new TextButton("", destroyStyle);
        destroyButton.setBounds(SHORT_EDGE-(30+200), LONG_EDGE-(15+200), 200, 200);
        addListenerToDestroyButton();
    }

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
        //updateCraneAction();
        stage.act(delta);
        box2DWorld.update(delta);
        world.getBodies(bodyArray);
        //worker.toSad();
        if (box2DWorld.getPercentageOverlap() > 0.95) {
            AssetLoader.winningBG = ScreenUtils.getFrameBufferTexture();
            EndGameWorld.star = 3;
            CURRENT_SCREEN = PSAGame.Screen.EndGameScreen;
        }
    }

    public Stage getStage() {
        return stage;
    }

    public World getWorld() {
        return world;
    }

    public Worker getWorker() { return worker;}
}
