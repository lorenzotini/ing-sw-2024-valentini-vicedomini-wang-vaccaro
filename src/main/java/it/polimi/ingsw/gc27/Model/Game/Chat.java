package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Chat implements Serializable, ClientChat {


    final BlockingQueue<Player> chatters = new LinkedBlockingQueue<>();
    final BlockingQueue<ChatMessage> chatMessages = new LinkedBlockingQueue<>();

    public Chat(){

    }
    public Chat(Player player1, Player player2){
        //creazione di una chat singola
        chatters.add(player1);
        chatters.add(player2);
    }
    public void addPlayer(Player player){
        chatters.add(player);
    }
    public synchronized void addChatMessage(ChatMessage chatMessage){
        //TODO don't forget to create a right notify
        chatMessages.add(chatMessage);
    }
    @Override
    public ArrayList<Player> getChatters() {
        return new ArrayList<>(chatters);
    }
    @Override
    public ArrayList<ChatMessage> getChatMessages(){
        return new ArrayList<>(this.chatMessages);
    }
    @Override
    public boolean contains(String username){
        for(Player p : getChatters()){
            if(p.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}
