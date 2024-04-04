package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.Board;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Manuscript;
import it.polimi.ingsw.gc27.Game.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DifferentPatternTest {

    @Test
    void calculateObjectivePoints() {
            //import parser
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
            //start initializer
            //creation of the game
        GameController controller=new GameController();
        Game game= new Game();
        game.setGameID(13);
        controller.setGame(game);
        Board board=new Board();
        game.setBoard(board);
        List<Player> players=new ArrayList<>();
        game.setPlayers(players);

            //player1
        Player p1= new Player();
        p1.setUsername("Olivia");
        p1.setPawnColour(PawnColour.BLUE);
        StarterCard starterCard2= starterDeck.get(0);
        Manuscript manuscript=new Manuscript(starterCard2.getFront());
        p1.setManuscript(manuscript);

        List<ResourceCard> hand1=new ArrayList<>();
        ResourceCard resourceCard1_p1= resourceDeck.get(24);
        ResourceCard resourceCard2_p1 = resourceDeck.get(16);
        ResourceCard resourceCard3_p1 = resourceDeck.get(34);
        ResourceCard goldCard_p1 = goldDeck.get(0);
        p1.getHand().add(resourceCard1_p1);
        p1.getHand().add(resourceCard2_p1);
        p1.getHand().add(goldCard_p1);
        board.setPointsBluePlayer(0);
        players.add(p1);





    }
}