package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.States.InitializingState;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.ReconnectPlayerCommand;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The GameController class is responsible for managing the overall state, flow of the game
 * and all the components that interact in the game
 * It handles player actions, game state transitions, command execution, and game suspension
 * This class works closely with the Game, Player, and TurnHandler classes to coordinate gameplay.
 * It also interacts with the GigaController who manages interactions separately in multiple games
 * and player initialization of a player in a specific game
 */
public class GameController implements Serializable {

    private GigaController console;
    private Game game;
    private TurnHandler turnHandler;
    private final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();
    private int numMaxPlayers;
    private int id;
    private long time;
    private boolean inMatch;
    private boolean suspended;
    private static final int MAX_TIME_BEFORE_CLOSING_GAME = 60000; //in milliseconds

    /**
     * Constructor for the GameController with a game instance
     * @param game The game instance to be controlled
     */
    public GameController(Game game) {
        this.game = game;
    }

//    /**
//     * Default constructor for GameController
//     */
//    public GameController() {
//    }   no usages

    /**
     * Constructor for GameController with game, number of maximum players, game id, and console.
     * @param game The game
     * @param numMaxPlayers The number of players
     * @param id The unique id for the game
     * @param console The GigaController multi game controller
     */
    public GameController(Game game, int numMaxPlayers, int id, GigaController console) {
        this.game = game;
        this.numMaxPlayers = numMaxPlayers;
        this.id = id;
        this.console = console;
        this.inMatch = true;
    }


    /**
     * This method changes the player's manuscript, adding the selected card on it and possibly adding
     * points on the board and then removes the card from the player's hand.
     *
     * @param player which player performed the action
     * @param card   which card is played
     * @param face   face up or face down
     * @param x      position index
     * @param y      position index
     */
    public void addCard(Player player, ResourceCard card, Face face, int x, int y) {
        player.getPlayerState().addCard(this.game, card, face, x, y);
    }

    /**
     * Draws a card for the player
     * @param player The player drawing the card
     * @param isGold Whether the card is a gold card
     * @param fromDeck Whether the card is drawn from the deck
     * @param faceUpCardIndex The index of the face-up card.
     * @throws InterruptedException if the operation is interrupted
     */
    public void drawCard(Player player, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        player.getPlayerState().drawCard(player, isGold, fromDeck, faceUpCardIndex);
        checkIfEndOfGameNoMoreCards();
    }

    /**
     * Allows the player to choose an objective card
     * @param player The player
     * @param objectiveCardIndex The index of the objective card
     */
    public void chooseObjectiveCard(Player player, int objectiveCardIndex) {
        player.getPlayerState().chooseObjectiveCard(this.game, objectiveCardIndex);
    }

    /**
     * Adds the starter card to the player's manuscript
     * @param player The player
     * @param starter The starter card
     * @param face The face of the card (face up or face  down)
     */
    public void addStarterCard(Player player, StarterCard starter, Face face) {
        player.getPlayerState().addStarterCard(this.game, starter, face);
    }

    /**
     * Suspends a player by marking them as disconnected and handling their disconnection
     * @param player The player to suspend
     */
    public void suspendPlayer(Player player) {
        player.setDisconnected(true);
        try {
            turnHandler.handleDisconnection(player, this);
        } catch (NullPointerException | InterruptedException e) {

        }
    }

    private void checkIfEndOfGameNoMoreCards(){
        // check if there are no more cards in decks and face up cards in market
        if(game.isMarketEmpty()){
            turnHandler.triggerEndingGameDueToNoMoreCards();
        }
    }

    // Create a player from command line, but hand, secret objective and starter are not instantiated
    /**
     * Initializes a player from the command line, without instantiating hand, secret objective, and starter card
     * @param client The client view
     * @param gigaChad The GigaController multi game controller
     */
    public void initializePlayer(VirtualView client, GigaController gigaChad) {
        String username;
        String pawnColor;
        PawnColour pawnColourSelected;
        Manuscript manuscript = new Manuscript();

        // Ask for the username
        boolean flag = false;
        try {
            do {
                if (flag) {
                    client.update(new KoMessage("invalidUsername"));
                }
                client.show("\nChoose your username: ");
                username = client.read();
                flag = true;
            } while (!gigaChad.validUsername(username, client));
        } catch (IOException e) {
            System.out.println("Disconnected player before choosing a username, he'll be removed");
            synchronized (game.getPlayers()) {
                game.setNumActualPlayers(game.getNumActualPlayers() - 1);
            }
            return;
        }
        // Ask for the pawn color
        synchronized (game.getAvailablePawns()) {
            try {
                do {
                    client.show("\nChoose your color: ");
                    for (PawnColour pawnColour : game.getAvailablePawns()) {
                        client.show(pawnColour.toString());
                        client.update(new OkMessage("Choose your color:" + pawnColour.toString()));
                    }
                    pawnColor = client.read();
                } while (!game.validPawn(pawnColor));
                pawnColourSelected = PawnColour.fromStringToPawnColour(pawnColor);
                game.getBoard().colourPlayerMap.put(username, pawnColourSelected);
                game.getAvailablePawns().remove(pawnColourSelected);
            } catch (IOException e) {
                System.out.println("Disconnected player before choosing a color");
                synchronized (game.getPlayers()) {
                    game.setNumActualPlayers(game.getNumActualPlayers() - 1);
                }
                return;
            }
        }


        Player p = new Player(username, manuscript, pawnColourSelected);

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

        try {
            client.setUsername(username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // All players are ready
        if (game.ready(p) == numMaxPlayers) {
            this.turnHandler = new TurnHandler(this.game);
            game.getBoard().initBoard(game.getPlayers());
            for (Player player : game.getPlayers()) {
                player.setPlayerState(new InitializingState(player, this.turnHandler));
                game.notifyObservers(new UpdateStartOfGameMessage(new MiniModel(player, game), ""));
            }
        }
    }

    /**
     * Sends a chat message
     * @param chatMessage The chat message
     */
    public void sendChatMessage(ChatMessage chatMessage) {
        try {
            Chat chat;
            if (chatMessage.getReceiver().equalsIgnoreCase("global")) {
                synchronized (game.getGeneralChat()) {
                    chat = game.getGeneralChat();
                    chat.addChatMessage(chatMessage);
                }
                game.notifyObservers(new UpdateChatMessage(chat));
            } else {
                synchronized (game.getGeneralChat()) {

                    chat = game.getChat(chatMessage.getSender(), chatMessage.getReceiver());
                    chat.addChatMessage(chatMessage);
                    //todo: username corretto
                }
                game.notifyObservers(new UpdateChatMessage(chat, game.getPlayer(chatMessage.getSender()), chatMessage.getReceiver()));
            }
        } catch (NullPointerException e) {
            game.notifyObservers(new GenericErrorMessage("There is no one with that name",new MiniModel(getPlayer(chatMessage.getSender()))));
        }
    }

    /**
     * Adds a command to the command queue
     * @param command The command
     */
    public void addCommand(Command command) {
        commands.add(command);
    }

    /**
     * Executes commands from the command queue in a separate thread
     */
    public void executeCommands() {

        new Thread(() -> {
            while (inMatch) {
                try {
                    commands.take().execute(this);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("This thread has been closed");
        }).start();
    }

    /**
     * Suspends the game, waiting for players to reconnect or the game to be closed
     * @throws InterruptedException if the operation is interrupted
     */
    public void suspendGame() throws InterruptedException {
        Command command = null;
        suspended = true;

        new Thread(() -> {
            time = System.currentTimeMillis();
            while (suspended) {
                long timeConfront = System.currentTimeMillis();
                synchronized (this) {

                    if (timeConfront - time > MAX_TIME_BEFORE_CLOSING_GAME) {
                        //todo: non riconnnessione 2 gioc
                        System.out.println("The game: " + id + " has been closed");
                        suspended = false;
                        inMatch = false;
                        console.closeGame(this);
                    }
                }
            }
        }).start();

        System.out.println("the game has been suspended");
        game.notifyObservers(new SuspendedGameMessage("The game has been suspended"));
        do {
            try {
                command = commands.take();
            } catch (InterruptedException e) {
                throw new RuntimeException("System exception, taking from the commands queue");
            }

            synchronized (this) {
                if (command instanceof ReconnectPlayerCommand) {
                    command.execute(this);
                    if (!game.isSuspended()) {
                        game.notifyObservers(new ContinueGameMessage(new MiniModel(game)));
                        suspended = false;
                        System.out.println("The game has been restored");
                    }

                } else {
                    game.notifyObservers(new SuspendedGameMessage("The game has been suspended, wait for someone to comeback"));
                }
            }
        } while (!(command instanceof ReconnectPlayerCommand));
    }

    /**
     * Gets the game instance
     * @return The game instance
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets the turn handler
     * @return The turn handler
     */
    public TurnHandler getTurnHandler() {
        return turnHandler;
    }
    public void setTurnHandler(TurnHandler turnHandler) {this.turnHandler = turnHandler;}

//    /**
//     * Sets the current game
//     * @param game The game
//     */
//    public void setGame(Game game) {
//        this.game = game;
//    }    no usages

    /**
     * Gets the unique id of the game
     * @return The unique id of the game
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the maximum number of players
     * @return The maximum number of players
     */
    public int getNumMaxPlayers() {
        return numMaxPlayers;
    }

    /**
     * Gets a player by their username
     * @param username The username of the player
     * @return The player with the specified username.
     */
    public Player getPlayer(String username) {
        return getGame().getPlayer(username);
    }
    public BlockingQueue<Command> getCommands() {
        return commands;
    }


}
