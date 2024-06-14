package it.polimi.ingsw.gc27.Model.Game;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    String sender;
    String receiver;
    String content;

    public ChatMessage(Player sender, Player receiver, String content){

        this.sender = sender.getUsername();
        this.receiver = receiver.getUsername();
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

    public void setContent(String content) {
        this.content = content;
    }

}
