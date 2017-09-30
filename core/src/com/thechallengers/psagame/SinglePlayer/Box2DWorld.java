package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
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
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.SinglePlayer.Objects.CraneData;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.SinglePlayer.Physics.RandomController;

import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.DOWN;;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.LEFT;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.RIGHT;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.STOP;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.UP;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class Box2DWorld {
    private World world;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;
    private Body ground, ceiling, crane;
    private Block nextBlock;
    public Array<Body> bodyArray;

    public Box2DWorld() {
        world = new World(new Vector2(0, -9.8f), true);
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
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();

                CraneData craneData = (CraneData) crane.getUserData();

                if ((craneData.cranedBody == bodyA && bodyB.getType() == BodyDef.BodyType.DynamicBody) ||
                        (craneData.cranedBody == bodyB && bodyA.getType() == BodyDef.BodyType.DynamicBody) ||
                        (craneData.cranedBody == bodyA && bodyB.getUserData() == "ground") ||
                        (craneData.cranedBody == bodyB && bodyA.getUserData() == "groundd")) {
                    craneData.cranedBody.setTransform(craneData.destination, 0);
                    craneData.cranedBody = null;
                }

            }
        });

        createGroundAndCeiling();
        createCrane();
        nextBlock = createNextBlock();
        bodyArray = new Array<Body>();
    }

    public void update(float delta) {
        updateCrane();
        world.getBodies(bodyArray);
        destroyInvalidBlocks();
        world.step(delta, 6, 2);
    }

    public void createGroundAndCeiling() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        ground = world.createBody(bodyDef);
        PolygonShape ground_shape = new PolygonShape();
        float[] vertices = {0, 0, 0, 0.50f, 10.80f, 0.50f, 10.80f, 0};
        ground_shape.set(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ground_shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        ground.createFixture(fixtureDef);
        ground.setUserData("Ground");

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        ceiling = world.createBody(bodyDef2);
        PolygonShape ceiling_shape = new PolygonShape();
        float[] vertices2 = {0, 17.5f, 0, 18f, 10.80f, 18f, 10.80f, 17.5f};
        ceiling_shape.set(vertices2);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = ceiling_shape;
        fixtureDef2.density = 1f;
        fixtureDef2.friction = 1f;
        ceiling.createFixture(fixtureDef2);
        ceiling.setUserData("Ceiling");
    }

    public void createCrane() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        crane = world.createBody(bodyDef);
        PolygonShape crane_shape = new PolygonShape();
        crane_shape.setAsBox(0.1f, 0.1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = crane_shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.isSensor = true;
        crane.createFixture(fixtureDef);
        crane.setTransform(5.4f, 17.5f - 0.1f, 0);
        crane.setUserData(new CraneData());
    }

    public void moveCrane(float screenX, float screenY) {
        System.out.println("called");
        float distance = (17.4f - crane.getPosition().y) + (Math.abs(screenX - crane.getPosition().x))
                            + (17.4f - screenY);

        float velocity = distance / 2f;

        CraneData craneData = (CraneData) crane.getUserData();

        craneData.isMoving = true;

        craneData.velocity = velocity;

        craneData.destination = new Vector2(screenX, screenY);
    }

    public void updateCrane() {
        CraneData craneData = (CraneData) crane.getUserData();

        switch (craneData.state) {
            case STOP: {
                if (!craneData.isMoving) {
                    crane.setTransform(craneData.destination, 0);
                    break;
                }
                else {
                    craneData.state = UP;
                    crane.setLinearVelocity(0, craneData.velocity);
                }
                break;
            }
            case UP: {
                if (crane.getPosition().y < 17.4f) break;
                else {
                    crane.setTransform(crane.getPosition().x, 17.4f, 0);

                    float horizontal_velocity = craneData.velocity * (craneData.destination.x >= crane.getPosition().x ? 1 : -1);

                    crane.setLinearVelocity(horizontal_velocity ,0);

                    if (horizontal_velocity >= 0) {
                        craneData.state = RIGHT;
                    }
                    else craneData.state = LEFT;

                    break;
                }
            }
            case LEFT: {
                if (crane.getPosition().x < craneData.destination.x) {
                    crane.setTransform(craneData.destination.x, crane.getPosition().y, 0);

                    craneData.state = DOWN;
                    crane.setLinearVelocity(0, -craneData.velocity);
                    Body cranedBody = createBody(nextBlock);
                    cranedBody.setTransform(crane.getPosition().x, crane.getPosition().y - 0.1f - nextBlock.height / 2f, 0);
                    craneData.cranedBody = cranedBody;
                    nextBlock = createNextBlock();
                }
                break;
            }
            case RIGHT: {
                if (crane.getPosition().x > craneData.destination.x) {
                    crane.setTransform(craneData.destination.x, crane.getPosition().y, 0);

                    craneData.state = DOWN;
                    crane.setLinearVelocity(0, -craneData.velocity);
                    Body cranedBody = createBody(nextBlock);
                    cranedBody.setTransform(crane.getPosition().x, crane.getPosition().y - 0.1f - nextBlock.height / 2f, 0);
                    craneData.cranedBody = cranedBody;
                    nextBlock = createNextBlock();
                }
                break;
            }
            case DOWN: {
                if (crane.getPosition().y < craneData.destination.y) {
                    crane.setTransform(crane.getPosition().x, craneData.destination.y, 0);

                    craneData.state = STOP;
                    craneData.velocity = 0;
                    craneData.isMoving = false;
                    crane.setLinearVelocity(0, 0);
                    if (craneData.cranedBody != null) {
                        craneData.cranedBody.setTransform(craneData.destination.x, craneData.destination.y - 0.1f - ((Block) craneData.cranedBody.getUserData()).height / 2f, 0);
                        craneData.cranedBody.setType(BodyDef.BodyType.DynamicBody);
                        craneData.cranedBody.getFixtureList().get(0).setSensor(false);
                        craneData.cranedBody = null;
                    }
                }
            }
            default:
        }

        if (craneData.cranedBody != null) {
            craneData.cranedBody.setTransform(crane.getPosition().x, crane.getPosition().y - 0.1f - ((Block) craneData.cranedBody.getUserData()).height / 2f, 0);
        }
    }

    public Block createNextBlock() {
        return RandomController.randomBlock();
    }

    public Body createBody(Block block) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(block.width / 2f, block.height / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);
        body.setUserData(block);
        return body;
    }

    public void destroyInvalidBlocks() {
        for(int i = 0; i < bodyArray.size; i++) {
            float rotation = bodyArray.get(i).getAngle();
            if (Math.abs(rotation) > 0.2f) {
                world.destroyBody(bodyArray.get(i));
                bodyArray.removeIndex(i);
                i--;
            }
        }
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

    public Body getCeiling() {
        return ceiling;
    }

    public Body getCrane() {
        return crane;
    }
}
