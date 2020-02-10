package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.TennisSet;
import com.onepoint.kata.tennisgame.domain.TennisSetScore;

import java.util.Optional;

import static com.onepoint.kata.tennisgame.domain.GameScore.FOURTY;
import static com.onepoint.kata.tennisgame.domain.TennisSetScore.SIX;

class TennisGameRules {

    static GameScore computeGameScoreAfterPointWon(GameScore pointWinnerGameScore, GameScore otherPlayerGameScore) {
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

    static GameScore updateGameScoreIfNeeded(Optional<Player> gameWinner, boolean isCurrentPlayerPointWinner,
                                             GameScore currentPlayerScore, GameScore pointWinnerScore) {
        if (isCurrentPlayerPointWinner) currentPlayerScore = pointWinnerScore;
        boolean advantageWillBeLost = !gameWinner.isPresent() && !isCurrentPlayerPointWinner &&
                GameScore.ADVANTAGE.equals(currentPlayerScore);
        return advantageWillBeLost ? FOURTY : currentPlayerScore;
    }

    static TennisSetScore computeTennisSetScore(TennisSetScore oldScore) {

        if (oldScore.getNumberOfPointWon() < SIX.getNumberOfPointWon()) {
            return oldScore.next();
        }
        return SIX;
    }

    static Optional<Player> declareGameWinnerIfHappen(TennisSet tennisSet, boolean isFirstPlayerPointWinner,
                                                      GameScore pointWinnerGameScore, GameScore otherPlayerGameScore) {
        if (pointWinnerGameScore.getNumberOfPointWon() >= GameScore.FOURTY.getNumberOfPointWon()) {
            if (pointWinnerGameScore.getNumberOfPointWon() > otherPlayerGameScore.getNumberOfPointWon()) {
                return Optional.of(isFirstPlayerPointWinner ? tennisSet.getFirstPlayer() : tennisSet.getSecondPlayer());
            }
        }
        return Optional.empty();
    }

    public static Optional<Player> declareSetWinnerIfHappen(TennisSet tennisSet, boolean isFirstPlayerGameWinner,
                                                            TennisSetScore tennisSetWinnerCurrentScore) {
        if (tennisSetWinnerCurrentScore == SIX) {
            return Optional.of(isFirstPlayerGameWinner ? tennisSet.getFirstPlayer() : tennisSet.getSecondPlayer());
        }

        return Optional.empty();
    }
}
