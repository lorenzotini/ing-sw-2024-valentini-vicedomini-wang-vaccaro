package it.polimi.ingsw.gc27.Model.Game;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Chat {
    final BlockingQueue<Player> chatters = new LinkedBlockingQueue<>();
    final BlockingQueue<ChatMessage> chatMessages = new LinkedBlockingQueue<>();

    public Chat(Player player1, Player player2){
        //creazione di una chat singola
        chatters.add(player1);
        chatters.add(player2);
    }
    public void addPlayer(Player player){
        chatters.add(player);
    }
    public void addChatMessage(ChatMessage chatMessage){
        //TODO don't forget to create a right notify
        chatMessages.add(chatMessage);
    }
}
