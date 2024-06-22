package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Chat implements Serializable, ClientChat {


    final BlockingQueue<String> chatters = new LinkedBlockingQueue<>();
    final BlockingQueue<ChatMessage> chatMessages = new LinkedBlockingQueue<>();

    public Chat(){
        chatters.add("global");
    }
    public Chat(Player player1, Player player2){
        //creazione di una chat singola
        chatters.add(player1.getUsername());
        chatters.add(player2.getUsername());
    }
    public synchronized void addChatMessage(ChatMessage chatMessage){
        chatMessages.add(chatMessage);
    }
    @Override
    public ArrayList<String> getChatters() {
        return new ArrayList<>(chatters);
    }
    @Override
    public ArrayList<ChatMessage> getChatMessages(){
        return new ArrayList<>(this.chatMessages);
    }
    @Override
    public boolean contains(String username){
        for(String p : getChatters()){
            if(p.equals(username)){
                return true;
            }
        }
        return false;
    }

}
