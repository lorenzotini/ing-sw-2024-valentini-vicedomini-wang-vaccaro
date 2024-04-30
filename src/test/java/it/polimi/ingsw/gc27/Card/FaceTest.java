package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.Corner;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;

class FaceTest {
    private static Kingdom colour;
    //UR = upper right, LL = lower left, etc...
    private static Corner cornerUR;
    private static Corner cornerUL;
    private static Corner cornerLR;
    private static Corner cornerLL;
    private static ArrayList<ResourceCard> resourceDeck;


    @Test
    void getCornerTest(){ //test for index out of bound
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        Face faceCard1=resourceDeck.get(0).getFront();

        assertNull(faceCard1.getCorner(2,2));

    }


}