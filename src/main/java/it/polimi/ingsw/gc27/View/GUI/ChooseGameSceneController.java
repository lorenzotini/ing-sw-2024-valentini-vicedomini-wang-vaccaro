package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseGameSceneController implements GeneralSceneController{
    private Gui gui;
    @FXML
    public Button joinGameButton;
    @FXML
    public Button newGameButton;

    @Override
    public void setGui(Gui gui) {
        this.gui=gui;
    }
//    @FXML
//    public void init(){
//        this.newGameButton=new Button();
//        this.joinGameButton=new Button();
//
//    }
//TODO: funzione change scene in gui + capire perchè non funziona (gui null), gestire in gui l'inizializzazione delle scene
    @FXML
    public void sendNewGame() throws IOException {

        System.out.println("pippo");
        Gui.stringFromSceneController("new");
        Gui.changeScene("/fxml/NewGameScene.fxml");
//        //da mettere nella gui
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewGameScene.fxml"));
//        Parent scene2Root = loader.load();
//        Scene scene2 = new Scene(scene2Root, 1600, 900);
//
//
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene2);
//        stage.show();
    }

    public void sendJoinGame() throws IOException {
        Gui.changeScene("/fxml/JoinGameScene.fxml");
        //changeScene, in quella farà gui.stringFrom..
    }

}