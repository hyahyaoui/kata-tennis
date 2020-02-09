package com.onepoint.kata.tennisgame.providers;

import com.onepoint.kata.tennisgame.domain.Game;
import com.onepoint.kata.tennisgame.entities.GameEntity;

import java.util.Optional;

public interface TennisRepositoryProvider {

    Optional<GameEntity> findGame(String id);

    void saveGame(GameEntity game);
}
