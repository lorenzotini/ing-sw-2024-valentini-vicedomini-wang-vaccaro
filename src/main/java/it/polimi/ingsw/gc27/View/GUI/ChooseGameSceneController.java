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

public class ChooseGameSceneController {

    public Button joinGameButton;
    public Button newGameButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Gui gui;


    public void sendNewGame(ActionEvent event) throws IOException {

        //c'è metodo sendJoinGame ma nella pratica fa solo cambiare la scena, poi nella scena successica in cui metti l'id lo manda al client


        //da mettere nella gui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewGameScene.fxml"));
        Parent scene2Root = loader.load();
        Scene scene2 = new Scene(scene2Root, 1600, 900);


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene2);
        stage.show();
    }
}