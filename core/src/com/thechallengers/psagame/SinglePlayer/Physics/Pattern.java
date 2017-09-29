package com.thechallengers.psagame.SinglePlayer.Physics;

/**
 * Created by Asus on 9/29/2017.
 */

public class Pattern {
    public boolean[][] toMatrix(int size) {
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
        for (int i=0; i<size; i++) {
            for (int j=0; j<i; j++) {
                mat[i][j] = true;
            }
        }
    }
}
