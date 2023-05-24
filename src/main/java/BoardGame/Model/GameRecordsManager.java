// GameResultsManager.java
package BoardGame.Model;

import BoardGame.Model.GameRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameRecordsManager {
    private static List<GameRecord> gameRecordList;
    private static final String FILE_NAME = "game_results.json";

    public GameRecordsManager() {
        gameRecordList = loadGameResults();
    }

    public static void saveGameResult(GameRecord gameRecord) {
        gameRecordList.add(gameRecord);
        writeGameResults();
    }

    public Map<String, Long> getPlayerWins() {
        return gameRecordList.stream()
                .collect(Collectors.groupingBy(GameRecord::getWinnerName, Collectors.counting()));
    }

    private List<GameRecord> loadGameResults() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try {
                return mapper.readValue(file, new TypeReference<List<GameRecord>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

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