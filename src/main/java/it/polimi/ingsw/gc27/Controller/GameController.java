package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.UpdateStartOfGameMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Model.States.InitializingState;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public class GameController implements Serializable {

    private Game game;
    private TurnHandler turnHandler;
    private int numMaxPlayers;
    private int id;

    public GameController(Game game) {
        this.game = game;
    }

    public GameController() {
    }

    public GameController(Game game, int numMaxPlayers, int id) {
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

    public int getNumMaxPlayers() {
        return numMaxPlayers;
    }


    /**
     * This method changes the player's manuscript, adding the selected card on it and possibly adding
     * points on the board and then removes the card from the player's hand.
     *
     * @param player
     * @param card
     * @param face
     * @param x
     * @param y
     */
    public void addCard(Player player, ResourceCard card, Face face, int x, int y) throws RemoteException {
        player.getPlayerState().addCard(this.game, card, face, x, y);
    }

    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        player.getPlayerState().drawCard(player, isGold, fromDeck, faceUpCardIndex);
    }

    public void chooseObjectiveCard(Player player, int objectiveCardIndex) throws RemoteException {
        player.getPlayerState().chooseObjectiveCard(this.game, objectiveCardIndex);
    }

    public void addStarterCard(Player player, StarterCard starter, Face face) throws IOException, InterruptedException {
        player.getPlayerState().addStarterCard(this.game, starter, face);
    }

    // Create a player from command line, but hand, secret objective and starter are not instantiated
    public Player initializePlayer(VirtualView client, GigaController gigaChad) throws IOException, InterruptedException {
        String username;
        String pawnColor;
        Manuscript manuscript = new Manuscript();

        // Ask for the username
        do {
            client.show("\nChoose your username: ");
            username = client.read();
        } while (!gigaChad.validUsername(username, client));


        // Ask for the pawn color
        synchronized (game.getAvailablePawns()) {
            do {
                client.show("\nChoose your color: ");
                for (PawnColour pawnColour : game.getAvailablePawns()) {
                    client.show(pawnColour.toString());
                }
                pawnColor = client.read();
            } while (!game.validPawn(pawnColor));
            game.getAvailablePawns().remove(PawnColour.fromStringToPawnColour(pawnColor));
        }


        Player p = new Player(username, manuscript, PawnColour.fromStringToPawnColour(pawnColor));

        StarterCard starterCard = this.game.getStarterDeck().removeFirst();
        p.setStarterCard(starterCard);

        // Add the player to the game
        game.addPlayer(p, client);

        // Draw the initial cards
        p.getHand().add(this.getGame().getMarket().getResourceDeck().removeFirst());
        p.getHand().add(this.getGame().getMarket().getResourceDeck().removeFirst());
        p.getHand().add(this.getGame().getMarket().getGoldDeck().removeFirst());

        // Get the secret objectives (Two cards are drawn at the beginning of the game)
        p.getSecretObjectives().add(this.getGame().getObjectiveDeck().removeFirst());
        p.getSecretObjectives().add(this.getGame().getObjectiveDeck().removeFirst());

        client.setUsername(username);

        // All players are ready
        if (game.getNumActualPlayers() == this.getNumMaxPlayers()) {
            this.turnHandler = new TurnHandler(this.game);
            for (Player player : game.getPlayers()) {
                player.setPlayerState(new InitializingState(player, this.turnHandler));
                //TODO fare bene l'addstarted e tutta la fase iniziale
                game.notifyObservers(new UpdateStartOfGameMessage(new MiniModel(player, game.getMarket(), game.getBoard()), player.getUsername()));
            }
        }

        return p;

    }

}
