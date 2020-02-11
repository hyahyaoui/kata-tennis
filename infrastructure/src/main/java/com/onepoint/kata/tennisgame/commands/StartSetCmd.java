package com.onepoint.kata.tennisgame.commands;

import com.onepoint.kata.tennisgame.command.StartSetCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.domain.Player;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class StartSetCmd implements StartSetCommand {
    @TargetAggregateIdentifier
    @NonNull
    private String id;
    @NonNull
    private Player firstPlayer;
    @NonNull
    private Player secondPlayer;

    public static StartSetCmd from(StartSetCommand cmd) {
        return new StartSetCmd()
                .setId(cmd.getId())
                .setFirstPlayer(cmd.getFirstPlayer())
                .setSecondPlayer(cmd.getSecondPlayer());
    }
}
