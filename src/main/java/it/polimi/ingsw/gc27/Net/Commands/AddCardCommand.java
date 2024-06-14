package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Messages.GenericErrorMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

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
    @Override
    public void execute(GameController gc) {
        Player player = gc.getPlayer(playerName);
        ResourceCard card;
        try{
            card = player.getHand().get(handCardIndex);
            Face face = isFrontFace ? card.getFront() : card.getBack();
            gc.addCard(player, card, face, x, y);
        } catch (IndexOutOfBoundsException e){
            gc.getGame().notifyObservers(new GenericErrorMessage("Draw a card", new MiniModel(player)));
        }

    }

    @Override
    public String getPlayerName() {
        return this.playerName;
    }

}
