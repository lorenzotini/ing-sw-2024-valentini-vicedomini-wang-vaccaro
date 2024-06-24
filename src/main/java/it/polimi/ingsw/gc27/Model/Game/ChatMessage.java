package it.polimi.ingsw.gc27.Model.Game;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    String sender;
    String receiver;
    String content;

    public ChatMessage(String sender, String receiver, String content){

        this.sender = sender;
        this.receiver = receiver;
        this.content = content;

    }


    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }


}
