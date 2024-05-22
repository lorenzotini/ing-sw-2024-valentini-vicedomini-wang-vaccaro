package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseGameSceneController implements GeneralSceneController{
    private Gui gui;
    public Button joinGameButton;
    public Button newGameButton;
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void sendNewGame(ActionEvent event) throws IOException {
        gui.stringFromSceneController("new");
        //c'è metodo sendJoinGame ma nella pratica fa solo cambiare la scena, poi nella scena successica in cui metti l'id lo manda al client


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

    public void sendJoinGame(ActionEvent event) throws IOException {
        //changeScene, in quella farà gui.stringFrom..
    }
    @Override
    public void setGui(Gui gui) {
        this.gui=gui;
    }
}