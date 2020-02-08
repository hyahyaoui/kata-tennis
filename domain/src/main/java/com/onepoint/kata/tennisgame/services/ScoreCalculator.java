package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.PointWonEvent;
import com.onepoint.kata.tennisgame.exceptions.BusinessRuleViolatedException;

public class ScoreCalculator {

    public static GameStartedEvent computeAndCreateEvent(StartGameCommand cmd) {
        return GameStartedEvent
                .builder()
                .id(cmd.getId())
                .firstPlayer(cmd.getFirstPlayer())
                .secondPlayer(cmd.getSecondPlayer())
                .firstPlayerScore(Score.LOVE)
                .secondPlayerScore(Score.LOVE)
                .build();
    }

    public static PointWonEvent computeAndCreateEvent(WinPointCommand cmd, Game game) {
        Player winner = null;
        Score newScore = Score.LOVE;
        if (game.getGameWinner() != null) {
            throw new BusinessRuleViolatedException("Game already finished !");
        }
        final boolean isFirstPlayerPointWinner = cmd.getPointWinner().equals(game.getFirstPlayer());
        final Score pointWinnerScore = isFirstPlayerPointWinner ? game.getFirstPlayerScore() :
                game.getSecondPlayerScore();
        if (Score.FORTY.equals(pointWinnerScore)) {
            winner = isFirstPlayerPointWinner ? game.getFirstPlayer() : game.getSecondPlayer();
        }
        newScore = computeNewScore(pointWinnerScore);

        final Score firstPlayerScore = isFirstPlayerPointWinner ? newScore : game.getFirstPlayerScore();
        final Score secondPlayerScore = !isFirstPlayerPointWinner ? newScore : game.getSecondPlayerScore();
        return PointWonEvent.builder()
                .gameId(cmd.getGameId())
                .firstPlayerScore(firstPlayerScore)
                .secondPlayerScore(secondPlayerScore)
                .gameWinner(winner)
                .build();
    }

    private static Score computeNewScore(Score pointWinnerScore) {
        switch (pointWinnerScore) {
            case LOVE:
                return Score.FIFTEEN;
            case FIFTEEN:
                return Score.THIRTY;
            default:
                return Score.FORTY;
        }
    }

}
