package com.thechallengers.psagame.Leaderboard;

/**
 * Created by VanHoang on 10/13/2017.
 */

public class HighscoreEntry implements Comparable<HighscoreEntry> {
    private String userName;
    private int level;
    private float score;

    HighscoreEntry(String userName, int level, float score) {
        this.userName = userName;
        this.score = score;
        this.level = level;
    }

    String getUserName() {
        return userName;
    }

    int getLevel() {
        return level;
    }

    float getScore() {
        return score;
    }

    @Override
    public int compareTo(HighscoreEntry other) {
        return Float.valueOf(this.score).compareTo(other.getScore());
    }

    public String toString() {
        return userName + " " + score + " " + level;
    }
}
