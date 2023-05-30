package BoardGame;

import BoardGame.Model.GameRecordsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.*;

public class BoardGameScene3Controller {
    @FXML
    private ListView<String> matchListView;
    private GameRecordsManager gameRecordsManager = new GameRecordsManager();

    public void initialize() {
        loadGameRecords();
    }
    @FXML
    void handleExitGameButtonClick(ActionEvent event){
        System.exit(0);
    }
    private void loadGameRecords() {
        try {
            Map<String, Integer> playerWinsMap = gameRecordsManager.getPlayerWins();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
