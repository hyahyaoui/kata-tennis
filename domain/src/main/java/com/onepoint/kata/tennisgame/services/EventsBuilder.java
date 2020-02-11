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

public class EventsBuilder {


    public static GameStartedEvent build(StartGameCommand cmd) {
        return GameStartedEvent
                .builder()
                .gameId(cmd.getId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerScore(LOVE)
                .secondPlayerScore(LOVE)
                .build();
    }

    public static TennisSetStartedEvent build(StartSetCommand cmd) {
        return TennisSetStartedEvent
                .builder()
                .id(cmd.getId())
                .firstPlayer(cmd.getFirstPlayer())
                .secondPlayer(cmd.getSecondPlayer())
                .firstPlayerScore(ZERO)
                .secondPlayerScore(ZERO)
                .build();
    }

    public static WonEvent build(WinPointCommand cmd, TennisSet tennisSet) {
        return buildWonEvent(cmd, tennisSet);
    }

    private static WonEvent buildWonEvent(WinPointCommand cmd, TennisSet tennisSet) {

        Game game = tennisSet.getGames().get(cmd.getGameId());

        if (game.getWinner() != null) {
            throw new BusinessRuleViolatedException("Game already finished !");
        }

        final boolean isFirstPlayerPointWinner = cmd.getWinner().equals(tennisSet.getFirstPlayer());
        GameScore firstPlayerGameScore = game.getFirstPlayerScore();
        GameScore secondPlayerGameScore = game.getSecondPlayerScore();
        final GameScore pointWinnerGameScore = isFirstPlayerPointWinner ? firstPlayerGameScore : secondPlayerGameScore;
        final GameScore otherPlayerGameScore = isFirstPlayerPointWinner ? secondPlayerGameScore : firstPlayerGameScore;


        Optional<Player> gameWinner = TennisGameRules.declareGameWinnerIfHappen(tennisSet, isFirstPlayerPointWinner,
                pointWinnerGameScore, otherPlayerGameScore);

        GameScore pointWinnerNewGameScore = TennisGameRules.computeGameScoreAfterPointWon(pointWinnerGameScore, otherPlayerGameScore);

        firstPlayerGameScore = TennisGameRules.updateGameScoreIfNeeded(gameWinner, isFirstPlayerPointWinner,
                firstPlayerGameScore, pointWinnerNewGameScore);
        secondPlayerGameScore = TennisGameRules.updateGameScoreIfNeeded(gameWinner, !isFirstPlayerPointWinner,
                secondPlayerGameScore, pointWinnerNewGameScore);


        if (!gameWinner.isPresent()) {
            return buildPointWonEvent(cmd, firstPlayerGameScore, secondPlayerGameScore);
        }
        return adjustTennisSetScoreAndBuildGameOrTennisSetWonEvent(cmd, gameWinner.get(),
                firstPlayerGameScore, secondPlayerGameScore, tennisSet);

    }


    private static PointWonEvent buildPointWonEvent(WinPointCommand cmd, GameScore firstPlayerGameScore,
                                                    GameScore secondPlayerGameScore) {
        return PointWonEvent.builder()
                .gameId(cmd.getGameId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerScore(firstPlayerGameScore)
                .secondPlayerScore(secondPlayerGameScore)
                .build();
    }

    private static WonEvent adjustTennisSetScoreAndBuildGameOrTennisSetWonEvent(WinPointCommand cmd, Player gameWinner,
                                                                                GameScore firstPlayerGameScore,
                                                                                GameScore secondPlayerGameScore,
                                                                                TennisSet tennisSet) {

        boolean isFirstPlayerGameWinner = gameWinner.getName().equals(tennisSet.getFirstPlayer().getName());

        TennisSetScore tennisSetWinnerOldScore = isFirstPlayerGameWinner ? tennisSet.getFirstPlayerScore() : tennisSet.getSecondPlayerScore();
        TennisSetScore tennisSetWinnerCurrentScore = TennisGameRules.computeTennisSetScore(tennisSetWinnerOldScore);

        Optional<Player> tennisSetWinner = TennisGameRules.declareSetWinnerIfHappen(tennisSet,
                isFirstPlayerGameWinner, tennisSetWinnerCurrentScore);

        if (!tennisSetWinner.isPresent()) {
            return buildGameWonEvent(cmd, gameWinner, firstPlayerGameScore, secondPlayerGameScore,
                    tennisSet, isFirstPlayerGameWinner, tennisSetWinnerCurrentScore);
        }

        return buildTennisSetWonEvent(cmd, tennisSetWinner.get(), firstPlayerGameScore, secondPlayerGameScore,
                tennisSet, isFirstPlayerGameWinner, tennisSetWinnerCurrentScore);
    }


    private static GameWonEvent buildGameWonEvent(WinPointCommand cmd, Player gameWinner,
                                                  GameScore firstPlayerGameScore, GameScore secondPlayerGameScore,
                                                  TennisSet tennisSet, boolean isFirstPlayerGameWinner,
                                                  TennisSetScore winnerScore) {
        return GameWonEvent.builder()
                .gameId(cmd.getGameId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerScore(firstPlayerGameScore)
                .secondPlayerScore(secondPlayerGameScore)
                .firstPlayerSetScore(isFirstPlayerGameWinner ? winnerScore : tennisSet.getFirstPlayerScore())
                .secondPlayerSetScore(!isFirstPlayerGameWinner ? winnerScore : tennisSet.getSecondPlayerScore())
                .secondPlayerSetScore(!isFirstPlayerGameWinner ? winnerScore : tennisSet.getSecondPlayerScore())
                .winner(gameWinner)
                .build();
    }


    private static TennisSetWonEvent buildTennisSetWonEvent(WinPointCommand cmd, Player tennisSetWinner,
                                                            GameScore firstPlayerGameScore, GameScore secondPlayerGameScore,
                                                            TennisSet tennisSet, boolean isFirstPlayerGameWinner,
                                                            TennisSetScore winnerScore) {
        return TennisSetWonEvent.builder()
                .lastGameId(cmd.getGameId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerGameScore(firstPlayerGameScore)
                .secondPlayerGameScore(secondPlayerGameScore)
                .firstPlayerSetScore(isFirstPlayerGameWinner ? winnerScore : tennisSet.getFirstPlayerScore())
                .firstPlayerSetScore(!isFirstPlayerGameWinner ? winnerScore : tennisSet.getSecondPlayerScore())
                .winner(tennisSetWinner)
                .build();

    }

}
