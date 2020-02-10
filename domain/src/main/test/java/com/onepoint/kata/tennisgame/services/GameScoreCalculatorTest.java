package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.PointWonEvent;
import com.onepoint.kata.tennisgame.exceptions.BusinessRuleViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameScoreCalculatorTest {
//
//    final Player player1 = new Player().setName("player1");
//    final Player player2 = new Player().setName("player2");
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Test
//    public void should_compute_score_for_game_started_event() {
//        StartGameCommand cmd = mock(StartGameCommand.class);
//    /*    when(cmd.getId()).thenReturn("id");*/
//        when(cmd.getFirstPlayer()).thenReturn(player1);
//        when(cmd.getSecondPlayer()).thenReturn(player2);
//        final GameStartedEvent event = EventsBuilder.computeAndCreateEvent(cmd);
//        assertEquals("player1",event.getFirstPlayer().getName());
//        assertEquals("player2",event.getSecondPlayer().getName());
//        assertEquals(GameScore.LOVE,event.getFirstPlayerGameScore());
//        assertEquals(GameScore.LOVE,event.getSecondPlayerGameScore());
//        assertEquals("id",event.getId());
//    }
//
//    @Test
//    public void should_throw_business_rule_violated_exception_when_wining_point_after_game_won() {
//        expectedException.expect(BusinessRuleViolatedException.class);
//        expectedException.expectMessage("Game already finished !");
//        WinPointCommand cmd = mock(WinPointCommand.class);
//        Game game = mock(Game.class);
//        when(game.getGameWinner()).thenReturn(new Player());
//        EventsBuilder.computeAndCreateEvent(cmd, game);
//    }
//
//    @Test
//    public void should_update_score_when_a_player_win_a_point() {
//        WinPointCommand cmd = mock(WinPointCommand.class);
//        Game game = mock(Game.class);
//        when(cmd.getPointWinner()).thenReturn(player1);
//        when(game.getFirstPlayer()).thenReturn(player1);
//        when(game.getSecondPlayer()).thenReturn(player2);
//        when(game.getFirstPlayerGameScore()).thenReturn(GameScore.FIFTEEN);
//        when(game.getSecondPlayerGameScore()).thenReturn(GameScore.FOURTY);
//        final PointWonEvent pointWonEvent = EventsBuilder.computeAndCreateEvent(cmd, game);
//        assertEquals(GameScore.THIRTY,pointWonEvent.getFirstPlayerGameScore());
//        assertEquals(GameScore.FOURTY,pointWonEvent.getSecondPlayerGameScore());
//    }
//
//    @Test
//    public void should_declare_game_winner() {
//        WinPointCommand cmd = mock(WinPointCommand.class);
//        Game game = mock(Game.class);
//        when(game.getFirstPlayerGameScore()).thenReturn(GameScore.FOURTY);
//        when(game.getSecondPlayerGameScore()).thenReturn(GameScore.FIFTEEN);
//        when(cmd.getPointWinner()).thenReturn(player1);
//        when(game.getFirstPlayer()).thenReturn(player1);
//        final PointWonEvent pointWonEvent = EventsBuilder.computeAndCreateEvent(cmd, game);
//        assertEquals(player1.getName(),pointWonEvent.getGameWinner().getName());
//    }
//
//    @Test
//    public void should_handle_deuce_case() {
//        WinPointCommand cmd = mock(WinPointCommand.class);
//        Game game = mock(Game.class);
//        when(game.getFirstPlayerGameScore()).thenReturn(GameScore.FOURTY);
//        when(game.getSecondPlayerGameScore()).thenReturn(GameScore.ADVANTAGE);
//        when(cmd.getPointWinner()).thenReturn(player1);
//        when(game.getFirstPlayer()).thenReturn(player1);
//        final PointWonEvent pointWonEvent = EventsBuilder.computeAndCreateEvent(cmd, game);
//        assertEquals(GameScore.FOURTY,pointWonEvent.getSecondPlayerGameScore());
//        assertEquals(GameScore.FOURTY,pointWonEvent.getFirstPlayerGameScore());
//    }
//
//    @Test
//    public void should_handle_advantage_case() {
//        WinPointCommand cmd = mock(WinPointCommand.class);
//        Game game = mock(Game.class);
//        when(game.getFirstPlayerGameScore()).thenReturn(GameScore.FOURTY);
//        when(game.getSecondPlayerGameScore()).thenReturn(GameScore.FOURTY);
//        when(cmd.getPointWinner()).thenReturn(player1);
//        when(game.getFirstPlayer()).thenReturn(player1);
//        final PointWonEvent pointWonEvent = EventsBuilder.computeAndCreateEvent(cmd, game);
//        assertEquals(GameScore.FOURTY,pointWonEvent.getSecondPlayerGameScore());
//        assertEquals(GameScore.ADVANTAGE,pointWonEvent.getFirstPlayerGameScore());
//    }
//
//    @Test
//    public void should_win_game_when_player_having_advantage_win_point() {
//        WinPointCommand cmd = mock(WinPointCommand.class);
//        Game game = mock(Game.class);
//        when(game.getFirstPlayerGameScore()).thenReturn(GameScore.ADVANTAGE);
//        when(game.getSecondPlayerGameScore()).thenReturn(GameScore.FOURTY);
//        when(cmd.getPointWinner()).thenReturn(player1);
//        when(game.getFirstPlayer()).thenReturn(player1);
//        final PointWonEvent pointWonEvent = EventsBuilder.computeAndCreateEvent(cmd, game);
//        assertEquals("player1",pointWonEvent.getGameWinner().getName());
//    }
}
