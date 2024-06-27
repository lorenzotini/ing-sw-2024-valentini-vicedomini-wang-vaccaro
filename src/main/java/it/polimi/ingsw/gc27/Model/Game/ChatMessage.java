package it.polimi.ingsw.gc27.Model.Game;

import java.io.Serializable;

/**
 * ChatMessage class represents a message in a chat.
 * Implements Serializable in order to serialize the object and send it through the network.
 */
public class ChatMessage implements Serializable {
    String sender;
    String receiver;
    String content;

    /**
     * Constructs a ChatMessage with the specified sender, receiver, and message
     * @param sender the sender of the message
     * @param receiver the receiver of the message
     * @param content the message's content
     */
    public ChatMessage(String sender, String receiver, String content){

        this.sender = sender;
        this.receiver = receiver;
        this.content = content;

    }

    /**
     * Gets the sender of the message
     * @return the sender of the message
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the receiver of the message
     * @return the receiver of the message
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets the content of the message
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }


}
