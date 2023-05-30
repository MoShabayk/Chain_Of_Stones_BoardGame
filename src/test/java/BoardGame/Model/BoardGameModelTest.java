package BoardGame.Model;

import javafx.beans.property.ReadOnlyObjectProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {
     private BoardGameModel model;

    @BeforeEach
    void setUp() {
        model = new BoardGameModel();
    }

    @Test
    void isEmpty() {
        assertTrue(model.isEmpty(new Position(1, 1)), "Expected position (1,1) to be empty.");
        assertFalse(model.isEmpty(new Position(0, 0)), "Expected position (0,0) to not be empty.");
    }

    @Test
    void squareProperty() {
        ReadOnlyObjectProperty<Square> square1 = model.squareProperty(0, 0);
        ReadOnlyObjectProperty<Square> square2 = model.squareProperty(4, 0);

        assertNotNull(square1, "Expected property to be non-null.");
        assertNotNull(square2, "Expected property to be non-null.");
        assertEquals(Square.BLUE, square1.get(), "Expected position (0,0) to be Blue.");
        assertEquals(Square.RED, square2.get(), "Expected position (4,0) to be Red.");

    }

    @Test
    void getSquare() {
        assertEquals(Square.BLUE, model.getSquare(new Position(0, 0)), "Expected position (0,0) to be Red.");
        assertEquals(Square.RED, model.getSquare(new Position(0, 1)), "Expected position (0,1) to be Blue.");
        assertEquals(Square.NONE, model.getSquare(new Position(1, 1)), "Expected position (1,1) to be None.");
    }

    @Test
    void canMove() {
        assertFalse(model.canMove(new Position(0, 0), new Position(1, 0)), "Expected Red piece at (0,0) to not be able to move to (1,0).");
        model.move(new Position(0, 2), new Position(1, 2));
        assertTrue(model.canMove(new Position(1, 2), new Position(2, 2)), "Expected Red piece at (1,2) to be able to move to (2,2).");
        assertFalse(model.canMove(new Position(2, 2), new Position(1, 2)), "Expected Blue piece at (2,2) to not be able to move to (1,2).");
    }

    @Test
    void testToString() {
        String expected = "2 1 2 1 \n0 0 0 0 \n0 0 0 0 \n0 0 0 0 \n1 2 1 2 \n";
        assertEquals(expected, model.toString(), "Expected and actual string representations of BoardGameModel do not match.");
    }

    @Test
    void getWinner() {
        String player1 = "Player 1";
        String player2 = "Player 2";
        assertNull(model.getWinner(player1, player2), "Expected no winner to be returned.");
        model.move(new Position(0, 1), new Position(1, 1));
        model.move(new Position(1, 1), new Position(1, 2));
        model.move(new Position(4, 0), new Position(3, 0));
        model.move(new Position(4, 2), new Position(3, 2));
        model.move(new Position(3, 2), new Position(3, 1));
        model.move(new Position(3, 1), new Position(2, 1));
        assertEquals(player1, model.getWinner(player1, player2), "Expected Player 1 to be the winner.");
    }

    @Test
    void checkWin() {
        assertFalse(model.checkWin(), "Expected no winner.");
        model.move(new Position(0, 1), new Position(1, 1));
        model.move(new Position(1, 1), new Position(1, 2));
        model.move(new Position(4, 0), new Position(3, 0));
        model.move(new Position(4, 2), new Position(3, 2));
        model.move(new Position(3, 2), new Position(3, 1));
        model.move(new Position(3, 1), new Position(2, 1));
        assertTrue(model.checkWin(), "Expected a winner.");
    }

    @Test
    void check_quadruplets() {
        assertFalse(model.check_quadruplets(Square.RED), "Expected Red not to have a winning quadruplets.");
        assertFalse(model.check_quadruplets(Square.BLUE), "Expected Blue not to have a winning quadruplets.");
        model.move(new Position(0, 1), new Position(1, 1));
        model.move(new Position(1, 1), new Position(1, 2));
        model.move(new Position(4, 0), new Position(3, 0));
        model.move(new Position(4, 2), new Position(3, 2));
        model.move(new Position(3, 2), new Position(3, 1));
        model.move(new Position(3, 1), new Position(2, 1));
        assertTrue(model.check_quadruplets(Square.RED), "Expected Red to have a winning quadruplets.");
        assertFalse(model.check_quadruplets(Square.BLUE), "Expected Blue not to have a winning quadruplets.");

        model.initializeBoard();
        model.move(new Position(0, 0), new Position(1, 0));
        model.move(new Position(4, 1), new Position(2, 1));
        model.move(new Position(0, 2), new Position(3, 2));
        assertTrue(model.check_quadruplets(Square.BLUE), "Expected Blue to have a winning quadruplets.");
        assertFalse(model.check_quadruplets(Square.RED), "Expected Red not to have a winning quadruplets.");
    }
}