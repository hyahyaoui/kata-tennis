package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GameStartedEvent {

    private final String id;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Score firstPlayerScore;
    private final Score secondPlayerScore;

}
