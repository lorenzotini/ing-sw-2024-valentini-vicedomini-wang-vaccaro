package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Controller.JsonParser;

import java.util.ArrayList;

public class VerificaParser {
    public static void main(String[] args) {
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        int a = 0;
    }
}