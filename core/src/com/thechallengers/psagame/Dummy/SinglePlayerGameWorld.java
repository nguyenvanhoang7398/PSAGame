package com.thechallengers.psagame.Dummy;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.thechallengers.psagame.Dummy.Objects.Crane;
import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class SinglePlayerGameWorld implements ScreenWorld {
    private Crane crane;
    private Stage stage;

    public SinglePlayerGameWorld() {
        crane = new Crane();
        stage = new Stage();
        stage.addActor(crane);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    public void swipeUp() {
        System.out.println("Swiped Up");
        crane.move(Crane.DIRECTION.UP);
    }

    public void swipeDown() {
        System.out.println("Swiped Down");
        crane.move(Crane.DIRECTION.DOWN);
    }

    public void swipeLeft() {
        System.out.println("Swiped Left");
        crane.move(Crane.DIRECTION.LEFT);
    }

    public void swipeRight() {
        System.out.println("Swiped Right");
        crane.move(Crane.DIRECTION.RIGHT);
    }

    public Stage getStage() {
        return stage;
    }
}
