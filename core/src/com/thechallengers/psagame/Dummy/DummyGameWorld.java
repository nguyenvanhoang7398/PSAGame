package com.thechallengers.psagame.Dummy;

import com.thechallengers.psagame.base_classes_and_interfaces.ScreenWorld;

/**
 * Created by Phung Tuan Hoang on 9/11/2017.
 */

public class DummyGameWorld implements ScreenWorld {


    @Override
    public void update(float delta) {

    }

    public void swipeUp() {
        System.out.println("Swiped Up");
    }

    public void swipeDown() {
        System.out.println("Swiped Down");
    }

    public void swipeLeft() {
        System.out.println("Swiped Left");
    }

    public void swipeRight() {
        System.out.println("Swiped Right");
    }
}
