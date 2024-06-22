package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientMarket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the market in the game, where players can draw cards either from decks or exposed cards
 */
public class Market implements Serializable, ClientMarket {

    private ResourceCard[] faceUpResources = new ResourceCard[2];
    private GoldCard[] faceUpGolds = new GoldCard[2];
    private ArrayList<ResourceCard> resourceDeck;
    private ArrayList<GoldCard> goldDeck;
    private ArrayList<ObjectiveCard> commonObjectives = new ArrayList<>();

    public Market() {

    }

    /**
     * constructor of the market, this the common area where players can draw cards
     * either from the decks or the exposed cards
     * @param resourceDeck deck of resource cards
     * @param goldDeck deck of gold cards
     * @param faceUpResources exposed resource cards
     * @param faceUpGolds exposed gold cards
     * @param commonObjectives common objectives
     */
    public Market(ArrayList<ResourceCard> resourceDeck, ArrayList<GoldCard> goldDeck, ResourceCard[] faceUpResources, GoldCard[] faceUpGolds, ArrayList<ObjectiveCard> commonObjectives) {
        this.faceUpResources = faceUpResources;
        this.faceUpGolds = faceUpGolds;
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.commonObjectives = commonObjectives;
    }


    /**
     * Gets the array of face-up cards based on the type specified
     * @param isGold Specifies whether to get face-up gold cards or face-up resource card
     * @return The array of face-up cards (either gold or resource)
     */
    public ResourceCard[] getFaceUp(boolean isGold) {
        if (isGold) {
            return faceUpGolds;
        } else {
            return faceUpResources;
        }
    }

    /**
     * Sets the face-up card at the specified index based on the card type (gold or resource)
     * @param card The card to set as face-up
     * @param index The index in the face-up array to set the card
     */
    public void setFaceUp(ResourceCard card, int index) {
        if (card instanceof GoldCard) {
            this.faceUpGolds[index] = (GoldCard) card;
        } else {
            this.faceUpResources[index] = card;
        }
    }

    /**
     * Gets the deck of resource cards in the market
     * @return The deck of resource cards
     */
    public ArrayList<ResourceCard> getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Sets the deck of resource cards in the market
     * @param resourceDeck The deck of resource cards to set
     */
    public void setResourceDeck(ArrayList<ResourceCard> resourceDeck) {
        this.resourceDeck = resourceDeck;
    }

    /**
     * Gets the deck of gold cards in the market
     * @return The deck of gold cards
     */
    public ArrayList<GoldCard> getGoldDeck() {
        return goldDeck;
    }

    /**
     * Sets the deck of gold cards in the market
     * @param goldDeck The deck of gold cards to set
     */
    public void setGoldDeck(ArrayList<GoldCard> goldDeck) {
        this.goldDeck = goldDeck;
    }

    /**
     * Gets the list of common objectives in the market
     * @return The list of common objectives
     */
    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }

    /**
     * Sets the list of common objectives in the market
     * @param commonObjectives The list of common objectives to set
     */
    public void setCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

}