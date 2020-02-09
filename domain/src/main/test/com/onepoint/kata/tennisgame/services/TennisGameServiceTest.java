package com.onepoint.kata.tennisgame.services;


import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.providers.CommandsProvider;
import com.onepoint.kata.tennisgame.providers.TennisRepositoryProvider;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TennisGameServiceTest {
    private CommandsProvider commandsProvider = mock(CommandsProvider.class);
    private TennisRepositoryProvider tennisRepositoryProvider = mock(TennisRepositoryProvider.class);
    private TennisGameService tennisGameService;
    private StartGameCommand startGameCommand = mock(StartGameCommand.class);
    private WinPointCommand winPointCommand = mock(WinPointCommand.class);

    @Before
    public void init() {
        tennisGameService = new TennisGameService(commandsProvider, tennisRepositoryProvider);
    }

    @Test
    public void should_send_game_started_command() {
        tennisGameService.send(startGameCommand);
        verify(commandsProvider, times(1))
                .send(any(StartGameCommand.class));
    }

    @Test
    public void should_send_game_win_point_command() {
        tennisGameService.send(winPointCommand);
        verify(commandsProvider, times(1))
                .send(any(WinPointCommand.class));
    }

}