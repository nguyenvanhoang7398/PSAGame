package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.thechallengers.psagame.SinglePlayer.Box2DWorld;
import com.thechallengers.psagame.SinglePlayer.Objects.NextBlock;
import com.thechallengers.psagame.SinglePlayer.Objects.Worker;
import com.thechallengers.psagame.SinglePlayer.Physics.Block;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.game.PSAGame;
import com.thechallengers.psagame.helpers.AssetLoader;

import com.thechallengers.psagame.Tutorial.Objects.FadeInFadeOutActor;

import java.util.ArrayDeque;
import java.util.ArrayList;

import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_LEVEL;
import static com.thechallengers.psagame.EndGame.EndGameScreen.END_SCREEN_TIME;
import static com.thechallengers.psagame.game.PSAGame.CURRENT_SCREEN;
import static com.thechallengers.psagame.game.PSAGame.LONG_EDGE;
import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

/**
 * Created by Phung Tuan Hoang on 10/1/2017.
 */

public class TutorialWorld implements ScreenWorld {
    Worker worker;
    private float gameTime;
    private float worldTime;
    public boolean hasStarted = false;
    public ArrayList<NextBlock> nextBlockArrayList;
    public ArrayDeque<Block> previousNextBlockQ;
    final static float PERCENTAGE_THRESHOLD = 0.5f;
    private World world;
    private Stage stage;
    private Array<Body> bodyArray;
    private FadeInFadeOutActor balloon;
    private FadeInFadeOutActor instructor;
    private FadeInFadeOutActor pointer;
    public Box2DWorld box2DWorld;
    private boolean isRefusingInput = false;
    private TextButton destroyButton;
    public enum TutorialState {
        WELCOME, AIM, DROP, DROP_INDICATOR, TILTED, TILTED_INDICATOR, AFTER_TILTED, AFTER_TILTED_INDICATOR,
        PROGRESS, DESTROY, DESTROY_INDICATOR_1, DESTROY_INDICATOR_2, GOODLUCK
    }

    private Queue<TutorialState> stateQueue;

    public TutorialWorld() {
        box2DWorld = new Box2DWorld(1);
        this.world = box2DWorld.getWorld();
        stage = new Stage();
        bodyArray = new Array<Body>();
        stateQueue = new Queue<TutorialState>();

        initialiseStateQueue();

        createOnScreenInstructions();
        createUI();

        worldTime = 300;
        gameTime = 0;
        nextBlockArrayList = new ArrayList<NextBlock>();
        previousNextBlockQ = box2DWorld.nextBlockQ.clone();
        loadNextBlockActors();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) CURRENT_SCREEN = PSAGame.Screen.MenuScreen;
        box2DWorld.update(delta);
        if (box2DWorld.getPercentageOverlap() > PERCENTAGE_THRESHOLD) {
            END_SCREEN_TIME = gameTime;
            END_SCREEN_LEVEL = 1;
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

        if (gForce > 2 && box2DWorld.cooldown <= 0 && hasStarted) {
            box2DWorld.destroyMode = true;
        }

        if (checkIfDropped()) {
            nextBlockArrayList.get(0).remove();
            nextBlockArrayList.remove(0);
            updateActorQ();
        }

        previousNextBlockQ = box2DWorld.nextBlockQ.clone();

        if (!hasStarted && gForce > 2 && stateQueue.first() == TutorialState.DESTROY_INDICATOR_1) {
            System.out.println("CALLLED");
            clicked();
            getStateQueue().removeFirst();
            createOnScreenInstructions();
            box2DWorld.destroyMode = true;
        }

        if (stateQueue.size != 0) System.out.printf("%s %s\n", stateQueue.first() ,box2DWorld.destroyMode);
    }

    private void initialiseStateQueue() {
        stateQueue.addLast(TutorialState.WELCOME);
        stateQueue.addLast(TutorialState.AIM);
        stateQueue.addLast(TutorialState.DROP);
        stateQueue.addLast(TutorialState.DROP_INDICATOR);
        stateQueue.addLast(TutorialState.TILTED);
        stateQueue.addLast(TutorialState.TILTED_INDICATOR);
        stateQueue.addLast(TutorialState.AFTER_TILTED);
        stateQueue.addLast(TutorialState.AFTER_TILTED_INDICATOR);
        stateQueue.addLast(TutorialState.PROGRESS);
        stateQueue.addLast(TutorialState.DESTROY);
        stateQueue.addLast(TutorialState.DESTROY_INDICATOR_1);
        stateQueue.addLast(TutorialState.DESTROY_INDICATOR_2);
        stateQueue.addLast(TutorialState.GOODLUCK);
    }

    private void createPointer(TutorialState state) {
        switch (state) {
            case WELCOME:
                break;
            case AIM:
                break;
            case DROP:
                break;
            case DROP_INDICATOR:
                pointer = new FadeInFadeOutActor(AssetLoader.pointer_drop, 0, 0);
                stage.addActor(pointer);
                break;
            case TILTED:
                break;
            case TILTED_INDICATOR:
                pointer = new FadeInFadeOutActor(AssetLoader.pointer_tilted, 0, 0);
                stage.addActor(pointer);
                break;
            case AFTER_TILTED:
                break;
            case AFTER_TILTED_INDICATOR:
                pointer = new FadeInFadeOutActor(AssetLoader.pointer_after_tilted, 0, 0);
                stage.addActor(pointer);
                break;
            case PROGRESS:
                break;
            case DESTROY:
                break;
            case DESTROY_INDICATOR_1:
                pointer = new FadeInFadeOutActor(AssetLoader.pointer_destroy_1, 0, 0);
                stage.addActor(pointer);
                break;
            case DESTROY_INDICATOR_2:
                pointer = new FadeInFadeOutActor(AssetLoader.pointer_destroy_2, 0, 0);
                stage.addActor(pointer);
                break;
            case GOODLUCK:
                break;
        }
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

    private void createInstructor(TutorialState state) {
        switch (state) {
            case WELCOME: {
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_welcome, 0, 0);
                stage.addActor(instructor);
                break;
            }
            case AIM:
                break;
            case DROP:
                break;
            case DROP_INDICATOR:
                break;
            case TILTED:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_tilted, 0, 0, 2f);
                stage.addActor(instructor);
                break;
            case TILTED_INDICATOR:
                break;
            case AFTER_TILTED:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_after_tilted, 0, 0, 2.75f);
                stage.addActor(instructor);
                break;
            case AFTER_TILTED_INDICATOR:
                break;
            case PROGRESS:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_progress, 0, 0, 2f);
                stage.addActor(instructor);
                break;
            case DESTROY:
                break;
            case DESTROY_INDICATOR_1:
                break;
            case DESTROY_INDICATOR_2:
                break;
            case GOODLUCK:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_goodluck, 0, 0);
                stage.addActor(instructor);
                break;
        }
    }

    private void createBalloon(TutorialState state) {
        switch (state) {
            case WELCOME: {
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 0, 0);
                stage.addActor(balloon);
                break;
            }
            case AIM:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_aim, 0, 0);
                stage.addActor(balloon);
                break;
            case DROP:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_drop, 0, 0);
                stage.addActor(balloon);
                break;
            case DROP_INDICATOR:
                break;
            case TILTED:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_tilted, 0, 0, 2f);
                stage.addActor(balloon);
                break;
            case TILTED_INDICATOR:
                break;
            case AFTER_TILTED:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_after_tilted, 0, 0, 2.75f);
                stage.addActor(balloon);
                break;
            case AFTER_TILTED_INDICATOR:
                break;
            case PROGRESS:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_progress, 0, 0, 2f);
                stage.addActor(balloon);
                break;
            case DESTROY:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_destroy, 0, 0);
                stage.addActor(balloon);
                break;
            case DESTROY_INDICATOR_1:
                break;
            case DESTROY_INDICATOR_2:
                break;
            case GOODLUCK:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_goodluck, 0, 0);
                stage.addActor(balloon);
                break;
        }
    }

    public void createOnScreenInstructions() {
        if (stateQueue.size == 0) return;
        createInstructor(stateQueue.first());
        createBalloon(stateQueue.first());
        createPointer(stateQueue.first());
    }

    public void clicked() {
        switch (stateQueue.first()) {
            case WELCOME:
                balloon.clicked();
                break;
            case AIM:
                balloon.clicked();
                break;
            case DROP:
                instructor.clicked();
                balloon.clicked();
                break;
            case DROP_INDICATOR:
                pointer.clicked();
                break;
            case TILTED:
                instructor.clicked();
                balloon.clicked();
                break;
            case TILTED_INDICATOR:
                pointer.clicked();
                break;
            case AFTER_TILTED:
                instructor.clicked();
                balloon.clicked();
                break;
            case AFTER_TILTED_INDICATOR:
                pointer.clicked();
                break;
            case PROGRESS:
                balloon.clicked();
                break;
            case DESTROY:
                instructor.clicked();
                balloon.clicked();
                break;
            case DESTROY_INDICATOR_1:
                pointer.clicked();
                break;
            case DESTROY_INDICATOR_2:
                pointer.clicked();
                break;
            case GOODLUCK:
                instructor.clicked();
                balloon.clicked();
                break;
        }
    }

    public void createUI() {
        //create the skin library for touchpad and release Button

        TextButton.TextButtonStyle destroyStyle = new TextButton.TextButtonStyle();
        Drawable destroyBG = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/dynamite.png")), 419, 375));
        destroyStyle.up = destroyBG;
        destroyStyle.down = destroyBG;
        destroyStyle.font = AssetLoader.arial;
        destroyButton = new TextButton("", destroyStyle);
        destroyButton.setBounds(SHORT_EDGE-(30+200), LONG_EDGE-(15+200), 200, 200);
        //addListenerToDestroyButton();
    }

    public void addListenerToDestroyButton() {
        destroyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Destroy button selected");
                if (box2DWorld.cooldown <= 0) {
                    box2DWorld.destroyMode = true;
                }
            }
        });
    }

    public World getWorld() {
        return world;
    }

    public Stage getStage() {
        return stage;
    }

    public Array<Body> getBodyArray() {
        return bodyArray;
    }

    public FadeInFadeOutActor getBalloon() {
        return balloon;
    }

    public FadeInFadeOutActor getInstructor() {
        return instructor;
    }

    public FadeInFadeOutActor getPointer() {
        return pointer;
    }

    public Box2DWorld getBox2DWorld() {
        return box2DWorld;
    }

    public Queue<TutorialState> getStateQueue() {
        return stateQueue;
    }

    public boolean isRefusingInput() {
        return isRefusingInput;
    }

    public TextButton getDestroyButton() {
        return destroyButton;
    }

    public float getWorldTime() {
        return gameTime;
    }
}
