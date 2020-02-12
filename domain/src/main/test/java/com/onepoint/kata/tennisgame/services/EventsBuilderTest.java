package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.TennisSetStartedEvent;
import org.junit.Assert;
import org.junit.Test;

import static com.onepoint.kata.tennisgame.domain.GameScore.LOVE;
import static com.onepoint.kata.tennisgame.domain.TennisSetScore.ZERO;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventsBuilderTest {

    public Player firstPlayer = new Player().setName("firstPlayer");
    public Player secondPlayer = new Player().setName("secondPlayer");

    @Test
    public void should_generate_game_started_event_from_start_game_command() {

        StartGameCommand startGameCommand = mock(StartGameCommand.class);
        when(startGameCommand.getId()).thenReturn("gid");
        when(startGameCommand.getTennisSetId()).thenReturn("tsid");
        GameStartedEvent gameStartedEvent = GameStartedEvent
                .builder()
                .gameId("gid")
                .tennisSetId("tsid")
                .firstPlayerScore(LOVE)
                .secondPlayerScore(LOVE)
                .build();
        GameStartedEvent built = EventsBuilder.build(startGameCommand);
        Assert.assertEquals(gameStartedEvent.getGameId(), built.getGameId());
        Assert.assertEquals(gameStartedEvent.getTennisSetId(), built.getTennisSetId());
        Assert.assertEquals(gameStartedEvent.getFirstPlayerScore(), built.getFirstPlayerScore());
        Assert.assertEquals(gameStartedEvent.getSecondPlayerScore(), built.getSecondPlayerScore());
    }


    @Test
    public void should_generate_tennis_set_started_event_from_start_set_command() {

        StartSetCommand startSetCommand = mock(StartSetCommand.class);
        when(startSetCommand.getId()).thenReturn("tsid");
        when(startSetCommand.getFirstPlayer()).thenReturn(firstPlayer);
        when(startSetCommand.getSecondPlayer()).thenReturn(secondPlayer);

        TennisSetStartedEvent gameStartedEvent = TennisSetStartedEvent
                .builder()
                .id("tsid")
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .firstPlayerScore(ZERO)
                .secondPlayerScore(ZERO)
                .build();
        TennisSetStartedEvent built = EventsBuilder.build(startSetCommand);
        Assert.assertEquals(gameStartedEvent.getId(), built.getId());
        Assert.assertEquals(gameStartedEvent.getFirstPlayer().getName(), built.getFirstPlayer().getName());
        Assert.assertEquals(gameStartedEvent.getSecondPlayer().getName(), built.getSecondPlayer().getName());
        Assert.assertEquals(gameStartedEvent.getFirstPlayerScore(), built.getFirstPlayerScore());
        Assert.assertEquals(gameStartedEvent.getSecondPlayerScore(), built.getSecondPlayerScore());
    }

}
