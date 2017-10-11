package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.SinglePlayer.Physics.Frame;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.ArrayDeque;

import static com.thechallengers.psagame.SinglePlayer.Box2DWorld.NUM_NEXT_BLOCK_INFORMED;

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
    public static float GROUND_HEIGHT = 75f;
    public static int size = 100;
    public static float NEXT_BLOCK_SCALE = 1f;
    public static float DESTROY_X_SCALE = 0.5f;
    public Frame myFrame;
    public static float cooldown_animation_runTime = 0;

    public SinglePlayerGameRenderer(SinglePlayerGameWorld world) {
        super();
        this.world = world;

        cam.setToOrtho(false, 10.80f, 19.20f);

        //debug_bg = new Texture(Gdx.files.internal("textures/debug_background.png"));
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        //world.box2DWorld.debugRender();
        batcher.draw(AssetLoader.game_background, 0, 0);
        if (world.box2DWorld.destroyMode) batcher.draw(AssetLoader.destroy_mode, 1080 / 2 - 500 / 2, 1920 / 2 - 100 / 2);
        batcher.draw(AssetLoader.silhouette_1, 0, 0);

        //BLOCKS
        for (int i = 0; i < world.box2DWorld.bodyArray.size; i++) {
            if (world.box2DWorld.bodyArray.get(i).getUserData() instanceof Block) {
                Body body = world.box2DWorld.bodyArray.get(i);
                Block block = (Block) world.box2DWorld.bodyArray.get(i).getUserData();
                Sprite sprite = AssetLoader.spriteHashtable.get(block.blockType);
                Vector2 translatedPosition = translatePosition(body.getPosition().x, body.getPosition().y, Integer.parseInt(block.blockType));
                sprite.setPosition(translatedPosition.x, translatedPosition.y);
                sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
                sprite.draw(batcher);
                sprite.setRotation(0);
            }
        }

        drawCrane(world.box2DWorld.getCrane());

        // INCOMING BLOCKS
        ArrayDeque<Block> copiedNextBlockQ = world.box2DWorld.nextBlockQ.clone();
        renderNextBlock(copiedNextBlockQ);

        AssetLoader.consolas_60.draw(batcher, "Next", 40, 1600);
        AssetLoader.consolas_60.draw(batcher, String.format("Progress: %d%%", (int) (world.box2DWorld.getPercentageOverlap() * 100)), 620, 1880);
        AssetLoader.consolas_60.draw(batcher, String.format("Time: %.0f", world.getWorldTime()), 620, 1780);

        if (world.box2DWorld.cooldown <= 0) AssetLoader.consolas_60.draw(batcher, String.format("Destroy: Rdy!", world.getWorldTime()), 620, 1680);
        else {
            AssetLoader.consolas_60.draw(batcher, String.format("Destroy: ", world.getWorldTime()), 620, 1680);
            cooldown_animation_runTime += Gdx.graphics.getDeltaTime();
            if (cooldown_animation_runTime <= 4) batcher.draw(AssetLoader.cooldown_animation.getKeyFrame(cooldown_animation_runTime, false), 900, 1610);
        }


        batcher.end();
    }

    //CRANE
    public void drawCrane(Body crane) {
        AssetLoader.game_crane.setPosition(100f * crane.getPosition().x - 37f, 100f* crane.getPosition().y);
        AssetLoader.game_crane.draw(batcher);
    }

    //NEXT BLOCKS
    public void renderNextBlock(ArrayDeque<Block> copiedNextBlockQ) {
        float nextX = 50f;
        float offsetX = 50f;
        final float GAP = 20f;
        final float MAX_HEIGHT = 200f;
        float offsetGap = 0;
        float offsetY = Gdx.graphics.getHeight() - 200f;

        for (int i = 0; i < NUM_NEXT_BLOCK_INFORMED; i++) {
            if (copiedNextBlockQ.isEmpty()) break;
            System.out.println(nextX);
            Block block = copiedNextBlockQ.removeLast();
            Sprite sprite = AssetLoader.spriteHashtable.get(block.blockType);

            if (i == 2) {
                sprite.setPosition(220f / 1080f * Gdx.graphics.getWidth(), 1470f / 1920f * Gdx.graphics.getHeight());
                if (block.height == 1) {
                    sprite.setPosition(220f / 1080f * Gdx.graphics.getWidth(), 1470f / 1920f * Gdx.graphics.getHeight() + 50);
                }
                sprite.draw(batcher);
            }
            else {
                sprite.setScale(NEXT_BLOCK_SCALE);
                nextX -= block.width * 100f * (1 - NEXT_BLOCK_SCALE) / 2f;
                sprite.setPosition(nextX, offsetY);

                if (block.height == 1) {
                    sprite.setPosition(nextX, offsetY + 50);
                }
                nextX += block.width * 100f * (1 - NEXT_BLOCK_SCALE) / 2f + block.width * 100f * NEXT_BLOCK_SCALE + 20f;
                sprite.draw(batcher);
                sprite.setScale(1);
            }
        }

    }

    //FOR TRANSLATING WORLD POSITION TO SCREEN POSITION - only for blocks
    //CRANED BODIES AND NEXT BODIES DONT APPLY
    public Vector2 translatePosition(float world_x, float world_y, int blockType) {
        Vector2 return_vector = new Vector2();

        int block_dimension = blockType % 100;

        int width = block_dimension / 10;
        int height = block_dimension % 10;

        return_vector.x = world_x * 100f - width * 100f / 2;
        return_vector.y = world_y * 100f - height * 100f / 2;

        return return_vector;
    }
}
