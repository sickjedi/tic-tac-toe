package application.repository;

import application.domain.Game;
import application.domain.Player;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Repository
public class GameRepository {
    public static List<Game> games = new ArrayList<>();
    public static List<Player> players = new ArrayList<>();

    public void saveGame(Game game) {
        games.add(game);
    }

    public Game getGame(String id) {
        for (Game game : games) {
            if (game.getGameId().equals(id)) {
                return game;
            }
        }
        throw new NoGameException(id);
    }

    public void addPlayers(List<Player> newPlayers) {
        if (players.isEmpty()) {
            for (Player newPlayer : newPlayers) {
                players.add(newPlayer);
            }
        } else {
            for(Player newPlayer : newPlayers) {
                boolean playerExists = false;

                for (Player player : players) {
                    if (newPlayer.getName().equals(player.getName()))
                        playerExists = true;
                }
                if (!playerExists)
                    players.add(newPlayer);
            }
        }
    }

    private class NoGameException extends RuntimeException{
        NoGameException(String id) {
            super(id);
        }
    }
}
