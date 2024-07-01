package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ResourceCardTest {
    private static ArrayList<ResourceCard> resourceDeck;
    @Test
    void setCardPoints() {
        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        resourceDeck = jsonParser.getResourceDeck();
    }
}