package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Net.Commands.AddStarterCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import  it.polimi.ingsw.gc27.View.Gui;

import java.io.IOException;

//sixth scene, allows the player to choose front or back of starterCard
public class PlaceStarterCardScene implements GenericController{

    @FXML
    public ImageView frontStarter;
    @FXML
    public ImageView backStarter;
    @FXML
    public Button frontStarterButton;
    @FXML
    public Button backStarterButton;

//    @FXML
//    public void initialize() throws RemoteException {
//        Platform.runLater(()->{
//            StarterCard starter = null;
//            try {
//                starter = Gui.getInstance().getClient().getMiniModel().getPlayer().getStarterCard();
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
//            PlaceStarterCardScene contr = (PlaceStarterCardScene) Gui.getInstance().getControllerFromName("/fxml/PlaceStarterCardScene.fxml");
//            contr.changeImageFront(getClass().getResource(starter.getFront().getImagePath()).toExternalForm());
//            contr.changeImageBack(getClass().getResource(starter.getBack().getImagePath()).toExternalForm());
//        });
//
//        //changeImageFront(Gui.getInstance().getClient().getMiniModel().getPlayer().getStarterCard().getFront().getImagePath());
//    }

    @FXML
    public void sendStarter(ActionEvent event) throws IOException, InterruptedException {
        if(event.getSource().equals(frontStarterButton)){
            //Gui.getInstance().stringFromSceneController("front");
            Command comm = new AddStarterCommand(Gui.getInstance().getClient().getUsername(), true);
            Gui.getInstance().getClient().sendCommand(comm);
        } else {
            Command comm = new AddStarterCommand(Gui.getInstance().getClient().getUsername(), false);
            Gui.getInstance().getClient().sendCommand(comm);
            //Gui.getInstance().stringFromSceneController("back");
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

    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}
