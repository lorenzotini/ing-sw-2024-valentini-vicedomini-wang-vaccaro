package it.polimi.ingsw.gc27.View.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 * The MainApp class is the entry point for the JavaFX application.
 * It initializes the GUI and sets up the primary stage.
 */
public class MainApp extends Application {

    /**
     * The start method sets up the stage, scene, and GUI elements.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     * @throws Exception If an error occurs while loading the FXML file or initializing the GUI.
     */
    public void start(Stage stage) throws Exception {

        startMusic();

        Gui.getInstance().setStage(stage);
        Gui.getInstance().initializing();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/StarterScene.fxml")); //root node
        Scene scene = new Scene(fxmlLoader.load(), 1535, 780);
        stage.setTitle("Codex Naturalis");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();

    }

    private void startMusic(){
        String musicFile = "/music/fluffingDuck.mp3"; // Replace with your file path
        Media sound = new Media(getClass().getResource(musicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
    }

}
