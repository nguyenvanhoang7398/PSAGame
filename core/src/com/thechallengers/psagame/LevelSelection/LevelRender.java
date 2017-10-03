package com.thechallengers.psagame.LevelSelection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;

/**
 * Created by name on 3/10/2017.
 */

class LevelRender extends ScreenRenderer {
    private LevelWorld world;

    //constructors
    public LevelRender(LevelWorld world) {
        super();
        this.world = world;
        stage = world.getStage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

}
