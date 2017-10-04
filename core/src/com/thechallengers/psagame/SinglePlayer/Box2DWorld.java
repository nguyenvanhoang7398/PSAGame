package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.SinglePlayer.Objects.CraneData;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.SinglePlayer.Physics.Frame;
import com.thechallengers.psagame.SinglePlayer.Physics.RandomController;

import java.util.ArrayDeque;

import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.DOWN;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.LEFT;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.RIGHT;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.STOP;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.UP;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class Box2DWorld {
    private static final int NUM_NEXT_BLOCK_INFORMED = 3;
    private static final float COOLDOWN_TIME = 5;

    private World world;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;
    private Body ground, ceiling, crane;
    public boolean destroyMode;
    public ArrayDeque<Block> nextBlockQ;
    public Array<Body> bodyArray;
    public float cooldown;
    public Polygon pattern;
    public static int size = 100;
    public Frame myFrame;
    private float patternArea;
    private float percentageOverlap = 0;

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
                        (craneData.cranedBody == bodyA && bodyB.getUserData() == "Ground") ||
                        (craneData.cranedBody == bodyB && bodyA.getUserData() == "Ground")) {
                    craneData.cranedBody.setTransform(craneData.destination, 0);
                    craneData.cranedBody = null;
                }

            }
        });

        createGroundAndCeiling();
        createCrane();
        nextBlockQ = new ArrayDeque<Block>();
        for (int i = 0; i< NUM_NEXT_BLOCK_INFORMED; i++) { // create next blocks and enqueue
            nextBlockQ.addLast(createNextBlock());
        }
        bodyArray = new Array<Body>();
        destroyMode = false;
        cooldown = 0;

        myFrame = new Frame(size);
        float[] pattern_verts = {1f, 1f, 1f, 1.50f, 6f, 5f, 11.80f, 1.50f, 11.80f, 1f};
        renderPattern(pattern_verts, world);
    }

    public Box2DWorld(String string) {
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
                        (craneData.cranedBody == bodyA && bodyB.getUserData() == "Ground") ||
                        (craneData.cranedBody == bodyB && bodyA.getUserData() == "Ground")) {
                    craneData.cranedBody.setTransform(craneData.destination, 0);
                    craneData.cranedBody = null;
                }

            }
        });

        createGroundAndCeiling();
        createCrane();
        nextBlockQ = new ArrayDeque<Block>();
        // create next blocks and enqueue
            nextBlockQ.addLast(new Block(1, 1, 1));
            nextBlockQ.addLast(new Block(4, 3, 2));
            nextBlockQ.addLast(new Block(5, 2, 1));
        bodyArray = new Array<Body>();
        destroyMode = false;
        cooldown = 0;

        myFrame = new Frame(size);
        float[] pattern_verts = {1f, 1f, 1f, 1.50f, 6f, 5f, 11.80f, 1.50f, 11.80f, 1f};
        renderPattern(pattern_verts, world);
    }

    public void update(float delta) {
        updateCrane();
        world.getBodies(bodyArray);
        destroyInvalidBlocks();
        world.step(delta, 6, 2);
        if(bodyArray != null && myFrame != null) {
            calculateOverlap(bodyArray);
        }
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
                    Block nextBlock = nextBlockQ.poll();
                    Body cranedBody = createBody(nextBlock);
                    cranedBody.setTransform(crane.getPosition().x, crane.getPosition().y - 0.1f - nextBlock.height / 2f, 0);
                    craneData.cranedBody = cranedBody;
                    nextBlockQ.addLast(createNextBlock());
                }
                break;
            }
            case RIGHT: {
                if (crane.getPosition().x > craneData.destination.x) {
                    crane.setTransform(craneData.destination.x, crane.getPosition().y, 0);

                    craneData.state = DOWN;
                    crane.setLinearVelocity(0, -craneData.velocity);
                    Block nextBlock = nextBlockQ.poll();
                    Body cranedBody = createBody(nextBlock);
                    cranedBody.setTransform(crane.getPosition().x, crane.getPosition().y - 0.1f - nextBlock.height / 2f, 0);
                    craneData.cranedBody = cranedBody;
                    nextBlockQ.addLast(createNextBlock());
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

    public void destroyBlock(float screenX, float screenY) {
        for(int i = 0; i < bodyArray.size; i++) {
            Body body = bodyArray.get(i);
            Fixture fixture = body.getFixtureList().get(0);
            if (!(body.getUserData() instanceof Block)) {
                continue;
            }
            if (fixture.testPoint(screenX, screenY)) {
                world.destroyBody(bodyArray.get(i));
                bodyArray.removeIndex(i);
                cooldown = COOLDOWN_TIME;
                break;
            }
        }
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

    public void renderPattern(float[] vertices, World world) {
        PolygonShape poly = new PolygonShape();
        ShapeRenderer renderer = new ShapeRenderer();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        Body body = world.createBody(bodyDef);
        poly.set(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        pattern = new Polygon();
        pattern.setVertices(vertices);
        patternArea = calculateArea(vertices, vertices.length / 2);
    }

    public void calculateOverlap(Array<Body> bodyArray) {
        float overlapArea = 0;
        for(Body body: bodyArray) {
            if(body.getUserData() instanceof Block) {
                float x = body.getPosition().x ;
                float y = body.getPosition().y ;
                float width = ((Block) body.getUserData()).width ;
                float height = ((Block) body.getUserData()).height ;
                float[] blockVertices = {x - width / 2, y - width / 2, x - width / 2, y + width / 2,
                        x + width / 2, y + width / 2, x + width / 2, y - width / 2};
                Polygon blockPoly = new Polygon();
                blockPoly.setVertices(blockVertices);
                Polygon overlapPoly = new Polygon();
                boolean a = Intersector.intersectPolygons(pattern, blockPoly, overlapPoly);
                overlapArea += calculateArea(overlapPoly.getVertices(), overlapPoly.getVertices().length / 2);
            }
        }

        percentageOverlap = overlapArea / patternArea;

        System.out.println("Percent: " + percentageOverlap);
    }

    public float calculateArea(float[] vertices, int numPoints) {
        float area = 0;
        int j = numPoints-1;  // The last vertex is the 'previous' one to the first

        for (int i=0; i<numPoints; i++)
        { area = area +  (vertices[2*j]+vertices[2*i]) * (vertices[2*j + 1]-vertices[2*i+1]);
            j = i;  //j is previous vertex to i
        }
        return area/2;
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

    public float getPercentageOverlap() {
        return percentageOverlap;
    }
}
