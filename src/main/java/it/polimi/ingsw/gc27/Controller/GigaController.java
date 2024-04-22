package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Exceptions.UserNotFoundException;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GigaController {

    private Map<String, Boolean> availableUsernames = new HashMap<>();

    private List<GameController> gameControllers = new ArrayList<>();

    public GameController userToGameController(String username){
        for(var c : gameControllers){
            if(c.getGame().getPlayers().stream().anyMatch(p -> p.getUsername().equals(username))){
                return c;
            }
        }
        throw new UserNotFoundException(username + "does not exist");
    }

    public void welcomePlayer(VirtualView client) throws IOException {
        client.show("Welcome to Codex Naturalis" + "\n" + "Do you want to start a new game or join an existing one? (enter 'new' or the gameId)");
        String game = client.read();
        boolean canEnter = false;

        if (game.equalsIgnoreCase("new")) {    // start a new game
            createNewGame(client);
        } else {
            // join an existing game
            do{
                for(var gc : gameControllers){
                    // TODO controllare che game sia convertibile in int
                    if(gc.getId() == Integer.parseInt(game)){
                        client.show("Joining game " + game + "...");
                        // TODO check the yellow line
                        synchronized (gc.getGame().getNumActualPlayers()){
                            if(gc.getGame().getNumActualPlayers() < gc.getNumMaxPlayers()){
                                int a = gc.getGame().getNumActualPlayers();
                                gc.getGame().setNumActualPlayers(a + 1);
                                canEnter = true;
                            } else {
                                client.show("Game is full. Restarting...");
                            }
                        }
                        if(!canEnter){
                            welcomePlayer(client);

                        }else {
                            gc.initializePlayer(client, this);
                        }
                        return;
                    }
                }
                client.show("Game not found. Please enter a valid game id or 'new' to start a new game");
                game = client.read();
                if(game.equalsIgnoreCase("new")){
                    createNewGame(client);
                    return;
                }
            }while(true);
        }
    }

    public void createNewGame(VirtualView client) throws IOException {
        client.show("How many player? there will be? (2-4)");
        int numPlayers = Integer.parseInt(client.read());
        GameController controller;
        Initializer init = new Initializer();
        synchronized (gameControllers){
            controller = new GameController(init.initialize(), numPlayers, gameControllers.size() + 1);
            gameControllers.add(controller);
        }
        client.show("Game created with id " + controller.getId() + "\n" + "Waiting for players to join...");
        controller.initializePlayer(client, this);
    }

    // TODO remember to remove the username from the availableUsernames map when a player leaves the game
    public boolean validUsername(String u){
        synchronized (availableUsernames){
            if(availableUsernames.containsKey(u) || u.isEmpty()){ // username already taken or empty
                return false;
            }
            availableUsernames.put(u, true);
            return true;
        }
    }
}
