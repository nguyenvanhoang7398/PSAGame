package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.thechallengers.psagame.SinglePlayer.Physics.Frame;
import com.thechallengers.psagame.SinglePlayer.Physics.Pattern;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

/*
    Khi draw sprite, multiply y voi
 */
public class SinglePlayerGameRenderer extends ScreenRenderer {
    private SinglePlayerGameWorld world;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    OrthographicCamera cam = new OrthographicCamera();
    private Texture debug_bg;
    public static float GROUND_HEIGHT = 375f;
    public static int size = 100;

    public SinglePlayerGameRenderer(SinglePlayerGameWorld world) {
        super();
        this.world = world;

        cam.setToOrtho(false, 10.80f, 19.20f);

        //debug_bg = new Texture(Gdx.files.internal("textures/debug_background.png"));
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batcher.begin();

        world.box2DWorld.debugRender();
        //batcher.draw(AssetLoader.game_background, 0, 0);
        //batcher.draw(debug_bg, 0, 0);
        drawCrane(world.box2DWorld.getCrane());

        float pattern_start_x = Gdx.graphics.getWidth()/2 - size/2 * AssetLoader.unitBlockTexture.getWidth();
        float pattern_start_y = GROUND_HEIGHT;
        Frame myFrame = new Frame(size);
        myFrame.setPattern(new Pattern(size));
        boolean[][] frame = myFrame.frame;

        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                if (frame[i][j]) {
                    batcher.draw(AssetLoader.unitBlockTexture, pattern_start_x + i * AssetLoader.unitBlockTexture.getWidth(),
                            pattern_start_y + j * AssetLoader.unitBlockTexture.getHeight());
                }
            }
        }

        /*
        Body crane = null, holding_box = null, next_box = null;

        for (int i = 0; i < world.bodyArray.size; i++) {
            Body body = world.bodyArray.get(i);

            if (world.bodyArray.get(i).getType() == BodyDef.BodyType.DynamicBody) {
                drawBlock(body);
            }

            else if (world.bodyArray.get(i).getType() == BodyDef.BodyType.KinematicBody) {
                drawCrane(body);
            }

            else {
                if (body == world.physics_engine.cranedBody) {

                    holding_box = body;
                }
                else if (body == world.physics_engine.nextBody) {

                    next_box = body;
                }
            }
        }

        if (world.physics_engine.cranedBody != null) {
            switch ((int) ((Block) world.physics_engine.cranedBody.getUserData()).density) {
                case 1: {
                    drawOneKG(world.physics_engine.cranedBody);
                    break;
                }
                case 2: {
                    drawTwoKG(world.physics_engine.cranedBody);
                    break;
                }
                case 3: {
                    drawThreeKG(world.physics_engine.cranedBody);
                    break;
                }
                case 4: {
                    drawFourKG(world.physics_engine.cranedBody);
                    break;
                }
                case 5: {
                    drawFiveKG(world.physics_engine.cranedBody);
                    break;
                }
                default:
            }
            AssetLoader.consolas_15.draw(batcher, String.format("%d %.1f %.1f", (int) ((Block) world.physics_engine.cranedBody.getUserData()).weight,
                    ((Block) world.physics_engine.cranedBody.getUserData()).remainingCapacity,
                    ((Block) world.physics_engine.cranedBody.getUserData()).remainingTime),
                    world.physics_engine.cranedBody.getPosition().x * 100f - 35,
                    world.physics_engine.cranedBody.getPosition().y * 100f);
        }

        if (world.physics_engine.isHoldingNext) {
            switch ((int) world.physics_engine.nextBlock.density) {
                case 1: {
                    drawOneKG(next_box);
                    break;
                }
                case 2: {
                    drawTwoKG(next_box);
                    break;
                }
                case 3: {
                    drawThreeKG(next_box);
                    break;
                }
                case 4: {
                    drawFourKG(next_box);
                    break;
                }
                case 5: {
                    drawFiveKG(next_box);
                    break;
                }
                default:
            }
            AssetLoader.consolas_15.draw(batcher, String.format("%d %.1f %.1f", (int) ((Block) next_box.getUserData()).weight,
                    ((Block) next_box.getUserData()).remainingCapacity, ((Block) next_box.getUserData()).remainingTime),
                    next_box.getPosition().x * 100f - 35, next_box.getPosition().y * 100f);
        }
        */
        batcher.end();
        world.getStage().draw();
    }

    //CRANE
    public void drawCrane(Body crane) {
        AssetLoader.game_crane.setPosition(100f * crane.getPosition().x - 37f, 100f* crane.getPosition().y);
        AssetLoader.game_crane.draw(batcher);
    }

    public void drawBlock(Body body) {

    }


    //METHODS FOR DRAWING BLOCKS
    //NOT FOR NEXT BODIES / CRANED BODIES / ANYTHING ELSE

    /*
    public void drawBlock(Body body) {
        switch ((int) ((Block) body.getUserData()).density) {
            case 1: {
                drawOneKG(body);
                break;
            }
            case 2: {
                drawTwoKG(body);
                break;
            }
            case 3: {
                drawThreeKG(body);
                break;
            }
            case 4: {
                drawFourKG(body);
                break;
            }
            case 5: {
                drawFiveKG(body);
                break;
            }
            default:
        }
        AssetLoader.consolas_15.draw(batcher, String.format("%d %.1f %.1f", (int)((Block) body.getUserData()).weight,((Block)body.getUserData()).remainingCapacity, ((Block)body.getUserData()).remainingTime),body.getPosition().x * 100f - 35, body.getPosition().y * 100f + 319);
    }
    */

    /*
    public void drawOneKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Block) body.getUserData()).type_int);
        if (body == world.physics_engine.nextBody || body == world.physics_engine.cranedBody) translated_position.y -= 323;

        switch (((Block) body.getUserData()).type_int) {
            case 11: {
                AssetLoader.block_sprite_1_11.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_1_11.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_1_11.draw(batcher);
                break;
            }
            case 12: {
                AssetLoader.block_sprite_1_12.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_1_12.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_1_12.draw(batcher);
                break;
            }
            case 21: {
                AssetLoader.block_sprite_1_21.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_1_21.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_1_21.draw(batcher);
                break;
            }
            case 22: {
                AssetLoader.block_sprite_1_22.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_1_22.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_1_22.draw(batcher);
                break;
            }
            case 32: {
                AssetLoader.block_sprite_1_32.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_1_32.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_1_32.draw(batcher);
                break;
            }
            case 31: {
                AssetLoader.block_sprite_1_31.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_1_31.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_1_31.draw(batcher);
                break;
            }
            default:
        }
    }
    */

    /*
    public void drawTwoKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Block) body.getUserData()).type_int);
        if (body == world.physics_engine.nextBody || body == world.physics_engine.cranedBody) translated_position.y -= 323;

        switch (((Block) body.getUserData()).type_int) {
            case 11: {
                AssetLoader.block_sprite_2_11.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_2_11.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_2_11.draw(batcher);
                break;
            }
            case 12: {
                AssetLoader.block_sprite_2_12.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_2_12.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_2_12.draw(batcher);
                break;
            }
            case 21: {
                AssetLoader.block_sprite_2_21.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_2_21.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_2_21.draw(batcher);
                break;
            }
            case 22: {
                AssetLoader.block_sprite_2_22.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_2_22.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_2_22.draw(batcher);
                break;
            }
            case 32: {
                AssetLoader.block_sprite_2_32.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_2_32.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_2_32.draw(batcher);
                break;
            }
            case 31: {
                AssetLoader.block_sprite_2_31.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_2_31.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_2_31.draw(batcher);
                break;
            }
            default:
        }
    }
    */

    /*
    public void drawThreeKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Block) body.getUserData()).type_int);
        if (body == world.physics_engine.nextBody || body == world.physics_engine.cranedBody) translated_position.y -= 323;

        switch (((Block) body.getUserData()).type_int) {
            case 11: {
                AssetLoader.block_sprite_3_11.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_3_11.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_3_11.draw(batcher);
                break;
            }
            case 12: {
                AssetLoader.block_sprite_3_12.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_3_12.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_3_12.draw(batcher);
                break;
            }
            case 21: {
                AssetLoader.block_sprite_3_21.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_3_21.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_3_21.draw(batcher);
                break;
            }
            case 22: {
                AssetLoader.block_sprite_3_22.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_3_22.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_3_22.draw(batcher);
                break;
            }
            case 32: {
                AssetLoader.block_sprite_3_32.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_3_32.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_3_32.draw(batcher);
                break;
            }
            case 31: {
                AssetLoader.block_sprite_3_31.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_3_31.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_3_31.draw(batcher);
                break;
            }
            default:
        }
    }
    */

    /*
    public void drawFourKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Block) body.getUserData()).type_int);
        if (body == world.physics_engine.nextBody || body == world.physics_engine.cranedBody) translated_position.y -= 323;

        switch (((Block) body.getUserData()).type_int) {
            case 11: {
                AssetLoader.block_sprite_4_11.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_4_11.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_4_11.draw(batcher);
                break;
            }
            case 12: {
                AssetLoader.block_sprite_4_12.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_4_12.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_4_12.draw(batcher);
                break;
            }
            case 21: {
                AssetLoader.block_sprite_4_21.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_4_21.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_4_21.draw(batcher);
                break;
            }
            case 22: {
                AssetLoader.block_sprite_4_22.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_4_22.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_4_22.draw(batcher);
                break;
            }
            case 32: {
                AssetLoader.block_sprite_4_32.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_4_32.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_4_32.draw(batcher);
                break;
            }
            case 31: {
                AssetLoader.block_sprite_4_31.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_4_31.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_4_31.draw(batcher);
                break;
            }
            default:
        }
    }
    */

    /*
    public void drawFiveKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Block) body.getUserData()).type_int);
        if (body == world.physics_engine.nextBody || body == world.physics_engine.cranedBody) translated_position.y -= 323;

        switch (((Block) body.getUserData()).type_int) {
            case 11: {
                AssetLoader.block_sprite_5_11.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_5_11.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_5_11.draw(batcher);
                break;
            }
            case 12: {
                AssetLoader.block_sprite_5_12.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_5_12.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_5_12.draw(batcher);
                break;
            }
            case 21: {
                AssetLoader.block_sprite_5_21.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_5_21.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_5_21.draw(batcher);
                break;
            }
            case 22: {
                AssetLoader.block_sprite_5_22.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_5_22.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_5_22.draw(batcher);
                break;
            }
            case 32: {
                AssetLoader.block_sprite_5_32.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_5_32.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_5_32.draw(batcher);
                break;
            }
            case 31: {
                AssetLoader.block_sprite_5_31.setPosition(translated_position.x, translated_position.y);
                AssetLoader.block_sprite_5_31.setRotation((float) Math.toDegrees(body.getAngle()));
                AssetLoader.block_sprite_5_31.draw(batcher);
                break;
            }
            default:
        }
    }
    */

    //FOR TRANSLATING WORLD POSITION TO SCREEN POSITION - only for blocks
    //CRANED BODIES AND NEXT BODIES DONT APPLY
    public Vector2 translatePosition(float world_x, float world_y, int block_dimension) {
        Vector2 return_vector = new Vector2();

        int width = block_dimension / 10;
        int height = block_dimension % 10;

        return_vector.x = world_x * 100f - width * 100f / 2;
        return_vector.y = world_y * 100f - height * 100f / 2 + 323;

        return return_vector;
    }
}