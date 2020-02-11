package com.onepoint.kata.tennisgame.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TieBreakPointWonEvent implements WonEvent {

    private final String tennisSetId;
    private final int firstPlayerTieBreakScore;
    private final int secondPlayerTieBreakScore;
    private final WonEventType wonEventType = WonEventType.TIE_BREAK_POINT;
}
