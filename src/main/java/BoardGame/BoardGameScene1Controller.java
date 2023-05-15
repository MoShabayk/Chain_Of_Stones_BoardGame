package BoardGame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.tinylog.Logger;

public class BoardGameScene1Controller {

    @FXML
    private Button startGameButton;

    @FXML
    private TextField player1Name;

    @FXML
    private  TextField player2Name;

    public void storeText() {
        String text1 = player1Name.getText();
        String text2 = player2Name.getText();

    }

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

        //application.switchScene(application.scene2);
    }
}