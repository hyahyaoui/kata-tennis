package com.onepoint.kata.tennisgame.adapters;

import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.entities.TennisSetEntity;
import com.onepoint.kata.tennisgame.providers.TennisRepositoryProvider;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class TennisRepositoryAdapter implements TennisRepositoryProvider {

    private Map<String, TennisSetEntity> tennisSets = new HashMap<>();

    @Override
    public Optional<GameEntity> findGame(String tennisSetId, String gameId) {
        TennisSetEntity tennisSet = tennisSets.get(tennisSetId);
        return (tennisSet != null) ?
                Optional.of(tennisSet.getGameEntities().get(gameId)) : Optional.empty();

    }

    @Override
    public void saveGame(String tennisSetId, GameEntity game) {
        TennisSetEntity tennisSet = tennisSets.get(tennisSetId);
        if (tennisSet != null) {
            tennisSet.getGameEntities().put(game.getId(), game);
        }

    }

    @Override
    public Optional<TennisSetEntity> findTennisSet(String id) {
        final TennisSetEntity tennisSetEntity = tennisSets.get(id);
        return (tennisSetEntity != null) ? Optional.of(tennisSetEntity) : Optional.empty();
    }

    @Override
    public void saveTennisSet(TennisSetEntity tennisSetEntity) {
        tennisSets.put(tennisSetEntity.getId(), tennisSetEntity);

    }
}
