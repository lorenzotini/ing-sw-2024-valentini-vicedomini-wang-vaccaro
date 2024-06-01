package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Model.Game.Game;
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


    public GameController userToGameController(String username) {
        synchronized (gameControllers){
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
            registeredUsernames.remove(username);
        } catch (NullPointerException e) {
            System.out.println("Client hadn't choose an username yet");
            System.out.println("NullPointerException caught while suspending player: " + e.getMessage());
        }

    }

    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {

        client.show("\nWelcome to Codex Naturalis\n" + "\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)");

        // check if the input is a valid game id or 'new'
        String game;
        int gameId = 0;
        while(true){
            game = client.read();
            if(game.equalsIgnoreCase("new")){
                break;
            } else {
                try {
                    gameId = Integer.parseInt(game);
                    break;
                } catch (NumberFormatException e) {
                    client.show("\nInvalid input. Please enter a valid game id or 'new' to start a new game");
                    client.update(new KoMessage("invalidFormat"));
                }
            }
        }

        boolean canEnter = false;


        if (game.equalsIgnoreCase("new")) {    // start a new game
            createNewGame(client);
        } else {


            // join an existing game
            do {
                GameController gc = null;
                synchronized (gameControllers){
                    for (var control : gameControllers) {
                        if (control.getId() == gameId) {
                            gc = control;
                        }
                    }
                }

                // TODO controllare che game sia convertibile in int

                // TODO non so se è meglio togliere questa show
                if(gc!= null) {
                    client.show("\nJoining game " + game + "...");
                    client.update(new OkMessage("validID"));
                    synchronized (gc.getGame().getPlayers()) {

                        if (gc.getGame().getNumActualPlayers() < gc.getNumMaxPlayers()) { // game not full, can join

                            int a = gc.getGame().getNumActualPlayers();
                            gc.getGame().setNumActualPlayers(a + 1);
                            canEnter = true;

                        } else if (gc.getGame().getNumActualPlayers() == gc.getNumMaxPlayers() &&      // game full, but a disconnected player can rejoin
                                gc.getGame().getPlayers().stream().anyMatch(Player::isDisconnected)) {

                            client.show("\nThis game has a disconnected player. Are you him? If so, please enter your username.");
                            String disconnectedUsername = client.read();

                            // If the player is found, reconnect him, else null is returned
                            boolean clientReconnected = tryReconnectPlayer(client, gc, disconnectedUsername);

                            if (clientReconnected) {
                                return;
                            }

                        } else {

                            client.show("\nGame is full. Restarting...");
                        }
                    }

                    if (!canEnter) {
                        welcomePlayer(client);
                    } else {
//                            if (player.isDisconnected ) {
//                                return;
//                            }
                        gc.initializePlayer(client, this);
                        return;
                    }
                }
                else {
                    client.show("\nGame not found. Please enter a valid game id or 'new' to start a new game");
                    client.update(new KoMessage("invalidID"));
                    game = client.read();

                    if (game.equalsIgnoreCase("new")) {
                        createNewGame(client);
                        return;
                    }
                }
            } while (true);

        }

    }

    public void createNewGame(VirtualView client) throws IOException, InterruptedException {

        client.show("\nHow many player? there will be? (2-4)");
        int numMaxPlayers;

        do {
            try {
                numMaxPlayers = Integer.parseInt(client.read());
                if (numMaxPlayers <= 4 && numMaxPlayers >= 1){
                    break;
                }
            } catch (NumberFormatException e) {
                client.show("\nInvalid format. Please enter a number between 2 and 4");
                continue;
            }
            client.show("\nInvalid input. Please enter a number between 2 and 4");
        } while (true);

        GameController controller;
        Initializer init = new Initializer();
        synchronized (gameControllers) {
            controller = new GameController(init.initialize(), numMaxPlayers, this.idCounter++, this);
            gameControllers.add(controller);
        }
        // count the player who created the game
        controller.getGame().setNumActualPlayers(1);
        client.show("\nGame created with id " + controller.getId() + "\n" + "\nWaiting for players to join...");
        controller.initializePlayer(client, this);
        controller.executeCommands();
    }

    private boolean tryReconnectPlayer(VirtualView client, GameController gc, String disconnectedUsername) throws RemoteException {

        for (Player p : gc.getGame().getPlayers()) {
            if (p.getUsername().equals(disconnectedUsername) && p.isDisconnected()) {
                //reconnectClient(client, p, gc);
                registeredUsernames.put(p.getUsername(), client);
                client.setUsername(p.getUsername());
                gc.addCommand(new ReconnectPlayerCommand(client, p));
                return true;
            }
        }

        System.out.println("Player not found");
        return false;

    }


    public boolean validUsername(String u, VirtualView view) {
        synchronized (registeredUsernames) {
            if (registeredUsernames.containsKey(u) || u.isEmpty()) { // username already taken or empty
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
        userToGameController(player).addCommand(command);
    }

    public void closeGame(GameController controller) {
        synchronized (gameControllers) {
            controller.getGame().notifyObservers(new ClosedGameForNoOneLeftMessage("The game has been closed because it's been suspended for too long"));
            gameControllers.remove(controller);
        }
    }
}
