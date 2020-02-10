package com.onepoint.kata.tennisgame.aggregates;

import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.GameWonEvent;
import com.onepoint.kata.tennisgame.events.PointWonEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Getter
@NoArgsConstructor
public class GameAggregate implements Game {

    private String id;
    private String tennisSetId;
    private GameScore firstPlayerScore = GameScore.LOVE;
    private GameScore secondPlayerScore = GameScore.LOVE;
    private Player winner;

    public void on(GameStartedEvent event) {
        this.id = event.getGameId();
        this.tennisSetId = event.getTennisSetId();
        this.firstPlayerScore = event.getFirstPlayerScore();
        this.secondPlayerScore = event.getSecondPlayerScore();
    }

    public void on(PointWonEvent event) {
        this.firstPlayerScore = event.getFirstPlayerScore();
        this.secondPlayerScore = event.getSecondPlayerScore();
    }

    public void on(GameWonEvent event) {
        this.firstPlayerScore = event.getFirstPlayerScore();
        this.secondPlayerScore = event.getSecondPlayerScore();
        this.winner = event.getWinner();
    }

    public GameAggregate(String id) {
        this.id = id;
    }

}
