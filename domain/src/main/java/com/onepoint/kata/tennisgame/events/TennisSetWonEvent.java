package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.TennisSetScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.onepoint.kata.tennisgame.events.WonEventType.SET;

@Builder
@Getter
@AllArgsConstructor
public class TennisSetWonEvent implements WonEvent {
    private final String lastGameId;
    private final String tennisSetId;
    private final GameScore firstPlayerGameScore;
    private final GameScore secondPlayerGameScore;
    private final TennisSetScore firstPlayerSetScore;
    private final TennisSetScore secondPlayerSetScore;
    private int firstPlayerTieBreakScore;
    private int secondPlayerTieBreakScore;
    private final Player winner;
    private final WonEventType wonEventType = SET;
}
