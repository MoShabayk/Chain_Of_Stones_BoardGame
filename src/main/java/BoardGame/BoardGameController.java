package BoardGame;

import BoardGame.Model.BoardGameModel;
import BoardGame.Model.Square;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardGameController {

    @FXML
    private GridPane board;

    private BoardGameApplication application;

    private ScoreSystem scoreSystem;

    @FXML
    private Label player1ScoreLabel;
    @FXML
    private Label player2ScoreLabel;
    @FXML
    private ListView<String> matchLogListView;
    //private List<GameRecord> gameRecords;

    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;


    @FXML
    private AnchorPane anchorPane;
    private BoardGameModel Model = new BoardGameModel();
    private int[] selectedTile = null;

    private StackPane previouslySelectedSquare = null;

    public BoardGameController() {
        scoreSystem = new ScoreSystem();
    }
    public void setApplication(BoardGameApplication application) {
        this.application = application;
    }

    @FXML
    public void setPlayerNames(String s1, String s2){
        labelPlayer1.setText(s1);
        labelPlayer2.setText(s2);
    }

    @FXML
    public void initialize() {


       Logger.info(Model.toString());

        //gameRecords = new ArrayList<>();

        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }


    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);
/*
        piece.fillProperty().bind(Bindings.when(Model.squareProperty(i, j).isEqualTo(Square.NONE))
                .then(Color.TRANSPARENT)
                .otherwise(Bindings.when(Model.squareProperty(i, j).isEqualTo(Square.RED))
                        .then(Color.RED)
                        .otherwise(Color.BLUE))
        );
*/
        piece.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(Model.squareProperty(i, j));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (Model.squareProperty(i, j).get()) {
                            case NONE -> Color.TRANSPARENT;
                            case RED -> Color.RED;
                            case BLUE -> Color.BLUE;
                        };
                    }
                }
        );
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        if(checkWin())
        {
            Logger.info("Game Over");
            return;
        }
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        Logger.info("Click on square ("+row+","+col+")");

        // If this is the first click (no tile is selected yet)
        if (selectedTile == null) {
            // If the selected tile is not empty, remember its position
            if (Model.getSquare(row, col) != Square.NONE) {
                selectedTile = new int[]{row, col};

                // Add the "square-clicked" class to the newly selected square
                square.getStyleClass().add("square-clicked");

                previouslySelectedSquare = square;
            }
        }
        // If this is the second click (i.e., a tile has already been selected)
        else {
            // If the selected tile is different from the current tile
            if (selectedTile[0] != row || selectedTile[1] != col) {
                // If the current tile is empty, move the selected piece to it
                if (Model.getSquare(row, col) == Square.NONE) {
                    if (Model.canMove(selectedTile[0], selectedTile[1], row, col)){
                        Model.move(selectedTile[0], selectedTile[1], row, col);
                    }
                }
            }
            // Unselect the tile
            if (previouslySelectedSquare != null) {
                previouslySelectedSquare.getStyleClass().remove("square-clicked");
            }
            selectedTile = null;

        }
        checkWin();
    }




    public boolean checkWin() {
        if (Model.check_win(Square.BLUE)) {
            Logger.info("Blue wins!");
            scoreSystem.addPlayer2Score(); // Blue is Player 2
            scoreSystem.addMatchLog("Player 2 (Blue) won the game.");
            updateScoreAndMatchLog();
            board.setDisable(true);
            return true;
        }
        if (Model.check_win(Square.RED)) {
            Logger.info("Red wins!");
            scoreSystem.addPlayer1Score(); // Red is Player 1
            scoreSystem.addMatchLog("Player 1 (Red) won the game.");
            updateScoreAndMatchLog();
            board.setDisable(true);
            return true;
        }
        return false;
    }

    public String getWinner() {
        if (Model.check_win(Square.BLUE)) {
            return labelPlayer2.getText();
        }
        if (Model.check_win(Square.RED)) {
            return labelPlayer1.getText();
        }
        return null;
    }
    public void onRestartButtonClick(javafx.event.ActionEvent event) {
        board.setDisable(false);
        //addGameRecord(getWinner()); // Replace with the actual winner name
        saveGameResult();
        Model.resetBoard();
    }

    public void onExitButtonClick(javafx.event.ActionEvent event){
        saveGameResult();
        application.goToLeaderBoard();

    }

    private void updateScoreAndMatchLog() {
        player1ScoreLabel.setText("Player 1: " + scoreSystem.getPlayer1Score());
        player2ScoreLabel.setText("Player 2: " + scoreSystem.getPlayer2Score());
        matchLogListView.getItems().setAll(scoreSystem.getMatchLog());
    }

//    private void addGameRecord(String winnerName) {
//        String startTime = LocalDateTime.now().toString();
//        String player1Name = labelPlayer1.getText();
//        String player2Name = labelPlayer2.getText();
//        GameRecord gameRecord = new GameRecord(startTime, player1Name, player2Name, winnerName);
//        gameRecords.add(gameRecord);
//    }

    private void saveGameResult() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("game_results.json");

        // Read existing game results from the file, if any
        List<GameRecord> gameResultsList;
        if (file.exists()) {
            try {
                gameResultsList = mapper.readValue(file, new TypeReference<List<GameRecord>>() {});
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            gameResultsList = new ArrayList<>();
        }

        // Add the current game result to the list
        String startTime = LocalDateTime.now().toString();
        String player1Name = labelPlayer1.getText();
        String player2Name = labelPlayer2.getText();
        String winnerName = getWinner();
        GameRecord gameRecord = new GameRecord(startTime, player1Name, player2Name, winnerName);
        gameResultsList.add(gameRecord);

        // Write the updated list back to the file
        try {
            mapper.writeValue(file, gameResultsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
