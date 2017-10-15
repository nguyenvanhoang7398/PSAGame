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

import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_TIME;
import static com.thechallengers.psagame.SinglePlayer.Box2DWorld.NUM_NEXT_BLOCK_INFORMED;
import static com.thechallengers.psagame.game.PSAGame.LEVEL;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
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
        world.getStage().setViewport(viewport);
        batcher.begin();

        //world.box2DWorld.debugRender();
        batcher.draw(AssetLoader.game_background, 0, 0);
//        if (!world.hasStarted) batcher.draw(AssetLoader.start_game, 0, 0);
        //if (world.box2DWorld.destroyMode) batcher.draw(AssetLoader.destroy_mode, 1080 / 2 - 500 / 2, 1920 / 2 - 100 / 2);
        batcher.draw(AssetLoader.silhouetteArrayList.get(LEVEL - 1), 0, 0);
        if (!world.hasStarted) batcher.draw(AssetLoader.start_game, 0, 0);

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
        batcher.draw(AssetLoader.game_background_2, 0, 0);
        if (world.hasStarted)
            batcher.draw(AssetLoader.arrow_animation.getKeyFrame(runTime, false), 135 + 80, 1563 + 30);
        else batcher.draw(AssetLoader.arrow_animation.getKeyFrame(0, false), 135 + 80, 1563 + 30);
        AssetLoader.consolas_60.draw(batcher, "Next", 135 - 60, 1563 + 100);

        if (world.box2DWorld.cooldown <= 0) batcher.draw(AssetLoader.bomb, 915, 1582);
        else {
            cooldown_animation_runTime += Gdx.graphics.getDeltaTime();
            if (cooldown_animation_runTime <= 4)
                batcher.draw(AssetLoader.cooldown_animation.getKeyFrame(cooldown_animation_runTime, false), 915, 1582);
        }

        float percentage = world.box2DWorld.getPercentageOverlap();

        if (percentage < 0.125f) batcher.draw(AssetLoader.progress[0], 630, 1582);
        else if (percentage < 0.25f) batcher.draw(AssetLoader.progress[1], 630, 1582);
        else if (percentage < 0.375f) batcher.draw(AssetLoader.progress[2], 630, 1582);
        else if (percentage < 0.5f) batcher.draw(AssetLoader.progress[3], 630, 1582);
        else if (percentage < 0.625f) batcher.draw(AssetLoader.progress[4], 630, 1582);
        else if (percentage < 0.75f) batcher.draw(AssetLoader.progress[5], 630, 1582);
        else if (percentage < 0.875f) batcher.draw(AssetLoader.progress[6], 630, 1582);
        else if (percentage < 1f) batcher.draw(AssetLoader.progress[7], 630, 1582);
        else batcher.draw(AssetLoader.progress[8], 630, 1582);
        AssetLoader.consolas_60.draw(batcher, String.format("%d%%", (int) (world.box2DWorld.getPercentageOverlap() * 100)), 710, 1650);

        if (world.hasStarted)
            batcher.draw(AssetLoader.clock_animation.getKeyFrame(runTime, false), 340, 1582);
        else batcher.draw(AssetLoader.clock_animation.getKeyFrame(0, false), 340, 1582);
        //Time
        float time = world.getWorldTime();
        int minute = (int) (time / 60f);
        int seconds = (int) (time - minute * 60);
        String secondsString;
        if (seconds < 10) secondsString = "0" + String.valueOf(seconds);
        else secondsString = String.valueOf(seconds);
        String timeString = String.valueOf(minute) + ":" + secondsString;
        AssetLoader.consolas_60.draw(batcher, timeString, 450, 1650);
        if (world.box2DWorld.destroyMode)
            batcher.draw(AssetLoader.destroy_mode, 1080 / 2 - 500 / 2, 1920 / 2 - 100 / 2);
        batcher.end();

        //INCOMING BLOCK
        world.getStage().draw();
    }

    //CRANE
    public void drawCrane(Body crane) {
        int present_crane = Gdx.app.getPreferences("prefs").getInteger("crane_present");

        switch (present_crane) {
            case 1: {
                AssetLoader.game_crane.setPosition(100f * crane.getPosition().x - 37f, 100f * crane.getPosition().y);
                AssetLoader.game_crane.draw(batcher);
                break;
            }
            case 2: {
                AssetLoader.game_crane_2.setPosition(100f * crane.getPosition().x - 37f, 100f * crane.getPosition().y);
                AssetLoader.game_crane_2.draw(batcher);
                break;
            }
            case 3: {
                AssetLoader.game_crane_3.setPosition(100f * crane.getPosition().x - 37f, 100f * crane.getPosition().y);
                AssetLoader.game_crane_3.draw(batcher);
                break;
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

