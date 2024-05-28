package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ResourceCardTest {
    private static ArrayList<ResourceCard> resourceDeck;
    @Test
    void setCardPoints() {
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    }
}