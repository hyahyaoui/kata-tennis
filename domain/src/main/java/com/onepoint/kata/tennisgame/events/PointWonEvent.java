package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.GameScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.onepoint.kata.tennisgame.events.WonEventType.POINT;

@Builder
@Getter
@AllArgsConstructor
public class PointWonEvent implements WonEvent {
    private final String gameId;
    private final String tennisSetId;
    private final GameScore firstPlayerScore;
    private final GameScore secondPlayerScore;
    private final WonEventType wonEventType = POINT;
}
