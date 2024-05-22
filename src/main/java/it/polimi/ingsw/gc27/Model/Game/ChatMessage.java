package it.polimi.ingsw.gc27.Model.Game;

public class ChatMessage {
    Player sender;
    Player receiver;
    String content;

    public ChatMessage(Player sender, Player receiver, String content){

        this.sender = sender;
        this.receiver = receiver;
        this.content = content;

    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
