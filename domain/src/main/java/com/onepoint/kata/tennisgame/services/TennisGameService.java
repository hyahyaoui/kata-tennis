package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.providers.CommandsProvider;
import com.onepoint.kata.tennisgame.providers.TennisRepositoryProvider;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TennisGameService {

    private final CommandsProvider commandsProvider;
    private final TennisRepositoryProvider tennisRepositoryProvider;

    public void send(StartGameCommand cmd) {
        this.commandsProvider.send(cmd);
    }

    public void send(WinPointCommand cmd) {
        this.commandsProvider.send(cmd);
    }

    public void send(StartSetCommand cmd) {
        this.commandsProvider.send(cmd);
    }

    public Optional<GameEntity> findGame(String tennisSetId, String gameId) {
        return tennisRepositoryProvider.findGame(tennisSetId, gameId);
    }
}
