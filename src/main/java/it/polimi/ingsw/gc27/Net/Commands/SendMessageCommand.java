package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientPlayer;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class SendMessageCommand implements Command {
    String sender;
    String receiver;
    String content;
    public  SendMessageCommand(ClientPlayer p, String receiver, String content){
        this.sender = p.getUsername();
        this.receiver = receiver;
        this.content = content;
    }
    @Override
    public void execute(GameController gc) {
        gc.sendChatMessage(new ChatMessage(sender, receiver, this.content));
    }
    @Override
    public String getPlayerName() {
        return this.sender;
    }
}
