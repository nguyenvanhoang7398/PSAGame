package com.thechallengers.psagame.SinglePlayer.Physics;

/**
 * Created by Phung Tuan Hoang on 9/25/2017.
 */
public class Block {
    public float previousPotential;
    public float potential;
    public float weight;
    public int sequenceNumber;
    public float width;
    public float height;
    public String type;
    public double remainingTime;
    public double decreaseTimeRate;
    public float remainingCapacity;
    public int upperCount;
    public float density;
    public int type_int;

    Block(String type) {
        this(0, 0, type, -1, 0);
    }

    Block(float height, float width , String type, int sequenceNumber, float density) {
        this.height = height;
        this.width = width;
        this.type_int = (int) (width * 10 + height);
        this.type = type;
        this.sequenceNumber = sequenceNumber;
        this.density = density;
        this.weight = height * width * density;
        this.remainingCapacity = weight;
        this.potential = weight;
        this.previousPotential = 0;
        this.remainingTime = 10;
        this.decreaseTimeRate = 0;
        this.upperCount = 0;
    }

    public void updateRemainingTime() {
        updateDecreaseTimeRate();
        this.remainingTime -= this.decreaseTimeRate;
        if (this.decreaseTimeRate == 0) remainingTime = 10;
//        System.out.println("Block " + this.sequenceNumber + " remain time " + this.remainingTime + " decreate " + this.decreaseTimeRate
//                         + " weight " + this.weight + " potential " + this.potential);
    }

    public String toString() {
        return type + " " + Integer.toString(sequenceNumber);
    }

    public float getRemainingCapacity() {
        return 2* this.weight - this.potential;
    }

    public void updateRemainingCapacity() {
        this.remainingCapacity = 2 * this.weight - this.potential;
    }

    public boolean isCranable() {
        return this.upperCount == 0;
    }

    private void updateDecreaseTimeRate() {
        if(this.remainingCapacity >= 0) {
            this.decreaseTimeRate = 0;
        } else {
            this.decreaseTimeRate = Math.abs(this.remainingCapacity) * 0.02;
        }
    }

//    @Override
//    public int hashCode() {
//        return potential * 2 + sequenceNumber * 3;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        }
//        if (!(o instanceof Block)) {
//            return false;
//        }
//        Block c = (Block) o;
//        return this.hashCode() === o.hashCode();
//    }
}