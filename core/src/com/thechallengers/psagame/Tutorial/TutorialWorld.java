package com.thechallengers.psagame.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.thechallengers.psagame.SinglePlayer.Box2DWorld;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;
import com.thechallengers.psagame.helpers.AssetLoader;

import com.thechallengers.psagame.Tutorial.Objects.FadeInFadeOutActor;

import static com.thechallengers.psagame.game.PSAGame.LONG_EDGE;
import static com.thechallengers.psagame.game.PSAGame.SHORT_EDGE;

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
    private boolean isRefusingInput = false;
    private TextButton destroyButton;
    public enum TutorialState {
        WELCOME, AIM, DROP, DROP_INDICATOR, TILTED, TILTED_INDICATOR, AFTER_TILTED, AFTER_TILTED_INDICATOR,
        PROGRESS, DESTROY, DESTROY_INDICATOR_1, DESTROY_INDICATOR_2, GOODLUCK
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
        createUI();
        stage.addActor(destroyButton);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        box2DWorld.update(delta);
        //need a condition here box2DWorld.update(delta);
        world.getBodies(bodyArray);
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
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_tilted, 58, 289, 2f);
                stage.addActor(instructor);
                break;
            case TILTED_INDICATOR:
                break;
            case AFTER_TILTED:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_after_tilted, 58, 289, 2.75f);
                stage.addActor(instructor);
                break;
            case AFTER_TILTED_INDICATOR:
                break;
            case PROGRESS:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_progress, 58, 289, 2f);
                stage.addActor(instructor);
                break;
            case DESTROY:
                break;
            case DESTROY_INDICATOR_1:
                break;
            case DESTROY_INDICATOR_2:
                break;
            case GOODLUCK:
                instructor = new FadeInFadeOutActor(AssetLoader.instructor_goodluck, 58, 289);
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
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_aim, 170, 920);
                stage.addActor(balloon);
                break;
            case DROP:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_drop, 170, 920);
                stage.addActor(balloon);
                break;
            case DROP_INDICATOR:
                break;
            case TILTED:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_tilted, 170, 920, 2f);
                stage.addActor(balloon);
                break;
            case TILTED_INDICATOR:
                break;
            case AFTER_TILTED:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_after_tilted, 170, 920, 2.75f);
                stage.addActor(balloon);
                break;
            case AFTER_TILTED_INDICATOR:
                break;
            case PROGRESS:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_progress, 170, 920, 2f);
                stage.addActor(balloon);
                break;
            case DESTROY:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_destroy, 170, 920);
                stage.addActor(balloon);
                break;
            case DESTROY_INDICATOR_1:
                break;
            case DESTROY_INDICATOR_2:
                break;
            case GOODLUCK:
                balloon = new FadeInFadeOutActor(AssetLoader.balloon_goodluck, 170, 920);
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
}
