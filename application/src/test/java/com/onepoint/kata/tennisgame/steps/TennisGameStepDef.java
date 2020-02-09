package com.onepoint.kata.tennisgame.steps;

import com.onepoint.kata.tennisgame.SpringTestConfig;
import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.services.TennisGameService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = SpringTestConfig.class)
public class TennisGameStepDef {

    @Autowired
    TennisGameService tennisGameService;
    String id = UUID.randomUUID().toString();

    @Given("A tennis game started with the following information")
    public void tennisGameStarted(Map<String, String> values) {
        StartGameCommand startGameCommand = new StartGameCmd()
                .setId(id)
                .setFirstPlayer(new Player().setName(values.get("firstPlayerName")))
                .setSecondPlayer(new Player().setName(values.get("secondPlayerName")));
        tennisGameService.send(startGameCommand);
    }

    @When("The following point are won")
    public void theFollowingPointAreWon(List<String> pointWinners) {
        pointWinners.forEach(pointWinner -> {
            tennisGameService.send(
                    new WinPointCmd().setGameId(id)
                            .setPointWinner(new Player().setName(pointWinner)));
        });
    }

    @Then("Player {string} should win the game")
    public void playerPlayerShouldWonTheGame(String winner) {
        final Optional<GameEntity> game = tennisGameService.findGame(id);
        GameEntity gameEntity = game.isPresent() ? game.get() : new GameEntity();
        assertEquals(gameEntity.getGameWinner(), winner);
    }

}
