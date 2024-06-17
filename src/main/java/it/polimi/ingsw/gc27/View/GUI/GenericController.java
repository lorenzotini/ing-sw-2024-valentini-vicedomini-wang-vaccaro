package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;

public interface GenericController {

    void receiveOk(String ackType);
    void overwriteChat(ClientChat chat, MiniModel minimodel);
    void receiveKo(String ackType);
}
