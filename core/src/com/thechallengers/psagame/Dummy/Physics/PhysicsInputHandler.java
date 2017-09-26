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

    public void setPhysics(Physics2 physics) {
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

        screenY += 319;

        Array<Body> bodyArray = physics.bodyArray;
        Body selectedBody = null;

        for (Body body: bodyArray) {
            Fixture fixture = body.getFixtureList().get(0);
            if (!(body.getUserData() instanceof Block)) {
                continue;
            }
            if (fixture.testPoint(physics.crane.getPosition().x, physics.crane.getPosition().y)) {
                selectedBody = body;
                break;
            }
        }

        screenY -= 319;
        if (screenX > 748 && screenX < 820 && screenY > 150 && screenY < 150 + 75) {
            physics.crane.setLinearVelocity(-2f, 0);
            return true;
        }

        if (screenX > 872 && screenX < 872 + 75 && screenY > 150 && screenY < 150 + 75) {
            {
                if (selectedBody == null && physics.cranedBody == null && physics.isHoldingNext) {
                    physics.createBlock(physics.crane.getPosition().x, physics.crane.getPosition().y);
                    physics.isHoldingNext = false;
                }
                else if (selectedBody != null && physics.cranedBody == null && !physics.isHoldingNext) {
                    if (((Block) selectedBody.getUserData()).isCranable()) physics.craneBody(selectedBody);
                }
                else if (physics.cranedBody != null && !physics.isHoldingNext) {
                    physics.releaseCranedBody(physics.crane.getPosition().x, physics.crane.getPosition().y);
                }
                else if (selectedBody == null && physics.cranedBody == null && !physics.isHoldingNext) {
                    physics.isHoldingNext = true;
                }

            }
            return true;
        }

        if (screenX > 872 && screenX < 872 + 75 && screenY > 263 && screenY < 263 + 75) {
            physics.crane.setLinearVelocity(0, -2f);
            return false;
        }

        if (screenX > 991 && screenX < 991 + 75 && screenY > 150 && screenY < 150 + 75) {
            physics.crane.setLinearVelocity(2f, 0);
            return true;
        }

        if (screenX > 872 && screenX < 872 + 75 && screenY > 40 && screenY < 40 + 75) {
            physics.crane.setLinearVelocity(0f, 2f);
            return true;
        }

        screenY += 319;
        /*
        if (selectedBody == null && physics.cranedBody == null && physics.isHoldingNext) {
            physics.createBlock(screenX/100f, (Gdx.graphics.getHeight() - screenY) /100f);
        } else if (selectedBody == null && physics.cranedBody == null && !physics.isHoldingNext){
            if (((Block) selectedBody.getUserData()).isCranable()) physics.craneBody(selectedBody);
        } else if (selectedBody == null){
            physics.releaseCranedBody(screenX/100f, (Gdx.graphics.getHeight() - screenY)/100f);
        }
        */
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX > 872 && screenX < 872 + 75 && screenY > 150 && screenY < 150 + 75) return false;
        physics.crane.setLinearVelocity(0 ,0);
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

