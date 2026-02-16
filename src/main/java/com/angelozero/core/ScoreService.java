package com.angelozero.core;

public interface ScoreService {

    void increment();

    void reset();

    void updateRecordIfBetter(int score);

    int getCurrentScoreAsInt();

    int getRecord();
}
