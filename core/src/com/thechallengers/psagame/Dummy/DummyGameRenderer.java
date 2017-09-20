package com.thechallengers.psagame.Dummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class DummyGameRenderer extends ScreenRenderer {
    private DummyGameWorld world;

    public DummyGameRenderer(DummyGameWorld world) {
        super();
        this.world = world;
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        batcher.draw(AssetLoader.game_background, 0, 0, 1080, 1920);
        world.getStage().draw();

        batcher.end();
    }
}
