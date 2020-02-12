package com.onepoint.kata.tennisgame.services;


import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.entities.TennisSetEntity;
import com.onepoint.kata.tennisgame.providers.CommandsProvider;
import com.onepoint.kata.tennisgame.providers.TennisRepositoryProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TennisGameServiceTest {
    private CommandsProvider commandsProvider = mock(CommandsProvider.class);
    private TennisRepositoryProvider tennisRepositoryProvider = mock(TennisRepositoryProvider.class);
    private TennisGameService tennisGameService;
    private StartGameCommand startGameCommand = mock(StartGameCommand.class);
    private WinPointCommand winPointCommand = mock(WinPointCommand.class);
    private StartSetCommand startSetCommand = mock(StartSetCommand.class);

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

    @Test
    public void should_start_set_command() {
        tennisGameService.send(startSetCommand);
        verify(commandsProvider, times(1))
                .send(any(StartSetCommand.class));
    }


    @Test
    public void should_find_game() {
        GameEntity gameEntity = new GameEntity().setId("gi");
        when(tennisRepositoryProvider.findGame("tsi", "gi")).thenReturn(Optional.of(gameEntity));
        final Optional<GameEntity> game = tennisGameService.findGame("tsi", "gi");
        assertEquals(true, game.isPresent());
        assertEquals("gi", game.get().getId());
    }

    @Test
    public void should_find_set() {
        TennisSetEntity setEntity = new TennisSetEntity().setId("tsi");
        when(tennisRepositoryProvider.findTennisSet("tsi")).thenReturn(Optional.of(setEntity));
        final Optional<TennisSetEntity> tennisSetEntity = tennisGameService.findTennisSet("tsi");
        assertEquals(true, tennisSetEntity.isPresent());
        assertEquals("tsi", tennisSetEntity.get().getId());
    }

}
