package com.thechallengers.psagame.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by Phung Tuan Hoang on 9/6/2017.
 */

public class MenuRenderer extends ScreenRenderer {
    private MenuWorld world;

    //constructor
    public MenuRenderer(MenuWorld world) {
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
