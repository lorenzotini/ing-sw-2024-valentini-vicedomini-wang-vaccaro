package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class ChooseGameSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;



    public void switchToNewGameScene(ActionEvent event) throws IOException {

        //notify del gigaController che voglio creare un nuovo game
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewGameScene.fxml"));
        Parent scene2Root = loader.load();
        Scene scene2 = new Scene(scene2Root,1600,900);


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene2);
        stage.show();


    }

}
