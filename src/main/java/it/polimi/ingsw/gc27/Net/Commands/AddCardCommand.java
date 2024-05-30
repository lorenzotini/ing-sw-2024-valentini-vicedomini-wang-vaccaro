package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

public class AddCardCommand implements Command {

    String playerName;
    int  handCardIndex;
    boolean isFrontFace;
    int x;
    int y;

    public AddCardCommand(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) {
        this.playerName = playerName;
        this.handCardIndex = handCardIndex;
        this.isFrontFace = isFrontFace;
        this.x = x;
        this.y = y;
    }

    public void execute(GameController gc) throws IOException, InterruptedException {
        Player player = gc.getPlayer(playerName);
        ResourceCard card = player.getHand().get(handCardIndex);
        Face face = isFrontFace ? card.getFront() : card.getBack();
        // TODO: gestire le eccezioni
        gc.addCard(player, card, face, x, y);
    }

    @Override
    public String getPlayerName() {
        return this.playerName;
    }

}
