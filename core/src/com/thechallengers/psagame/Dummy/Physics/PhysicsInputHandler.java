package com.thechallengers.psagame.Dummy.Physics;

/**
 * Created by Phung Tuan Hoang on 9/25/2017.
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Phung Tuan Hoang on 9/20/2017.
 */

public class PhysicsInputHandler implements InputProcessor {
    private Physics2 physics;
    private int counter = 0;
    public PhysicsInputHandler(Physics2 physics) {
        this.physics = physics;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        switch (counter) {
//            case 0:{
//                physics.createBlock(screenX/100f, (Gdx.graphics.getHeight() - screenY) /100f);
//                counter++;
//                break;
//            }
//            case 1: {
//                physics.createBlock(screenX/100f, (Gdx.graphics.getHeight() - screenY) /100f);
//                counter++;
//                break;
//            }
//            case 2: {
//                physics.createBlock(screenX/100f, (Gdx.graphics.getHeight() - screenY) /100f);
//                counter++;
//                break;
//            }
//            case 3: {
//                physics.bodyArray.get(2).setAwake(true);
//            }
//        }
        Array<Body> bodyArray = physics.bodyArray;
        Body selectedBody = null;

        for (Body body: bodyArray) {
            Fixture fixture = body.getFixtureList().get(0);
            if (!(body.getUserData() instanceof Block)) {
                continue;
            }
            if (fixture.testPoint(screenX/100f, (Gdx.graphics.getHeight() - screenY)/100f)) {
                selectedBody = body;
                break;
            }
        }

        if (selectedBody == null && physics.cranedBody == null) {
            physics.createBlock(screenX/100f, (Gdx.graphics.getHeight() - screenY) /100f);
        } else if (physics.cranedBody == null){
            physics.craneBody(selectedBody);
        } else if (selectedBody == null){
            physics.releaseCranedBody(screenX/100f, (Gdx.graphics.getHeight() - screenY)/100f);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

