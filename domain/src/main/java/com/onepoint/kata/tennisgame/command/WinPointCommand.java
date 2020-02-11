package com.onepoint.kata.tennisgame.command;

import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;

public interface WinPointCommand {

    String getTennisSetId();

    String getGameId();

    Player getWinner();

}
