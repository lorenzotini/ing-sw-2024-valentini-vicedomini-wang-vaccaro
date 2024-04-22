package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void addCardTest() {

        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);

        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ResourceCard resourceCard1= resourceDeck.get(0);
        StarterCard starterCard= starterDeck.get(0);
        Manuscript manuscript=new Manuscript();

        Game game= new Game();
        List<Player> players=new ArrayList<>();
        Board board=new Board();
        Player p1= new Player();
        //Face[][] field;
        //field = new Face[85][85];
        p1.setUsername("plauto");
        p1.setPawnColour(PawnColour.BLUE);
        board.setPointsBluePlayer(0);


        game.setGameID(23);
        players.add(p1);
        game.setPlayers(players);
        p1.setManuscript(manuscript);
        game.setBoard(board);
        //p1.getManuscript().setField(field);


        p1.addCard(game, resourceCard1, resourceCard1.getFront(), 43,43);
        System.out.println("test 43,43 ok");
        assertTrue(p1.getManuscript().getField()[42][42].getCorner(1,-1).isHidden());
        assertEquals(resourceCard1.getFront(), manuscript.getField()[43][43]);
        System.out.println("test 43,43 ok");

        p1.addCard(game, resourceCard1, resourceCard1.getFront(), 41,41);
        assertEquals(resourceCard1.getFront(), manuscript.getField()[41][41]);
        System.out.println("test 41,41 ok");

        p1.addCard(game, resourceCard1, resourceCard1.getFront(), 43,41);
        assertEquals(resourceCard1.getFront(), manuscript.getField()[43][41]);
        System.out.println("test 3 ok");

        p1.addCard(game, resourceCard1, resourceCard1.getFront(), 41,43);
        assertEquals(resourceCard1.getFront(), manuscript.getField()[41][43]);
        System.out.println("test 4 ok");

    }
}