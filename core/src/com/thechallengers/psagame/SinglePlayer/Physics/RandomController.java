package com.thechallengers.psagame.SinglePlayer.Physics;

import java.util.*;

/**
 * Created by Hieu on 9/25/2017.
 */

public class RandomController {
    public static Random r = new Random();
    public static ArrayList<Integer> color_array;

    public static Block randomBlock() {
        if (color_array == null || color_array.size() == 0) resetColor();
        int temp = r.nextInt(color_array.size());
        int color = color_array.get(temp);
        color_array.remove(temp);



        Block block = new Block(color, r.nextInt(3) + 1, r.nextInt(2) + 1);
        return block;
    }

    private static void resetColor() {
        color_array = new ArrayList<Integer>(5);
        for (int i = 1; i < 6; i++) {
            color_array.add(i);
        }
    }
}