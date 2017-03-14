package application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private String name;
    private double wins;
    private double losses;
    private double draws;

    public Player (String playerName) {
        this.name = playerName;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    public double getNumberOfGames(Player player) {
        return wins + losses + draws;
    }

    public void updateDraw() {
        draws = draws + 1;
    }

    public void updateWin() {
        wins = wins + 1;
    }

    public void updateLoss() {
        losses = losses + 1;
    }
}
