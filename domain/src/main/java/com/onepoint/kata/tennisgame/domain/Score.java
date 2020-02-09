package com.onepoint.kata.tennisgame.domain;

public enum Score {

    LOVE(0), FIFTEEN(15), THIRTY(30), FOURTY(40),
    ADVANTAGE(50);

    private int numberOfPointWon;

    Score(int numberOfPointWon) {
        this.numberOfPointWon = numberOfPointWon;
    }

    public int getNumberOfPointWon() {
        return numberOfPointWon;
    }
}
