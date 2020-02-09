package com.onepoint.kata.tennisgame.rest;

import com.onepoint.kata.tennisgame.command.WinPointCommand;
import com.onepoint.kata.tennisgame.commands.StartGameCmd;
import com.onepoint.kata.tennisgame.commands.WinPointCmd;
import com.onepoint.kata.tennisgame.entities.GameEntity;
import com.onepoint.kata.tennisgame.services.TennisGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/tennis-game")
public class TennisGameController {

    private final TennisGameService tennisGameService;

    @RequestMapping(value = "/new-game", method = RequestMethod.POST)
    public ResponseEntity<Void> startGame(@RequestBody StartGameCmd startGameCmd) {
        tennisGameService.send(startGameCmd);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/win-point", method = RequestMethod.POST)
    public ResponseEntity<Void> winPoint(@RequestBody WinPointCmd winPointCmd) {
        tennisGameService.send(winPointCmd);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<GameEntity> winPoint(@RequestParam(name = "id") String id) {
        final Optional<GameEntity> game = tennisGameService.findGame(id);
        return ResponseEntity.ok(game.isPresent() ? game.get() : new GameEntity());
    }
}
