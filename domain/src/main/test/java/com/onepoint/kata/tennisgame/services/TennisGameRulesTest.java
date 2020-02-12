package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.TennisSetScore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TennisGameRulesTest {


    @Test
    public void should_increment_score_after_point_won() {
        final GameScore newScore = TennisGameRules.computeGameScoreAfterPointWon(GameScore.LOVE, GameScore.LOVE);
        assertEquals(GameScore.FIFTEEN, newScore);
    }

    @Test
    public void should_return_advantage_in_case_of_two_player_has_fourty_score() {
        final GameScore newScore = TennisGameRules.computeGameScoreAfterPointWon(GameScore.FOURTY, GameScore.FOURTY);
        assertEquals(GameScore.ADVANTAGE, newScore);
    }


    @Test
    public void should_compute_new_score_when_no_advantage() {
        final GameScore newScore = TennisGameRules.updateGameScoreIfNeeded(false, true,
                GameScore.THIRTY, GameScore.FOURTY);
        assertEquals(GameScore.FOURTY, newScore);
    }

    @Test
    public void should_lose_advantage_id_other_player_wins_apoint() {
        final GameScore newScore = TennisGameRules.updateGameScoreIfNeeded(false, true,
                GameScore.ADVANTAGE, GameScore.FOURTY);
        assertEquals(GameScore.FOURTY, newScore);
    }


    @Test
    public void should_compute_new_tennis_set_score() {
        final TennisSetScore newScore = TennisGameRules.computeTennisSetScore(TennisSetScore.FIVE);
        assertEquals(TennisSetScore.SIX, newScore);

    }

    @Test
    public void should_not_win_game_if_old_point_winner_score_id_under_fourty() {
        final boolean isGameWon = TennisGameRules.isGameWon(GameScore.THIRTY, GameScore.FIFTEEN);
        assertFalse(isGameWon);

    }

    @Test
    public void should_not_win_game_if_old_point_winner_score_id_under_fourty_and_other_player_has_advantage() {
        final boolean isGameWon = TennisGameRules.isGameWon(GameScore.FOURTY, GameScore.ADVANTAGE);
        assertFalse(isGameWon);

    }

    @Test
    public void should_win_game() {
        final boolean isGameWon = TennisGameRules.isGameWon(GameScore.FOURTY, GameScore.THIRTY);
        assertTrue(isGameWon);

    }


    @Test
    public void should_not_play_tie_break_if_the_tennis_games_score_are_not_equals_six_both() {
        final boolean playTieBreak = TennisGameRules.shouldPlayTieBreak(TennisSetScore.SIX, TennisSetScore.FIVE);
        assertFalse(playTieBreak);

    }

    @Test
    public void should_play_tie_break() {
        final boolean playTieBreak = TennisGameRules.shouldPlayTieBreak(TennisSetScore.SIX, TennisSetScore.SIX);
        assertTrue(playTieBreak);

    }

    @Test
    public void should_compute_new_tie_break_score() {
        final int tieBreakScore = TennisGameRules.computeNewTieBreakScore(5);
        assertEquals(6, tieBreakScore);

    }

    @Test
    public void should_not_win_tie_break_if_point_difference_is_under_two() {
        final boolean isSetWonAfterTieBreak = TennisGameRules.isSetWithTieBreakWon(7,6);
        assertFalse(isSetWonAfterTieBreak);

    }
    @Test
    public void should_win_tie_break_if_point_difference_is_equals_two() {
        final boolean isSetWonAfterTieBreak = TennisGameRules.isSetWithTieBreakWon(8,6);
        assertTrue(isSetWonAfterTieBreak);

    }

}
