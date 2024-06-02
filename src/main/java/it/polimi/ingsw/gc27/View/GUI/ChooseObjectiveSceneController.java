package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Net.Commands.ChooseObjectiveCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ChooseObjectiveSceneController implements GenericController{
    @FXML
    public Button objButton1;
    @FXML
    public Button objButton2;
    @FXML
    public ImageView obj1;
    @FXML
    public ImageView obj2;


    @FXML
    public void sendObj(ActionEvent event) throws IOException, InterruptedException {
        if(event.getSource().equals(objButton1)){
            Command comm = new ChooseObjectiveCommand(Gui.getInstance().getClient().getUsername(), 1);
            Gui.getInstance().getClient().sendCommand(comm);
        } else {
            Command comm = new ChooseObjectiveCommand(Gui.getInstance().getClient().getUsername(), 2);
            Gui.getInstance().getClient().sendCommand(comm);
        }
        Platform.runLater(()->{
            try {
                Gui.getInstance().switchScene("/fxml/ManuscriptScene.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void changeImageObj1(String imagePath) {
        Image image = new Image(imagePath);
        obj1.setImage(image);
    }
    public void changeImageObj2(String imagePath) {
        Image image = new Image(imagePath);
        obj2.setImage(image);
    }

    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}
