package it.polimi.ingsw.gc27.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

//first scene of the game, displayes logo and "Play", "Exit" buttons
public class StarterSceneController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToChooseGameScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGameScene.fxml"));
        Parent sceneRoot = loader.load();
        Scene scene = new Scene(sceneRoot,1200,800);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }




}

