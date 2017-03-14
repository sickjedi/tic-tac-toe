package application.controller;

import application.domain.Game;
import application.domain.Player;
import application.service.GameService;
import application.service.PlayService;
import application.service.PlayerService;
import application.service.StatisticsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController {

    private GameService gameService;
    private PlayerService playerService;
    private StatisticsService statisticsService;
    private PlayService playService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService,
                          StatisticsService statisticsService, PlayService playService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.statisticsService = statisticsService;
        this.playService = playService;
    }

    @RequestMapping (method = RequestMethod.GET, value = "new")
    public String newGame(@RequestParam(value = "first", defaultValue = "computer") String player1,
                          @RequestParam(value = "second", defaultValue = "computer") String player2) {

        List<Player> players = playerService.definePlayers(player1, player2);

        if (!playerService.playersAreValid(players)) {
            return "Players not valid!";
        }

        Game game = gameService.newGame(players);

        return gameService.getGameIdJson(game);
    }

    @RequestMapping (method = RequestMethod.GET, value = "status")
    public String gameStatus (@RequestParam(value = "gameId") String id) {
        String gameStatus;
        try {
            gameStatus = gameService.getGameStatus(id);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            gameStatus = "Error getting game status";
        }
        return gameStatus;
    }

    @RequestMapping (method = RequestMethod.GET, value = "stats")
    public String gameStats () {
        return statisticsService.getStats();
    }

    @RequestMapping (method = RequestMethod.GET, value = "play")
    public ResponseEntity<String> playGame (@RequestParam (value = "gameId") String id,
                                             @RequestParam (value = "row") int row,
                                             @RequestParam (value = "column") int column) {

        Game game = gameService.getGame(id);

        if (gameService.isGameFinished(game))
            return new ResponseEntity<>("Game finished!", HttpStatus.PRECONDITION_FAILED);

        if (!playService.isMoveValid(game, row, column))
            return new ResponseEntity<>("Move not valid!", HttpStatus.PRECONDITION_FAILED);

        char mark = playerService.getMarkForHumanPlayer(game);

        playService.playMove(game, row, column, mark);
        if (gameService.isGameFinished(game))
            return new ResponseEntity<>("Game over!", HttpStatus.OK);
        else {
            playService.playComputerMove(game);
            if (gameService.isGameFinished(game))
                return new ResponseEntity<>("Game over!", HttpStatus.OK);
        }
            return new ResponseEntity<>("Good move!", HttpStatus.OK);
    }
}
