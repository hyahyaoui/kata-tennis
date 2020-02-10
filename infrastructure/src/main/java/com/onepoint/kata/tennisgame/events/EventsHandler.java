package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.providers.TennisRepositoryProvider;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;

import java.util.Optional;

@RequiredArgsConstructor
public class EventsHandler {

    public final TennisRepositoryProvider tennisRepositoryProvider;

    @EventHandler
    public void onGameStartedEvent(GameStartedEvent event) {
        GameEntity game = new GameEntity();
        game.setId(event.getId())
                .setFirstPlayerName(event.getFirstPlayer().getName())
                .setSecondPlayerName(event.getSecondPlayer().getName())
                .setFirstPlayerScore(event.getFirstPlayerScore())
                .setSecondPlayerScore(event.getSecondPlayerScore());
        tennisRepositoryProvider.saveGame(game);
    }

    @EventHandler
    public void onGameStartedEvent(PointWonEvent event) {
        Optional<GameEntity> game = tennisRepositoryProvider
                .findGame(event.getGameId());
        if (game.isPresent()) {
            final GameEntity gameEntity = game.get();
            final String gameWinner = event.getGameWinner() != null ? event.getGameWinner().getName() : null;
            gameEntity.setFirstPlayerScore(event.getFirstPlayerScore())
                    .setSecondPlayerScore(event.getSecondPlayerScore())
                    .setGameWinner(gameWinner);
            tennisRepositoryProvider.saveGame(gameEntity);
        }
    }
}
