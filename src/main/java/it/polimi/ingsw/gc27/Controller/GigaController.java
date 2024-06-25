package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.ClosingGameMessage;
import it.polimi.ingsw.gc27.Messages.KoMessage;
import it.polimi.ingsw.gc27.Messages.OkMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.ReconnectPlayerCommand;
import it.polimi.ingsw.gc27.Net.Commands.SuspendPlayerCommand;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GigaController {

    private int idCounter = 0;

    private final Map<String, VirtualView> registeredUsernames = new HashMap<>();

    private final List<GameController> gameControllers = new ArrayList<>();

    public List<GameController> getGameControllers() {
        return gameControllers;
    }


    public GameController userToGameController(String username) {
        synchronized (gameControllers) {
            for (var c : gameControllers) {
                if (c.getGame().getPlayers().stream().anyMatch(p -> p.getUsername().equals(username))) {
                    return c;
                }
            }
        }

        return null;//TODO sarebbe bene tornare un eccezione
    }


      // Remove refs and set player state to disconnected
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
//                            if (player.isDisconnected ) {
//                                return;
//                            }
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

    public void createNewGame(VirtualView client) {
        int numMaxPlayers;
        try {

            client.show("\nHow many players there will be? (2-4)");

            do {
                try {
                    numMaxPlayers = Integer.parseInt(client.read());
                    if (numMaxPlayers <= 4 && numMaxPlayers >= 1) {
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
            client.update(new OkMessage("\nGame created with id " + controller.getId() + "\n" + "\nWaiting for players to join..."));
            client.show("\nGame created with id " + controller.getId() + "\n" + "\nWaiting for players to join...");

        }catch(IOException e){
            System.out.print("Lost before knowing id game");
            return;
        }
        controller.initializePlayer(client, this);
        controller.executeCommands();
    }

    private boolean tryReconnectPlayer(VirtualView client, GameController gc, String disconnectedUsername) throws IOException {

        for (Player p : gc.getGame().getPlayers()) {
            if (p.getUsername().equals(disconnectedUsername) && p.isDisconnected()) {
                client.setUsername(p.getUsername());
                registeredUsernames.replace(p.getUsername(), client);
                gc.addCommand(new ReconnectPlayerCommand(client, p));
                return true;
            }
        }

        System.out.println("Player not found");
        return false;

    }


    public boolean validUsername(String u, VirtualView view) {
        if(u.equalsIgnoreCase("global") || u.isEmpty())
            return false;
        synchronized (registeredUsernames) {
            if (registeredUsernames.containsKey(u)) { // username already taken or empty
                return false;
            }
            registeredUsernames.put(u, view);
            return true;
        }
    }

    public VirtualView getView(String user) {
        return registeredUsernames.get(user);
    }

    public Map<String, VirtualView> getRegisteredUsernames() {
        return registeredUsernames;
    }

    public String getUsername(VirtualView client) {
        for (String username : registeredUsernames.keySet()) {
            if (registeredUsernames.get(username).equals(client)) {
                return username;
            }
        }
        return "";
    }

    public Player getPlayer(String username) {
        return this.userToGameController(username).getGame().getPlayer(username);
    }

    public void addCommandToGameController(Command command) {
        String player = command.getPlayerName();
        try{
            userToGameController(player).addCommand(command);
        }catch(NullPointerException e){
            //this happens only if the player is not created yet, and the server want to give the SuspendPlayerCommand,
            //but it's not required because the player doesn't exit
        }
    }

    public void closeGame(GameController controller) {
        synchronized (gameControllers) {
            //controller.getGame().notifyObservers(new ClosingGameMessage("The game has been closed because it's been suspended for too long"));
            for(Player p :controller.getGame().getPlayers()){
                registeredUsernames.remove(p.getUsername());
            }
            gameControllers.remove(controller);
        }
    }

}
