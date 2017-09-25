package com.thechallengers.psagame.Dummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.thechallengers.psagame.Dummy.Objects.Dummy;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

/*
    Khi draw sprite, multiply y voi
 */
public class SinglePlayerGameRenderer extends ScreenRenderer {
    private SinglePlayerGameWorld world;
    private Sprite block_sprite;
    private Sprite block_sprite_1_11, block_sprite_1_12, block_sprite_1_21, block_sprite_1_22, block_sprite_1_32, block_sprite_1_31;
    private Sprite block_sprite_2_11, block_sprite_2_12, block_sprite_2_21, block_sprite_2_22, block_sprite_2_32, block_sprite_2_31;
    private Sprite block_sprite_3_11, block_sprite_3_12, block_sprite_3_21, block_sprite_3_22, block_sprite_3_32, block_sprite_3_31;
    private Sprite block_sprite_4_11, block_sprite_4_12, block_sprite_4_21, block_sprite_4_22, block_sprite_4_32, block_sprite_4_31;
    private Sprite block_sprite_5_11, block_sprite_5_12, block_sprite_5_21, block_sprite_5_22, block_sprite_5_32, block_sprite_5_31;
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    OrthographicCamera cam = new OrthographicCamera();

    public SinglePlayerGameRenderer(SinglePlayerGameWorld world) {
        super();
        this.world = world;
        block_sprite = new Sprite(new Texture(Gdx.files.internal("textures/block.png")));

        cam.setToOrtho(false, 10.80f, 19.20f);
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




        //debugRenderer.render(world.getWorld(), cam.combined);
        batcher.begin();

        batcher.draw(AssetLoader.game_background, 0, 0);

        for (int i = 0; i < world.bodyArray.size; i++) {
            if (world.bodyArray.get(i).getType() == BodyDef.BodyType.DynamicBody) {
                Body body = world.bodyArray.get(i);

                switch ((int) body.getFixtureList().get(0).getDensity()) {
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
            }
        }


        batcher.end();
    }

    public void drawOneKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Dummy) body.getUserData()).getSize());

        switch (((Dummy) body.getUserData()).getSize()) {
            case 11: {
                block_sprite_1_11.setPosition(translated_position.x, translated_position.y);
                block_sprite_1_11.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_1_11.draw(batcher);
                break;
            }
            case 12: {
                block_sprite_1_12.setPosition(translated_position.x, translated_position.y);
                block_sprite_1_12.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_1_12.draw(batcher);
                break;
            }
            case 21: {
                block_sprite_1_21.setPosition(translated_position.x, translated_position.y);
                block_sprite_1_21.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_1_21.draw(batcher);
                break;
            }
            case 22: {
                block_sprite_1_22.setPosition(translated_position.x, translated_position.y);
                block_sprite_1_22.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_1_22.draw(batcher);
                break;
            }
            case 32: {
                block_sprite_1_32.setPosition(translated_position.x, translated_position.y);
                block_sprite_1_32.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_1_32.draw(batcher);
                break;
            }
            case 31: {
                block_sprite_1_31.setPosition(translated_position.x, translated_position.y);
                block_sprite_1_31.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_1_31.draw(batcher);
                break;
            }
            default:
        }
    }

    public void drawTwoKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Dummy) body.getUserData()).getSize());

        switch (((Dummy) body.getUserData()).getSize()) {
            case 11: {
                block_sprite_2_11.setPosition(translated_position.x, translated_position.y);
                block_sprite_2_11.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_2_11.draw(batcher);
                break;
            }
            case 12: {
                block_sprite_2_12.setPosition(translated_position.x, translated_position.y);
                block_sprite_2_12.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_2_12.draw(batcher);
                break;
            }
            case 21: {
                block_sprite_2_21.setPosition(translated_position.x, translated_position.y);
                block_sprite_2_21.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_2_21.draw(batcher);
                break;
            }
            case 22: {
                block_sprite_2_22.setPosition(translated_position.x, translated_position.y);
                block_sprite_2_22.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_2_22.draw(batcher);
                break;
            }
            case 32: {
                block_sprite_2_32.setPosition(translated_position.x, translated_position.y);
                block_sprite_2_32.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_2_32.draw(batcher);
                break;
            }
            case 31: {
                block_sprite_2_31.setPosition(translated_position.x, translated_position.y);
                block_sprite_2_31.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_2_31.draw(batcher);
                break;
            }
            default:
        }
    }

    public void drawThreeKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Dummy) body.getUserData()).getSize());

        switch (((Dummy) body.getUserData()).getSize()) {
            case 11: {
                block_sprite_3_11.setPosition(translated_position.x, translated_position.y);
                block_sprite_3_11.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_3_11.draw(batcher);
                break;
            }
            case 12: {
                block_sprite_3_12.setPosition(translated_position.x, translated_position.y);
                block_sprite_3_12.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_3_12.draw(batcher);
                break;
            }
            case 21: {
                block_sprite_3_21.setPosition(translated_position.x, translated_position.y);
                block_sprite_3_21.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_3_21.draw(batcher);
                break;
            }
            case 22: {
                block_sprite_3_22.setPosition(translated_position.x, translated_position.y);
                block_sprite_3_22.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_3_22.draw(batcher);
                break;
            }
            case 32: {
                block_sprite_3_32.setPosition(translated_position.x, translated_position.y);
                block_sprite_3_32.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_3_32.draw(batcher);
                break;
            }
            case 31: {
                block_sprite_3_31.setPosition(translated_position.x, translated_position.y);
                block_sprite_3_31.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_3_31.draw(batcher);
                break;
            }
            default:
        }
    }

    public void drawFourKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Dummy) body.getUserData()).getSize());

        switch (((Dummy) body.getUserData()).getSize()) {
            case 11: {
                block_sprite_4_11.setPosition(translated_position.x, translated_position.y);
                block_sprite_4_11.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_4_11.draw(batcher);
                break;
            }
            case 12: {
                block_sprite_4_12.setPosition(translated_position.x, translated_position.y);
                block_sprite_4_12.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_4_12.draw(batcher);
                break;
            }
            case 21: {
                block_sprite_4_21.setPosition(translated_position.x, translated_position.y);
                block_sprite_4_21.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_4_21.draw(batcher);
                break;
            }
            case 22: {
                block_sprite_4_22.setPosition(translated_position.x, translated_position.y);
                block_sprite_4_22.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_4_22.draw(batcher);
                break;
            }
            case 32: {
                block_sprite_4_32.setPosition(translated_position.x, translated_position.y);
                block_sprite_4_32.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_4_32.draw(batcher);
                break;
            }
            case 31: {
                block_sprite_4_31.setPosition(translated_position.x, translated_position.y);
                block_sprite_4_31.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_4_31.draw(batcher);
                break;
            }
            default:
        }
    }

    public void drawFiveKG(Body body) {
        Vector2 translated_position = translatePosition(body.getPosition().x, body.getPosition().y, ((Dummy) body.getUserData()).getSize());

        switch (((Dummy) body.getUserData()).getSize()) {
            case 11: {
                block_sprite_5_11.setPosition(translated_position.x, translated_position.y);
                block_sprite_5_11.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_5_11.draw(batcher);
                break;
            }
            case 12: {
                block_sprite_5_12.setPosition(translated_position.x, translated_position.y);
                block_sprite_5_12.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_5_12.draw(batcher);
                break;
            }
            case 21: {
                block_sprite_5_21.setPosition(translated_position.x, translated_position.y);
                block_sprite_5_21.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_5_21.draw(batcher);
                break;
            }
            case 22: {
                block_sprite_5_22.setPosition(translated_position.x, translated_position.y);
                block_sprite_5_22.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_5_22.draw(batcher);
                break;
            }
            case 32: {
                block_sprite_5_32.setPosition(translated_position.x, translated_position.y);
                block_sprite_5_32.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_5_32.draw(batcher);
                break;
            }
            case 31: {
                block_sprite_5_31.setPosition(translated_position.x, translated_position.y);
                block_sprite_5_31.setRotation((float) Math.toDegrees(body.getAngle()));
                block_sprite_5_31.draw(batcher);
                break;
            }
            default:
        }
    }

    //FOR TRANSLATING WORLD POSITION TO SCREEN POSITION
    public Vector2 translatePosition(float world_x, float world_y, int block_dimension) {
        Vector2 return_vector = new Vector2();

        int width = block_dimension / 10;
        int height = block_dimension % 10;

        return_vector.x = world_x * 100f - width * 100f / 2;
        return_vector.y = world_y * 100f - height * 100f / 2 + 323;

        return return_vector;
    }
}
