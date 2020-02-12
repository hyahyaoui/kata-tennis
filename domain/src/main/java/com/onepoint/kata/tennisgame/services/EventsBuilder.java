package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.*;
import com.onepoint.kata.tennisgame.events.*;

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

        if (tennisSet.isWithTieBreak()) {
            return buildTieBreakPointOrGameWonEvent(cmd, tennisSet);
        }

        final boolean isFirstPlayerPointWinner = cmd.getWinner().equals(tennisSet.getFirstPlayer());
        GameScore firstPlayerGameScore = game.getFirstPlayerScore();
        GameScore secondPlayerGameScore = game.getSecondPlayerScore();
        final GameScore pointWinnerGameScore = isFirstPlayerPointWinner ? firstPlayerGameScore : secondPlayerGameScore;
        final GameScore otherPlayerGameScore = !isFirstPlayerPointWinner ? firstPlayerGameScore : secondPlayerGameScore;


        boolean isGameWon = TennisGameRules.isGameWon(pointWinnerGameScore, otherPlayerGameScore);

        GameScore pointWinnerNewGameScore = TennisGameRules.computeGameScoreAfterPointWon(pointWinnerGameScore, otherPlayerGameScore);

        firstPlayerGameScore = TennisGameRules.updateGameScoreIfNeeded(isGameWon, isFirstPlayerPointWinner,
                firstPlayerGameScore, pointWinnerNewGameScore);
        secondPlayerGameScore = TennisGameRules.updateGameScoreIfNeeded(isGameWon, !isFirstPlayerPointWinner,
                secondPlayerGameScore, pointWinnerNewGameScore);


        if (!isGameWon) {
            return buildPointWonEvent(cmd, firstPlayerGameScore, secondPlayerGameScore);
        }
        return adjustTennisSetScoreAndBuildGameOrTennisSetWonEvent(cmd, cmd.getWinner(),
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
        TennisSetScore otherPlayerScore = !isFirstPlayerGameWinner ? tennisSet.getFirstPlayerScore() : tennisSet.getSecondPlayerScore();
        TennisSetScore tennisSetWinnerCurrentScore = TennisGameRules.computeTennisSetScore(tennisSetWinnerOldScore);

        boolean isSetWon = TennisGameRules.isSetWon(tennisSetWinnerCurrentScore, otherPlayerScore);

        final TennisSetScore firstPlayerSetScore = isFirstPlayerGameWinner ? tennisSetWinnerCurrentScore :
                tennisSet.getFirstPlayerScore();
        final TennisSetScore secondPlayerSetScore = !isFirstPlayerGameWinner ? tennisSetWinnerCurrentScore :
                tennisSet.getSecondPlayerScore();

        if (!isSetWon) {
            boolean isTieBreakToBeTriggered = TennisGameRules.shouldPlayTieBreak(firstPlayerSetScore,
                    secondPlayerSetScore);
            return buildGameWonEvent(cmd, firstPlayerGameScore, secondPlayerGameScore,
                    firstPlayerSetScore, secondPlayerSetScore, isTieBreakToBeTriggered);
        }

        return buildTennisSetWonEvent(cmd, firstPlayerGameScore, secondPlayerGameScore,
                firstPlayerSetScore, secondPlayerSetScore, 0, 0);
    }


    private static WonEvent buildTieBreakPointOrGameWonEvent(WinPointCommand cmd, TennisSet tennisSet) {

        boolean isFirstPlayerPointWinner = cmd.getWinner().equals(tennisSet.getFirstPlayer());

        int pointWinnerTieBreakScore = isFirstPlayerPointWinner ? tennisSet.getFirstPlayerTieBreakScore() :
                tennisSet.getSecondPlayerTieBreakScore();
        int otherPlayerTieBreakScore = !isFirstPlayerPointWinner ? tennisSet.getFirstPlayerTieBreakScore() :
                tennisSet.getSecondPlayerTieBreakScore();

        int newWinnerTieBreakScore = TennisGameRules.computeNewTieBreakScore(pointWinnerTieBreakScore);
        int firstPlayerTieBreakScore = isFirstPlayerPointWinner ? newWinnerTieBreakScore : otherPlayerTieBreakScore;
        int secondPlayerTieBreakScore = !isFirstPlayerPointWinner ? newWinnerTieBreakScore : otherPlayerTieBreakScore;

        boolean isGameWon = TennisGameRules.isSetWithTieBreakWon(newWinnerTieBreakScore, otherPlayerTieBreakScore);

        if (!isGameWon) {
            return buildTieBreakPointWonEvent(cmd.getTennisSetId(), firstPlayerTieBreakScore,
                    secondPlayerTieBreakScore);
        }
        return buildTennisSetWonEvent(cmd, null, null,
                tennisSet.getFirstPlayerScore(), tennisSet.getSecondPlayerScore(),
                firstPlayerTieBreakScore, secondPlayerTieBreakScore);
    }

    private static GameWonEvent buildGameWonEvent(WinPointCommand cmd,
                                                  GameScore firstPlayerGameScore, GameScore secondPlayerGameScore,
                                                  TennisSetScore firstPlayerSetScore, TennisSetScore secondPlayerSetScore,
                                                  boolean isTieBreakToBeTriggered) {
        return GameWonEvent.builder()
                .gameId(cmd.getGameId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerScore(firstPlayerGameScore)
                .secondPlayerScore(secondPlayerGameScore)
                .firstPlayerSetScore(firstPlayerSetScore)
                .secondPlayerSetScore(secondPlayerSetScore)
                .gameTriggeringTieBreak(isTieBreakToBeTriggered)
                .winner(cmd.getWinner()) // last one to win a point is necessarily game winner
                .build();
    }


    private static TennisSetWonEvent buildTennisSetWonEvent(WinPointCommand cmd,
                                                            GameScore firstPlayerGameScore, GameScore secondPlayerGameScore,
                                                            TennisSetScore firstPlayerSetScore,
                                                            TennisSetScore secondPlayerSetScore,
                                                            int firstPlayerTieBreakScore,
                                                            int secondPlayerTieBreakScore) {
        return TennisSetWonEvent.builder()
                .lastGameId(cmd.getGameId())
                .tennisSetId(cmd.getTennisSetId())
                .firstPlayerGameScore(firstPlayerGameScore)
                .secondPlayerGameScore(secondPlayerGameScore)
                .firstPlayerSetScore(firstPlayerSetScore)
                .secondPlayerSetScore(secondPlayerSetScore)
                .firstPlayerTieBreakScore(firstPlayerTieBreakScore)
                .secondPlayerTieBreakScore(secondPlayerTieBreakScore)
                .winner(cmd.getWinner()) // last one to win a point is necessarily game winner
                .build();

    }

    private static TieBreakPointWonEvent buildTieBreakPointWonEvent(String tennisSetId, int firstPlayerTieBreakScore,
                                                                    int secondPlayerTieBreakScore) {
        return TieBreakPointWonEvent
                .builder()
                .tennisSetId(tennisSetId)
                .firstPlayerTieBreakScore(firstPlayerTieBreakScore)
                .secondPlayerTieBreakScore(secondPlayerTieBreakScore)
                .build();
    }
}
