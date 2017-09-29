package com.thechallengers.psagame.SinglePlayer.Physics;

/**
 * Created by Asus on 9/29/2017.
 */

public class Frame {
    public boolean[][] frame;
    public int size;
    public Pattern pattern;

    public Frame(int size) {
        this.size = size;
        this.pattern = new Pattern(size);
        this.frame = new boolean[size][size];
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
        this.frame = pattern.toMatrix();
    }
}
