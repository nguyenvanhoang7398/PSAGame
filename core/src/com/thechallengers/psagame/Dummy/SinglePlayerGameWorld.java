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
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.Dummy.Objects.Crane;
import com.thechallengers.psagame.Dummy.Physics.Physics2;
import com.thechallengers.psagame.Dummy.Physics.PhysicsInputHandler;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class SinglePlayerGameWorld implements ScreenWorld {
    private Crane crane;
    private Stage stage;
    private World world;
    public Array<Body> bodyArray = new Array<Body>();
    Physics2 physics_engine;

    public SinglePlayerGameWorld(Physics2 physics_engine) {
        stage = new Stage();

        this.physics_engine = physics_engine;
        world = physics_engine.getWorld();

    }

    @Override
    public void update(float delta) {
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
