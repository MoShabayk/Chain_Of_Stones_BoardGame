package BoardGame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class BoardGameScene1Controller {
    @FXML
    private TextField player1Name;
    @FXML
    private  TextField player2Name;
    @FXML
    public void onStartGameButtonClick(javafx.event.ActionEvent event) throws IOException {
        if(player1Name.getText().isEmpty() || player2Name.getText().isEmpty()) {
            Logger.error("Please enter both player names");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = loader.load();
        BoardGameController boardGameController = loader.getController();
        boardGameController.setPlayerNames(player1Name.getText(), player2Name.getText());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}