package BoardGame.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameRecordTest {

    private GameRecord gameRecord;

    @BeforeEach
    void setUp() {
        gameRecord = new GameRecord("2023-05-24 10:00:00", "Mohamed", "Mahmoud", "Mohamed", 10, 9);
    }

    @Test
    void getStartTime() {
        assertEquals("2023-05-24 10:00:00", gameRecord.getStartTime());
    }

    @Test
    void getPlayer1Name() {
        assertEquals("Mohamed", gameRecord.getPlayer1Name());
    }

    @Test
    void getPlayer2Name() {
        assertEquals("Mahmoud", gameRecord.getPlayer2Name());
    }

    @Test
    void getWinnerName() {
        assertEquals("Mohamed", gameRecord.getWinnerName());
    }

    @Test
    void getPlayer1turns() {
        assertEquals(10, gameRecord.getPlayer1turns());
    }

    @Test
    void getPlayer2turns() {
        assertEquals(9, gameRecord.getPlayer2turns());
    }

    @Test
    void testToString() {
        String expected = "GameRecord{startTime=2023-05-24 10:00:00, player1Name='Mohamed', player2Name='Mahmoud', winnerName='Mohamed', player1turns=10, player2turns=9}";
        assertEquals(expected, gameRecord.toString());
    }
}