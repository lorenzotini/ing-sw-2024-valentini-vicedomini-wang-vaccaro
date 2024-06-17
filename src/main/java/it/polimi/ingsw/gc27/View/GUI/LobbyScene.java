package it.polimi.ingsw.gc27.View.GUI;

//fifth scene
//waiting for other players to join, will be displayed when player is  waiting for other players to join the game

import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;

import java.io.IOException;

public class LobbyScene implements GenericController{

    @Override
    public void receiveOk(String ackType) {
//        StarterCard starter = null;
//        try {
//            starter = Gui.getInstance().getClient().getMiniModel().getPlayer().getStarterCard();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        PlaceStarterCardScene contr = (PlaceStarterCardScene) Gui.getInstance().getControllerFromName("/fxml/PlaceStarterCardScene.fxml");
//        //contr.changeImageFront(getClass().getResource(starter.getFront().getImagePath()).toExternalForm());
//        contr.changeImageFront("file:" + starter.getFront().getImagePath());
//        contr.changeImageFront("file:" + starter.getBack().getImagePath());
//        //contr.changeImageBack(getClass().getResource(starter.getBack().getImagePath()).toExternalForm());
//        Platform.runLater(()->{
//            try {
//                Gui.getInstance().switchScene("/fxml/PlaceStarterCardScene.fxml");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}
