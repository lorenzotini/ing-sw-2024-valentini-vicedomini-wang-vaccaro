package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Card.BackFace;
import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.FrontFace;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Player;

public class GameController {


    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * This method changes the player's manuscript, adding the selected card on it and possibly adding
     * points on the board and then removes the card from the player's hand.
     * @param player
     * @param card
     * @param face
     * @param x
     * @param y
     */
    public void addCard(Player player, ResourceCard card, Face face, int x, int y){
        if(player.getManuscript().isValidPlacement(x, y) && ((face instanceof FrontFace && player.getManuscript().satisfiedRequirement(card)) || (face instanceof BackFace))){
            player.addCard(this.game, card, face, x, y);
            player.getHand().remove(card);
        }else{
            System.err.println("Error: invalid position");
        }
    }

}
