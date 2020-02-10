package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.PointWonEvent;
import com.onepoint.kata.tennisgame.exceptions.BusinessRuleViolatedException;

import java.util.Optional;

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
        Optional<Player> winner = Optional.empty();
        Score pointWinnerNewScore;

        if (game.getGameWinner() != null) {
            throw new BusinessRuleViolatedException("Game already finished !");
        }

        final boolean isFirstPlayerPointWinner = cmd.getPointWinner().equals(game.getFirstPlayer());

        final Score pointWinnerScore = isFirstPlayerPointWinner ? game.getFirstPlayerScore() :
                game.getSecondPlayerScore();
        final Score otherPlayerScore = isFirstPlayerPointWinner ? game.getSecondPlayerScore() :
                game.getFirstPlayerScore();

        winner = declareWinnerIfHappen(game, isFirstPlayerPointWinner, pointWinnerScore, otherPlayerScore);

        pointWinnerNewScore = computeNewScore(pointWinnerScore, otherPlayerScore);

        Score firstPlayerScore = isFirstPlayerPointWinner ? pointWinnerNewScore : game.getFirstPlayerScore();
        Score secondPlayerScore = !isFirstPlayerPointWinner ? pointWinnerNewScore : game.getSecondPlayerScore();

        // Adjust score in case advantage lost
        if (!winner.isPresent() && isFirstPlayerPointWinner && Score.ADVANTAGE.equals(secondPlayerScore)) {
            secondPlayerScore = Score.FOURTY;
        }

        if (!winner.isPresent() && !isFirstPlayerPointWinner && Score.ADVANTAGE.equals(firstPlayerScore)) {
            firstPlayerScore = Score.FOURTY;
        }

        return PointWonEvent.builder()
                .gameId(cmd.getGameId())
                .firstPlayerScore(firstPlayerScore)
                .secondPlayerScore(secondPlayerScore)
                .gameWinner(winner.orElse(null))
                .build();
    }

    private static Optional<Player> declareWinnerIfHappen(Game game,boolean isFirstPlayerPointWinner,
                                                Score pointWinnerScore, Score otherPlayerScore) {
        if (pointWinnerScore.getNumberOfPointWon() >= Score.FOURTY.getNumberOfPointWon()) {
            if (pointWinnerScore.getNumberOfPointWon() > otherPlayerScore.getNumberOfPointWon()) {
                return  Optional.of(isFirstPlayerPointWinner ? game.getFirstPlayer() : game.getSecondPlayer());
            }
        }
        return Optional.empty();
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
