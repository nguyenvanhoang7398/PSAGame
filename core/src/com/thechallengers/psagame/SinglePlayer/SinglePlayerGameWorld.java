package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.thechallengers.psagame.EndGame.EndGameWorld;
import com.thechallengers.psagame.SinglePlayer.Objects.NextBlock;
import com.thechallengers.psagame.SinglePlayer.Objects.Worker;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_LEVEL;
import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_PERCENT;
import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_TIME;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;
import static com.thechallengers.psagame.game.PSAGame.LONG_EDGE;
import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class SinglePlayerGameWorld implements ScreenWorld {
    final static float PERCENTAGE_THRESHOLD = 0.5f;
    private Stage stage;
    private World world;
    public Array<Body> bodyArray = new Array<Body>();
    private Worker worker;
    private float gameTime;
    private float worldTime;
    public boolean hasStarted = false;
    public ArrayList<NextBlock> nextBlockArrayList;
    public ArrayDeque<Block> previousNextBlockQ;
    private int level;

    public Box2DWorld box2DWorld;


    public SinglePlayerGameWorld(int level) {
        worldTime = 300;
        gameTime = 0;
        this.level = level;
        worker = new Worker();
        stage = new Stage();
        nextBlockArrayList = new ArrayList<NextBlock>();
        box2DWorld = new Box2DWorld(level);
        previousNextBlockQ = box2DWorld.nextBlockQ.clone();
        this.world = box2DWorld.getWorld();
        loadNextBlockActors();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
        box2DWorld.update(delta);
        if (box2DWorld.getPercentageOverlap() > PERCENTAGE_THRESHOLD) {
            END_SCREEN_TIME = gameTime;
            END_SCREEN_LEVEL = level;
            if (box2DWorld.endGameWaitTime >= 0.5f) {
                AssetLoader.winningBG = ScreenUtils.getFrameBufferTexture();
                CURRENT_SCREEN = PSAGame.Screen.EndGameScreen;
            }
            return;
        }
        stage.act(delta);
        world.getBodies(bodyArray);
        if (box2DWorld.cooldown > 0) box2DWorld.cooldown -= delta;
        if (hasStarted) {
            gameTime += delta;
            worldTime -= delta;
        }

        if (worldTime < 0) {
            AssetLoader.losingBG = ScreenUtils.getFrameBufferTexture();
            CURRENT_SCREEN = PSAGame.Screen.EndGameScreen;
            return;
        }

        float xGrav = Gdx.input.getAccelerometerX() / 9.81f;
        float yGrav = Gdx.input.getAccelerometerY() / 9.81f;
        float zGrav = Gdx.input.getAccelerometerZ() / 9.81f;

        // gForce will be close to 1 when there is no movement.
        float gForce = (float)Math.sqrt((xGrav * xGrav) + (yGrav * yGrav) + (zGrav * zGrav));

        if (gForce > 2 && box2DWorld.cooldown <= 0) {
            box2DWorld.destroyMode = true;
        }

        if (checkIfDropped()) {
            nextBlockArrayList.get(0).remove();
            nextBlockArrayList.remove(0);
            updateActorQ();
        }

        previousNextBlockQ = box2DWorld.nextBlockQ.clone();
    }

    public void loadNextBlockActors() {
        ArrayDeque<Block> nextBlockQ = box2DWorld.nextBlockQ.clone();

        for (int i = 0; i < 3; i++) {
            NextBlock nextBlock = new NextBlock(nextBlockQ.removeLast());
            switch (i) {
                case 0:
                    nextBlock.setPosition(0, 1920 - 225);
                    break;
                case 1:
                    nextBlock.setPosition(360, 1920 - 225);
                    break;
                case 2:
                    nextBlock.setPosition(720, 1920 - 225);
                    break;
                default:

            }
            stage.addActor(nextBlock);
            nextBlockArrayList.add(nextBlock);
        }
    }

    public void updateActorQ() {
        if (nextBlockArrayList.size() < 3) {
            ArrayDeque<Block> nextBlockQ = box2DWorld.nextBlockQ.clone();
            NextBlock nextBlock = new NextBlock(nextBlockQ.removeLast());
            nextBlock.setPosition(1080, 1920 - 225);
            stage.addActor(nextBlock);
            nextBlockArrayList.add(nextBlock);

            for (int i = 0; i < nextBlockArrayList.size(); i++) {
                NextBlock thisBlock = nextBlockArrayList.get(i);
                thisBlock.addAction(Actions.moveTo(thisBlock.getX() - 360, thisBlock.getY(), 0.2f));
            }
        }
    }

    public boolean checkIfDropped() {
        Object[] array_1 = previousNextBlockQ.toArray();
        Object[] array_2 = box2DWorld.nextBlockQ.toArray();

        if (((Block) array_1[0]).blockType.equals(((Block) array_2[0]).blockType) &&
                ((Block) array_1[1]).blockType.equals(((Block) array_2[1]).blockType) &&
                ((Block) array_1[2]).blockType.equals(((Block) array_2[2]).blockType)) {
            return false;
        }
        else return true;
    }


    public Stage getStage() {
        return stage;
    }

    public World getWorld() {
        return world;
    }

    public Worker getWorker() { return worker;}

    public float getWorldTime() {
        return worldTime;
    }
}
