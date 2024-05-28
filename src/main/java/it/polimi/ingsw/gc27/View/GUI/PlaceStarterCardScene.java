package it.polimi.ingsw.gc27.View.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import  it.polimi.ingsw.gc27.View.Gui;

public class PlaceStarterCardScene implements GenericController{

    @FXML
    public ImageView frontStarter;
    @FXML
    public ImageView backStarter;
    @FXML
    public Button frontStarterButton;
    @FXML
    public Button backStarterButton;


    public void sendStarter(ActionEvent event) {
        if(event.getSource().equals(frontStarterButton)){
            Gui.getInstance().stringFromSceneController("front");
        } else {
            Gui.getInstance().stringFromSceneController("back");
        }
    }

    public void changeImageFront(String imagePath) {
        Image image = new Image(imagePath);
        frontStarter.setImage(image);
    }

    public void changeImageBack(String imagePath) {
        Image image = new Image(imagePath);
        backStarter.setImage(image);
    }

}
