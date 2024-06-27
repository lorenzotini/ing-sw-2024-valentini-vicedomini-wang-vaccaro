package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.KoMessage;
import it.polimi.ingsw.gc27.Messages.OkMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Commands.Command;
import it.polimi.ingsw.gc27.Commands.ReconnectPlayerCommand;
import it.polimi.ingsw.gc27.Commands.SuspendPlayerCommand;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GigaController class manages multiple game controllers and handles
 * player connections, disconnections, and game creation
 */
public class GigaController {

    private int idCounter = 0;

    private static final int MAX_USERNAME_LENGTH = 20;

    private final int NUM_MAX_PLAYERS = 4;

    private final int NUM_MIN_PLAYERS = 2;

    private final Map<String, VirtualView> registeredUsernames = new HashMap<>();

    private final List<GameController> gameControllers = new ArrayList<>();

    /**
     * @return the list of game controllers.
     */
    public List<GameController> getGameControllers() {
        return gameControllers;
    }

    /**
     * Finds the game controller that controls the game containing the player matching the given username
     * @param username the username to search for
     * @return the game controller associated with the username, or null if not found.
     */
    public GameController userToGameController(String username) {
        synchronized (gameControllers) {
            for (var c : gameControllers) {
                if (c.getGame().getPlayers().stream().anyMatch(p -> p.getUsername().equals(username))) {
                    return c;
                }
            }
        }
        return null;
    }


    /**
     * Removes references to a disconnected client and sets the player's state to disconnected
     * @param client the client's VirtualView
     */
    public void removeReferences(VirtualView client) {

        try {
            String username = getUsername(client);
            userToGameController(username).addCommand(new SuspendPlayerCommand(username));
            //registeredUsernames.remove(username);
        } catch (NullPointerException e) {
            System.out.println("Client hadn't choose an username yet");
            System.out.println("Caught while suspending player " );
        }
    }

    /**
     * Welcomes a new player and allows them to start or join an already existing game
     * handles the case when the player disconnects and the case when the game is full
     * @param client the client's VirtualView
     */
    public void welcomePlayer(VirtualView client) {
        String game;
        try {
            client.update(new OkMessage("Connected to the server!"));
            client.show("\nWelcome to Codex Naturalis\n" + "\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)");

            // check if the input is a valid game id or 'new'
            game = client.read();
        }catch(IOException e){
            return;
        }
        boolean canEnter = false;

        if (game.equalsIgnoreCase("new")) {    // start a new game
            createNewGame(client);
        } else {

            // join an existing game
            do {
                if (game.equalsIgnoreCase("new")) {    // start a new game
                    createNewGame(client);
                    break;
                } else {
                    try {
                        Integer.parseInt(game);
                    } catch (NumberFormatException e) {
                        try {
                            client.show("\nInvalid input. Please enter a valid game id or 'new' to start a new game");
                            client.update(new KoMessage("invalidFormatID"));
                            game = client.read();
                        } catch (IOException es) {
                            System.out.println("Connection lost after trying to join a game (110)");
                            return;
                        }
                        continue;
                    }
                    GameController gc = null;
                    synchronized (gameControllers) {
                        for (var control : gameControllers) {
                            if (control.getId() == Integer.parseInt(game)) {
                                gc = control;
                            }
                        }
                    }

                    if (gc != null) {
                        try {
                            client.show("\nJoining game " + game + "...");
                        } catch (IOException e) {
                            System.out.println("Connection lost after trying to join a game (110)");
                        }
                        synchronized (gc.getGame().getPlayers()) {

                            if (gc.getGame().getNumActualPlayers() < gc.getNumMaxPlayers()) { // game not full, can join

                                int a = gc.getGame().getNumActualPlayers();
                                gc.getGame().setNumActualPlayers(a + 1);
                                canEnter = true;
                                try {
                                    client.update(new OkMessage("validID"));
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }

                            } else if (gc.getGame().getNumActualPlayers() == gc.getNumMaxPlayers() &&      // game full, but a disconnected player can rejoin
                                    gc.getGame().getPlayers().stream().anyMatch(Player::isDisconnected)) {
                                String disconnectedUsername;
                                try {
                                    client.show("\nThis game has a disconnected player. Are you him? If so, please enter your username.");
                                    client.update(new OkMessage("disconnectedPlayer"));
                                    disconnectedUsername = client.read();
                                } catch (IOException e) {
                                    System.out.println("Connection lost before reconnecting a game (123)");
                                    return;
                                }
                                // If the player is found, reconnect him, else null is returned
                                try {

                                    boolean clientReconnected = tryReconnectPlayer(client, gc, disconnectedUsername);
                                    if (clientReconnected) {
                                        client.update(new OkMessage("playerReconnected"));
                                        return;
                                    } else {
                                        client.show("invalid username");
                                        client.update(new KoMessage("Wrong username"));
                                        welcomePlayer(client);
                                        return;
                                    }
                                } catch (IOException e) {
                                    System.out.println("Connection lost before reconnecting a game (155)");
                                    return;
                                }

                            } else {
                                try {
                                    client.show("\nGame is full. Restarting...");
                                    client.update(new KoMessage("gameFull"));
                                } catch (IOException e) {
                                    System.out.println("Connection lost before entering game (164)");
                                    return;
                                }
                            }
                        }
                        if (!canEnter) {
                            welcomePlayer(client);
                            return;
                        } else {
                            gc.initializePlayer(client, this);
                            return;
                        }
                    }
                    try {
                        client.show("\nGame not found. Please enter a valid game id or 'new' to start a new game");
                        client.update(new KoMessage("invalidID"));
                        game = client.read();
                    } catch (IOException e) {
                        System.out.println("Connection lost before entering game (131)");
                        return;
                    }
                    if (game.equalsIgnoreCase("new")) {
                        createNewGame(client);
                        return;
                    }
                }
                } while (true) ;

        }

    }

    /**
     * Creates a new game and registers the player as the first participant,
     * sets the number of players (2-4) and displays the id of the game created
     * @param client the client's VirtualView
     */
    public void createNewGame(VirtualView client) {
        int numMaxPlayers;
        try {

            client.show("\nHow many players there will be? (2-4)");

            do {
                try {
                    numMaxPlayers = Integer.parseInt(client.read());
                    if (numMaxPlayers <= NUM_MAX_PLAYERS && numMaxPlayers >= NUM_MIN_PLAYERS) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    client.show("\nInvalid format. Please enter a number between 2 and 4");
                    continue;
                }
                client.show("\nInvalid input. Please enter a number between 2 and 4");
            } while (true);
        }catch(IOException e){
            System.out.print("Lost before creating a game");
            return;
        }
        GameController controller;
        Initializer init = new Initializer();
        synchronized (gameControllers) {
            controller = new GameController(init.initialize(), numMaxPlayers, this.idCounter++, this);
            gameControllers.add(controller);
        }
        // count the player who created the game
        controller.getGame().setNumActualPlayers(1);
        try{
            client.update(new OkMessage("Game created with id " + controller.getId() + " Waiting for players to join..."));
            client.show("\nGame created with id " + controller.getId() + "\n" + "\nWaiting for players to join...");

        }catch(IOException e){
            System.out.print("Lost before knowing id game");
            return;
        }
        controller.initializePlayer(client, this);
        controller.executeCommands();
    }

    /**
     * Tries to reconnect a player to a game.
     * @param client the client's VirtualView.
     * @param gc the game controller.
     * @param disconnectedUsername the username of the disconnected player.
     * @return true if the player was successfully reconnected, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    private boolean tryReconnectPlayer(VirtualView client, GameController gc, String disconnectedUsername) throws IOException {

        for (Player p : gc.getGame().getPlayers()) {
            if (p.getUsername().equals(disconnectedUsername) && p.isDisconnected()) {
                client.setUsername(p.getUsername());
                registeredUsernames.put(p.getUsername(), client);
                gc.addCommand(new ReconnectPlayerCommand(client, p));
                return true;
            }
        }
        System.out.println("Player not found");
        return false;
    }


    /**
     * Checks the validity and availability of a player's username
     * @param username the username to validate
     * @param view the client's VirtualView
     * @return true if the username is valid and not taken, false otherwise
     */
    public boolean validUsername(String username, VirtualView view) {
        if(username.equalsIgnoreCase("global") || username.contains(" ") || username.length() > MAX_USERNAME_LENGTH)
            return false;
        synchronized (registeredUsernames) {
            if (registeredUsernames.containsKey(username) || username.isEmpty()) { // username already taken or empty
                return false;
            }
            registeredUsernames.put(username, view);
            return true;
        }
    }

    /**
     * Gets the VirtualView associated with a username
     * @param user the username
     * @return the VirtualView associated with the username
     */
    public VirtualView getView(String user) {
        return registeredUsernames.get(user);
    }

    /**
     * Gets the map of registered usernames and their associated VirtualViews
     * @return the map of registered usernames and VirtualViews
     */
    public Map<String, VirtualView> getRegisteredUsernames() {
        return registeredUsernames;
    }

    /**
     * Gets the username associated with a client's VirtualView
     * @param client the client's VirtualView
     * @return the username associated with the client's VirtualView
     */
    public String getUsername(VirtualView client) {
        for (String username : registeredUsernames.keySet()) {
            if (registeredUsernames.get(username).equals(client)) {
                return username;
            }
        }
        return "";
    }

    /**
     * Gets the Player instance associated with its username
     * @param username the username
     * @return the Player
     */
    public Player getPlayer(String username) {
        return this.userToGameController(username).getGame().getPlayer(username);
    }

    /**
     * Adds a command to the game controller of the player associated with the command
     * @param command the command to add
     */
    public void addCommandToGameController(Command command) {
        String player = command.getPlayerName();
        try{
            userToGameController(player).addCommand(command);
        }catch(NullPointerException e){
            System.out.println("Didn't find controller while adding command ");
        }
    }

    /**
     * Closes a game and removes all references to its players and controller
     * @param controller the game controller to close
     */
    public void closeGame(GameController controller) {
        synchronized (gameControllers) {
            for(Player p :controller.getGame().getPlayers()){
                registeredUsernames.remove(p.getUsername());
            }
            gameControllers.remove(controller);
        }
    }

}
