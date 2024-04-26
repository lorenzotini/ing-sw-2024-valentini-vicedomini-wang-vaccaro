package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.States.PlayerStates.InitializingState;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameController implements Serializable {

    private Game game;
    private TurnHandler turnHandler;
    public int getNumMaxPlayers() {
        return numMaxPlayers;
    }

    // TODO make numMaxPlayers final
    private int numMaxPlayers;
    private int id;

    public GameController(Game game) {
        this.game = game;
    }

    public GameController(){}

    public GameController(Game game, int numMaxPlayers, int id){
        this.game = game;
        this.numMaxPlayers = numMaxPlayers;
        this.id = id;
    }
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public int getId() {
        return id;
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
        player.getPlayerState().addCard(this.game, card, face, x, y);
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

    public void chooseObjectiveCard(Player player, int objectiveCardIndex){
        player.getPlayerState().chooseObjectiveCard(this.game, objectiveCardIndex);
    }

    // Create a player from command line, but hand, secret objective and starter are not instantiated
    public Player initializePlayer(VirtualView client, GigaController gigaChad) throws IOException, RemoteException, InterruptedException {
        String username;
        String pawnColor;
        Manuscript manuscript = new Manuscript();

        // Ask for the username
        do {
            client.show("Choose your username: ");
            username = client.read();
        }while(!gigaChad.validUsername(username));
        client.setUsername(username);


        // Ask for the pawn color
        synchronized (game.getAvailablePawns()){
            do {
                client.show("Choose your color: ");
                for (PawnColour pawnColour : game.getAvailablePawns()) {
                    client.show(pawnColour +"\n" );
                }
                pawnColor = client.read();
            }while(!game.validPawn(pawnColor));
            game.getAvailablePawns().remove(PawnColour.fromStringToPawnColour(pawnColor));
        }


        Player p = new Player(username, manuscript, PawnColour.fromStringToPawnColour(pawnColor));

        // Add the player to the game
        game.getPlayers().add(p);

        // Draw the initial cards
        p.getHand().add(this.getGame().getMarket().getResourceDeck().removeFirst());
        p.getHand().add(this.getGame().getMarket().getResourceDeck().removeFirst());
        p.getHand().add(this.getGame().getMarket().getGoldDeck().removeFirst());



        // TODO risolvere il problema degli attributi ricorsivi turnhandler e player
        if(game.getNumActualPlayers() == this.getNumMaxPlayers()){
            this.turnHandler = new TurnHandler(this.game);
            for(Player player : game.getPlayers()){
                player.setPlayerState(new InitializingState(player, this.turnHandler));
            }
        }

        return p;
    }
}
