package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;

import java.util.ArrayList;

/**
 * The ClientMarket interface provides methods for accessing the market data,
 * therefore face-up cards, resource deck, gold deck, and common objectives.
 */
public interface ClientMarket {

    /**
     * Gets the face-up ResourceCards/GoldCard in the market
     * @param isGold if true, returns GoldCard, otherwise returns ResourceCards.
     * @return an array of ResourceCards that are face-up in the market.
     */
    ResourceCard[] getFaceUp(boolean isGold);

    /**
     * Gets the deck of ResourceCards in the market.
     * @return an ArrayList of ResourceCards representing the resource deck.
     */
    ArrayList<ResourceCard> getResourceDeck();

    /**
     * Gets the deck of GoldCards in the market.
     * @return an ArrayList of GoldCards representing the gold deck.
     */
    ArrayList<GoldCard> getGoldDeck();

    /**
     * Gets the list of common ObjectiveCards in the market.
     * @return an ArrayList of ObjectiveCards representing the common objectives.
     */
    ArrayList<ObjectiveCard> getCommonObjectives();

}
