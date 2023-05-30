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

        selector.select(new Position(0, 1));
        assertEquals(BoardGameMoveSelector.Phase.SELECT_TO, selector.getPhase());

        selector.select(new Position(1, 1));
        assertEquals(BoardGameMoveSelector.Phase.READY_TO_MOVE, selector.getPhase());
    }

    @Test
    void phaseProperty() {
        assertNotNull(selector.phaseProperty());
        assertEquals(BoardGameMoveSelector.Phase.SELECT_FROM, selector.phaseProperty().get());

        selector.select(new Position(0, 1));
        assertEquals(BoardGameMoveSelector.Phase.SELECT_TO, selector.phaseProperty().get());

        selector.select(new Position(1, 1));
        assertEquals(BoardGameMoveSelector.Phase.READY_TO_MOVE, selector.phaseProperty().get());
    }

    @Test
    void isReadyToMove() {
        assertFalse(selector.isReadyToMove());

        selector.select(new Position(0, 1));
        assertFalse(selector.isReadyToMove());

        selector.select(new Position(1, 1));
        assertTrue(selector.isReadyToMove());

        selector.reset();
        assertFalse(selector.isReadyToMove());
    }

    @Test
    void getFrom() {
        selector.select(new Position(0, 1));
        assertEquals(new Position(0, 1), selector.getFrom());

        selector.reset();
        assertThrows(IllegalStateException.class, selector::getFrom);
    }

    @Test
    void getTo() {
        selector.select(new Position(0, 1));
        selector.select(new Position(1, 1));
        assertEquals(new Position(1, 1), selector.getTo());

        selector.reset();
        assertThrows(IllegalStateException.class, selector::getTo);
    }
}