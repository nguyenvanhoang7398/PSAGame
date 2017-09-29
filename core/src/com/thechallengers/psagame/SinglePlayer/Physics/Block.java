package com.thechallengers.psagame.SinglePlayer.Physics;

/**
 * Created by Phung Tuan Hoang on 9/29/2017.
 */

public class Block {
    public int color_type;
    public int width;
    public int height;
    public String blockType;

    public Block(int color_type, int width, int height) {
        this.color_type = color_type;
        this.width = width;
        this.height = height;
        this.blockType = Integer.toString(color_type * 100 + width * 10 + height);
    }
}
