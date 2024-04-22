package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;

import java.io.Serializable;
import java.util.ArrayList;

public class Market implements Serializable{
    private ResourceCard[] faceUpResources = new ResourceCard[2];
    private GoldCard[] faceUpGolds = new GoldCard[2];
    private ArrayList<ResourceCard> resourceDeck;
    private ArrayList<GoldCard> goldDeck;

    public Market(){

    }
    public Market(ArrayList<ResourceCard> resourceDeck, ArrayList<GoldCard> goldDeck, ResourceCard[] faceUpResources, GoldCard[] faceUpGolds) {
        this.faceUpResources = faceUpResources;
        this.faceUpGolds = faceUpGolds;
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
    }

    public ResourceCard[] getFaceUpResources() {
        return faceUpResources;
    }

    public void setFaceUpResources(ResourceCard card, int index) {
        this.faceUpResources[index] = card;
    }

    public GoldCard[] getFaceUpGolds() {
        return faceUpGolds;
    }

    public void setFaceUpGolds(GoldCard card, int index) {
        this.faceUpGolds[index] = card;
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
}