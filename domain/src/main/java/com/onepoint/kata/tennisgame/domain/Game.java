package com.onepoint.kata.tennisgame.domain;

public interface Game {

    String getId();

    Player getFirstPlayer();

    Player getSecondPlayer();

    Score getFirstPlayerScore();

    Score getSecondPlayerScore();

    Player getGameWinner();

}
