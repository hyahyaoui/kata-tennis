package com.onepoint.kata.tennisgame.adapters;

import com.onepoint.kata.tennisgame.aggregates.GameAggregate;
import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.services.TennisGameService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcedAggregate;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommandsAdapterTest {

    private CommandGateway commandGateway = mock(CommandGateway.class);
    private Repository<GameAggregate> gameAggregateRepository = mock(Repository.class);
    private CommandsAdapter commandsAdapter;

    @Before
    public void init() {
        commandsAdapter = new CommandsAdapter(commandGateway, gameAggregateRepository);
    }

    @Test
    public void should_call_send_and_wait_when_start_game_cmd() {
        StartGameCmd startGameCmd = new StartGameCmd().setId("")
                .setFirstPlayer(new Player().setName(""))
                .setSecondPlayer(new Player().setName(""));
        commandsAdapter.send(startGameCmd);
        verify(commandGateway, times(1))
                .sendAndWait(any(StartGameCmd.class));
    }

    @Test
    public void should_call_send_and_wait_when_win_point_cmd() {
        WinPointCmd winPointCmd = new WinPointCmd().setGameId("");
        commandsAdapter.send(winPointCmd);
        verify(commandGateway, times(1))
                .sendAndWait(any(WinPointCmd.class));
    }
}