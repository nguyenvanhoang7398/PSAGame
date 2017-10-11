package com.thechallengers.psagame.Leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenRenderer;

/**
 * Created by VanHoang on 10/12/2017.
 */

public class LeaderboardRender extends ScreenRenderer {
    private LeaderboardWorld world;
    SpriteBatch batch;

    public LeaderboardRender(LeaderboardWorld world) {
        super();
        this.world = world;
        stage = world.getStage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }

    public void render(float runTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        batch.begin();

        batch.end();

    }
}
