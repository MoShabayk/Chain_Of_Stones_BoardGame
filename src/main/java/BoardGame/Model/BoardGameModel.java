package BoardGame.Model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

public class BoardGameModel {

    public static final int BOARD_WIDTH = 5;
    public static final int BOARD_LENGTH = 4;
    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_WIDTH][BOARD_LENGTH];
    private  int currentPlayer = 1;
    public BoardGameModel() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                if (i == 0) {
                    if (j % 2 == 0) {
                        board[i][j] = new ReadOnlyObjectWrapper<>(Square.RED);
                    } else {
                        board[i][j] = new ReadOnlyObjectWrapper<>(Square.BLUE);
                    }
                } else {
                    board[i][j] = new ReadOnlyObjectWrapper<>(Square.NONE);
                }
                if (i == BOARD_WIDTH - 1) {
                    if (j % 2 == 0) {
                        board[i][j] = new ReadOnlyObjectWrapper<>(Square.BLUE);
                    } else {
                        board[i][j] = new ReadOnlyObjectWrapper<>(Square.RED);
                    }
                }
            }
        }


    }


    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public Square getSquare(int i, int j) {
        return board[i][j].get();
    }
    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol){
        var fromSquare = board[fromRow][fromCol];
        var toSquare = board[toRow][toCol];
        var fromPiece = fromSquare.get();
        var toPiece = toSquare.get();
        if (fromPiece == Square.NONE || toPiece != Square.NONE) {
            // illegal move, either fromSquare is empty or toSquare is occupied
            Logger.info("Illegal move");
            return false;
        }
        // check if the move is vertical or horizontal and make sure it is only one square away
        if ((Math.abs(fromRow - toRow) == 1 && fromCol == toCol) || (Math.abs(fromCol - toCol) == 1 && fromRow == toRow)) {
            // Only allow the move if it's the current player's turn
            if ((currentPlayer == 1 && fromPiece == Square.RED) || (currentPlayer == 2 && fromPiece == Square.BLUE)) {
                return true;
            }
        }

        return false;
    }

    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        var fromSquare = board[fromRow][fromCol];
        var toSquare = board[toRow][toCol];
        var fromPiece = fromSquare.get();
        var toPiece = toSquare.get();

        toSquare.set(fromPiece);
        fromSquare.set(Square.NONE);
        // Change the current player
        currentPlayer = currentPlayer == 1 ? 2 : 1;
    }

    // Get the current player's turn
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var i = 0; i < BOARD_WIDTH; i++) {
            for (var j = 0; j < BOARD_LENGTH; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        var model = new BoardGameModel();
        Logger.info(model);
    }

    public boolean check_win(Square player) {
        // Check rows
        for (int row = 0; row < BOARD_WIDTH; row++) {
            boolean win = true;
            for (int col = 0; col < BOARD_LENGTH; col++) {
                if (getSquare(row, col) != player) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check columns
        for (int col = 0; col < BOARD_LENGTH; col++) {
            boolean win = true;
            for (int row = 0; row < BOARD_WIDTH; row++) {
                if (getSquare(row, col) != player) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check horizontal diagonals
        for (int row = 0; row < BOARD_WIDTH - 3; row++) {
            for (int col = 0; col < BOARD_LENGTH; col++) {
                if (getSquare(row, col) == player
                        && getSquare(row + 1, col) == player
                        && getSquare(row + 2, col) == player
                        && getSquare(row + 3, col) == player) {
                    return true;
                }
            }
        }

        // Check vertical diagonals
        for (int col = 0; col < BOARD_LENGTH - 3; col++) {
            for (int row = 0; row < BOARD_WIDTH; row++) {
                if (getSquare(row, col) == player
                        && getSquare(row, col + 1) == player
                        && getSquare(row, col + 2) == player
                        && getSquare(row, col + 3) == player) {
                    return true;
                }
            }
        }

        return false;
    }

    public void resetBoard() {
        for (var i = 0; i < BOARD_WIDTH; i++) {
            for (var j = 0; j < BOARD_LENGTH; j++) {
                if (i == 0 || i == BOARD_WIDTH - 1) {
                    if (j % 2 == 0) {
                        board[i][j].set(Square.RED);
                    } else {
                        board[i][j].set(Square.BLUE);
                    }
                } else {
                    board[i][j].set(Square.NONE);
                }
            }
        }
    }
}
