package com.thechallengers.psagame.Dummy;

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
        batcher.begin();

        batcher.draw(AssetLoader.game_background, 0, 0);

        batcher.end();
    }
}
