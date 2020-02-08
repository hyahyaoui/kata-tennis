package com.onepoint.kata.tennisgame.command;

import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;

public interface WinPointCommand {

    String getGameId();

    Player getPointWinner();

}
