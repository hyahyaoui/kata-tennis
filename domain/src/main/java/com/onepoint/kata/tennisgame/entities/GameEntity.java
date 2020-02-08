package com.onepoint.kata.tennisgame.entities;

import com.onepoint.kata.tennisgame.domain.Score;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GameEntity {

    private String id;
    private String firstPlayerName;
    private String secondPlayerName;
    private Score firstPlayerScore;
    private Score secondPlayerScore;
    private String gameWinner;
}
