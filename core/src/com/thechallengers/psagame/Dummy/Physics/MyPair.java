package com.thechallengers.psagame.Dummy.Physics;

/**
 * Created by Phung Tuan Hoang on 9/25/2017.
 */

public class MyPair<K, V> {
//    private int NeighBorBlockNumber;
//    private float edgeWeight;

    private K first;
    private V second;

    MyPair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public String toString() {
        return first.toString() + " " + second.toString();
    }
}