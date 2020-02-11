package com.onepoint.kata.tennisgame.events;

import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.TennisSetScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.onepoint.kata.tennisgame.events.WonEventType.GAME;

@Builder
@Getter
@AllArgsConstructor
public class GameWonEvent implements WonEvent{
    private final String gameId;
    private final String tennisSetId;
    private final GameScore firstPlayerScore;
    private final GameScore secondPlayerScore;
    private final TennisSetScore firstPlayerSetScore;
    private final TennisSetScore secondPlayerSetScore;
    private final Player winner;
    private final WonEventType wonEventType = GAME;

}
