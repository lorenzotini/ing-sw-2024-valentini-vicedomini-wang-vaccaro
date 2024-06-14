package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;

import java.util.ArrayList;

public interface ClientMarket {
    ResourceCard[] getFaceUp(boolean isGold);
    ArrayList<ResourceCard> getResourceDeck();
    ArrayList<GoldCard> getGoldDeck();
    ArrayList<ObjectiveCard> getCommonObjectives();

}
