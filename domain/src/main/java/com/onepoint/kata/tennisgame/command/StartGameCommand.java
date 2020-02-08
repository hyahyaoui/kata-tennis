package com.onepoint.kata.tennisgame.command;

import com.onepoint.kata.tennisgame.domain.Player;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface StartGameCommand {
    String getId();

    Player getFirstPlayer();

    Player getSecondPlayer();
}
