package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.SuspendedGameMessage;
import it.polimi.ingsw.gc27.Messages.UpdateChatMessage;
import it.polimi.ingsw.gc27.Messages.KoMessage;
import it.polimi.ingsw.gc27.Messages.OkMessage;
import it.polimi.ingsw.gc27.Messages.UpdateStartOfGameMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Model.States.InitializingState;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.ReconnectPlayerCommand;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameController implements Serializable {

    private Game game;
    boolean suspended;
    private TurnHandler turnHandler;
    private int numMaxPlayers;
    private int id;
    private final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();
    private GigaController console;

    long time;

    public GameController(Game game) {
        this.game = game;
    }

    public GameController() {
    }

    public GameController(Game game, int numMaxPlayers, int id, GigaController console) {
        this.game = game;
        this.numMaxPlayers = numMaxPlayers;
        this.id = id;
        this.console = console;
    }

    public Game getGame() {
        return game;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
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
    public void addCard(Player player, ResourceCard card, Face face, int x, int y) {
        player.getPlayerState().addCard(this.game, card, face, x, y);
    }

    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        player.getPlayerState().drawCard(player, isGold, fromDeck, faceUpCardIndex);
    }

    public void chooseObjectiveCard(Player player, int objectiveCardIndex) {
        player.getPlayerState().chooseObjectiveCard(this.game, objectiveCardIndex);
    }

    public void addStarterCard(Player player, StarterCard starter, Face face) {
        player.getPlayerState().addStarterCard(this.game, starter, face);
    }

    public void suspendPlayer(Player player) {
        player.setDisconnected(true);
        turnHandler.handleDisconnection(player, this);
    }

    // Create a player from command line, but hand, secret objective and starter are not instantiated
    public void initializePlayer(VirtualView client, GigaController gigaChad) throws IOException, InterruptedException {
        String username;
        String pawnColor;
        Manuscript manuscript = new Manuscript();

        // Ask for the username
        boolean flag = false;
        do {
            if(flag){
                client.update(new KoMessage("invalidUsername"));
            }
            client.show("\nChoose your username: ");
            username = client.read();
            flag = true;
        } while (!gigaChad.validUsername(username, client));
        client.update(new OkMessage("validUsername"));


        // Ask for the pawn color
        synchronized (game.getAvailablePawns()) {
            do {
                client.show("\nChoose your color: ");
                for (PawnColour pawnColour : game.getAvailablePawns()) {
                    client.show(pawnColour.toString());
                    //client.update(new ColorMessage());
                }
                pawnColor = client.read();
            } while (!game.validPawn(pawnColor));
            game.getAvailablePawns().remove(PawnColour.fromStringToPawnColour(pawnColor));
            //todo: far visualizzare solo i PawnColour disponibili nella gui
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
                game.notifyObservers(new UpdateStartOfGameMessage(new MiniModel(player, game.getMarket(), game.getBoard()), player.getUsername()));
            }
            client.update(new OkMessage("thegamecanstart")); //for gui
        }

    }

    public void sendChatMessage(ChatMessage chatMessage) {
        Chat chat;
        if (chatMessage.getReceiver() == null) {
            chat = game.getGeneralChat();
            chat.addChatMessage(chatMessage);
            game.notifyObservers(new UpdateChatMessage(chat));
        } else {
            chat = game.getChat(chatMessage.getSender(), chatMessage.getReceiver());
            chat.addChatMessage(chatMessage);
            game.notifyObservers(new UpdateChatMessage(chat, chatMessage.getSender(), chatMessage.getReceiver().getUsername()));
        }

    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void executeCommands() {

        new Thread(() -> {
            while (this != null) {
                try {
                    commands.take().execute(this);
                } catch (InterruptedException e) {
                    //TODO eventuale non so se va gestito
                }
            }
        }).start();
    }

    public Player getPlayer(String username) {
        return getGame().getPlayer(username);
    }

    public void suspendGame()  {
        Command command = null;
        suspended = true;

        new Thread(() -> {
            time = System.currentTimeMillis();
            while (suspended) {
                long timeConfront = System.currentTimeMillis();
                synchronized (this) {

                    if (timeConfront - time > 60000) {
                        System.out.println("The game: " + id + " has been closed");
                        suspended = false;
                        console.closeGame(this);
                    }
                }
            }
        }).start();

        do {
            try{
                command = commands.take();
            }catch (InterruptedException e ){

            }

            synchronized (this){
                if (command instanceof ReconnectPlayerCommand) {
                    command.execute(this);
                    suspended = false;

                } else {
                    System.out.println("the game has been suspended");
                    game.notifyObservers(new SuspendedGameMessage("The game has been suspended"));
                }
            }
        } while (!(command instanceof ReconnectPlayerCommand));
    }
}
