package com.onepoint.kata.tennisgame.command;

import com.onepoint.kata.tennisgame.domain.Player;

public interface StartSetCommand {
    
    String getId();

    Player getFirstPlayer();

    Player getSecondPlayer();
}
