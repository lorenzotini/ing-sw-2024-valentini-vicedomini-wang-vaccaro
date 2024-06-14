package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.States.ChooseObjectiveState;
import it.polimi.ingsw.gc27.Model.States.EndingState;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

class TurnHandlerTest {
    private static GameController gc1;
    private static Game g1;
    private static Player p1;
    private static Player p2;
    private static Player p3;
    private static Player p4;
    private static ArrayList<Player> players1;

    private static Market market;

    private static ArrayList<StarterCard> starterDeck;
    private static ArrayList<ResourceCard> resourceDeck;
    private static ArrayList<ObjectiveCard> objectiveDeck;
    private static ArrayList<GoldCard> goldDeck;
    private static ResourceCard[] faceUpResources;
    private static GoldCard[] faceUpGolds;

    private static TurnHandler turnHandler;
    private static ArrayList<ObjectiveCard> secretObjectives;

    public  void initializeGame() {

        players1 = new ArrayList<>();
        g1 = new Game(1, new Board(), players1);
        gc1 = new GameController(g1);


        // generate decks
        starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);

        // create players and add them to the game.
        p1 = new Player("Giocatore 1", new Manuscript(), PawnColour.RED);
        p1.setHand(new ArrayList<ResourceCard>());
        p2 = new Player("Giocatore 2", new Manuscript(), PawnColour.GREEN);
        p2.setHand(new ArrayList<ResourceCard>());
        p3 = new Player("Giocatore 3", new Manuscript(), PawnColour.BLUE);
        p3.setHand(new ArrayList<ResourceCard>());
        p4 = new Player("Giocatore 4", new Manuscript(), PawnColour.YELLOW);
        p4.setHand(new ArrayList<ResourceCard>());

        players1.add(p1);
        players1.add(p2);
        players1.add(p3);
        players1.add(p4);

        faceUpResources = new ResourceCard[2];
        faceUpGolds= new GoldCard[2];
        faceUpResources[0]= resourceDeck.get(0);
        faceUpResources[1]= resourceDeck.get(1);
        faceUpGolds[0]=goldDeck.get(0);
        faceUpGolds[1]=goldDeck.get(1);
        g1.setMarket(market);

        /*secretObjectives=new ArrayList<>();
        secretObjectives.add(objectiveDeck.get(0));
        secretObjectives.add(objectiveDeck.get(1));
        p1.setSecretObjectives(secretObjectives);
           */

        turnHandler=new TurnHandler(g1);

        // create game and its controller


        ChooseObjectiveState state=new ChooseObjectiveState(p1, turnHandler);
        p1.setPlayerState(state);
        /*
        Collections.shuffle(resourceDeck);
        Collections.shuffle(goldDeck);
        Collections.shuffle(objectiveDeck);
        */
    }

    @Test
    void notifyChooseObjectiveState() {

    }

    @Test
    void notifyInitializingState() {
    }

    @Test
    void notifyEndOfTurnStateTest() throws RemoteException {
        initializeGame();
        g1.getBoard().setPointsBluePlayer(20);
        turnHandler.notifyEndOfTurnState(p1);
    }

    @Test
    void notifyCalculateObjectivePoints() throws RemoteException {
        initializeGame();
        p1.setPlayerState(new EndingState(p1, turnHandler));
        p2.setPlayerState(new EndingState(p2, turnHandler));
        p3.setPlayerState(new EndingState(p3, turnHandler));
        p4.setPlayerState(new EndingState(p4, turnHandler));
        secretObjectives=new ArrayList<>();
        secretObjectives.add(objectiveDeck.get(0));

        //turnHandler.notifyCalculateObjectivePoints(p1);

    }
}