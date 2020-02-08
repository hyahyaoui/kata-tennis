package com.onepoint.kata.tennisgame.providers;

import com.onepoint.kata.tennisgame.events.GameStartedEvent;

public interface EventsProvider {
    void on(GameStartedEvent event);

}
