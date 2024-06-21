package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.JsonParser;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DoublePatternTest {

    @Test
    void calculateObjectivePointsTest() {
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);

        ResourceCard resourceCard1= resourceDeck.get(0);
        ResourceCard resourceCard2= resourceDeck.get(4);
        ResourceCard resourceCard3= resourceDeck.get(14);


        StarterCard starterCard= starterDeck.get(1);
        ObjectiveCard objectiveCard = objectiveDeck.get(15);

        FrontFace frontface =objectiveCard.getFront();
        BackFace backface =objectiveCard.getBack();

        Manuscript manuscript=new Manuscript();

        Face[][] localField = manuscript.getField();
        localField[41][41]= resourceCard1.getFront();
        localField[43][43]= resourceCard2.getFront();
        localField[42][44]= resourceCard3.getFront();

    }
}