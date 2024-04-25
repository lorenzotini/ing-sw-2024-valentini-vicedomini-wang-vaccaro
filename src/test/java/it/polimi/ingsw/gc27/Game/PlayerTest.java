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

        p1.setUsername("plauto");
        assertEquals("plauto",p1.getUsername());


    }
}