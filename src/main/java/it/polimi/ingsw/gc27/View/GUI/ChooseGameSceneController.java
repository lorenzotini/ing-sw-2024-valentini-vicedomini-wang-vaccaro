package it.polimi.ingsw.gc27.View.GUI;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewGameScene.fxml"));
        Parent scene2Root = loader.load();
        Scene scene2 = new Scene(scene2Root,1200,800);


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene2);
        stage.show();


    }

}
