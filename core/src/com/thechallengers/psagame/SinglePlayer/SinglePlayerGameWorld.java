package com.thechallengers.psagame.SinglePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.thechallengers.psagame.SinglePlayer.Objects.NextBlock;
import com.thechallengers.psagame.SinglePlayer.Objects.Worker;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;
import com.thechallengers.psagame.helpers.SoundLoader;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_LEVEL;
import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_PERCENT;
import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_TIME;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class SinglePlayerGameWorld implements ScreenWorld {
    final static float PERCENTAGE_THRESHOLD = 0.7f;
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
    private boolean winningSoundPlayed = false;

    public Box2DWorld box2DWorld;


    public SinglePlayerGameWorld(int level) {
        worldTime = 120;
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
        if (box2DWorld.getPercentageOverlap() > PERCENTAGE_THRESHOLD || worldTime < 0) {
            SoundLoader.musicHashtable.get("ingame_bgm.mp3").stop();
            box2DWorld.setTimesUp();
            if (!winningSoundPlayed && worldTime > 0) {
                SoundLoader.musicHashtable.get("win_sound.mp3").play();
                winningSoundPlayed = true;
            }

            END_SCREEN_TIME = gameTime;
            END_SCREEN_LEVEL = level;
            END_SCREEN_PERCENT = box2DWorld.getPercentageOverlap();
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

        // if (worldTime < 10) playSound("");

        float xGrav = Gdx.input.getAccelerometerX() / 9.81f;
        float yGrav = Gdx.input.getAccelerometerY() / 9.81f;
        float zGrav = Gdx.input.getAccelerometerZ() / 9.81f;

        // gForce will be close to 1 when there is no movement.
        float gForce = (float)Math.sqrt((xGrav * xGrav) + (yGrav * yGrav) + (zGrav * zGrav));

        if (gForce > 2 && box2DWorld.cooldown <= 0 && hasStarted) {
            box2DWorld.destroyMode = true;
        }

        if (checkIfDropped()) {
            nextBlockArrayList.get(2).remove();
            nextBlockArrayList.remove(2);
            updateActorQ();
        }

        previousNextBlockQ = box2DWorld.nextBlockQ.clone();

        addDust();
    }

    public void loadNextBlockActors() {
        ArrayDeque<Block> nextBlockQ = box2DWorld.nextBlockQ.clone();

        for (int i = 2; i >= 0; i--) {
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
            nextBlockArrayList.add(0, nextBlock);

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

    public void addDust() {
        if (box2DWorld.dust != null) {
            stage.addActor(box2DWorld.dust[0]);
            stage.addActor(box2DWorld.dust[1]);
            box2DWorld.dust = null;
        }
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
