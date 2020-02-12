package com.onepoint.kata.tennisgame.providers;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;

public interface CommandsProvider {

    void send(StartGameCommand startGameCommand);

    void send(WinPointCommand cmd);

    void send(StartSetCommand cmd);
}
