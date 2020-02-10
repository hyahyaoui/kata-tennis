package com.onepoint.kata.tennisgame.domain;

import java.util.Map;

public interface TennisSet {

    String getId();

    Map<String, ? extends Game> getGames();

    Player getFirstPlayer();

    Player getSecondPlayer();

    TennisSetScore getFirstPlayerScore();

    TennisSetScore getSecondPlayerScore();

    Player getWinner();

}
