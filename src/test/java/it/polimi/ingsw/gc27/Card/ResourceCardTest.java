package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Controller.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    private static ArrayList<ResourceCard> resourceDeck;
    @Test
    void setCardPoints() {
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    }
}