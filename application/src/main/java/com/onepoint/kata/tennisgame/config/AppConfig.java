package com.onepoint.kata.tennisgame.config;

import com.onepoint.kata.tennisgame.adapters.TennisRepositoryAdapter;
import com.onepoint.kata.tennisgame.adapters.CommandsAdapter;
import com.onepoint.kata.tennisgame.aggregates.GameAggregate;
import com.onepoint.kata.tennisgame.aggregates.TennisSetAggregate;
import com.onepoint.kata.tennisgame.events.EventsHandler;
import com.onepoint.kata.tennisgame.providers.CommandsProvider;
import com.onepoint.kata.tennisgame.services.ScoreCalculator;
import com.onepoint.kata.tennisgame.services.TennisGameService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TennisGameService tennisGameService(CommandsProvider commandsProvider,
                                               TennisRepositoryAdapter tennisRepositoryAdapter) {
        return new TennisGameService(commandsProvider, tennisRepositoryAdapter);
    }

    @Bean
    public CommandsProvider commandsProvider(CommandGateway commandGateway,
                                             Repository<TennisSetAggregate> tennisSetAggregateRepository) {
        return new CommandsAdapter(commandGateway, tennisSetAggregateRepository);
    }

    @Bean
    public Repository<TennisSetAggregate> tennisSetAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(TennisSetAggregate.class)
                .cache(cache())
                .eventStore(eventStore)
                .build();
    }

    @Bean
    public ScoreCalculator scoreCalculator() {
        return new ScoreCalculator();
    }

    @Bean
    public TennisRepositoryAdapter tennisRepositoryAdapter() {
        return new TennisRepositoryAdapter();
    }

    @Bean
    public EventsHandler eventsHandler(TennisRepositoryAdapter tennisRepositoryAdapter){
        return  new EventsHandler(tennisRepositoryAdapter);
    }

    private Cache cache() {
        return new WeakReferenceCache();
    }

}
