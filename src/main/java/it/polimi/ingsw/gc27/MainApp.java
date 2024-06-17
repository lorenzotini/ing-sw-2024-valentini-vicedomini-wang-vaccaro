package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public void start(Stage stage) throws Exception {
        Gui.getInstance().setStage(stage);
        Gui.getInstance().initializing();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/StarterScene.fxml")); //root node
        Scene scene = new Scene(fxmlLoader.load(), 1535, 780);
        //stage.setFullScreen(true);
        stage.setTitle("Codex Naturalis");
        stage.setScene(scene);
        stage.show();
    }

}
