package com.onepoint.kata.tennisgame.aggregates;

import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import com.onepoint.kata.tennisgame.events.GameStartedEvent;
import com.onepoint.kata.tennisgame.events.PointWonEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Getter
@NoArgsConstructor
public class GameAggregate implements Game {

    @AggregateIdentifier
    private String id;
    private Player firstPlayer;
    private Player secondPlayer;
    private Score firstPlayerScore;
    private Score secondPlayerScore;
    private Player gameWinner;


    public void handle(GameStartedEvent gameStartedEvent) {
        apply(gameStartedEvent);
    }

    public void handle(PointWonEvent pointWonEvent) {
        apply(pointWonEvent);
    }

    @EventSourcingHandler
    public void on(GameStartedEvent gameStartedEvent) {
        this.id = gameStartedEvent.getId();
        this.firstPlayer = gameStartedEvent.getFirstPlayer();
        this.secondPlayer = gameStartedEvent.getSecondPlayer();
        this.firstPlayerScore = gameStartedEvent.getFirstPlayerScore();
        this.secondPlayerScore = gameStartedEvent.getSecondPlayerScore();
    }

    @EventSourcingHandler
    public void on(PointWonEvent pointWonEvent) {
        this.firstPlayerScore = pointWonEvent.getFirstPlayerScore();
        this.secondPlayerScore = pointWonEvent.getSecondPlayerScore();
        this.gameWinner = pointWonEvent.getGameWinner();
    }

    public GameAggregate(String id) {
        this.id = id;
    }

}
