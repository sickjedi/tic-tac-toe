package application.service;

import application.domain.Game;
import application.domain.GameStatus;
import application.domain.GameStrategy;
import application.domain.Player;
import application.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService (GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game newGame(List<Player> players) {
        GameStrategy strategy = calculateStrategy(players);
        // TODO: 13.3.2017. Replace hardcoded WEAK strategy with strategy after implementation of STRONG strategy
        Game game = new Game(players.get(0), players.get(1), GameStrategy.WEAK);

        gameRepository.saveGame(game);
        gameRepository.addPlayers(players);

        // Play first move for computer if First player is computer
        if ("computer".equals(game.getPlayer1().getName())) {
            PlayService.playComputerMove(game);
        }

        return game;
    }

    public String getGameStatus(String id) throws JsonProcessingException {
        Game game;

        try {
            game = gameRepository.getGame(id);
        } catch (Exception e) {
            log.info("No game for id " + e.getMessage());
            return "No game for this ID";
        }

        return getGameStatusJson(game);
    }

    public String getGameIdJson(Game game) {
        return "{\"gameId\":\"" + game.getGameId() + "\"}";
    }

    public Game getGame(String id) {
        return gameRepository.getGame(id);
    }

    public boolean isGameFinished(Game game) {
        return GameStatus.FINISHED.equals(game.getStatus());
    }

    public void finishGame(Game game) {
        game.setStatus(GameStatus.FINISHED);
    }

    private GameStrategy calculateStrategy(List<Player> players) {
        for (Player player : players) {
            if (!"computer".equals(player.getName())) {
                double numberOfGames = player.getNumberOfGames(player);
                if (numberOfGames == 0)
                    return GameStrategy.getRandomStrategy();
                if (player.getWins()/numberOfGames < 0.3)
                    return GameStrategy.WEAK;
                if (player.getWins()/numberOfGames > 0.9)
                    return GameStrategy.STRONG;
                return GameStrategy.getRandomStrategy();
            }
        }
        return null;
    }

    private String getGameStatusJson(Game game) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(game);
    }
}