package com.onepoint.kata.tennisgame.commands;

import com.onepoint.kata.tennisgame.command.StartGameCommand;
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
    private String id;
    @NonNull
    private Player firstPlayer;
    @NonNull
    private Player secondPlayer;

    public static  StartGameCmd from(StartGameCommand cmd) {
        return new StartGameCmd()
                .setId(cmd.getId())
                .setFirstPlayer(cmd.getFirstPlayer())
                .setSecondPlayer(cmd.getSecondPlayer());
    }
}
