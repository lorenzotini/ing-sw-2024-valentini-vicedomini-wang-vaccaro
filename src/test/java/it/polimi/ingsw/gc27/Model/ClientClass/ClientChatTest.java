package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Game.ChatMessage;

import java.util.ArrayList;
// class created to mock the chat, all the methods are implementation of interface ClientChat
public class ClientChatTest implements ClientChat{
    @Override
    public ArrayList<String> getChatters() {
        return null;
    }

    @Override
    public ArrayList<ChatMessage> getChatMessages() {
        return null;
    }

    @Override
    public boolean contains(String username) {
        return false;
    }
}
