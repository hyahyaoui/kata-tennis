package com.onepoint.kata.tennisgame.adapters;

import com.onepoint.kata.tennisgame.aggregates.TennisSetAggregate;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.domain.Player;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.Repository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CommandsAdapterTest {

    private CommandGateway commandGateway = mock(CommandGateway.class);
    private Repository<TennisSetAggregate> tennisSetAggregateRepository = mock(Repository.class);
    private CommandsAdapter commandsAdapter;

    @Before
    public void init() {
        commandsAdapter = new CommandsAdapter(commandGateway, tennisSetAggregateRepository);
    }

    @Test
    public void should_call_send_and_wait_when_start_game_cmd() {
        StartGameCmd startGameCmd = new StartGameCmd()
                .setTennisSetId("")
                .setId("");
        commandsAdapter.send(startGameCmd);
        verify(commandGateway, times(1))
                .sendAndWait(any(StartGameCmd.class));
    }

    @Test
    public void should_call_send_and_wait_when_win_point_cmd() {
        WinPointCmd winPointCmd = new WinPointCmd()
                .setGameId("")
                .setTennisSetId("")
                .setWinner(new Player().setName(""));
        commandsAdapter.send(winPointCmd);
        verify(commandGateway, times(1))
                .sendAndWait(any(WinPointCmd.class));
    }
}
