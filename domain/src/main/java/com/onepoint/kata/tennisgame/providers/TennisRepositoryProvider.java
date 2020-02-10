package com.onepoint.kata.tennisgame.providers;

import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.entities.TennisSetEntity;

import java.util.Optional;

public interface TennisRepositoryProvider {

    Optional<GameEntity> findGame(String tennisSetId, String gameId);

    void saveGame(String tennisSetId, GameEntity game);

    Optional<TennisSetEntity> findTennisSet(String id);

    void saveTennisSet(TennisSetEntity tennisSet);
}
