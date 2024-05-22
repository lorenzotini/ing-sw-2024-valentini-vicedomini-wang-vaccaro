package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static it.polimi.ingsw.gc27.View.Tui.*;
import static java.lang.System.out;

class TuiTest {
    private ArrayList<ResourceCard> resourceDeck;
    private ArrayList<StarterCard> starterDeck;
    private ArrayList<GoldCard> goldDeck;
    private ArrayList<ObjectiveCard> objectiveDeck;

    @BeforeEach
    void setUp() {
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
        objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
    }
    @Test
    void showManuscript1() {
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

        System.out.print(Tui.showManuscript(m));
    }
    @Test
    void showManuscript2() {
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


        System.out.print(Tui.showManuscript(m));
    }
    @Test
    void showManuscript3() {
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


        System.out.print(Tui.showManuscript(m));
    }
    @Test
    void showManuscript4() {
        Manuscript m = new Manuscript();

        // #0
        m.getField()[40][40] = starterDeck.get(0).getFront();
        m.getField()[40][40].getCornerLL().setHidden(true);
        m.getField()[40][40].getCornerLR().setHidden(true);
        m.getField()[40][40].getCornerUR().setHidden(true);
        m.getField()[40][40].getCornerUL().setHidden(true);

        // #1
        m.getField()[39][41] = goldDeck.get(20).getFront();
        m.getField()[39][41].getCornerLL().setHidden(true);
        m.getField()[39][41].getCornerLR().setHidden(true);

        // #2
        m.getField()[39][39] = goldDeck.get(21).getFront();
        m.getField()[39][39].getCornerUR().setHidden(true);

        // #3
        m.getField()[38][42] = goldDeck.get(22).getFront();
        m.getField()[38][42].getCornerLL().setHidden(true);

        // #4
        m.getField()[37][43] = goldDeck.get(23).getFront();
        m.getField()[37][43].getCornerUL().setHidden(true);

        // #5
        m.getField()[40][38] = goldDeck.get(0).getFront();
        m.getField()[40][38].getCornerLR().setHidden(true);

        // #6
        m.getField()[41][39] = goldDeck.get(30).getFront();
        m.getField()[41][39].getCornerLR().setHidden(true);

        // #7
        m.getField()[36][42] = goldDeck.get(31).getFront();

        // #8
        m.getField()[42][40] = goldDeck.get(10).getFront();
        m.getField()[42][40].getCornerLL().setHidden(true);

        // #9
        m.getField()[40][42] = goldDeck.get(11).getFront();
        m.getField()[40][42].getCornerUR().setHidden(true);

        // #10
        m.getField()[41][41] = goldDeck.get(32).getFront();

        m.setxMin(36);
        m.setyMin(38);
        m.setxMax(42);
        m.setyMax(43);

        System.out.print(Tui.showManuscript(m));
    }
    @Test
    void showManuscript5() {
        Manuscript m = new Manuscript();

        // #0
        m.getField()[40][40] = starterDeck.get(0).getBack();
        m.getField()[40][40].getCornerLR().setHidden(true);

        // #1
        m.getField()[41][41] = resourceDeck.get(20).getBack();
        m.getField()[41][41].getCornerLR().setHidden(true);

        // #2
        m.getField()[42][42] = resourceDeck.get(21).getBack();
        m.getField()[42][42].getCornerLR().setHidden(true);

        // #3
        m.getField()[43][43] = resourceDeck.get(22).getBack();
        m.getField()[43][43].getCornerLR().setHidden(true);

        // #4
        m.getField()[44][44] = resourceDeck.get(23).getBack();


        m.setxMin(40);
        m.setyMin(40);
        m.setxMax(44);
        m.setyMax(44);

        System.out.print(Tui.showManuscript(m));
    }
    @Test
    void showManuscript6() {
        Manuscript m = new Manuscript();

        // #0
        m.getField()[40][40] = starterDeck.get(0).getBack();
        m.getField()[40][40].getCornerLR().setHidden(true);

        // #1
        m.getField()[41][41] = resourceDeck.get(20).getBack();
        m.getField()[41][41].getCornerLR().setHidden(true);

        // #2
        m.getField()[42][42] = resourceDeck.get(21).getBack();
        m.getField()[42][42].getCornerLR().setHidden(true);

        // #3
        m.getField()[43][43] = resourceDeck.get(22).getBack();
        m.getField()[43][43].getCornerLR().setHidden(true);
        m.getField()[43][43].getCornerUR().setHidden(true);

        m.getField()[44][42] = resourceDeck.get(23).getBack();
        m.getField()[44][42].getCornerUR().setHidden(true);

        m.getField()[45][41] = resourceDeck.get(24).getBack();

        // #4
        m.getField()[44][44] = resourceDeck.get(25).getBack();


        m.setxMin(40);
        m.setyMin(40);
        m.setxMax(45);
        m.setyMax(44);

        System.out.print(Tui.showManuscript(m));
    }
    @Test
    void showManuscript7() {
        Manuscript m = new Manuscript();

        // #2
        m.getField()[42][42] = resourceDeck.get(21).getBack();
        m.getField()[42][42].getCornerLR().setHidden(true);

        // #3
        m.getField()[43][43] = resourceDeck.get(22).getBack();
        m.getField()[43][43].getCornerLR().setHidden(true);
        m.getField()[43][43].getCornerUR().setHidden(true);
        //m.getField()[43][43].getCornerUL().setHidden(true);
        m.getField()[43][43].getCornerLL().setHidden(true);

        m.getField()[44][44] = resourceDeck.get(23).getBack();

        m.getField()[44][42] = resourceDeck.get(24).getBack();
        m.getField()[44][42].getCornerUR().setHidden(true);

        m.getField()[42][44] = resourceDeck.get(25).getBack();

        m.getField()[45][41] = resourceDeck.get(26).getBack();
        //m.getField()[45][41].getCornerLL().setHidden(true);

        m.setxMin(42);
        m.setyMin(41);
        m.setxMax(45);
        m.setyMax(44);

        System.out.print(Tui.showManuscript(m));
    }
    @Test
    void showManuscript8() {
        Manuscript m = new Manuscript();

        m.getField()[41][41] = resourceDeck.get(21).getBack();
        m.getField()[41][41].getCornerLR().setHidden(true);

        // #2
        m.getField()[42][42] = resourceDeck.get(21).getBack();
        m.getField()[42][42].getCornerLR().setHidden(true);

        // #3
        m.getField()[43][43] = resourceDeck.get(22).getBack();
        m.getField()[43][43].getCornerLR().setHidden(true);
        m.getField()[43][43].getCornerUR().setHidden(true);
        //m.getField()[43][43].getCornerUL().setHidden(true);
        m.getField()[43][43].getCornerLL().setHidden(true);

        m.getField()[44][44] = resourceDeck.get(23).getBack();

        m.getField()[44][42] = resourceDeck.get(24).getBack();
        m.getField()[44][42].getCornerUR().setHidden(true);

        m.getField()[42][44] = resourceDeck.get(25).getBack();

        m.getField()[45][41] = resourceDeck.get(26).getBack();
        //m.getField()[45][41].getCornerLL().setHidden(true);

        m.setxMin(41);
        m.setyMin(41);
        m.setxMax(45);
        m.setyMax(44);

        System.out.print(Tui.showManuscript(m));

    }

    @Test
    void visualizeResourceCards(){
        for(var c : resourceDeck){
            out.println(toCliCard(c, true).stream().collect(Collectors.joining("\n")));
            out.println();
            out.println(toCliCard(c, false).stream().collect(Collectors.joining("\n")));
            out.println();
        }
    }

    @Test
    void visualizeGoldCards(){
        for(var c : goldDeck){
            out.println(toCliCard(c, true).stream().collect(Collectors.joining("\n")));
            out.println();
            out.println(toCliCard(c, false).stream().collect(Collectors.joining("\n")));
            out.println();
        }
    }

    @Test
    void visualizeStarterCards(){
        for(var c : starterDeck){
            out.println(toCliCard(c, true).stream().collect(Collectors.joining("\n")));
            out.println();
            out.println(toCliCard(c, false).stream().collect(Collectors.joining("\n")));
            out.println();
        }
    }

    @Test
    void visualizeObjectiveCards(){
        for(int i = 0; i < objectiveDeck.size(); i+=2){
            ArrayList<ObjectiveCard> cards = new ArrayList<>();
            cards.add(objectiveDeck.get(i));
            cards.add(objectiveDeck.get(i+1));
            out.println(showObjectives(cards));
            out.println();
        }
    }

    @Test
    void showObjectivesTest(){
        Collections.shuffle(objectiveDeck);
        ArrayList<ObjectiveCard> cards = new ArrayList<>();
        cards.add(objectiveDeck.get(0));
        cards.add(objectiveDeck.get(1));
        out.println(showObjectives(cards));
        out.println();
    }

    @Test
    void showMarketTest(){

        ResourceCard[] faceUpResources = new ResourceCard[2];
        GoldCard[] faceUpGolds = new GoldCard[2];

        faceUpResources[0] = resourceDeck.get(6);
        faceUpResources[1] = resourceDeck.get(7);

        faceUpGolds[0] = goldDeck.get(0);
        faceUpGolds[1] = goldDeck.get(1);

        Market market = new Market(resourceDeck, goldDeck, faceUpResources, faceUpGolds);

        out.println(showMarket(market));

    }

    @Test
    void showBoardTest() throws RemoteException {
        Board board = new Board();
        board.setPointsBluePlayer(10);
        board.setPointsGreenPlayer(8);
        board.setPointsRedPlayer(15);
        board.setPointsYellowPlayer(3);
        out.println(showBoard(board));
    }

    @Test
    void showStarterTest(){
        Collections.shuffle(starterDeck);
        out.println(showStarter(starterDeck.getFirst()));
        out.println();
    }

}



/*
TEST1
                                             ╭-----------------╮
                                             |                 |
                                             |        F        |
                              ╭-----------------╮              |
                              |                 |--------------╯
                              |        F        |
               ╭-----------------╮              |
               |                 |--------------╯
               |        F        |
╭-----------------╮              |
|                 |--------------╯
|        F        |
|                 |
╰-----------------╯

TEST2
╭-----------------╮           ╭-----------------╮
|                 |           |                 |
|        F        |           |        F        |
|              ╭-----------------╮           ╭-----------------╮
╰--------------|                 |-----------|                 |
               |        F        |           |        F        |
╭-----------------╮              |-----------|                 |
|                 |--------------╯           ╰-----------------╯
|        F        |           |        F        |
|              ╭-----------------╮           ╭-----------------╮
╰--------------|                 |-----------|                 |
               |        F        |           |        F        |
               |                 |           |                 |
               ╰-----------------╯           ╰-----------------╯

TEST3
╭-----------------╮           ╭-----------------╮
|                 |           |                 |
|        F        |           |        F        |
|              ╭-----------------╮           ╭-----------------╮
╰--------------|                 |-----------|                 |
               |        F        |           |        F        |
               |                 |           |                 |
               ╰-----------------╯           ╰-----------------╯

TEST4
                                                            ╭-----------------╮
                                                            |                 |
                                                            |        F        |
                                             ╭--------------|              ╭-----------------╮
                                             |              ╰--------------|                 |
                                             |        A        |           |        I        |
                                             |                 |-----------|              ╭-----------------╮
                                             ╰-----------------╯           ╰--------------|                 |
                                                            |        I        |           |        P        |
                                             ╭-----------------╮           ╭-----------------╮              |
                                             |                 |-----------|                 |--------------╯
                                             |        A        |           |        I        |
╭-----------------╮           ╭-----------------╮           ╭--------------|                 |
|                 |           |                 |-----------|              ╰-----------------╯
|        I        |           |        A        |           |        P        |
|                 |--------------╮              |           |                 |
╰-----------------╯              |--------------╯           ╰-----------------╯
               |        A        |
               |                 |
               ╰-----------------╯

TEST5
╭-----------------╮
|                P|
|        I        |
|I             ╭-----------------╮
╰--------------|                 |
               |        A        |
               |              ╭-----------------╮
               ╰--------------|                 |
                              |        A        |
                              |              ╭-----------------╮
                              ╰--------------|                 |
                                             |        A        |
                                             |              ╭-----------------╮
                                             ╰--------------|                 |
                                                            |        A        |
                                                            |                 |
                                                            ╰-----------------╯

TEST6
╭-----------------╮
|                P|
|        I        |
|I             ╭-----------------╮                                             ╭-----------------╮
╰--------------|                 |                                             |                 |
               |        A        |                                             |        A        |
               |              ╭-----------------╮               ╭--------------|                 |
               ╰--------------|                 |               |              ╰-----------------╯
                              |        A        |               |        A        |
                              |                ╭----------------|                 |
                              ╰----------------|                ╰-----------------╯
                                               |        A        |
                                               |                ╭-----------------╮
                                               ╰----------------|                 |
                                                                |        A        |
                                                                |                 |
                                                                ╰-----------------╯

TEST7
                                             ╭-----------------╮
                                             |                 |
                                             |        A        |
╭-----------------╮           ╭--------------|                 |
|                 |           |              ╰-----------------╯
|        A        |           |        A        |
|              ╭--------------|                 |
╰--------------|              ╰-----------------╯
               |        A        |
╭-----------------╮           ╭-----------------╮
|                 |-----------|                 |
|        A        |           |        A        |
|                 |           |                 |
╰-----------------╯           ╰-----------------╯

TEST8
╭-----------------╮                                         ╭-----------------╮
|                 |                                         |                 |
|        A        |                                         |        A        |
|              ╭-----------------╮           ╭--------------|                 |
╰--------------|                 |           |              ╰-----------------╯
               |        A        |           |        A        |
               |              ╭--------------|                 |
               ╰--------------|              ╰-----------------╯
                              |        A        |
               ╭-----------------╮           ╭-----------------╮
               |                 |-----------|                 |
               |        A        |           |        A        |
               |                 |           |                 |
               ╰-----------------╯           ╰-----------------╯
*/