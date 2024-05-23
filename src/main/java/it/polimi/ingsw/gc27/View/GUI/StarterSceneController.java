package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

//first scene of the game, displayes logo and "Play", "Exit" buttons
public class StarterSceneController implements GeneralSceneController { //per chiamare start
    private Gui gui;
    @FXML
    public Button startGameButton; //servirà thread che ascolta i bottoni e thread che manda le chiamate al controller

    //creare costruttore che viene chiamato da rmi e server
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    public void switchToChooseGameScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseGameScene.fxml"));
        Parent sceneRoot = loader.load();
        Scene scene = new Scene(sceneRoot,1600,900);

        //startGameButton.setOnMouseClicked();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void setGui(Gui gui) {
        this.gui=gui;
    }
}

