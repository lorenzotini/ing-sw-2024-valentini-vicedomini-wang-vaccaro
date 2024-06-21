package it.polimi.ingsw.gc27.View.Gui.SceneController;

//fifth scene
//waiting for other players to join, will be displayed when player is  waiting for other players to join the game

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

public class LobbySceneController implements GenericController{

    @Override
    public void receiveOk(String ackType) {
//        StarterCard starter = null;
//        try {
//            starter = Gui.getInstance().getClient().getMiniModel().getPlayer().getStarterCard();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        PlaceStarterCardSceneController contr = (PlaceStarterCardSceneController) Gui.getInstance().getControllerFromName("/fxml/PlaceStarterCardSceneController.fxml");
//        //contr.changeImageFront(getClass().getResource(starter.getFront().getImagePath()).toExternalForm());
//        contr.changeImageFront("file:" + starter.getFront().getImagePath());
//        contr.changeImageFront("file:" + starter.getBack().getImagePath());
//        //contr.changeImageBack(getClass().getResource(starter.getBack().getImagePath()).toExternalForm());
//        Platform.runLater(()->{
//            try {
//                Gui.getInstance().switchScene("/fxml/PlaceStarterCardSceneController.fxml");
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
