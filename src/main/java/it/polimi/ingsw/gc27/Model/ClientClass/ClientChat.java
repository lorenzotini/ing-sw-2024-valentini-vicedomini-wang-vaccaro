package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.util.ArrayList;

public interface ClientChat {
    ArrayList<String> getChatters();
    ArrayList<ChatMessage> getChatMessages();
    boolean contains(String username);
}
