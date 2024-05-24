package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class GigaController {

    private int idCounter = 0;

    private final Map<String, VirtualView> registeredUsernames = new HashMap<>();

    private final List<GameController> gameControllers = new ArrayList<>();

    public List<GameController> getGameControllers() {
        return gameControllers;
    }

    //public void setGameControllers(List<GameController> gameControllers) {this.gameControllers = gameControllers;}

    public GameController userToGameController(String username) {
        for (var c : gameControllers) {
            if (c.getGame().getPlayers().stream().anyMatch(p -> p.getUsername().equals(username))) {
                return c;
            }
        }
        return null;
    }

    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {

        client.show("\nWelcome to Codex Naturalis\n" + "\nDo you want to start a new game or join an existing one? (enter 'new' or the gameId)");
        String game = client.read();
        boolean canEnter = false;

        if (game.equalsIgnoreCase("new")) {    // start a new game
            createNewGame(client);
        } else {
            // join an existing game
            do {
                for (var gc : gameControllers) {
                    // TODO controllare che game sia convertibile in int
                    if (gc.getId() == Integer.parseInt(game)) {
                        // TODO non so se è meglio togliere questa show
                        client.show("\nJoining game " + game + "...");
                        synchronized (gc.getGame().getPlayers()) {

                            if (gc.getGame().getNumActualPlayers() < gc.getNumMaxPlayers()) { // game not full, can join

                                int a = gc.getGame().getNumActualPlayers();
                                gc.getGame().setNumActualPlayers(a + 1);
                                canEnter = true;

                            } else if(gc.getGame().getNumActualPlayers() == gc.getNumMaxPlayers() &&      // game full, but a disconnected player can rejoin
                                    gc.getGame().getPlayers().stream().anyMatch(Player::isDisconnected)){

                                AtomicBoolean foundDisconnectedPlayer = new AtomicBoolean(false);
                                client.show("This game has a disconnected player. Are you him? If so, please enter your username.");
                                String disconnectedUsername = client.read();

                                gc.getGame().getPlayers().stream().filter(Player::isDisconnected).forEach(p -> {
                                    if(p.getUsername().equals(disconnectedUsername)){
                                        try {
                                            foundDisconnectedPlayer.set(true);
                                            reconnectPlayer(client, p);
                                        } catch (IOException | InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                });

                                if(foundDisconnectedPlayer.get()){
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
                }

                client.show("\nGame not found. Please enter a valid game id or 'new' to start a new game");
                game = client.read();

                if (game.equalsIgnoreCase("new")) {
                    createNewGame(client);
                    return;
                }

            } while (true);

        }

    }

    public void createNewGame(VirtualView client) throws IOException, InterruptedException {
        client.show("\nHow many player? there will be? (2-4)");
        int numMaxPlayers = Integer.parseInt(client.read());
        while (numMaxPlayers > 4 || numMaxPlayers < 1) {
            client.show("\nInvalid number of players, insert a value between 2-4");
            numMaxPlayers = Integer.parseInt(client.read());
        }
        GameController controller;
        Initializer init = new Initializer();
        synchronized (gameControllers) {
            controller = new GameController(init.initialize(), numMaxPlayers, this.idCounter++);
            gameControllers.add(controller);
        }
        // count the player who created the game
        controller.getGame().setNumActualPlayers(1);
        client.show("\nGame created with id " + controller.getId() + "\n" + "\nWaiting for players to join...");
        controller.initializePlayer(client, this);
    }

    private void reconnectPlayer(VirtualView client, Player p) throws IOException, InterruptedException {
        p.setDisconnected(false);
        client = getView(p.getUsername());
    }

    // TODO remember to remove the username from the registeredUsernames map when a player leaves the game
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

    public String getUsername(VirtualView client){
        for (String username : registeredUsernames.keySet()){
            if (registeredUsernames.get(username).equals(client)){
                return username;
            }
        }
        return "";
    }

    public Player getPlayer(String username) {
        return this.userToGameController(username).getGame().getPlayer(username);
    }


    public void setGameControllers(List<GameController> gameControllers) {
    }
}
