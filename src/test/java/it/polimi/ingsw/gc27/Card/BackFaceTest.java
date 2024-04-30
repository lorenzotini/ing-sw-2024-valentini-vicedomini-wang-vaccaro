package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
class BackFaceTest {
    private static ArrayList<ResourceCard> resourceDeck;

    @Test
    void copy() {
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        BackFace card1=resourceDeck.get(0).getBack();
        BackFace card2= (BackFace) card1.copy(card1);
        assertEquals(card1.getColour(), card2.getColour());
        assertEquals(card1.getCornerLR(), card2.getCornerLR());
        assertEquals(card1.getCornerUR(), card2.getCornerUR());
        assertEquals(card1.getCornerLL(), card2.getCornerLL());
        assertEquals(card1.getCornerUL(), card2.getCornerUL());

    }

    @Test
    void getPermanentResources() {
    }
}