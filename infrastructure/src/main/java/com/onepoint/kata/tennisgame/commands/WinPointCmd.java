package com.onepoint.kata.tennisgame.commands;

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
public class WinPointCmd implements WinPointCommand {
    @TargetAggregateIdentifier
    @NonNull
    private String gameId;
    private Player pointWinner;

    public static WinPointCmd from(WinPointCommand cmd) {
        return new WinPointCmd()
                .setGameId(cmd.getGameId())
                .setPointWinner(cmd.getPointWinner());
    }
}
