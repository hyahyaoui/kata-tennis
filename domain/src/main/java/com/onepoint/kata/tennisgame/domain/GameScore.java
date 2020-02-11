package com.onepoint.kata.tennisgame.domain;

import java.util.Arrays;

public enum GameScore {

    LOVE(0), FIFTEEN(15), THIRTY(30), FOURTY(40),
    ADVANTAGE(50);

    private int numberOfPointWon;

    GameScore(int numberOfPointWon) {
        this.numberOfPointWon = numberOfPointWon;
    }

    public int getNumberOfPointWon() {
        return numberOfPointWon;
    }
}
