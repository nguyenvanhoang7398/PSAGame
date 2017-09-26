package com.thechallengers.psagame.Dummy.Physics;

/**
 * Created by Phung Tuan Hoang on 9/25/2017.
 */

import java.util.*;

/**
 * Created by Hieu on 9/25/2017.
 */

public class RandomController {
    Physics2 physics;
    Random rn = new Random();
    int upper_bound = 5;
    float[] width = new float[]{1f, 1f, 1f, 2f, 2f, 2f};
    float[] height = new float[]{1f, 2f, 3f, 1f, 2f, 3f};
    RandomController(Physics2 physics) {
        this.physics = physics;
    }

    public MyPair<Float, Float> getSize() {
        int widthIndex = rn.nextInt(width.length);
        int heightIndex = rn.nextInt(height.length);
        return new MyPair<Float, Float>(width[widthIndex], height[heightIndex]);
    }

    public int getDensity() {
        return 1 + rn.nextInt(upper_bound);
    }
}
