package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Model.Card.Corner;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Utils.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;

class FaceTest {
    private static ArrayList<ResourceCard> resourceDeck;
    @Test
    void getCornerTest(){ //test for index out of bound
        JsonParser jsonParser = new JsonParser("codex_cards_collection.json");
        resourceDeck = jsonParser.getResourceDeck();

        Face faceCard1=resourceDeck.get(0).getFront();

        assertNull(faceCard1.getCorner(2,2));
    }


}