package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Game.Chat;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class SendMessageCommand implements Command {
    Player player;
    String receiver;
    String content;
    public  SendMessageCommand(Player p, String receiver, String content){
        this.player = p;
        this.receiver = receiver;
        this.content = content;
    }
    @Override
    public void execute(GigaController gigaController) throws IOException, InterruptedException {
        GameController gc = gigaController.userToGameController(player.getUsername());
        gc.sendChatMessage(new ChatMessage(gigaController.getPlayer(player.getUsername()), gigaController.getPlayer(receiver), this.content));
    }
}
