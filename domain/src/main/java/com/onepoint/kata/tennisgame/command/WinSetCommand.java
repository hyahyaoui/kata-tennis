package com.onepoint.kata.tennisgame.command;

import com.onepoint.kata.tennisgame.domain.Player;

public interface WinSetCommand {

    String getId();

    Player getSetWinner();
}
