package application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class Board {

    static char[][] board;

    public Board() {
        this.board = new char[][]{{'e', 'e', 'e'}, {'e', 'e', 'e'}, {'e', 'e', 'e'}};
    }

    public char[][] getBoard() {
        return board;
    }

    public static boolean isMoveValid(Game game, int row, int column) {
        char position = getPosition(row , column );

        if (row < 1 || row > 3 || column < 1 || column > 3)
            return false;

        return position == 'e';
    }

    public static void playMove(Game game, Move move) {
        game.getBoard().setField(move.getRow() - 1, move.getColumn() - 1, move.getMark());
    }

    public static char getPosition(int row, int column) {
        return board[row - 1][column - 1];
    }

    private void setField(int row, int column, char playerSign) {
        board[row][column] = playerSign;
    }
}
