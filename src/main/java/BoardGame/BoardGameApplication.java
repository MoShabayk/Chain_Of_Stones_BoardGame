package BoardGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardGameApplication extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        try {
            // Load the first FXML file and create its controller
            FXMLLoader loader1 = new FXMLLoader(getClass().getClassLoader().getResource("uiScene1.fxml"));
            Parent root1 = loader1.load();
            BoardGameScene1Controller controller1 = loader1.getController();
            controller1.setApplication(this);
            // Show the first scene
            primaryStage.setScene(new Scene(root1));
            primaryStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void goToGame(String player1Name, String player2Name)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui.fxml"));
        try {
            Parent root = loader.load();
            BoardGameController boardGameController = loader.getController();
            boardGameController.setPlayerNames(player1Name, player2Name);
            boardGameController.setApplication(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void goToLeaderBoard()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("uiScene3.fxml"));
        try {
            Parent root = loader.load();
            BoardGameScene3Controller boardGameScene3Controller = loader.getController();
            boardGameScene3Controller.setApplication(this);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}