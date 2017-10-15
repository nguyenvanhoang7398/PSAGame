package com.thechallengers.psagame.SinglePlayer.Physics;

import java.util.*;

/**
 * Created by Hieu on 9/25/2017.
 */

public class RandomController {
    public static Random r = new Random();
    public static double WIDTH_3_PERCENTAGE = 0.7;
    public static ArrayList<Integer> color_array;

    public static Block randomBlock() {
        Block block = new Block(r.nextInt(5) + 1, r.nextInt(3) + 1, r.nextInt(2) + 1);
        if (color_array == null || color_array.size() == 0) resetColor();
        int temp = r.nextInt(color_array.size());
        int color = color_array.get(temp);
        color_array.remove(temp);

        while (block.width != 3 && !(r.nextDouble() <= WIDTH_3_PERCENTAGE)) {
            block = new Block(r.nextInt(5) + 1, r.nextInt(3) + 1, r.nextInt(2) + 1);
        }
        return block;
    }

    private static void resetColor() {
        color_array = new ArrayList<Integer>(5);
        for (int i = 1; i < 6; i++) {
            color_array.add(i);
        }
    }
}
