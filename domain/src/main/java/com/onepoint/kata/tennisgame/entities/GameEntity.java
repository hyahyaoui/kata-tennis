package com.onepoint.kata.tennisgame.entities;

import com.onepoint.kata.tennisgame.domain.GameScore;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GameEntity {

    private String id;
    private GameScore firstPlayerGameScore;
    private GameScore secondPlayerGameScore;
    private String winner;
}
