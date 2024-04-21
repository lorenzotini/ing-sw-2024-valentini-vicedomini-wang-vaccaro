package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Manuscript;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameController implements Serializable {

    private Game game;
    public GameController(Game game) {
        this.game = game;
    }
    public GameController(){}
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * This method changes the player's manuscript, adding the selected card on it and possibly adding
     * points on the board and then removes the card from the player's hand.
     * @param player
     * @param card
     * @param face
     * @param x
     * @param y
     */
    public void addCard(Player player, ResourceCard card, Face face, int x, int y)  {
        if(player.getManuscript().isValidPlacement(x, y) && ((face instanceof FrontFace && player.getManuscript().satisfiedRequirement((ResourceCard) card)) || (face instanceof BackFace))){
            player.getPlayerState().addCard(this.game, card, face, x, y);

        }else{
            System.err.println("Error: invalid position");
        }
    }

    public void addStarterCard(Player player, StarterCard card, Face face){
        player.getPlayerState().addStarterCard(this.game, card, face, Manuscript.FIELD_DIM/2, Manuscript.FIELD_DIM/2);
    }

    // TODO: I DUE METODI SONO UGUALI, MAGARI CI PUO' ANDARE UN DESIGN PATTERN
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex){
        player.getPlayerState().drawResourceCard(player, fromDeck, faceUpCardIndex, this.game);
    }

    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex){
        player.getPlayerState().drawGoldCard(player, fromDeck, faceUpCardIndex, this.game);
    }

    // Create a player from command line, but hand, secret objective and starter are not instantiated
    public Player welcomePlayer(VirtualView client) throws RemoteException {
        String username = "";
        String pawnColor = "";
        Manuscript manuscript = new Manuscript();

        // Ask for the username
        do {
           client.show("Choose your username: ");
            username = client.read();
        }while(!game.validUsername(username));

        // Ask for the pawn color
        do {
            client.show("Choose your color: ");
            for (PawnColour pawnColour : game.getAvailablePawns()) {
                client.show(pawnColour + " ");
            }
            pawnColor = client.read();
        }while(!game.validPawn(pawnColor));

        Player p = new Player(username, manuscript, PawnColour.fromStringToPawnColour(pawnColor));

        game.getPlayers().add(p);
        game.getAvailablePawns().remove(PawnColour.fromStringToPawnColour(pawnColor));

        this.drawResourceCard(p, true, 0);
        this.drawResourceCard(p, true, 0);
        this.drawGoldCard(p, true, 0);

        // TODO: PER ADESSO INIZIALIZZO CON UN SOLO OBIETTIVO, POI DOVRA' AVERNE DUE E SCEGLIERE
        p.setSecretObjective(game.getObjectiveDeck().removeLast());

        // TODO: ORA LA METTE FRONT, POI DOVRA' SCEGLIERE QUALE LATO GIOCARE
        StarterCard starter = game.getStarterDeck().removeLast();
        this.addStarterCard(p, starter, starter.getFront());

        return p;
    }
}
