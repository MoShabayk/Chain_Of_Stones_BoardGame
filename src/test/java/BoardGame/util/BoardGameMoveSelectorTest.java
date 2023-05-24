package BoardGame.util;

import BoardGame.Model.BoardGameModel;
import BoardGame.Model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameMoveSelectorTest {

    private BoardGameMoveSelector selector;

    @BeforeEach
    void setUp() {
        BoardGameModel model = new BoardGameModel();
        selector = new BoardGameMoveSelector(model);
    }

    @Test
    void getPhase() {
        assertEquals(BoardGameMoveSelector.Phase.SELECT_FROM, selector.getPhase());
    }

    @Test
    void phaseProperty() {
        assertNotNull(selector.phaseProperty());
        assertEquals(BoardGameMoveSelector.Phase.SELECT_FROM, selector.phaseProperty().get());
    }

    @Test
    void isReadyToMove() {
        assertFalse(selector.isReadyToMove());
        selector.select(new Position(0, 1));
        assertFalse(selector.isReadyToMove());
        selector.select(new Position(1, 1));
        assertTrue(selector.isReadyToMove());
    }

    @Test
    void getFrom() {
        selector.select(new Position(0, 1));
        assertEquals(new Position(0, 1), selector.getFrom());
        assertThrows(IllegalStateException.class, selector::getFrom);
    }

    @Test
    void getTo() {
        selector.select(new Position(0, 0));
        selector.select(new Position(1, 0));
        assertEquals(new Position(1, 0), selector.getTo());
        assertThrows(IllegalStateException.class, selector::getTo);
    }
}