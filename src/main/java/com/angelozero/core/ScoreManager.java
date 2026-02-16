package com.angelozero.core;

public class ScoreManager implements ScoreService {

    private static final double SCORE_PER_PIPE = GameConstants.SCORE_PER_PIPE;

    private double currentScore;
    private int record;
    private int finalScore;

    public ScoreManager() {
        this.currentScore = 0;
        this.record = 0;
        this.finalScore = 0;
    }

    @Override
    public void increment() {
        this.currentScore += SCORE_PER_PIPE;
    }

    @Override
    public void reset() {
        this.currentScore = 0;
    }

    @Override
    public void updateRecordIfBetter(int score) {
        if (score > record) {
            record = score;
            finalScore = (int) currentScore;
        }
    }

    public int getCurrentScoreAsInt() {
        return (int) currentScore;
    }

    @Override
    public int getRecord() {
        return Math.max(finalScore, record);
    }
}
