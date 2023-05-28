package BoardGame.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameRecordTest {

    private GameRecord gameRecord;

    @BeforeEach
    void setUp() {
        gameRecord = new GameRecord(10, 9, "2023-05-28T15:47:07.489834600", "Mohamed", "Mahmoud", "Mohamed");
    }

    @Test
    void getStartTime() {
        assertEquals("2023-05-28T15:47:07.489834600", gameRecord.getStartTime());
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
        String expected = "GameRecord(player1turns=10, player2turns=9, startTime=2023-05-28T15:47:07.489834600, player1Name=Mohamed, player2Name=Mahmoud, winnerName=Mohamed)";
        assertEquals(expected, gameRecord.toString());
    }
}