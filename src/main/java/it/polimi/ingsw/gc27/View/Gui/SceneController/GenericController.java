package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

public interface GenericController {

    void receiveOk(String ackType);
    void overwriteChat(ClientChat chat, MiniModel minimodel);
    void receiveKo(String ackType);
}
