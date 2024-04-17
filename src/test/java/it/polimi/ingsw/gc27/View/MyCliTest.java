package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Game.Manuscript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyCliTest {
    private ArrayList<ResourceCard> resourceDeck;
    private ArrayList<StarterCard> starterDeck;
    @BeforeEach
    void setUp() {
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
    }
    @Test
    void printManuscript1() {
        Manuscript m = new Manuscript();

        m.getField()[40][40] = resourceDeck.get(0).getBack();
        m.getField()[40][40].getCornerLL().setHidden(true);

        m.getField()[39][41] = resourceDeck.get(1).getBack();
        m.getField()[39][41].getCornerLL().setHidden(true);

        m.getField()[38][42] = resourceDeck.get(2).getBack();
        m.getField()[38][42].getCornerLL().setHidden(true);

        m.getField()[37][43] = resourceDeck.get(3).getBack();


        m.setxMin(37);
        m.setyMin(40);
        m.setxMax(40);
        m.setyMax(43);

        MyCli.printManuscript(m);
    }
    @Test
    void printManuscript2() {
        Manuscript m = new Manuscript();

        m.getField()[41][41] = resourceDeck.get(0).getBack();
        m.getField()[41][41].getCornerLR().setHidden(true);

        m.getField()[43][41] = resourceDeck.get(1).getBack();
        m.getField()[43][41].getCornerLR().setHidden(true);
        m.getField()[43][41].getCornerLL().setHidden(true);

        m.getField()[42][42] = resourceDeck.get(2).getBack();
        m.getField()[42][42].getCornerLL().setHidden(true);

        m.getField()[44][42] = resourceDeck.get(3).getBack();

        m.getField()[41][43] = resourceDeck.get(4).getBack();
        m.getField()[41][43].getCornerLR().setHidden(true);

        m.getField()[43][43] = resourceDeck.get(5).getBack();
        m.getField()[43][43].getCornerLL().setHidden(true);
        m.getField()[43][43].getCornerUR().setHidden(true);
        m.getField()[43][43].getCornerUL().setHidden(true);
        m.getField()[43][43].getCornerLR().setHidden(true);

        m.getField()[42][44] = resourceDeck.get(6).getBack();

        m.getField()[44][44] = resourceDeck.get(7).getBack();

        m.setxMin(41);
        m.setyMin(41);
        m.setxMax(44);
        m.setyMax(44);


        MyCli.printManuscript(m);
    }
    @Test
    void printManuscript3() {
        Manuscript m = new Manuscript();

        m.getField()[41][41] = resourceDeck.get(0).getBack();
        m.getField()[41][41].getCornerLR().setHidden(true);

        m.getField()[43][41] = resourceDeck.get(1).getBack();
        m.getField()[43][41].getCornerLR().setHidden(true);
        m.getField()[43][41].getCornerLL().setHidden(true);

        m.getField()[42][42] = resourceDeck.get(2).getBack();

        m.getField()[44][42] = resourceDeck.get(3).getBack();

        m.setxMin(41);
        m.setyMin(41);
        m.setxMax(44);
        m.setyMax(42);


        MyCli.printManuscript(m);
    }
    @Test
    void printManuscript4() {
        Manuscript m = new Manuscript();

        // #0
        m.getField()[40][40] = starterDeck.get(0).getBack();
        m.getField()[40][40].getCornerLL().setHidden(true);
        m.getField()[40][40].getCornerLR().setHidden(true);
        m.getField()[40][40].getCornerUR().setHidden(true);
        m.getField()[40][40].getCornerUL().setHidden(true);

        // #1
        m.getField()[39][41] = resourceDeck.get(20).getBack();
        m.getField()[39][41].getCornerLL().setHidden(true);
        m.getField()[39][41].getCornerLR().setHidden(true);

        // #2
        m.getField()[39][39] = resourceDeck.get(21).getBack();
        m.getField()[39][39].getCornerUR().setHidden(true);

        // #3
        m.getField()[38][42] = resourceDeck.get(22).getBack();
        m.getField()[38][42].getCornerLL().setHidden(true);

        // #4
        m.getField()[37][43] = resourceDeck.get(23).getBack();
        m.getField()[37][43].getCornerUL().setHidden(true);

        // #5
        m.getField()[40][38] = resourceDeck.get(0).getBack();
        m.getField()[40][38].getCornerLR().setHidden(true);

        // #6
        m.getField()[41][39] = resourceDeck.get(30).getBack();
        m.getField()[41][39].getCornerLR().setHidden(true);

        // #7
        m.getField()[36][42] = resourceDeck.get(31).getBack();

        // #8
        m.getField()[42][40] = resourceDeck.get(10).getBack();
        m.getField()[42][40].getCornerLL().setHidden(true);

        // #9
        m.getField()[40][42] = resourceDeck.get(11).getBack();
        m.getField()[40][42].getCornerUR().setHidden(true);

        // #10
        m.getField()[41][41] = resourceDeck.get(32).getBack();

        m.setxMin(36);
        m.setyMin(38);
        m.setxMax(42);
        m.setyMax(43);

        MyCli.printManuscript(m);
    }
}