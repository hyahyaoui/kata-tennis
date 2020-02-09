package com.onepoint.kata.tennisgame.adapters;

import com.onepoint.kata.tennisgame.aggregates.GameAggregate;
import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.exceptions.MiddleWareException;
import com.onepoint.kata.tennisgame.providers.CommandsProvider;
import com.onepoint.kata.tennisgame.services.ScoreCalculator;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.Repository;

@RequiredArgsConstructor
public class CommandsAdapter implements CommandsProvider {

    private final CommandGateway commandGateway;
    private final Repository<GameAggregate> gameAggregateRepository;

    @Override
    public void send(StartGameCommand cmd) {
        commandGateway.sendAndWait(StartGameCmd.from(cmd));
    }

    @Override
    public void send(WinPointCommand cmd) {
        commandGateway.sendAndWait(WinPointCmd.from(cmd));
    }


    @CommandHandler
    public void handle(StartGameCmd cmd) {
        GameAggregate gameAggregate = new GameAggregate(cmd.getId());
        try {
            gameAggregateRepository.newInstance(() -> gameAggregate);
        } catch (Exception e) {
            throw new MiddleWareException(e);
        }

        gameAggregateRepository.load(cmd.getId())
                .execute(aggregate -> aggregate.handle(ScoreCalculator.computeAndCreateEvent(cmd)));
    }

    @CommandHandler
    public void handle(WinPointCmd cmd) {
        gameAggregateRepository.load(cmd.getGameId())
                .execute(aggregate -> aggregate.handle(ScoreCalculator.computeAndCreateEvent(cmd, aggregate)));
    }


}
