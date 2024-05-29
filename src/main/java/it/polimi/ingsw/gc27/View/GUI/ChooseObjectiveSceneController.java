package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    public void sendObj(ActionEvent event) {
        if(event.getSource().equals(objButton1)){
            Gui.getInstance().stringFromSceneController("1");
        } else {
            Gui.getInstance().stringFromSceneController("2");
        }
    }

    public void changeImageObj1(String imagePath) {
        Image image = new Image(imagePath);
        obj1.setImage(image);
    }
    public void changeImageObj2(String imagePath) {
        Image image = new Image(imagePath);
        obj2.setImage(image);
    }

}
