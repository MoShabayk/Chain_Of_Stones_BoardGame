// GameResultsManager.java
package BoardGame.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles the management of game records.
 * It allows saving and loading game results, as well as retrieving player wins.
 */
public class GameRecordsManager {
    private static List<GameRecord> gameRecordList;
    private static final String FILE_NAME = "game_results.json";

    /**
     * Constructs a new instance of the GameRecordsManager class.
     * Loads the game results from the file.
     */
    public GameRecordsManager() {
        gameRecordList = loadGameResults();
    }

    /**
     * Saves a game result to the list of game records and writes them to the file.
     * @param gameRecord the game record to save
     */
    public static void saveGameResult(GameRecord gameRecord) {
        gameRecordList.add(gameRecord);
        writeGameResults();
    }

    /**
     * Retrieves the player wins as a map of player names to the number of wins.
     * @return the map of player names to the number of wins
     */
    public Map<String, Long> getPlayerWins() {
        return gameRecordList.stream()
                .collect(Collectors.groupingBy(GameRecord::getWinnerName, Collectors.counting()));
    }

    /**
     * Loads the game results from the file.
     * @return the list of game records
     */
    private List<GameRecord> loadGameResults() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try {
                return mapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }


    /**
     * Writes the game results to the file.
     */
    private static void writeGameResults() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_NAME);

        try {
            mapper.writeValue(file, gameRecordList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}