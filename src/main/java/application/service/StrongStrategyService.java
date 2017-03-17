package application.service;

import application.domain.Board;
import application.domain.Move;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StrongStrategyService {

    static PlayService playService;

    @Autowired
    public StrongStrategyService (PlayService playService) {
        this.playService  = playService;
    }

    public Move getStrongMove(Board board, char mark) {
        Move strongMove = new Move();

        if (checkPossibleWin(board, mark) != null)

            return null;

        // TODO: 15.3.2017. Update with valid move
        return null;
    }

    @Nullable
    private Move checkPossibleWin(Board board, char mark) {

        int winRow = checkRowsForWin(board, mark);

        if (winRow != 0) {
            return new Move(mark, winRow, findWinningPointInRow(board, winRow));
        }
        return null;
    }

    private int checkRowsForWin(Board board, char mark) {
        for (int row = 1; row <= 3; row++) {
            int sum = 0;
            for (int column = 1; column <= 3; column++) {
                sum = sum + board.getPositionValue(row, column, mark);
            }
            if (sum == 2) {
                return row;
            }
        }

        return 0;
    }

    private int findWinningPointInRow(Board board, int winRow) {
        for (int column = 1; column <= 3; column++) {
            if (board.getPositionValue(winRow, column, 'X') == 0)
                return column;
        }

        return 0;
    }
}
