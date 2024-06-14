package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientPlayer;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class SendMessageCommand implements Command {
    Player player;
    String receiver;
    String content;
    public  SendMessageCommand(ClientPlayer p, String receiver, String content){
        this.player = (Player) p;
        this.receiver = receiver;
        this.content = content;
    }
    @Override
    public void execute(GameController gc) {
        gc.sendChatMessage(new ChatMessage(gc.getPlayer(player.getUsername()), gc.getPlayer(receiver), this.content));
    }
    @Override
    public String getPlayerName() {
        return this.player.getUsername();
    }
}
