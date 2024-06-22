package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientMarket;

import java.io.Serializable;
import java.util.ArrayList;

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
     * getters and setters
     */
    public ResourceCard[] getFaceUp(boolean isGold) {
        if (isGold) {
            return faceUpGolds;
        } else {
            return faceUpResources;
        }
    }

    public void setFaceUp(ResourceCard card, int index) {
        if (card instanceof GoldCard) {
            this.faceUpGolds[index] = (GoldCard) card;
        } else {
            this.faceUpResources[index] = card;
        }
    }

    public ArrayList<ResourceCard> getResourceDeck() {
        return resourceDeck;
    }

    public void setResourceDeck(ArrayList<ResourceCard> resourceDeck) {
        this.resourceDeck = resourceDeck;
    }

    public ArrayList<GoldCard> getGoldDeck() {
        return goldDeck;
    }

    public void setGoldDeck(ArrayList<GoldCard> goldDeck) {
        this.goldDeck = goldDeck;
    }

    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }

    public void setCommonObjectives(ArrayList<ObjectiveCard> commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

}