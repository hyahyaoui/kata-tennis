package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.PointWonEvent;
import com.onepoint.kata.tennisgame.exceptions.BusinessRuleViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScoreCalculatorTest {

    final Player player1 = new Player().setName("player1");
    final Player player2 = new Player().setName("player2");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_compute_score_for_game_started_event() {
        StartGameCommand cmd = mock(StartGameCommand.class);
        when(cmd.getId()).thenReturn("id");
        when(cmd.getFirstPlayer()).thenReturn(player1);
        when(cmd.getSecondPlayer()).thenReturn(player2);
        final GameStartedEvent event = ScoreCalculator.computeAndCreateEvent(cmd);
        assertEquals("player1",event.getFirstPlayer().getName());
        assertEquals("player2",event.getSecondPlayer().getName());
        assertEquals(Score.LOVE,event.getFirstPlayerScore());
        assertEquals(Score.LOVE,event.getSecondPlayerScore());
        assertEquals("id",event.getId());
    }

    @Test
    public void should_throw_business_rule_violated_exception_when_wining_point_after_game_won() {
        expectedException.expect(BusinessRuleViolatedException.class);
        expectedException.expectMessage("Game already finished !");
        WinPointCommand cmd = mock(WinPointCommand.class);
        Game game = mock(Game.class);
        when(game.getGameWinner()).thenReturn(new Player());
        ScoreCalculator.computeAndCreateEvent(cmd, game);
    }

    @Test
    public void should_update_score_when_a_player_win_a_point() {
        WinPointCommand cmd = mock(WinPointCommand.class);
        Game game = mock(Game.class);
        when(cmd.getPointWinner()).thenReturn(player1);
        when(game.getFirstPlayer()).thenReturn(player1);
        when(game.getSecondPlayer()).thenReturn(player2);
        when(game.getFirstPlayerScore()).thenReturn(Score.FIFTEEN);
        when(game.getSecondPlayerScore()).thenReturn(Score.FOURTY);
        final PointWonEvent pointWonEvent = ScoreCalculator.computeAndCreateEvent(cmd, game);
        assertEquals(Score.THIRTY,pointWonEvent.getFirstPlayerScore());
        assertEquals(Score.FOURTY,pointWonEvent.getSecondPlayerScore());
    }

    @Test
    public void should_declare_game_winner() {
        WinPointCommand cmd = mock(WinPointCommand.class);
        Game game = mock(Game.class);
        when(game.getFirstPlayerScore()).thenReturn(Score.FOURTY);
        when(game.getSecondPlayerScore()).thenReturn(Score.FIFTEEN);
        when(cmd.getPointWinner()).thenReturn(player1);
        when(game.getFirstPlayer()).thenReturn(player1);
        final PointWonEvent pointWonEvent = ScoreCalculator.computeAndCreateEvent(cmd, game);
        assertEquals(player1.getName(),pointWonEvent.getGameWinner().getName());
    }

    @Test
    public void should_handle_deuce_case() {
        WinPointCommand cmd = mock(WinPointCommand.class);
        Game game = mock(Game.class);
        when(game.getFirstPlayerScore()).thenReturn(Score.FOURTY);
        when(game.getSecondPlayerScore()).thenReturn(Score.ADVANTAGE);
        when(cmd.getPointWinner()).thenReturn(player1);
        when(game.getFirstPlayer()).thenReturn(player1);
        final PointWonEvent pointWonEvent = ScoreCalculator.computeAndCreateEvent(cmd, game);
        assertEquals(Score.FOURTY,pointWonEvent.getSecondPlayerScore());
        assertEquals(Score.FOURTY,pointWonEvent.getFirstPlayerScore());
    }

    @Test
    public void should_handle_advantage_case() {
        WinPointCommand cmd = mock(WinPointCommand.class);
        Game game = mock(Game.class);
        when(game.getFirstPlayerScore()).thenReturn(Score.FOURTY);
        when(game.getSecondPlayerScore()).thenReturn(Score.FOURTY);
        when(cmd.getPointWinner()).thenReturn(player1);
        when(game.getFirstPlayer()).thenReturn(player1);
        final PointWonEvent pointWonEvent = ScoreCalculator.computeAndCreateEvent(cmd, game);
        assertEquals(Score.FOURTY,pointWonEvent.getSecondPlayerScore());
        assertEquals(Score.ADVANTAGE,pointWonEvent.getFirstPlayerScore());
    }

    @Test
    public void should_win_game_when_player_having_advantage_win_point() {
        WinPointCommand cmd = mock(WinPointCommand.class);
        Game game = mock(Game.class);
        when(game.getFirstPlayerScore()).thenReturn(Score.ADVANTAGE);
        when(game.getSecondPlayerScore()).thenReturn(Score.FOURTY);
        when(cmd.getPointWinner()).thenReturn(player1);
        when(game.getFirstPlayer()).thenReturn(player1);
        final PointWonEvent pointWonEvent = ScoreCalculator.computeAndCreateEvent(cmd, game);
        assertEquals("player1",pointWonEvent.getGameWinner().getName());
    }
}
