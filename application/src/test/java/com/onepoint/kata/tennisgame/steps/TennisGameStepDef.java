package com.onepoint.kata.tennisgame.steps;

import com.onepoint.kata.tennisgame.SpringTestConfig;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.StartSetCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.domain.Player;
import com.onepoint.kata.tennisgame.domain.GameScore;
import com.onepoint.kata.tennisgame.domain.TennisSet;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.entities.TennisSetEntity;
import com.onepoint.kata.tennisgame.services.TennisGameService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = SpringTestConfig.class)
public class TennisGameStepDef {

    @Autowired
    TennisGameService tennisGameService;
    String id;

    @Given("A tennis set started with the following information")
    public void aTennisSetStartedWithTheFollowingInformation(Map<String, String> setInfo) {

        id = UUID.randomUUID().toString();
        final StartSetCmd startSetCmd = new StartSetCmd().setId(id)
                .setFirstPlayer(new Player().setName(setInfo.get("firstPlayerName")))
                .setSecondPlayer(new Player().setName(setInfo.get("secondPlayerName")));
        tennisGameService.send(startSetCmd);
    }

    @And("A tennis game with id {string} started")
    public void aTennisGameWithIdGame_oneStarted(String gamed_id) {
        startGame(gamed_id);
    }

    private void startGame(String gamed_id) {
        final StartGameCmd startGameCmd = new StartGameCmd()
                .setId(gamed_id)
                .setTennisSetId(id);
        tennisGameService.send(startGameCmd);
    }

    @And("The following point are won in tie break")
    public void theFollowingPointAreWonInTieBreak(List<String> pointWinners) {
        pointWinners.forEach(pointWinner -> {
            waitFor(50);
            tennisGameService.send(
                    new WinPointCmd().setGameId("").setTennisSetId(id).setWinner(
                            new Player().setName(pointWinner)));
        });
    }
    @When("The following point are won for {string}")
    public void theFollowingPointAreWon(String gameId, List<String> pointWinners) {
        winPoint(gameId, pointWinners);
    }

    private void winPoint(String gameId, List<String> pointWinners) {
        pointWinners.forEach(pointWinner -> {
            waitFor(50);
            tennisGameService.send(
                    new WinPointCmd().setGameId(gameId).setTennisSetId(id).setWinner(
                            new Player().setName(pointWinner)));
        });
    }

    @Then("Player {string} should win the game {string}")
    public void playerPlayerShouldWonTheGame(String player, String gameId) {
        waitFor(50);
        GameEntity gameEntity = findGameById(id, gameId);
        assertEquals(player, gameEntity.getWinner());
    }

    @Then("Second player should have advantage for {string}")
    public void playerPlayerShouldHaveAdvantage(String gameId) {
        waitFor(50);
        GameEntity gameEntity = findGameById(id, gameId);
        assertEquals(GameScore.ADVANTAGE, gameEntity.getSecondPlayerGameScore());
    }


    @Then("deuce rule should be applied for {string}")
    public void deuceRuleShouldBeApplied(String gameId) {
        waitFor(50);
        GameEntity gameEntity = findGameById(id, gameId);
        assertEquals(GameScore.FOURTY, gameEntity.getFirstPlayerGameScore());
        assertEquals(GameScore.FOURTY, gameEntity.getSecondPlayerGameScore());
    }


    @Then("Player {string} should win the set")
    public void playerPlayerShouldWinTheSet(String player) {
        waitFor(50);
        TennisSetEntity tennisSetEntity = findTennisSetById(id);
        assertEquals(player, tennisSetEntity.getWinner());
    }

    @When("The following point are won in game for {int} times")
    public void theFollowingPointAreWonInGameForTimes(int times, List<String> pointWinners) {
        for (int i = 0; i < times; i++) {

            final String gameId = "game" + i + UUID.randomUUID();
            startGame(gameId);
            waitFor(50);
            winPoint(gameId, pointWinners);
        }
    }


    @Then("No one wins the set")
    public void noOneWinsTheSet() {
        waitFor(50);
        assertEquals(null, findTennisSetById(id).getWinner());
    }

    private void waitFor(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private GameEntity findGameById(String tennisSetId, String gameId) {
        final Optional<GameEntity> game =
                tennisGameService.findGame(tennisSetId, gameId);
        return game.orElseGet(GameEntity::new);
    }

    private TennisSetEntity findTennisSetById(String id) {
        final Optional<TennisSetEntity> tennisSet =
                tennisGameService.findTennisSet(id);
        return tennisSet.orElseGet(TennisSetEntity::new);
    }

}
