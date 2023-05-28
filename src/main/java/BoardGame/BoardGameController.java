package BoardGame;

import BoardGame.Model.*;
import BoardGame.util.BoardGameMoveSelector;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.tinylog.Logger;
import java.io.IOException;
import java.time.LocalDateTime;
public class BoardGameController {
    @FXML
    private GridPane board;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;
    private BoardGameModel Model = new BoardGameModel();
    private StackPane previouslySelectedSquare = null;
    private BoardGameMoveSelector selector = new BoardGameMoveSelector(Model);

    private GameRecordsManager gameRecordsManager = new GameRecordsManager();

    @FXML
    public void setPlayerNames(String s1, String s2){
        labelPlayer1.setText(s1);
        labelPlayer2.setText(s2);
    }
    @FXML
    public void initialize() {
       Logger.info(Model.toString());
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
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        Logger.info(new Position(row, col).toString());

        // If this is the first click (no tile is selected yet)
        if (selector.getPhase() == BoardGameMoveSelector.Phase.SELECT_FROM) {
            // If the selected tile is not empty, remember its position
            if (Model.getSquare(new Position(row, col)) != Square.NONE) {
                selector.select(new Position(row, col));

                // Add the "square-clicked" class to the newly selected square
                square.getStyleClass().add("square-clicked");
                previouslySelectedSquare = square;
            }
        }
        // If this is the second click (i.e., a tile has already been selected)
        else if (selector.getPhase() == BoardGameMoveSelector.Phase.SELECT_TO) {
            // If the selected tile is different from the current tile
            if (selector.getFrom().row() != row || selector.getFrom().col() != col) {
                selector.select(new Position(row, col));

                // If the move is valid, apply it to the model
                if (selector.isReadyToMove() && !selector.isInvalidSelection()) {
                    selector.makeMove();
                }
                // If the move is invalid, unselect the tile
                else {
                    selector.reset();
                }
            }
            // Unselect the tile
            if (previouslySelectedSquare != null) {
                previouslySelectedSquare.getStyleClass().remove("square-clicked");
            }
            Model.checkWin();
            if (Model.checkWin()) {
                board.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Winner");
                alert.setHeaderText(null);
                String winnerName = Model.getWinner(labelPlayer1.getText(), labelPlayer2.getText());
                alert.setContentText(String.format("%s wins!", winnerName));
                alert.showAndWait();
            }
        }
    }
    public void onRestartButtonClick(javafx.event.ActionEvent event) {
        board.setDisable(false);

        //Add the current game result to the list
        String startTime = LocalDateTime.now().toString();
        String player1Name = labelPlayer1.getText();
        String player2Name = labelPlayer2.getText();
        String winnerName = Model.getWinner(player1Name, player2Name);
        int player1turns = Model.player1turns;
        int player2turns = Model.player2turns;
        GameRecord gameRecord = new GameRecord(startTime, player1Name, player2Name, winnerName, player1turns, player2turns);

        GameRecordsManager.saveGameResult(gameRecord);
        Model.initializeBoard();
    }
    public void onGoToLeaderBoardClick(javafx.event.ActionEvent event) throws IOException {
        String startTime = LocalDateTime.now().toString();
        String player1Name = labelPlayer1.getText();
        String player2Name = labelPlayer2.getText();
        String winnerName = Model.getWinner(player1Name, player2Name);
        int player1turns = Model.player1turns;
        int player2turns = Model.player2turns;
        GameRecord gameRecord = new GameRecord(startTime, player1Name, player2Name, winnerName, player1turns, player2turns);

        gameRecordsManager.saveGameResult(gameRecord);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("uiScene3.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
