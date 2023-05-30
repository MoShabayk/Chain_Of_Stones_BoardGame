package BoardGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoardGameApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the first FXML file and create its controller
        FXMLLoader loader1 = new FXMLLoader(getClass().getClassLoader().getResource("uiScene1.fxml"));
        Parent root1 = loader1.load();
        // Show the first scene
        primaryStage.setScene(new Scene(root1));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}