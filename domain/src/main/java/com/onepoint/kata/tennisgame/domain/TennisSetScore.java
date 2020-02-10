package com.onepoint.kata.tennisgame.domain;

import java.util.Arrays;

public enum TennisSetScore {

    ZERO(0), ONE(1),
    TWO(2), THREE(3),
    FOUR(4), FIVE(5), SIX(6);

    private int numberOfPointWon;

    TennisSetScore(int numberOfPointWon) {
        this.numberOfPointWon = numberOfPointWon;
    }

    public int getNumberOfPointWon() {
        return numberOfPointWon;
    }
    
    public TennisSetScore next() {
        return getFromValue(this.numberOfPointWon+1);
    }

    private TennisSetScore getFromValue(int value) {
        return Arrays.stream(TennisSetScore.values())
                .filter(score->score.numberOfPointWon == value)
                .findFirst()
                .get();
    }
}
