package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.SinglePlayer.Objects.Worker;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.helpers.AssetLoader;

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
    private Drawable releaseBG;

    //worker-related variables

    public Physics physics;


    public SinglePlayerGameWorld() {
        createUI();
        worker = new com.thechallengers.psagame.SinglePlayer.Objects.Worker();
        stage = new Stage();
        //stage.addActor(touchpad);
        //stage.addActor(releaseButton);
        //stage.addActor(worker);

        //Gdx.input.setInputProcessor(stage);

        physics = new Physics();
        this.world = physics.getWorld();
    }

    /*
    public void updateCraneAction() {

        //control crane with touchpad
        crane.setLinearVelocity(CRANE_SPEED*touchpad.getKnobPercentX(), CRANE_SPEED*touchpad.getKnobPercentY());
        //release/grab containers

    }
    */

    //create everthing considering user interface
    public void createUI() {
        //create the skin library for touchpad and release Button
        skin = new Skin();
        skin.add("touchBackground", new Texture("textures/touchBackground.png"));
        skin.add("touchKnob", new Texture("textures/touchKnob.png"));
        skin.add("releaseButton", new Texture("textures/releaseButton.png"));

        //create the style (from the skin) for touchpad
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = skin.getDrawable("touchBackground");
        touchKnob = skin.getDrawable("touchKnob");
        //apply skin to style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //create touchpad with style
        touchpad = new Touchpad(10, touchpadStyle); //minimum radius to move knob, style
        touchpad.setBounds(SHORT_EDGE-(15+200),15, 200, 200);

        releaseStyle = new TextButton.TextButtonStyle();
        releaseBG = skin.getDrawable("releaseButton");
        releaseStyle.up = releaseBG;
        releaseStyle.down = releaseBG;
        releaseStyle.font = AssetLoader.arial;
        releaseButton = new TextButton("", releaseStyle);
        releaseButton.setBounds(SHORT_EDGE-(15+450),15, 200, 200);
        addListenerToReleaseButton();
    }

    public void addListenerToReleaseButton() {
        releaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
    }

    @Override
    public void update(float delta) {
        //updateCraneAction();
        stage.act(delta);
        physics.update(delta);
        world.getBodies(bodyArray);
        //worker.toSad();
    }

    public Stage getStage() {
        return stage;
    }

    public World getWorld() {
        return world;
    }

    public Worker getWorker() { return worker;}
}
