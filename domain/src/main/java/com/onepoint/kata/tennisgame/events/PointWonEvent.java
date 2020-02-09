package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PointWonEvent {
    private String gameId;
    private Score firstPlayerScore;
    private Score secondPlayerScore;
    private Player gameWinner;
}
