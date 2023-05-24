package BoardGame;

import BoardGame.Model.GameRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BoardGameScene3Controller {
    @FXML
    private ListView<String> matchListView;

    public void initialize() {
        loadGameRecords();
    }

    @FXML
    void handleExitGameButtonClick(ActionEvent event){
        System.exit(0);
    }
    private void loadGameRecords() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonData = new String(Files.readAllBytes(Paths.get("game_results.json")));

            List<GameRecord> gameRecords = mapper.readValue(jsonData, new TypeReference<List<GameRecord>>() {});

            Map<String, Integer> playerWinsMap = new HashMap<>();

            // Count the number of wins for each player
            for (GameRecord record : gameRecords) {
                String winnerName = record.getWinnerName();
                playerWinsMap.put(winnerName, playerWinsMap.getOrDefault(winnerName, 0) + 1);
            }

            // Sort the player wins in descending order
            List<Map.Entry<String, Integer>> sortedPlayerWins = new ArrayList<>(playerWinsMap.entrySet());
            sortedPlayerWins.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // Display the top 5 players in the ListView
            int count = 0;
            for (Map.Entry<String, Integer> entry : sortedPlayerWins) {
                String player = entry.getKey();
                String listItem = String.format("Player: %s, Wins: %d", player, entry.getValue());
                matchListView.getItems().add(listItem);
                count++;
                if (count == 5) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
