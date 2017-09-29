package com.thechallengers.psagame.SinglePlayer.Physics;

/**
 * Created by Phung Tuan Hoang on 9/25/2017.
 */

import com.thechallengers.psagame.SinglePlayer.Physics.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.ArrayList;

public class Physics2 extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite sprite, ground_sprite;
    Texture img, ground;
    World world;
    Body body, ground_body;
    public Array<Body> bodyArray;
    Box2DDebugRenderer debug_renderer;
    OrthographicCamera cam;
    World fail_safe_world;
    public int boxIdx = 0;
    private Body currentContactBody = null;
    private ArrayList<Body> contactedBodyArr = new ArrayList<Body>();
    private boolean handlingContact = true;
    private RandomController randomController;
    private final Physics2 self = this;
    public Body nextBody = null;
    public Block nextBlock = null;
    public Body cranedBody = null;
    public final String BOX_BLOCK_TYPE = "BOX";
    public final String GROUND_BLOCK_TYPE = "GROUND";
    public WeightLogicGraph gameGraph = new WeightLogicGraph();
    private PhysicsInputHandler physicsInputHandler;
    public Body crane;
    public boolean isHoldingNext = true;
    public boolean justDestroy = false;
    public int justDestroyTime = 0;


    private final int nextBlockXCord = 2;
    private final int nextBlockYCord = 18;
    private final int cranedBlockXCord = 9;
    private final int cranedBlockYCord = 18;

    public Physics2(PhysicsInputHandler physicsInputHandler) {
        this.physicsInputHandler = physicsInputHandler;
        create();
    }

    private void handleContact() {
        System.out.println("Handled contact called"); // Signal handling contact
        System.out.println("Current contact body " + currentContactBody.getUserData());
        if (! (currentContactBody.getUserData() instanceof Block)) {
            return;
        }

        Block newBlock = (Block) currentContactBody.getUserData(); // incoming block
        ArrayList<MyPair<Integer, Float>> contactedBlocks = new ArrayList<MyPair<Integer, Float>>();
        ArrayList<Float> contactedLengths = new ArrayList<Float>();
        float sumContactedLength = 0;

        for (Body contactedBody: contactedBodyArr) {
            System.out.println(contactedBody.getUserData());
            if (contactedBody.getUserData() instanceof Block) {
                Block contactedBlock = (Block) contactedBody.getUserData();
                if (!((Block) contactedBody.getUserData()).type.equals(BOX_BLOCK_TYPE)) {
                    gameGraph.addBlock(newBlock, contactedBlocks); // add block for contacting with ground
                    return;
                }
                Body bodyLeft = contactedBody.getPosition().x < currentContactBody.getPosition().x ?
                        contactedBody : currentContactBody;
                Body bodyRight = currentContactBody.getPosition().x > contactedBody.getPosition().x ?
                        currentContactBody : contactedBody;
                System.out.println("Left " + bodyLeft.getUserData());
                System.out.println("Left width " + ((Block) bodyLeft.getUserData()).width);
                System.out.println("Left x " + bodyLeft.getPosition().x);
                System.out.println("Right " + bodyRight.getUserData());
                System.out.println("Right width " + ((Block) bodyRight.getUserData()).width);
                System.out.println("Right x " + bodyRight.getPosition().x);
                float halfWidthLeft = ((Block) bodyLeft.getUserData()).width / 2;
                float halfWidthRight = ((Block) bodyRight.getUserData()).width / 2;
                float contactedLength = (halfWidthLeft * 2 + halfWidthRight * 2) - ((bodyRight.getPosition().x
                        + halfWidthRight) - (bodyLeft.getPosition().x - halfWidthLeft));
                // contactedBlocks.add(new MyPair(contactedBlock.sequenceNumber, contactedLength / newBlock.width));
                contactedLengths.add(contactedLength);
                sumContactedLength += contactedLength;
            }
        }

        int i = 0;
        for (Body contactedBody: contactedBodyArr) {
            Block contactedBlock = (Block) contactedBody.getUserData();
            contactedBlocks.add(new MyPair<Integer, Float>(contactedBlock.sequenceNumber, contactedLengths.get(i) / sumContactedLength));
            i++;
        }

        for (MyPair<Integer, Float> pair: contactedBlocks) {
            System.out.println("Contacts of " + newBlock + " " + pair);
        }

        gameGraph.addBlock(newBlock, contactedBlocks);
        gameGraph.printPotential();

    }

    @Override
    public void create() {
        AssetLoader.loadMenuTexture();
        batch = new SpriteBatch();
        randomController = new RandomController(this);
        world = new World(new Vector2(0, -.98f), true);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 10.80f, 19.20f);
        debug_renderer = new Box2DDebugRenderer();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                System.out.println("Begin contact"); // Signal begin contact
                Body bodyA = contact.getFixtureA().getBody(); // Get contacting bodies
                Body bodyB = contact.getFixtureB().getBody();
                if (bodyA.getType() == BodyDef.BodyType.KinematicBody || bodyB.getType() == BodyDef.BodyType.KinematicBody) {
                    self.handlingContact = false;
                    return;
                }
                Body contactBody, contactedBody;
                contactBody = bodyA.getPosition().y > bodyB.getPosition().y ? bodyA : bodyB; // Check y coord for contacting and contacted bodies
                contactedBody = bodyB.getPosition().y < bodyA.getPosition().y ? bodyB : bodyA;
                if (self.currentContactBody == null || self.currentContactBody == contactBody)
                    self.currentContactBody = contactBody;
                self.contactedBodyArr.add(contactedBody);
                self.handlingContact = true;
                System.out.println("Contact body " + contactBody.getUserData());
                System.out.println("Contacted body " + contactedBody.getUserData());
            }

            @Override
            public void endContact(Contact contact) {
                System.out.println("End contact");
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                if (self.handlingContact) {
                    self.handleContact();
                    self.handlingContact = false;
                    self.contactedBodyArr = new ArrayList<Body>();
                    self.currentContactBody = null;
                }
            }
        });
        bodyArray = new Array<Body>();

        Gdx.input.setInputProcessor(physicsInputHandler);

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        ground_body = world.createBody(bodyDef2);
        ground_body.setUserData(new Block(GROUND_BLOCK_TYPE));
        PolygonShape ground_shape = new PolygonShape();
        float[] vertices = {0, 0, 0, 0.50f, 10.80f, 0.50f, 10.80f, 0};
        ground_shape.set(vertices);
        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = ground_shape;
        fixtureDef1.density = 1f;
        fixtureDef1.friction = 1f;
        ground_body.createFixture(fixtureDef1);

        createCrane();
        createNextBlock();
        createNextBodyImage();
    }

    @Override
    public void render() {
        if (justDestroy && justDestroyTime <50) {
            justDestroyTime ++;
        }
        if (justDestroyTime == 50) justDestroy = false;

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateBlockStates();
        destroyInvalidBlocks();
        world.getBodies(bodyArray);
        debug_renderer.render(world, cam.combined);

        batch.begin();

        for (int i = 0; i < bodyArray.size; i++) {
            if (bodyArray.get(i).getType() != BodyDef.BodyType.DynamicBody) continue;
            Body body = bodyArray.get(i);
            AssetLoader.consolas_15.draw(batch, String.format("%d %.1f %.1f", (int)((Block) body.getUserData()).weight,((Block)body.getUserData()).remainingCapacity, ((Block)body.getUserData()).remainingTime),body.getPosition().x * 100f, body.getPosition().y * 100f);

        }

        batch.end();
        /*
        batch.begin();

        for (int i = 0; i < bodyArray.size; i++) {
            if (bodyArray.get(i) == ground_body) continue;
            Vector2 body_origin_position = bodyArray.get(i).getPosition();
            sprite.setPosition(bodyArray.get(i).getPosition().x, bodyArray.get(i).getPosition().y);

            sprite.setRotation(bodyArray.get(i).getAngle() * MathUtils.radiansToDegrees);
            batch.draw(sprite, sprite.getX(), sprite.getY());
        }

        batch.draw(ground_sprite, ground_sprite.getX(), ground_sprite.getY());

        batch.end();
        */
    }

    @Override
    public void dispose() {
        // Hey, I actually did some clean up in a code sample!
        img.dispose();
        world.dispose();
    }

    public void createBlock(float horizontal_position, float vertical_position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(horizontal_position, vertical_position);
        Body temp_body = world.createBody(bodyDef);
        temp_body.setUserData(nextBlock);

        this.boxIdx++;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(nextBlock.width/2, nextBlock.height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;

        temp_body.createFixture(fixtureDef);
        shape.dispose();
        createNextBlock();
        createNextBodyImage();
    }

    public void destroyInvalidBlocks() {
        for(int i = 0; i < bodyArray.size; i++) {
            if (bodyArray.get(i).getType() == BodyDef.BodyType.KinematicBody) continue;
            float rotation = bodyArray.get(i).getAngle();
            Block currentBlock = (Block)bodyArray.get(i).getUserData();
            if (Math.abs(rotation) > 0.3 || currentBlock.remainingTime <= 0) {
                gameGraph.deleteNode(currentBlock.sequenceNumber);
                world.destroyBody(bodyArray.get(i));
                bodyArray.removeIndex(i);
                i--;
                justDestroy = true;
            }
        }
    }

    public void updateBlockStates() {
        for(int i = 0; i < bodyArray.size; i++) {
            Body currentBody = bodyArray.get(i);
            if (currentBody.getType() == BodyDef.BodyType.KinematicBody) continue;
            Block currentBlock = (Block)currentBody.getUserData();
            currentBlock.updateRemainingCapacity();
            currentBlock.updateRemainingTime();
        }
    }

    public void createNextBodyImage() {
        System.out.println("Create image next body called");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(nextBlockXCord, nextBlockYCord);
        Body nextBody = world.createBody(bodyDef);
        nextBody.setUserData(nextBlock);

        PolygonShape nextBodyShape = new PolygonShape();
        nextBodyShape.setAsBox(nextBlock.width/2, nextBlock.height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = nextBodyShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;

        nextBody.createFixture(fixtureDef);
        this.nextBody = nextBody;
        nextBodyShape.dispose();
    }

    public void createNextBlock() {
        if (this.nextBlock != null) {
            world.destroyBody(nextBody);
            for (int i=0; i < bodyArray.size; i++) {
                if (body == nextBody) {
                    bodyArray.removeIndex(i);
                    break;
                }
            }
        }
        MyPair<Float, Float> generatedSize = randomController.getSize();
        int generatedDensity = randomController.getDensity();
        nextBlock = new Block(generatedSize.getFirst(), generatedSize.getSecond(),
                BOX_BLOCK_TYPE, boxIdx, generatedDensity);
    }

    public void craneBody(Body body) {
        body.setTransform(new Vector2(cranedBlockXCord, cranedBlockYCord), 0);
        if ((body.getUserData() instanceof Block)) {
            Block block = (Block) body.getUserData();
            gameGraph.deleteNode(block.sequenceNumber);
            block.potential = block.weight;
            block.previousPotential = 0;
            body.setType(BodyDef.BodyType.StaticBody);
            cranedBody = body;
        }
    }

    public void releaseCranedBody(float horizontal_position, float vertical_position) {
        cranedBody.setTransform(new Vector2(horizontal_position, vertical_position), 0);
        cranedBody.setType(BodyDef.BodyType.DynamicBody);
        cranedBody = null;
    }

    public World getWorld() {
        return world;
    }

    public void createCrane() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(5.4f, 6f);
        crane = world.createBody(bodyDef);

        PolygonShape craneShape = new PolygonShape();
        craneShape.setAsBox(0.05f/2, 0.05f/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = craneShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.isSensor = true;

        crane.createFixture(fixtureDef);
        craneShape.dispose();
    }

    public void pullUpCrane() {
        crane.setLinearVelocity(0, 2f);
        if (crane.getPosition().y >= 19f) {
            isHoldingNext = true;
            crane.setLinearVelocity(0, 0);
        }
    }

    public PhysicsInputHandler getPhysicsInputHandler() {
        return physicsInputHandler;
    }
}