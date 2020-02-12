package com.onepoint.kata.tennisgame.adapters;

import com.onepoint.kata.tennisgame.aggregates.TennisSetAggregate;
import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.StartSetCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.exceptions.MiddleWareException;
import com.onepoint.kata.tennisgame.providers.CommandsProvider;
import com.onepoint.kata.tennisgame.services.EventsBuilder;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.Repository;

@RequiredArgsConstructor
public class CommandsAdapter implements CommandsProvider {

    private final CommandGateway commandGateway;
    private final Repository<TennisSetAggregate> tennisSetAggregateRepository;

    @Override
    public void send(StartGameCommand cmd) {
        commandGateway.sendAndWait(StartGameCmd.from(cmd));
    }

    @Override
    public void send(WinPointCommand cmd) {
        commandGateway.sendAndWait(WinPointCmd.from(cmd));
    }

    @Override
    public void send(StartSetCommand cmd) {
        commandGateway.sendAndWait(StartSetCmd.from(cmd));
    }


    @CommandHandler
    public void handle(StartSetCmd cmd) {
        TennisSetAggregate tennisSetAggregate = new TennisSetAggregate(cmd.getId());
        try {
            tennisSetAggregateRepository.newInstance(() -> tennisSetAggregate);
        } catch (Exception e) {
            throw new MiddleWareException(e);
        }

        tennisSetAggregateRepository.load(cmd.getId())
                .execute(aggregate -> aggregate.handle(EventsBuilder.build(cmd)));
    }

    @CommandHandler
    public void handle(StartGameCmd cmd) {
        tennisSetAggregateRepository.load(cmd.getTennisSetId())
                .execute(aggregate -> aggregate.handle(EventsBuilder.build(cmd)));
    }

    @CommandHandler
    public void handle(WinPointCmd cmd) {
        tennisSetAggregateRepository.load(cmd.getTennisSetId())
                .execute(aggregate -> aggregate.handle(
                        EventsBuilder.build(cmd, aggregate)));
    }


}
