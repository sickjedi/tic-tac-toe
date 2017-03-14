package application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Game {
    private UUID gameId;
    private Player player1;
    private Player player2;
    private GameStatus status;
    private Board board;
    private String winner;
    private GameStrategy strategy;

    public Game(Player player1, Player player2, GameStrategy strategy) {
        this.gameId = UUID.randomUUID();
        this.player1 = player1;
        this.player2 = player2;
        this.status = GameStatus.IN_PROGRESS;
        this.winner = "None";
        this.strategy = strategy;
        this.board = new Board();
    }

    public String getGameId() {
        return this.gameId.toString();
    }
}
