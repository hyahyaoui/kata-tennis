package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.*;
import com.onepoint.kata.tennisgame.events.*;
import com.onepoint.kata.tennisgame.exceptions.BusinessRuleViolatedException;

import java.util.Optional;

import static com.onepoint.kata.tennisgame.domain.GameScore.LOVE;
import static com.onepoint.kata.tennisgame.domain.TennisSetScore.*;

public class ScoreCalculator {

    public static GameStartedEvent computeScoreWhenGameStartedEvent(StartGameCommand cmd) {
        return GameStartedEvent
                .builder()
                .gameId(cmd.getId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerScore(LOVE)
                .secondPlayerScore(LOVE)
                .build();
    }

    public static TennisSetStartedEvent computeScoreWhenTennisSetStartedEvent(StartSetCommand cmd) {
        return TennisSetStartedEvent
                .builder()
                .id(cmd.getId())
                .id(cmd.getId())
                .firstPlayer(cmd.getFirstPlayer())
                .secondPlayer(cmd.getSecondPlayer())
                .firstPlayerScore(ZERO)
                .secondPlayerScore(ZERO)
                .build();
    }

    public static WonEvent computeScoreWhenPointWonEvent(WinPointCommand cmd, TennisSet tennisSet) {
        return gameOrPointWon(cmd, tennisSet);
    }

    private static WonEvent gameOrPointWon(WinPointCommand cmd, TennisSet tennisSet) {
        Optional<Player> gameWinner = Optional.empty();
        GameScore pointWinnerNewGameScore;
        Game game = tennisSet.getGames().get(cmd.getGameId());
        if (game.getWinner() != null) {
            throw new BusinessRuleViolatedException("Game already finished !");
        }

        final boolean isFirstPlayerPointWinner = cmd.getWinner().equals(tennisSet.getFirstPlayer());

        final GameScore firstPlayerScore = game.getFirstPlayerScore();
        final GameScore secondPlayerScore = game.getSecondPlayerScore();
        final GameScore pointWinnerGameScore = isFirstPlayerPointWinner ? firstPlayerScore : secondPlayerScore;
        final GameScore otherPlayerGameScore = isFirstPlayerPointWinner ? secondPlayerScore : firstPlayerScore;

        gameWinner = declareGameWinnerIfHappen(tennisSet, isFirstPlayerPointWinner, pointWinnerGameScore, otherPlayerGameScore);

        pointWinnerNewGameScore = computeNewScore(pointWinnerGameScore, otherPlayerGameScore);

        GameScore firstPlayerGameScore = isFirstPlayerPointWinner ? pointWinnerNewGameScore : firstPlayerScore;
        GameScore secondPlayerGameScore = !isFirstPlayerPointWinner ? pointWinnerNewGameScore : secondPlayerScore;

        // Adjust score in case advantage lost

        if (!gameWinner.isPresent() && isFirstPlayerPointWinner && GameScore.ADVANTAGE.equals(secondPlayerGameScore)) {
            secondPlayerGameScore = GameScore.FOURTY;
        }

        if (!gameWinner.isPresent() && !isFirstPlayerPointWinner && GameScore.ADVANTAGE.equals(firstPlayerGameScore)) {
            firstPlayerGameScore = GameScore.FOURTY;
        }

        if (!gameWinner.isPresent()) {
            return pointWonEvent(cmd, firstPlayerGameScore, secondPlayerGameScore);
        }
        return gameWonEvent(cmd, gameWinner, firstPlayerGameScore, secondPlayerGameScore, tennisSet);
    }

    private static Optional<Player> declareGameWinnerIfHappen(TennisSet tennisSet, boolean isFirstPlayerPointWinner,
                                                              GameScore pointWinnerGameScore, GameScore otherPlayerGameScore) {
        if (pointWinnerGameScore.getNumberOfPointWon() >= GameScore.FOURTY.getNumberOfPointWon()) {
            if (pointWinnerGameScore.getNumberOfPointWon() > otherPlayerGameScore.getNumberOfPointWon()) {
                return Optional.of(isFirstPlayerPointWinner ? tennisSet.getFirstPlayer() : tennisSet.getSecondPlayer());
            }
        }
        return Optional.empty();
    }


    private static PointWonEvent pointWonEvent(WinPointCommand cmd, GameScore firstPlayerGameScore,
                                               GameScore secondPlayerGameScore) {
        return PointWonEvent.builder()
                .gameId(cmd.getGameId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerScore(firstPlayerGameScore)
                .secondPlayerScore(secondPlayerGameScore)
                .build();
    }

    private static GameWonEvent gameWonEvent(WinPointCommand cmd, Optional<Player> gameWinner,
                                             GameScore firstPlayerGameScore, GameScore secondPlayerGameScore,
                                             TennisSet tennisSet) {
        boolean isFirstPlayerGameWinner = gameWinner.get().getName().equals(tennisSet.getFirstPlayer().getName());
        TennisSetScore oldWinnerScore = isFirstPlayerGameWinner ? tennisSet.getFirstPlayerScore() : tennisSet.getSecondPlayerScore();
        TennisSetScore winnerScore = computeNewScore(oldWinnerScore);
        return GameWonEvent.builder()
                .gameId(cmd.getGameId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerScore(firstPlayerGameScore)
                .secondPlayerScore(secondPlayerGameScore)
                .firstPlayerSetScore(isFirstPlayerGameWinner ? winnerScore : tennisSet.getFirstPlayerScore())
                .firstPlayerSetScore(!isFirstPlayerGameWinner ? winnerScore : tennisSet.getSecondPlayerScore())
                .winner(gameWinner.get())
                .build();
    }

    private static GameScore computeNewScore(GameScore pointWinnerGameScore, GameScore otherPlayerGameScore) {
        switch (pointWinnerGameScore) {
            case LOVE:
                return GameScore.FIFTEEN;
            case FIFTEEN:
                return GameScore.THIRTY;
            case THIRTY:
                return GameScore.FOURTY;
            case FOURTY:
                return GameScore.FOURTY.equals(otherPlayerGameScore) ? GameScore.ADVANTAGE : GameScore.FOURTY;
            default:
                return GameScore.ADVANTAGE;
        }
    }

    private static TennisSetScore computeNewScore(TennisSetScore oldScore) {

        if (oldScore.getNumberOfPointWon() < SIX.getNumberOfPointWon()) {
            return oldScore.next();
        }
        return SIX;
    }


}
