package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.aggregates.GameAggregate;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.entities.TennisSetEntity;
import com.onepoint.kata.tennisgame.providers.TennisRepositoryProvider;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;

import java.util.Optional;

@RequiredArgsConstructor
public class EventsHandler {

    public final TennisRepositoryProvider tennisRepositoryProvider;

    @EventHandler
    public void on(TennisSetStartedEvent event) {
        TennisSetEntity tennisSetEntity = new TennisSetEntity();
        tennisSetEntity.setId(event.getId())
                .setFirstPlayerName(event.getFirstPlayer().getName())
                .setSecondPlayerName(event.getSecondPlayer().getName())
                .setFirstPlayerScore(event.getFirstPlayerScore())
                .setSecondPlayerScore(event.getSecondPlayerScore());

        tennisRepositoryProvider.saveTennisSet(tennisSetEntity);
    }

    @EventHandler
    public void on(GameStartedEvent event) {
        GameEntity game = new GameEntity();
        game.setId(event.getGameId())
                .setFirstPlayerGameScore(event.getFirstPlayerScore())
                .setSecondPlayerGameScore(event.getSecondPlayerScore());
        tennisRepositoryProvider.saveGame(event.getTennisSetId(), game);
    }

    @EventHandler
    public void on(PointWonEvent event) {
        Optional<GameEntity> game = tennisRepositoryProvider
                .findGame(event.getTennisSetId(), event.getGameId());
        if (game.isPresent()) {
            final GameEntity gameEntity = game.get();
            gameEntity.setFirstPlayerGameScore(event.getFirstPlayerScore())
                    .setSecondPlayerGameScore(event.getSecondPlayerScore());
            tennisRepositoryProvider.saveGame(event.getTennisSetId(), gameEntity);
        }

    }

    @EventHandler
    public void on(GameWonEvent event) {
        Optional<GameEntity> game = tennisRepositoryProvider
                .findGame(event.getTennisSetId(), event.getGameId());
        if (game.isPresent()) {
            final GameEntity gameEntity = game.get();
            final String gameWinner = (event.getWinner() != null) ? event.getWinner().getName() : null;
            gameEntity.setFirstPlayerGameScore(event.getFirstPlayerScore())
                    .setSecondPlayerGameScore(event.getSecondPlayerScore())
                    .setWinner(gameWinner);
            tennisRepositoryProvider.saveGame(event.getTennisSetId(), gameEntity);
        }
    }

    @EventHandler
    public void on(TennisSetWonEvent event) {
        Optional<TennisSetEntity> tennisSet = tennisRepositoryProvider
                .findTennisSet(event.getTennisSetId());
        if (tennisSet.isPresent()) {
            final String gameWinner = event.getWinner().getName();
            final String lastGameId = event.getLastGameId();

            TennisSetEntity tennisSetEntity = tennisSet.get();
            final boolean isTieBreak = event.getFirstPlayerTieBreakScore() != 0
                    || event.getSecondPlayerTieBreakScore() != 0;
            if (!isTieBreak) {
                tennisSetEntity.getGameEntities().get(lastGameId)
                        .setFirstPlayerGameScore(event.getFirstPlayerGameScore());
                tennisSetEntity.getGameEntities().get(lastGameId)
                        .setSecondPlayerGameScore(event.getFirstPlayerGameScore());
                tennisSetEntity.getGameEntities().get(lastGameId).setWinner(event.getWinner().getName());
            }
            tennisSetEntity.setSecondPlayerScore(event.getSecondPlayerSetScore());
            tennisSetEntity.setSecondPlayerScore(event.getSecondPlayerSetScore());
            tennisSetEntity.setTieBeakFirstPlayerScore(event.getFirstPlayerTieBreakScore());
            tennisSetEntity.setTieBeakSecondPlayerScore(event.getSecondPlayerTieBreakScore());
            tennisSetEntity.setWinner(gameWinner);

            tennisRepositoryProvider.saveTennisSet(tennisSetEntity);
        }
    }
}
