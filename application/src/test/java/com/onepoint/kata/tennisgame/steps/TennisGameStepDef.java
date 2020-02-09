package com.onepoint.kata.tennisgame.steps;

import com.onepoint.kata.tennisgame.SpringTestConfig;
import com.onepoint.kata.tennisgame.command.StartGameCommand;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.Score;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.services.TennisGameService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
    String id;

    @Given("A tennis game started with the following information")
    public void tennisGameStarted(Map<String, String> values) {
        id = UUID.randomUUID().toString();
        StartGameCommand startGameCommand = new StartGameCmd()
                .setId(id)
                .setFirstPlayer(new Player().setName(values.get("firstPlayerName")))
                .setSecondPlayer(new Player().setName(values.get("secondPlayerName")));
        tennisGameService.send(startGameCommand);
    }

    @When("The following point are won")
    public void theFollowingPointAreWon(List<String> pointWinners) {
        pointWinners.forEach(pointWinner -> tennisGameService.send(
                new WinPointCmd().setGameId(id).setPointWinner(
                        new Player().setName(pointWinner))));
    }

    @Then("Player {string} should win the game")
    public void playerPlayerShouldWonTheGame(String player) {
        GameEntity gameEntity = getGameEntity();
        assertEquals(gameEntity.getGameWinner(), player);
    }

    @Then("Player {string} should have advantage")
    public void playerPlayerShouldHaveAdvantage(String player) {
        GameEntity gameEntity = getGameEntity();
        assertEquals(gameEntity.getSecondPlayerScore(), Score.ADVANTAGE);
    }


    @Then("deuce rule should be applied")
    public void deuceRuleShouldBeApplied() {
        GameEntity gameEntity = getGameEntity();
        assertEquals(gameEntity.getFirstPlayerScore(), Score.FOURTY);
        assertEquals(gameEntity.getSecondPlayerScore(), Score.FOURTY);
    }

    private GameEntity getGameEntity() {
        final Optional<GameEntity> game = tennisGameService.findGame(id);
        return game.orElseGet(GameEntity::new);
    }

}
