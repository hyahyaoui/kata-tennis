package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.PointWonEvent;
import com.onepoint.kata.tennisgame.exceptions.BusinessRuleViolatedException;

import static com.onepoint.kata.tennisgame.domain.Score.LOVE;

public class ScoreCalculator {

    public static GameStartedEvent computeAndCreateEvent(StartGameCommand cmd) {
        return GameStartedEvent
                .builder()
                .id(cmd.getId())
                .firstPlayer(cmd.getFirstPlayer())
                .secondPlayer(cmd.getSecondPlayer())
                .firstPlayerScore(LOVE)
                .secondPlayerScore(LOVE)
                .build();
    }

    public static PointWonEvent computeAndCreateEvent(WinPointCommand cmd, Game game) {
        Player winner = null;
        Score newScore;
        if (game.getGameWinner() != null) {
            throw new BusinessRuleViolatedException("Game already finished !");
        }
        final boolean isFirstPlayerPointWinner = cmd.getPointWinner().equals(game.getFirstPlayer());
        final Score pointWinnerScore = isFirstPlayerPointWinner ? game.getFirstPlayerScore() :
                game.getSecondPlayerScore();
        final Score otherPlayerScore = isFirstPlayerPointWinner ? game.getSecondPlayerScore() :
                game.getFirstPlayerScore();

        if (pointWinnerScore.getNumberOfPointWon() >= Score.FOURTY.getNumberOfPointWon()) {
            if (pointWinnerScore.getNumberOfPointWon() > otherPlayerScore.getNumberOfPointWon()) {
                winner = isFirstPlayerPointWinner ? game.getFirstPlayer() : game.getSecondPlayer();
            }
        }
        newScore = computeNewScore(pointWinnerScore, otherPlayerScore);

        Score firstPlayerScore = isFirstPlayerPointWinner ? newScore : game.getFirstPlayerScore();
        Score secondPlayerScore = !isFirstPlayerPointWinner ? newScore : game.getSecondPlayerScore();

        if (winner == null && isFirstPlayerPointWinner && Score.ADVANTAGE.equals(secondPlayerScore)) {
            secondPlayerScore = Score.FOURTY;
        }

        if (winner == null && !isFirstPlayerPointWinner && Score.ADVANTAGE.equals(firstPlayerScore)) {
            firstPlayerScore = Score.FOURTY;
        }

        return PointWonEvent.builder()
                .gameId(cmd.getGameId())
                .firstPlayerScore(firstPlayerScore)
                .secondPlayerScore(secondPlayerScore)
                .gameWinner(winner)
                .build();
    }

    private static Score computeNewScore(Score pointWinnerScore, Score otherPlayerScore) {
        switch (pointWinnerScore) {
            case LOVE:
                return Score.FIFTEEN;
            case FIFTEEN:
                return Score.THIRTY;
            case THIRTY:
                return Score.FOURTY;
            case FOURTY:
                return Score.FOURTY.equals(otherPlayerScore) ? Score.ADVANTAGE : Score.FOURTY;
            default:
                return Score.ADVANTAGE;
        }
    }

}
