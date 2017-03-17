package application.domain;

import lombok.*;

@Data
@AllArgsConstructor
public class Move {
    private char mark;
    private int row;
    private int column;
    private boolean winningMove;

    public Move() {
        this.row = 0;
        this.column = 0;
        this.winningMove = false;
    }

    public Move(char mark, int row, int column) {
        this.mark = mark;
        this.row = row;
        this.column = column;
        this.winningMove = false;
    }
}
