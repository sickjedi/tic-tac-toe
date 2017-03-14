package application.service;

import application.domain.Game;
import application.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PlayerService {
    private static final String COMPUTER = "computer";

    public List<Player> definePlayers(String player1, String player2) {
        List<Player> players = new ArrayList<>();

        // Two computers
        if (COMPUTER.equals(player1) && COMPUTER.equals(player2)) {
            log.info("Two computers!");
            return players;
        }

        // Two players
        if (!COMPUTER.equals(player1) && !COMPUTER.equals(player2)) {
            log.info("Two players");
            return players;
        }

        if (COMPUTER.equals(player1)) {
            players.add(new Player(COMPUTER));
            players.add(new Player(player2));
            return players;
        }

        players.add(new Player(player1));
        players.add(new Player(COMPUTER));

        return players;
    }

    public boolean playersAreValid(List<Player> players) {
        return players.size() == 2;
    }

    public void setPlayer1Win(Game game) {
        game.getPlayer1().updateWin();
        game.getPlayer2().updateLoss();
        game.setWinner(game.getPlayer1().getName());
    }

    public void setPlayer2Win(Game game) {
        game.getPlayer1().updateLoss();
        game.getPlayer2().updateWin();
        game.setWinner(game.getPlayer2().getName());
    }

    public char getMarkForHumanPlayer(Game game) {
        if ("computer".equals(game.getPlayer1().getName()))
            return 'O';
        else
            return 'X';
    }

    public char getMarkForComputerPlayer(Game game) {
        if ("computer".equals(game.getPlayer1().getName()))
            return 'X';
        else
            return 'O';
    }
}