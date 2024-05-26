package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class PlaceStarterCardScene {
    public ImageView frontStarter;
    public ImageView backStarter;
    public Button frontStarterButton;
    public Button backStarterButton;



    public void sendStarter(ActionEvent event) {
        if(event.getSource().equals(frontStarterButton)){
            Gui.getInstance().stringFromSceneController("front");
        } else {
            Gui.getInstance().stringFromSceneController("back");
        }
    }


}
