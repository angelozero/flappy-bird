package com.angelozero.core;

public class ScoreManager {
    private double currentScore;
    private int record;
    private int finalScore;

    public ScoreManager() {
        this.currentScore = 0;
        this.record = 0;
        this.finalScore = 0;
    }

    public void increment() {
        this.currentScore += 0.5;
    }

    public void setRecord(int score) {
        if (score > record) {
            record = score;
            finalScore = (int) currentScore;
        }
    }

    public void reset() {
        this.currentScore = 0;
    }

    public int getCurrentScoreAsInt() {
        return (int) currentScore;
    }

    public int getRecord() {
        return Math.max(finalScore, record);
    }

    public String getFormattedScore() {
        return String.valueOf(getCurrentScoreAsInt());
    }
}
