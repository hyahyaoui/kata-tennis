package com.onepoint.kata.tennisgame.commands;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.domain.Player;
import lombok.*;
import lombok.experimental.Accessors;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class StartGameCmd implements StartGameCommand {
    @TargetAggregateIdentifier
    @NonNull
    private String tennisSetId;
    @NonNull
    private String id;

    public static StartGameCmd from(StartGameCommand cmd) {
        return new StartGameCmd()
                .setTennisSetId(cmd.getTennisSetId())
                .setId(cmd.getId());
    }
}
