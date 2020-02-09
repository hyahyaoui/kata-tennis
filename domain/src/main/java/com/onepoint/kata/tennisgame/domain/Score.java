package com.onepoint.kata.tennisgame.domain;

public enum Score {

    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40);

    private int numberOfPointWon;

    Score(int numberOfPointWon) {
        this.numberOfPointWon = numberOfPointWon;
    }
}
