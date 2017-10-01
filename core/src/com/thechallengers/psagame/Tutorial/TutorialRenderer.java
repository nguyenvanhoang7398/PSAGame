package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by Phung Tuan Hoang on 10/1/2017.
 */

public class TutorialRenderer extends ScreenRenderer {
    private TutorialWorld world;

    public TutorialRenderer(TutorialWorld world) {
        super();
        this.world = world;

        cam.setToOrtho(false, 10.80f, 19.20f);
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        //world.box2DWorld.debugRender();
        batcher.draw(AssetLoader.game_background, 0, 0);

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

        batcher.end();

        world.getStage().draw();
    }

    public void drawCrane(Body crane) {
        AssetLoader.game_crane.setPosition(100f * crane.getPosition().x - 37f, 100f* crane.getPosition().y);
        AssetLoader.game_crane.draw(batcher);
    }

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
