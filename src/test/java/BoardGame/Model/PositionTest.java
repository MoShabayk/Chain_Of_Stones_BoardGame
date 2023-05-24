package BoardGame.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testToString() {
        Position position = new Position(2, 3);
        String expected = "(2,3)";
        String actual = position.toString();
        assertEquals(expected, actual, "Expected and actual values do not match.");
    }

    @Test
    void row() {
        Position position = new Position(2, 3);
        int expected = 2;
        int actual = position.row();
        assertEquals(expected, actual, "Expected and actual values do not match.");
    }

    @Test
    void col() {
        Position position = new Position(2, 3);
        int expected = 3;
        int actual = position.col();
        assertEquals(expected, actual, "Expected and actual values do not match.");
    }
}