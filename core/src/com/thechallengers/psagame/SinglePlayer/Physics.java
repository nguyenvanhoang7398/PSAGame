package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class Physics {
    private World world;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;
    private Body ground;

    public Physics() {
        world = new World(new Vector2(0, -.98f), true);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 10.80f, 19.20f);
        debugRenderer = new Box2DDebugRenderer();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        ground = world.createBody(bodyDef);
        PolygonShape ground_shape = new PolygonShape();
        float[] vertices = {0, 0, 0, 0.50f, 10.80f, 0.50f, 10.80f, 0};
        ground_shape.set(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ground_shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        ground.createFixture(fixtureDef);
    }

    public void update(float delta) {
        world.step(delta, 6, 2);
    }

    public void debugRender() {
        debugRenderer.render(world, cam.combined);
    }

    public World getWorld() {
        return world;
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public Box2DDebugRenderer getDebugRenderer() {
        return debugRenderer;
    }

    public Body getGround() {
        return ground;
    }
}
