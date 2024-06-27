package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Chat class represents a chat between players in the game.
 * It is serializable, allowing it to be written to an output stream and read back.
 */
public class Chat implements Serializable, ClientChat {
    final BlockingQueue<String> chatters = new LinkedBlockingQueue<>();
    final BlockingQueue<ChatMessage> chatMessages = new LinkedBlockingQueue<>();

    /**
     * Default constructor. Initializes a global chat.
     */
    public Chat() {
        chatters.add("global");
    }

    /**
     * Constructor for a chat between two players.
     *
     * @param player1 The first player in the chat.
     * @param player2 The second player in the chat.
     */
    public Chat(Player player1, Player player2) {
        chatters.add(player1.getUsername());
        chatters.add(player2.getUsername());
    }

    /**
     * Adds a chat message to the chat
     *
     * @param chatMessage the chat message to add
     */
    public synchronized void addChatMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
    }

    /**
     * Gets the list of chat participants
     *
     * @return the list of chat participants
     */
    @Override
    public ArrayList<String> getChatters() {
        return new ArrayList<>(chatters);
    }

    /**
     * Gets the list of chat messages
     *
     * @return the list of chat messages
     */
    @Override
    public ArrayList<ChatMessage> getChatMessages() {
        return new ArrayList<>(this.chatMessages);
    }

    /**
     * Checks if a username is a participant of the chat
     *
     * @param username the username to check
     * @return true if the username is a participant, false otherwise
     */
    @Override
    public boolean contains(String username) {
        for (String p : getChatters()) {
            if (p.equals(username)) {
                return true;
            }
        }
        return false;
    }

}
