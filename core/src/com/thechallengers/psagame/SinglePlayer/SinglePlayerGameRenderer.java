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
import com.thechallengers.psagame.SinglePlayer.Physics.Pattern;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.ArrayDeque;

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
    public static float NEXT_BLOCK_SCALE = 0.5f;

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
        //batcher.draw(debug_bg, 0, 0);

//        /*
        float pattern_start_x = Gdx.graphics.getWidth()/2 - size/2 * AssetLoader.unitBlockTexture.getWidth();
        float pattern_start_y = GROUND_HEIGHT;
        Frame myFrame = new Frame(size);
        myFrame.setPattern(new Pattern(size));
        myFrame.calculatePercentage(world.bodyArray, pattern_start_x, pattern_start_y);
        boolean[][] frame = myFrame.frame;

        System.out.println("AAA");
        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                if (frame[i][j]) {
                    batcher.draw(AssetLoader.unitBlockTexture, pattern_start_x + i * AssetLoader.unitBlockTexture.getWidth(),
                            pattern_start_y + j * AssetLoader.unitBlockTexture.getHeight());
                }
            }
        }
//        */

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
            }
        }

        drawCrane(world.box2DWorld.getCrane());

        // INCOMING BLOCKS
        ArrayDeque<Block> copiedNextBlockQ = world.box2DWorld.nextBlockQ.clone();
        float offsetX = 50f;
        float offsetY = Gdx.graphics.getHeight() - 50f;
        while (!copiedNextBlockQ.isEmpty()) {
            Block block = copiedNextBlockQ.poll();
            Sprite sprite = AssetLoader.spriteHashtable.get(block.blockType);
            sprite.setPosition(offsetX, offsetY-sprite.getHeight()*NEXT_BLOCK_SCALE);
            sprite.setRotation(0);
            offsetX += sprite.getWidth()*NEXT_BLOCK_SCALE;
            batcher.draw(sprite, sprite.getX(), sprite.getY(), sprite.getWidth()*NEXT_BLOCK_SCALE, sprite.getHeight()*NEXT_BLOCK_SCALE);
        }
        batcher.end();
        world.getStage().draw();
    }

    //CRANE
    public void drawCrane(Body crane) {
        AssetLoader.game_crane.setPosition(100f * crane.getPosition().x - 37f, 100f* crane.getPosition().y);
        AssetLoader.game_crane.draw(batcher);
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
