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
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class SinglePlayerGameWorld implements ScreenWorld {
    private Crane crane;
    private Stage stage;
    private World world;
    public Array<Body> bodyArray = new Array<Body>();

    public SinglePlayerGameWorld() {
        stage = new Stage();

        world = new World(new Vector2(0, -.98f), true);

        createThreeDummyBlocks();
        createFloor();

        createCrane();

        world.getBodies(bodyArray);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        world.step(delta, 6, 2);
        world.getBodies(bodyArray);
    }

    public void createThreeDummyBlocks() {
        BodyDef bodyDef;
        Body body;
        float horizontal_pos = 0, vertical_pos = 0;

        for (int i = 0; i < 3; i++) {
            //insert hori and verti pos here
            if (i == 0) {
                horizontal_pos = 1; vertical_pos = 2;
            }
            else if (i == 1) {
                horizontal_pos = 2; vertical_pos = 5;
            }
            else {
                horizontal_pos = 7; vertical_pos = 9f;
            }

            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;

            bodyDef.position.set(horizontal_pos, vertical_pos);

            body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(2f / 2, 2f / 2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            fixtureDef.friction = 1f;

            body.createFixture(fixtureDef);
            shape.dispose();
        }
    }

    public void createFloor() {
        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        Body ground_body = world.createBody(bodyDef2);

        PolygonShape ground_shape = new PolygonShape();
        float[] vertices = {0, 0, 0, 0.50f, 10.80f, 0.50f, 10.80f, 0};
        ground_shape.set(vertices);

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = ground_shape;
        fixtureDef1.density = 1f;
        fixtureDef1.friction = 1f;

        ground_body.createFixture(fixtureDef1);
    }

    public void createCrane() {

    }

    public Stage getStage() {
        return stage;
    }

    public World getWorld() {
        return world;
    }
}
