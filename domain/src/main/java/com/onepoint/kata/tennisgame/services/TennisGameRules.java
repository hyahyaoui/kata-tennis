package com.onepoint.kata.tennisgame.services;

import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.TennisSet;
import com.onepoint.kata.tennisgame.domain.TennisSetScore;

import java.util.Optional;

import static com.onepoint.kata.tennisgame.domain.GameScore.FOURTY;
import static com.onepoint.kata.tennisgame.domain.TennisSetScore.SEVEN;
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

    static GameScore updateGameScoreIfNeeded(boolean isGameWon, boolean isCurrentPlayerPointWinner,
                                             GameScore currentPlayerScore, GameScore pointWinnerScore) {
        if (isCurrentPlayerPointWinner) currentPlayerScore = pointWinnerScore;
        boolean advantageWillBeLost = !isGameWon && !isCurrentPlayerPointWinner &&
                GameScore.ADVANTAGE.equals(currentPlayerScore);
        return advantageWillBeLost ? FOURTY : currentPlayerScore;
    }

    static TennisSetScore computeTennisSetScore(TennisSetScore oldScore) {

        if (oldScore.getNumberOfPointWon() < SEVEN.getNumberOfPointWon()) {
            return oldScore.next();
        }
        return SEVEN;
    }

    static boolean isGameWon(GameScore pointWinnerGameScore, GameScore otherPlayerGameScore) {
        return (pointWinnerGameScore.getNumberOfPointWon() >= GameScore.FOURTY.getNumberOfPointWon())
                && (pointWinnerGameScore.getNumberOfPointWon() > otherPlayerGameScore.getNumberOfPointWon());
    }

    public static boolean isSetWon(TennisSetScore tennisSetWinnerCurrentScore,
                                   TennisSetScore otherPlayerScore) {
        return tennisSetWinnerCurrentScore.getNumberOfPointWon() >= SIX.getNumberOfPointWon()
                && tennisSetWinnerCurrentScore.getNumberOfPointWon() > otherPlayerScore.getNumberOfPointWon() + 1;
    }


    public static boolean shouldPlayTieBreak(TennisSetScore firstPlayerTennisSetScore,
                                             TennisSetScore secondPlayerTennisSetScore) {
        return firstPlayerTennisSetScore.equals(SIX) && secondPlayerTennisSetScore.equals(SIX);
    }

    public static int computeNewTieBreakScore(int pointWinnerTieBreakScore) {
        return ++pointWinnerTieBreakScore;
    }

    public static boolean isSetWithTieBreakWon(int newWinnerTieBreakScore, int otherPlayerTieBreakScore) {
        return newWinnerTieBreakScore >= 6 && (newWinnerTieBreakScore > otherPlayerTieBreakScore + 1);
    }
}
