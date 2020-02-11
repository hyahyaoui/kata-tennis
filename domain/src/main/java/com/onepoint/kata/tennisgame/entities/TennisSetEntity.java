package com.onepoint.kata.tennisgame.entities;

import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.TennisSetScore;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class TennisSetEntity {

    private String id;
    private String firstPlayerName;
    private String secondPlayerName;
    private Map<String, GameEntity> gameEntities = new HashMap<>();
    private TennisSetScore firstPlayerScore;
    private TennisSetScore secondPlayerScore;
    private String winner;
}
