package com.thechallengers.psagame.Leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

/**
 * Created by VanHoang on 10/12/2017.
 */

public class LeaderboardScreen implements Screen {
    private PSAGame game;
    private LeaderboardWorld leaderboard_world;
    private LeaderboardRender leaderboard_render;
    private float runTime = 0;

    public LeaderboardScreen(PSAGame game) {
        AssetLoader.loadLeaderboardTexture();
        this.game = game;
        leaderboard_world = new LeaderboardWorld(game);
        leaderboard_render = new LeaderboardRender(leaderboard_world);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(leaderboard_world.getStage());
        runTime += delta;
        leaderboard_world.update(delta);
        leaderboard_render.render(runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        AssetLoader.disposeLeaderboardTexture();
        leaderboard_world.getStage().dispose();

    }
}
