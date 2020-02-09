package com.onepoint.kata.tennisgame.adapters;

import com.onepoint.kata.tennisgame.aggregates.GameAggregate;
import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.providers.TennisRepositoryProvider;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class TennisRepositoryAdapter implements TennisRepositoryProvider {

    private Map<String, GameEntity> games = new HashMap<>();

    @Override
    public Optional<GameEntity> findGame(String id) {
        final GameEntity game = games.get(id);
        return (game != null) ? Optional.of(game) : Optional.empty();
    }

    @Override
    public void saveGame(GameEntity game) {
        games.put(game.getId(), game);
    }

}
