package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Manuscript;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;

import java.util.ArrayList;

public class GameController {
    private Game game;
    public GameController(Game game) {
        this.game = game;
    }
    public GameController(){}
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
    public void addCard(Player player, ResourceCard card, Face face, int x, int y)  {
        if(player.getManuscript().isValidPlacement(x, y) && ((face instanceof FrontFace && player.getManuscript().satisfiedRequirement((ResourceCard) card)) || (face instanceof BackFace))){
            player.addCard(this.game, card, face, x, y);
            player.getHand().remove(card);
        }else{
            System.err.println("Error: invalid position");
        }
    }

    public void addStarterCard(Player player, StarterCard card, Face face){
        player.addCard(this.game, card, face, Manuscript.FIELD_DIM/2, Manuscript.FIELD_DIM/2);
    }

    // Overloading drawCard to manage Resource cards and deck
    public void drawCard(Market market, Player player, ArrayList<ResourceCard> deck, ResourceCard card, int faceUpCardIndex){
        // TODO: è SBAGLIATO GESTIRE QUI L'ECCEZIONE, DOVREBBE ESSERE IL MODEL A FARLO. NON RISPETTA MVC ==> CAMBIARE
        if(deck != null && card != null){
            throw new IllegalArgumentException("Something went wrong: impossible call to drawCard method");
        }

        player.getHand().add(card);

        //replace card on market
        if(deck == null){ // player drawn a face up card from the market
            market.setFaceUpResources(market.getResourceDeck().removeLast(), faceUpCardIndex);
        }else{  // player drawn card from a deck
            market.getResourceDeck().removeLast();
        }
    }

    // Overloading drawCard to manage Gold cards and deck
    public void drawCard(Market market, Player player, ArrayList<GoldCard> deck, GoldCard card, int faceUpCardIndex){
        // TODO: è SBAGLIATO GESTIRE QUI L'ECCEZIONE, DOVREBBE ESSERE IL MODEL A FARLO. NON RISPETTA MVC ==> CAMBIARE
        if(deck != null && card != null){
            throw new IllegalArgumentException("Something went wrong: impossible call to drawCard method");
        }

        player.getHand().add(card);

        //replace card on market
        if(deck == null){ // player drawn a face up card from the market
            market.setFaceUpGolds(market.getGoldDeck().removeLast(), faceUpCardIndex);
        }else{  // player drawn card from a deck
            market.getGoldDeck().removeLast();
        }
    }

}
