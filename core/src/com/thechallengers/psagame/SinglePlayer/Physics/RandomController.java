package com.thechallengers.psagame.SinglePlayer.Physics;

import java.util.*;

/**
 * Created by Hieu on 9/25/2017.
 */

public class RandomController {
    public static Random r = new Random();

    public static Block randomBlock() {
        Block block = new Block(r.nextInt(5) + 1, r.nextInt(3) + 1, r.nextInt(2) + 1);
        return block;
    }
}