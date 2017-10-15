package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
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
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.SinglePlayer.Objects.CraneData;
import com.thechallengers.psagame.SinglePlayer.Objects.Dust;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.SinglePlayer.Physics.Frame;
import com.thechallengers.psagame.SinglePlayer.Physics.RandomController;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.SoundLoader;

import java.util.ArrayDeque;
import java.util.Arrays;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.DOWN;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.LEFT;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.RIGHT;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.STOP;
import static com.thechallengers.psagame.SinglePlayer.Objects.CraneData.STATE.UP;
import static com.thechallengers.psagame.SinglePlayer.SinglePlayerGameRenderer.cooldown_animation_runTime;
import static com.thechallengers.psagame.SinglePlayer.SinglePlayerGameWorld.PERCENTAGE_THRESHOLD;
import static com.thechallengers.psagame.game.PSAGame.playSound;

/**
 * Created by Phung Tuan Hoang on 9/28/2017.
 */

public class Box2DWorld {
    public static final int NUM_NEXT_BLOCK_INFORMED = 3;
    private static final float COOLDOWN_TIME = 4;
    private World world;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;
    private Body ground, ceiling, crane;
    public boolean destroyMode;
    public ArrayDeque<Block> nextBlockQ;
    public Array<Body> bodyArray;
    public float cooldown;
    public float timeCount = 0;
    public Array<Polygon> pattern = new Array<Polygon>();
    public static int size = 100;
    public Frame myFrame;
    public float totalArea = 0;
    private float percentageOverlap = 0;
    private int num_width_3_consecutively = 0; // number of 3 consecutive block with width = 3
    public float endGameWaitTime = 0;
    private float impulseRefuseTime = 0;
    private boolean refusingImpulse = false;
    public Dust[] dust = null;
    private boolean timesUp = false;

    public Box2DWorld(int level) {
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
                if ((impulse.getNormalImpulses())[0] > 7) {
                    if (!refusingImpulse) {
                        playSound("block_hitting_ground.mp3");
                        String a = "", b = "";
                        if (bodyA.getType() == BodyDef.BodyType.DynamicBody) a = ((Block) bodyA.getUserData()).blockType;
                        if (bodyB.getType() == BodyDef.BodyType.DynamicBody) b = ((Block) bodyB.getUserData()).blockType;
                        System.out.println(a + " " + b + " " + (impulse.getNormalImpulses())[0]);
                        refusingImpulse = true;

                        if (bodyA.getType() == BodyDef.BodyType.DynamicBody) {
                            Vector2 a_pos = bodyA.getPosition();

                            if (bodyB.getType() == BodyDef.BodyType.DynamicBody) {
                                Vector2 b_pos = bodyB.getPosition();
                                Vector2[] a_lower = getLowerVertices(bodyA);
                                Vector2[] b_lower = getLowerVertices(bodyB);

                                if (a_lower[0].y > b_lower[0].y) {
                                    createDust(getDustPoint(a_lower, b_lower));
                                }
                                else {
                                    createDust(getDustPoint(b_lower, a_lower));
                                }
                            } else {
                                Vector2[] a_lower = getLowerVertices(bodyA);
                                createDust(getDustPoint(a_lower));
                            }
                        } else {
                            Vector2[] b_lower = getLowerVertices(bodyB);
                            createDust(getDustPoint(b_lower));
                        }
                    }
                }
            }});

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

        Array<float[]> vert_arr = new Array<float[]> ();

        //handling different levels
        switch (level) {
            //TODO: Re-factor this.
            case 1:
                float[] pattern_verts = {1.44f, 13.26f, 9.32f, 13.26f, 9.32f, 8.42f, 1.44f, 8.42f};

                for (int i = 1; i <= pattern_verts.length; i += 2) {
                    pattern_verts[i] -= 6.5f;
                }

                vert_arr.add(pattern_verts);
                renderPattern(vert_arr, world);

                nextBlockQ = new ArrayDeque<Block>();
                nextBlockQ.addLast(new Block(1, 1, 1));
                nextBlockQ.addLast(new Block(4, 3, 2));
                nextBlockQ.addLast(new Block(5, 2, 1));
                break;
            case 3:
                float[] pattern_verts1 = {1.68f, 12.22f, 2.04f, 12.58f, 3.84f, 12.58f, 4.16f, 12.26f, 4.16f, 8.54f, 3.84f, 8.14f, 2.04f, 8.14f, 1.68f, 8.6f};
                for (int i = 1; i <= pattern_verts1.length; i += 2) {
                    pattern_verts1[i] -= 3.5f;
                }
                vert_arr.add(pattern_verts1);
                float[] pattern_verts2 = {4.16f, 12.26f, 4.4f, 12.58f, 6.3f, 12.58f, 6.6f, 12.26f, 6.6f, 8.54f, 6.32f, 8.14f, 4.4f, 8.14f, 4.16f, 8.56f};
                for (int i = 1; i <= pattern_verts2.length; i += 2) {
                    pattern_verts2[i] -= 3.5f;
                }
                vert_arr.add(pattern_verts2);
                float[] pattern_verts3 = {6.6f, 12.28f, 6.84f, 12.58f, 8.74f, 12.58f, 9.08f, 12.22f, 9.08f, 8.62f, 8.76f, 8.14f, 6.82f, 8.14f, 6.6f, 8.54f};
                for (int i = 1; i <= pattern_verts3.length; i += 2) {
                    pattern_verts3[i] -= 3.5f;
                }
                vert_arr.add(pattern_verts3);
                renderPattern(vert_arr, world);
                break;
            case 4:
                float[] pattern_verts4 = {3.28f, 15.14f, 7.64f, 15.14f, 7.64f, 10.8f, 3.28f, 10.8f};
                for (int i = 1; i <= pattern_verts4.length; i += 2) {
                    pattern_verts4[i] -= 4f;
                }
                vert_arr.add(pattern_verts4);
                float[] pattern_verts5 = {2.22f, 14.12f, 3.28f, 14.12f, 3.28f, 11.6f, 2.22f, 11.6f};
                for (int i = 1; i <= pattern_verts5.length; i += 2) {
                    pattern_verts5[i] -= 4f;
                }
                vert_arr.add(pattern_verts5);
                float[] pattern_verts6 = {7.66f, 14.12f, 8.7f, 14.12f, 8.7f, 11.6f, 7.66f, 11.6f};
                for (int i = 1; i <= pattern_verts6.length; i += 2) {
                    pattern_verts6[i] -= 4f;
                }
                vert_arr.add(pattern_verts6);
                float[] pattern_verts7 = {4.86f, 10.8f, 6.08f, 10.8f, 6.08f, 8.84f, 4.86f, 8.84f};
                for (int i = 1; i <= pattern_verts7.length; i += 2) {
                    pattern_verts7[i] -= 4f;
                }
                vert_arr.add(pattern_verts7);
                float[] pattern_verts8 = {3.28f, 8.84f, 7.64f, 8.84f, 7.64f, 8.02f, 3.28f, 8.02f};
                for (int i = 1; i <= pattern_verts8.length; i += 2) {
                    pattern_verts8[i] -= 4f;
                }
                vert_arr.add(pattern_verts8);
                float[] pattern_verts9 = {2.6f, 8.02f, 8.34f, 8.02f, 8.34f, 6.54f, 2.6f, 6.54f};
                for (int i = 1; i <= pattern_verts9.length; i += 2) {
                    pattern_verts9[i] -= 4f;
                }
                vert_arr.add(pattern_verts9);
                renderPattern(vert_arr, world);
                break;
            case 5:
                float[] pattern_verts10 = {1.98f, 16.1f, 2.08f, 16.2f, 5.76f, 16.2f, 5.86f, 16.1f, 5.86f, 11.5f, 2.08f, 11.5f, 1.98f, 11.6f};
                for (int i = 1; i <= pattern_verts10.length; i += 2) {
                    pattern_verts10[i] -= 4f;
                }
                vert_arr.add(pattern_verts10);
                float[] pattern_verts11 = {3.62f, 12.52f, 6.72f, 12.52f, 6.82f, 12.42f, 6.82f, 8f, 3.72f, 8f, 3.62f, 8.1f};
                for (int i = 1; i <= pattern_verts11.length; i += 2) {
                    pattern_verts11[i] -= 4f;
                }
                vert_arr.add(pattern_verts11);
                float[] pattern_verts12 = {4.7f, 8.92f, 8.74f, 8.92f, 8.84f, 8.82f, 8.84f, 4.9f, 8.74f, 4.82f, 4.8f, 4.82f, 4.7f, 4.9f};
                for (int i = 1; i <= pattern_verts12.length; i += 2) {
                    pattern_verts12[i] -= 4f;
                }
                vert_arr.add(pattern_verts12);
                renderPattern(vert_arr, world);
                break;
            case 2:
                float[] pattern_verts13 = {3.4f, 13.12f, 4.92f, 13.12f, 4.92f, 12.2f, 3.4f, 12.2f};
                for (int i = 1; i <= pattern_verts13.length; i += 2) {
                    pattern_verts13[i] -= 2.5f;
                }
                vert_arr.add(pattern_verts13);
                float[] pattern_verts14 = {2.66f, 12.2f, 5.62f, 12.2f, 5.62f, 11.2f, 2.66f, 11.2f};
                for (int i = 1; i <= pattern_verts14.length; i += 2) {
                    pattern_verts14[i] -= 2.5f;
                }
                vert_arr.add(pattern_verts14);
                float[] pattern_verts15 = {1.92f, 11.2f, 6.38f, 11.2f, 6.38f, 10.28f, 1.92f, 10.28f};
                for (int i = 1; i <= pattern_verts15.length; i += 2) {
                    pattern_verts15[i] -= 2.5f;
                }
                vert_arr.add(pattern_verts15);
                float[] pattern_verts16 = {0.82f, 10.26f, 9.88f, 10.26f, 9.88f, 6.4f, 3.58f, 6.4f};
                for (int i = 1; i <= pattern_verts16.length; i += 2) {
                    pattern_verts16[i] -= 2.5f;
                }
                vert_arr.add(pattern_verts16);
                float[] pattern_verts17 = {6.92f, 11.54f, 9.88f, 11.54f, 9.88f, 10.26f, 6.92f, 10.26f};
                for (int i = 1; i <= pattern_verts17.length; i += 2) {
                    pattern_verts17[i] -= 2.5f;
                }
                vert_arr.add(pattern_verts17);
                float[] pattern_verts18 = {8.88f, 13.08f, 9.88f, 13.08f, 9.88f, 11.54f, 8.88f, 11.54f};
                for (int i = 1; i <= pattern_verts18.length; i += 2) {
                    pattern_verts18[i] -= 2.5f;
                }
                vert_arr.add(pattern_verts18);
                renderPattern(vert_arr, world);
                break;
            default:
        }
    }

    public void update(float delta) {
        if (refusingImpulse) {
            impulseRefuseTime += delta;
        }
        if (impulseRefuseTime > 0.3f && refusingImpulse) {
            refusingImpulse = false;
            impulseRefuseTime = 0;
        }
        if (percentageOverlap > PERCENTAGE_THRESHOLD || timesUp) {
            SoundLoader.musicHashtable.get("crane_pulley.mp3").stop();
            endGameWaitTime += delta;
            return;
        }
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
        crane.setTransform(5.4f, 14.4f, 0);
        crane.setUserData(new CraneData());
    }

    public void moveCrane(float screenX, float screenY) {
        float distance = (14.4f - crane.getPosition().y) + (Math.abs(screenX - crane.getPosition().x))
                            + (14.4f - screenY);

        float velocity = distance / 2f;

        CraneData craneData = (CraneData) crane.getUserData();

        craneData.isMoving = true;

        //adjust the velocity of crane here
        int present_crane = Gdx.app.getPreferences("prefs").getInteger("crane_present");
        switch (present_crane) {
            case 1:
                craneData.velocity = 12;
                break;
            case 2:
                craneData.velocity = 14;
                break;
            case 3:
                craneData.velocity = 16;
                break;
        }

        craneData.destination = new Vector2(screenX, screenY);

        SoundLoader.musicHashtable.get("crane_pulley.mp3").play();
    }

    public void updateCrane() {
        CraneData craneData = (CraneData) crane.getUserData();

        switch (craneData.state) {
            case STOP: {
                if (!craneData.isMoving) {
                    crane.setTransform(craneData.destination, 0);
                    SoundLoader.musicHashtable.get("crane_pulley.mp3").stop();
                    break;
                }
                else {
                    craneData.state = UP;
                    crane.setLinearVelocity(0, craneData.velocity);
                }
                break;
            }
            case UP: {
                if (crane.getPosition().y < 14.4f) break;
                else {
                    crane.setTransform(crane.getPosition().x, 14.4f, 0);

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
            craneData.cranedBody.setTransform(crane.getPosition().x, crane.getPosition().y - ((Block) craneData.cranedBody.getUserData()).height / 2f, 0);
        }
    }

    public Block createNextBlock() {
        Block _nextBlock = RandomController.randomBlock();

        if (num_width_3_consecutively == 1) {
            while (_nextBlock.width == 3) {
                _nextBlock = RandomController.randomBlock();
            }
            num_width_3_consecutively = 0;
        }

        if (_nextBlock.width == 3) {
            num_width_3_consecutively++;
        }

        return _nextBlock;
    }

    public Body createBody(Block block) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(block.width / 2f, block.height / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 6 / (block.width * block.height);
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
                cooldown_animation_runTime = 0;
                break;
            }
        }
    }

    public void destroyInvalidBlocks() {
        for(int i = 0; i < bodyArray.size; i++) {
            float rotation = bodyArray.get(i).getAngle();
            if (Math.abs(rotation) > 0.2f) {
                playSound("block_destroyed.mp3");
                world.destroyBody(bodyArray.get(i));
                bodyArray.removeIndex(i);
                i--;
            }
        }
    }

    public void renderPattern(Array<float[]> verts_arr, World world) {
        totalArea = 0;
        for(int i = 0; i < verts_arr.size; i ++) {
            float[] vertices = verts_arr.get(i);
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
            Polygon patPoly = new Polygon();
            patPoly.setVertices(vertices);
            pattern.add(new Polygon());
            pattern.set(i, patPoly);
            totalArea += calculateArea(vertices, vertices.length / 2);
        }
    }

    public void calculateOverlap(Array<Body> bodyArray) {
        float overlapArea = 0;
        for(int i = 0; i < pattern.size; i ++) {
            for (Body body : bodyArray) {
                if (body.getUserData() instanceof Block) {
                    float x = body.getPosition().x;
                    float y = body.getPosition().y;
                    float width = ((Block) body.getUserData()).width;
                    float height = ((Block) body.getUserData()).height;
                    float[] blockVertices = {x - width / 2, y - height / 2, x - width / 2, y + height / 2,
                            x + width / 2, y + height / 2, x + width / 2, y - height / 2};
                    Polygon blockPoly = new Polygon();
                    blockPoly.setVertices(blockVertices);
                    Polygon overlapPoly = new Polygon();
                    boolean a = Intersector.intersectPolygons(pattern.get(i), blockPoly, overlapPoly);
                    overlapArea += calculateArea(overlapPoly.getVertices(), overlapPoly.getVertices().length / 2);
                }
            }
        }

        percentageOverlap = overlapArea / totalArea;
    }

    public void createDust(Vector2 position) {
        Dust left = new Dust(-1, position.x, position.y);
        Dust right = new Dust(1, position.x, position.y);
        left.addAction(sequence(fadeOut(0.5f), removeActor()));
        right.addAction(sequence(fadeOut(0.5f), removeActor()));
        dust = new Dust[] {left, right};
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

    private Vector2[] getLowerVertices(Body body) {
        Block block = (Block) body.getUserData();
        float x = body.getPosition().x;
        float y = body.getPosition().y;

        Vector2 pnt_1 = new Vector2(x - block.width * 0.5f, y - block.height * 0.5f);
        Vector2 pnt_2 = new Vector2(x + block.width * 0.5f, y - block.height * 0.5f);

        return new Vector2[] {pnt_1, pnt_2};
    }

    private Vector2 getDustPoint(Vector2[] upper, Vector2[] lower) {
        float[] array = {upper[0].x, upper[1].x, lower[0].x, lower[1].x};
        Arrays.sort(array);
        return new Vector2((array[1] + array[2]) / 2f * 100f, upper[0].y * 100f);
    }

    private Vector2 getDustPoint(Vector2[] vector) {
        return new Vector2((vector[0].x + vector[1].x) / 2f * 100f, vector[0].y * 100f);
    }

    public void setTimesUp() {
        timesUp = true;
    }
}
