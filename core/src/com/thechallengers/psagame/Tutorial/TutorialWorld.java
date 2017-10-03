package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.thechallengers.psagame.SinglePlayer.Box2DWorld;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.helpers.AssetLoader;

import com.thechallengers.psagame.Tutorial.Objects.FadeInFadeOutActor;

/**
 * Created by Phung Tuan Hoang on 10/1/2017.
 */

public class TutorialWorld implements ScreenWorld {
    private World world;
    private Stage stage;
    private Array<Body> bodyArray;
    private FadeInFadeOutActor balloon;
    private FadeInFadeOutActor instructor;
    private FadeInFadeOutActor pointer;
    public Box2DWorld box2DWorld;
    public enum TutorialState {
        WELCOME, AIM, DROP, DROP_INDICATOR, TILTED, TILTED_INDICATOR, AFTER_TILTED, AFTER_TILTED_INDICATOR,
        PROGRESS, DESTROY, DESTROY_INDICATOR, GOODLUCK
    }

    private Queue<TutorialState> stateQueue;

    public TutorialWorld() {
        box2DWorld = new Box2DWorld();
        this.world = box2DWorld.getWorld();
        stage = new Stage();
        bodyArray = new Array<Body>();
        stateQueue = new Queue<TutorialState>();

        initialiseStateQueue();

        createOnScreenInstructions();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        box2DWorld.update(delta);
        //need a condition here box2DWorld.update(delta);
        world.getBodies(bodyArray);
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
        stateQueue.addLast(TutorialState.DESTROY_INDICATOR);
        stateQueue.addLast(TutorialState.GOODLUCK);
    }

    private void createPointer(TutorialState state) {
    }

    private void createInstructor(TutorialState state) {
        switch (state) {
            case WELCOME: {
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_welcome, 58, 289);
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
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_welcome, 58, 289);
                stage.addActor(instructor);
                break;
            case TILTED_INDICATOR:
                break;
            case AFTER_TILTED:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_welcome, 58, 289);
                stage.addActor(instructor);
                break;
            case AFTER_TILTED_INDICATOR:
                break;
            case PROGRESS:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_welcome, 58, 289);
                stage.addActor(instructor);
                break;
            case DESTROY:
                break;
            case DESTROY_INDICATOR:
                break;
            case GOODLUCK:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_welcome, 58, 289);
                stage.addActor(instructor);
                break;
        }
    }

    private void createBalloon(TutorialState state) {
        switch (state) {
            case WELCOME: {
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
                stage.addActor(balloon);
                break;
            }
            case AIM:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
                stage.addActor(balloon);
                break;
            case DROP:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
                stage.addActor(balloon);
                break;
            case DROP_INDICATOR:
                break;
            case TILTED:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
                stage.addActor(balloon);
                break;
            case TILTED_INDICATOR:
                break;
            case AFTER_TILTED:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
                stage.addActor(balloon);
                break;
            case AFTER_TILTED_INDICATOR:
                break;
            case PROGRESS:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
                stage.addActor(balloon);
                break;
            case DESTROY:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
                stage.addActor(balloon);
                break;
            case DESTROY_INDICATOR:
                break;
            case GOODLUCK:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_welcome, 170, 920);
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
                //pointer.clicked();
                break;
            case TILTED:
                instructor.clicked();
                balloon.clicked();
                break;
            case TILTED_INDICATOR:
                //pointer.clicked();
                break;
            case AFTER_TILTED:
                instructor.clicked();
                balloon.clicked();
                break;
            case AFTER_TILTED_INDICATOR:
                //pointer.clicked();
                break;
            case PROGRESS:
                balloon.clicked();
                break;
            case DESTROY:
                instructor.clicked();
                balloon.clicked();
                break;
            case DESTROY_INDICATOR:
                //pointer.clicked();
                break;
            case GOODLUCK:
                instructor.clicked();
                balloon.clicked();
                break;
        }
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
}
