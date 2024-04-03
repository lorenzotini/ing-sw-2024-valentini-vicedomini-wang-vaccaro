package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Card.StarterCard;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /*@BeforeEach
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

    Manuscript manuscript=new Manuscript(starterCard.getFront());

    Face[][] localField = manuscript.getField();
    localField[41][41]= resourceCard1.getFront();
    localField[43][43]= resourceCard2.getFront();
    localField[42][44]= resourceCard3.getFront();*/

    @Test
    void addPointsTest() {
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ResourceCard resourceCard1= resourceDeck.get(0);
        StarterCard starterCard= starterDeck.get(0);
        Manuscript manuscript=new Manuscript(starterCard.getFront());

        Game game= new Game();
        List<Player> players=new ArrayList<>();
        Board board=new Board();
        Player p1= new Player();
        Face[][] field;
        field = new Face[85][85];
        p1.setUsername("plauto");
        p1.setPawnColour(PawnColour.BLUE);
        board.setPointsBluePlayer(0);

        game.setGameID(23);
        players.add(p1);
        game.setPlayers(players);
        p1.setManuscript(manuscript);
        game.setBoard(board);
        p1.getManuscript().setField(field);

        game.addPoints(p1, 1);
        assertEquals(1, game.getBoard().getPointsBluePlayer());
        game.addPoints(p1, 3);
        assertEquals(4,game.getBoard().getPointsBluePlayer());


    }
}