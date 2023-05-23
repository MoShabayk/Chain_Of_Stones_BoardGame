package BoardGame;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.tinylog.Logger;

public class BoardGameScene1Controller {
    @FXML
    private TextField player1Name;
    @FXML
    private  TextField player2Name;

    private BoardGameApplication application;

    public void setApplication(BoardGameApplication application) {
        this.application = application;
    }
    @FXML
    public void onStartGameButtonClick(javafx.event.ActionEvent event) {
        if(player1Name.getText().isEmpty() || player2Name.getText().isEmpty()) {
            Logger.error("Please enter both player names");
            return;
        }
        application.goToGame(player1Name.getText(), player2Name.getText());
    }
}