package com.thechallengers.psagame.Leaderboard;

/**
 * Created by VanHoang on 10/13/2017.
 */

public class HighscoreEntry implements Comparable<HighscoreEntry> {
    private String userName;
    private int level;
    private String score;

    HighscoreEntry(String userName, int level, String score) {
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

    String getScore() {
        return score;
    }

    @Override
    public int compareTo(HighscoreEntry other) {
        return this.score.compareTo(other.getScore());
    }

    public String toString() {
        return userName + " " + score + " " + level;
    }
}
