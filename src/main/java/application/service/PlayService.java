package application.service;

import application.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayService {

    static GameService gameService;
    static PlayerService playerService;
    static StrongStrategyService strongStrategyService;

    @Autowired
    public PlayService (GameService gameService, PlayerService playerService, StrongStrategyService strongStrategyService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.strongStrategyService = strongStrategyService;
    }

    public static boolean isMoveValid(Game game, int row, int column) {
        return Board.isMoveValid(game, row, column);
    }

    public static void playMove(Game game, Move move) {
        Board.playMove(game, move);

        checkWinCondition(game);

        checkDrawCondition(game);
    }

    public static void playComputerMove(Game game) {
        if (GameStrategy.WEAK.equals(game.getStrategy())){
            playWeakMove(game);
        }
        playStrongMove(game);
    }

    private static void checkWinCondition(Game game) {
        Board board = game.getBoard();

        if (rowWin(board) == 'X') {
            gameService.finishGame(game);
            playerService.setPlayer1Win(game);
        } else if (rowWin(board) == 'O') {
            gameService.finishGame(game);
            playerService.setPlayer2Win(game);
        }

        if (columnWin(board) == 'X') {
            gameService.finishGame(game);
            playerService.setPlayer1Win(game);
        } else if (columnWin(board) == 'O') {
            gameService.finishGame(game);
            playerService.setPlayer2Win(game);
        }

        if (diagonalWin(board) == 'X') {
            gameService.finishGame(game);
            playerService.setPlayer1Win(game);
        } else if (diagonalWin(board) == 'O') {
            gameService.finishGame(game);
            playerService.setPlayer2Win(game);
        }

        if (altDiagonalWin(board) == 'X') {
            gameService.finishGame(game);
            playerService.setPlayer1Win(game);
        } else if (altDiagonalWin(board) == 'O') {
            gameService.finishGame(game);
            playerService.setPlayer2Win(game);
        }
    }

    private static void checkDrawCondition(Game game) {
        Board board = game.getBoard();

        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 3; column++) {
                if (board.getPosition(row, column) == 'e')
                    return;
            }
        }
        game.setStatus(GameStatus.FINISHED);

        game.getPlayer1().updateDraw();
        game.getPlayer2().updateDraw();
    }

    private static void playWeakMove(Game game) {
        char mark = playerService.getMarkForComputerPlayer(game);
        Move move;
        for (int row = 1; row <= 3; row++) {
            for (int column = 1; column <= 3; column++) {
                if (isMoveValid(game, row, column)) {
                    move = new Move(mark, row, column);
                    playMove(game, move);
                    return;
                }
            }
        }
    }

    private static void playStrongMove(Game game) {

    }

    private static char rowWin(Board board) {
        for (int row = 1; row <= 3; row++) {
            if (board.getPosition(row, 1) != 'e' &&
                    board.getPosition(row, 1) == board.getPosition(row, 2) &&
                    board.getPosition(row, 1) == board.getPosition(row, 3))
            return board.getPosition(row, 1);
        }
        return 'e';
    }

    private static char columnWin(Board board) {
        for (int column = 1; column <= 3; column++){
            if (board.getPosition(1, column) != 'e' &&
                    board.getPosition(1, column) == board.getPosition(2, column) &&
                    board.getPosition(1, column) == board.getPosition(3, column))
                return board.getPosition(1, column);
        }
        return 'e';
    }

    private static char diagonalWin(Board board) {
        if (board.getPosition(1, 1) != 'e' &&
                board.getPosition(1, 1) == board.getPosition(2, 2) &&
                board.getPosition(1, 1) == board.getPosition(3, 3))
            return board.getPosition(1, 1);
        return 'e';
    }

    private static char altDiagonalWin(Board board) {
        if (board.getPosition(1, 3) != 'e' &&
                board.getPosition(1, 3) == board.getPosition(2, 2) &&
                board.getPosition(1, 3) == board.getPosition(3, 1))
            return board.getPosition(1, 3);
        return 'e';
    }
}
