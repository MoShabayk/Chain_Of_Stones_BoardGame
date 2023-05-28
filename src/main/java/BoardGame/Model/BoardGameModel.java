package BoardGame.Model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.tinylog.Logger;

public class BoardGameModel {

    public static final int BOARD_WIDTH = 5;
    public static final int BOARD_LENGTH = 4;
    private final ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_WIDTH][BOARD_LENGTH];
    private  int currentPlayer = 1;
    public int player1turns = 0;
    public int player2turns = 0;

    /**
     * Initializes a new instance of the BoardGameModel class.
     */
    public BoardGameModel() {
        initializeBoard();
    }

    /**
     * Initializes the game board with the initial configuration.
     */
    public void initializeBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_LENGTH; j++) {
                Square newValue;
                if (i == 0) {
                    newValue = (j % 2 == 0) ? Square.BLUE : Square.RED;
                } else if (i == BOARD_WIDTH - 1) {
                    newValue = (j % 2 == 0) ? Square.RED : Square.BLUE;
                } else {
                    newValue = Square.NONE;
                }

                if (board[i][j] == null) {
                    board[i][j] = new ReadOnlyObjectWrapper<>(newValue);
                } else {
                    board[i][j].set(newValue);
                }
            }
        }
        // set the current player to 1
        currentPlayer = 1;
        // reset the number of turns
        player1turns = 0;
        player2turns = 0;
    }

    /**
     * Sets the square at the specified position to the given square value.
     * @param p      the position of the square
     * @param square the square value to set
     */
    private void setSquare(Position p, Square square) {
        board[p.row()][p.col()].set(square);
    }


    /**
     * Checks if the square at the specified position is empty.
     * @param p the position to check
     * @return true if the square is empty, false otherwise
     */
    public boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }


    /**
     * Returns the read-only object property of the square at the specified coordinates on the game board.
     * @param i the row index of the square
     * @param j the column index of the square
     * @return the read-only object property of the square at the specified coordinates
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * Gets the square value at the given position on the game board.
     * @param p the position of the square
     * @return the square value at the specified position
     */
    public Square getSquare(Position p) {

        return board[p.row()][p.col()].get();
    }

    /**
     * Checks if a move is valid from the given starting position to the destination position.
     * @param from the starting position
     * @param to   the destination position
     * @return true if the move is valid, false otherwise
     */
    public boolean canMove(Position from, Position to){
        var fromPiece = getSquare(from);
        var toPiece = getSquare(to);
        if (fromPiece == Square.NONE || toPiece != Square.NONE) {
            // illegal move, either fromSquare is empty or toSquare is occupied
            Logger.info("Illegal move");
            return false;
        }
        // check if the move is vertical or horizontal and make sure it is only one square away
        if ((Math.abs(from.row() - to.row()) == 1 && from.col() == to.col()) || (Math.abs(from.col() - to.col()) == 1 && from.row() == to.row())) {
            // Only allow the move if it's the current player's turn
            return (currentPlayer == 1 && fromPiece == Square.RED) || (currentPlayer == 2 && fromPiece == Square.BLUE);
        }
        return false;
    }

    /**
     * Moves a piece from one position to another on the game board.
     * @param from the starting position of the piece
     * @param to   the destination position for the piece
     */
    public void move(Position from, Position to) {
        // Move the piece
        setSquare(to, getSquare(from));
        setSquare(from, Square.NONE);
        // Increment the turn counter
        if (currentPlayer == 1) {
            player1turns++;
        } else {
            player2turns++;
        }
        // Change the current player
        currentPlayer = currentPlayer == 1 ? 2 : 1;
    }

    /**
     * Returns a string representation of the game board.
     * @return the string representation of the board
     */
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


    /**
     * Determines the name of the winner of the game based on the current board state.
     * @param labelPlayer1 the label for player 1
     * @param labelPlayer2 the label for player 2
     * @return the label of the winning player, or null if there is no winner yet
     */
    public String getWinner(String labelPlayer1, String labelPlayer2) {
        if (check_quadruplets(Square.BLUE)) {
            return labelPlayer2;
        }
        if (check_quadruplets(Square.RED)) {
            return labelPlayer1;
        }
        return null;
    }


    /**
     * Checks if there is a winning condition is met on the game board.
     * @return true if there is a winner, false otherwise
     */
    public boolean checkWin() {
        if (check_quadruplets(Square.BLUE)) {
            Logger.info("Blue wins!");
            Logger.info("Player 2 (Blue) won the game.");
            return true;
        }
        if (check_quadruplets(Square.RED)) {
            Logger.info("Red wins!");
            Logger.info("Player 1 (Red) won the game.");
            return true;
        }
        return false;
    }


    /**
     * Checks if there are quadruplets of the specified player on the game board, indicating a win condition.
     * @param player the player to check for quadruplets
     * @return true if there are quadruplets of the player, false otherwise
     */
    public boolean check_quadruplets(Square player) {
        // Check rows
        for (int row = 0; row < BOARD_WIDTH; row++) {
            boolean win = true;
            for (int col = 0; col < BOARD_LENGTH; col++) {

                if (getSquare(new Position(row, col)) != player) {
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
                if (getSquare(new Position(row, col)) != player) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check major diagonals (top-left to bottom-right)
        for (int row = 0; row < BOARD_WIDTH - 3; row++) {
            for (int col = 0; col < BOARD_LENGTH - 3; col++) {
                if (getSquare(new Position(row, col)) == player
                        && getSquare(new Position(row +1, col + 1)) == player
                        && getSquare(new Position(row + 2, col + 2)) == player
                        && getSquare(new Position(row + 3, col + 3)) == player) {
                    return true;
                }
            }
        }

        // Check minor diagonals (bottom-left to top-right)
        for (int row = 3; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_LENGTH - 3; col++) {
                if (getSquare(new Position(row, col)) == player
                        && getSquare(new Position(row - 1, col + 1)) == player
                        && getSquare(new Position(row - 2, col + 2)) == player
                        && getSquare(new Position(row - 3, col + 3)) == player) {
                    return true;
                }
            }
        }

        return false;
    }
}

