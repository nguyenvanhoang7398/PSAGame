package com.thechallengers.psagame.Dummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.Dummy.Objects.Crane;
import com.thechallengers.psagame.Dummy.Physics.Physics2;
import com.thechallengers.psagame.Dummy.Physics.PhysicsInputHandler;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class SinglePlayerGameWorld implements ScreenWorld {
    private Body crane;
    float CRANE_SPEED = 4f;
    private Stage stage;
    private World world;
    public Array<Body> bodyArray = new Array<Body>();
    Physics2 physics_engine;

    //touchpad-related variables
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin skin; //store the skin for user interface: touchpad and release button
    private Drawable touchBackground, touchKnob;

    public SinglePlayerGameWorld(Physics2 physics_engine) {
        createUI();
        stage = new Stage();
        stage.addActor(touchpad);

        this.physics_engine = physics_engine;
        crane = physics_engine.crane;
        world = physics_engine.getWorld();

        Gdx.input.setInputProcessor(stage);

    }

    public void updateCraneAction() {
        crane.setLinearVelocity(CRANE_SPEED*touchpad.getKnobPercentX(), CRANE_SPEED*touchpad.getKnobPercentY());
    }

    //create everthing considering user interface
    public void createUI() {
        //create the skin library for touchpad and release Button
        skin = new Skin();
        skin.add("touchBackground", new Texture("textures/touchBackground.png"));
        skin.add("touchKnob", new Texture("textures/touchKnob.png"));
        //skin.add("releaseButton", new Texture("texture/releaseButton.png"));

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
    }

    @Override
    public void update(float delta) {
        updateCraneAction();
        stage.act(delta);
        physics_engine.render();
        world.getBodies(bodyArray);
    }

    public Stage getStage() {
        return stage;
    }

    public World getWorld() {
        return world;
    }
}
