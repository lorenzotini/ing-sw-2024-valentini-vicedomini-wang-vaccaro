package it.polimi.ingsw.gc27;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        HBox buttonBox = new HBox(10);
        VBox scoreBox = new VBox(10);
        Button startButton = new Button("Start");
        Button exitButton = new Button("Exit");

        buttonBox.getChildren().addAll(startButton, exitButton);
        root.setCenter(buttonBox);
        root.setRight(scoreBox);

        startButton.setOnAction(event -> {
            System.out.println("Il gioco è stato avviato!");
        });

        exitButton.setOnAction(event -> {
            stage.close();
        });

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Game GUI");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
