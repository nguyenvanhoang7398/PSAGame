package com.thechallengers.psagame.SinglePlayer.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Asus on 9/29/2017.
 */

public class Pattern {
    public int size;

    public Pattern(int size) {
        this.size = size;
    }

    public boolean[][] toMatrix() {
        boolean[][] mat = new boolean[size][size];
        initMat(mat, size);
        renderTriangle(mat, size);
        return mat;
    }

    public static void initMat(boolean[][] mat, int size) {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                mat[i][j] = false;
            }
        }
    }

    public static void renderTriangle(boolean[][] mat, int size) {
        Texture a = new Texture(Gdx.files.internal("textures/triangle.png"));
        a.getTextureData().prepare();
        Pixmap pixmap = a.getTextureData().consumePixmap();
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                if(pixmap.getPixel(i, j) != 0) {
                    mat[i][j] = true;
                }
            }
        }

    }
}
