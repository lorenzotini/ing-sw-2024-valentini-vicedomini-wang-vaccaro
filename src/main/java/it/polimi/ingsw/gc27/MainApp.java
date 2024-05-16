package it.polimi.ingsw.gc27;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/StarterScene.fxml")); //root node
        Scene scene=new Scene(fxmlLoader.load(),500,500);
        stage.setFullScreen(true);
        stage.setTitle("Codex Naturalis");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}


