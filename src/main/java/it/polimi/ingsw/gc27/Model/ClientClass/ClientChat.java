package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.util.ArrayList;

/**
 * The ClientChat interface provides methods for accessing chat participants,
 * chat messages, and checking if a user is part of the chat.
 */
public interface ClientChat {

    /**
     * Gets the list of users participating in the chat (global or private)
     *
     * @return an ArrayList of usernames participating in the chat
     */
    ArrayList<String> getChatters();

    /**
     * Gets the list of messages in the chat
     *
     * @return an ArrayList of ChatMessage objects representing the chat messages
     */
    ArrayList<ChatMessage> getChatMessages();

    /**
     * Checks if the specified user is part of the chat
     *
     * @param username the username to check
     * @return true if the username is part of the chat, false otherwise
     */
    boolean contains(String username);
}
