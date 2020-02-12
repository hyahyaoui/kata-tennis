package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.TennisSetScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TennisSetStartedEvent {

    private final String id;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final TennisSetScore firstPlayerScore;
    private final TennisSetScore secondPlayerScore;
}
