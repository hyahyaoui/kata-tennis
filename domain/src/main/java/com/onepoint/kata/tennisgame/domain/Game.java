package com.onepoint.kata.tennisgame.domain;

public interface Game {

    String getId();

    String getTennisSetId();

    GameScore getFirstPlayerScore();

    GameScore getSecondPlayerScore();

    Player getWinner();

}
