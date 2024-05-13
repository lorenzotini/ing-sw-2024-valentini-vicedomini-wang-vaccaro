package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;

import java.io.Serializable;
import java.util.ArrayList;

public class Market implements Serializable {
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

    public ResourceCard[] getFaceUp(boolean isGold) {
        if(isGold){
            return faceUpGolds;
        }else{
            return faceUpResources;
        }
    }

    public void setFaceUp(ResourceCard card, int index) {
        if(card instanceof GoldCard){
            this.faceUpGolds[index] = (GoldCard) card;
        }else{
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

}