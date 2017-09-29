package com.thechallengers.psagame.SinglePlayer.Physics;

/**
 * Created by Asus on 9/29/2017.
 */

public class Frame {
    public boolean[][] frame;
    public int size;
    public Pattern pattern;

    Frame(int size) {
        this.size = size;
        this.pattern = new Pattern();
        this.frame = new boolean[size][size];
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
        this.frame = pattern.toMatrix(this.size);
    }

    public void renderFrame()

//    public void readFrame(world) {
//        world.draw(unitBlock)
//    }
}
