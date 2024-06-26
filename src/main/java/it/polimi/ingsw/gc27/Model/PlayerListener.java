package it.polimi.ingsw.gc27.Model;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * The PlayerListener class listens for updates to be sent to a specific player.
 * It checks whether a message should be sent to the player's client based on the
 * conditions defined in the update method
 * This class implements Serializable to allow instances of this class
 * to be serialized
 */
public class PlayerListener implements Serializable {

    VirtualView client;
    String playerUsername;

    /**
     * Constructs a PlayerListener with the specified client and player
     * @param client The client's VirtualView.
     * @param player The player associated with this listener
     */
    public PlayerListener(VirtualView client, Player player) {
        this.client = client;
        this.playerUsername = player.getUsername();
    }



    /**
     * Updates the client with the given message if the conditions are met, the conditions are contained in the Minimodel
     * @param message The message to be potentially sent to the client. This method checks if the client should receive the update,
     *                without modifying the message given
     */
    public void update(Message message) {

            if (message.getMiniModel() == null ||
                    (message.getMiniModel().currentPlayer == null && message.getMiniModel().getPlayer() == null) ||
                    (message.getMiniModel().currentPlayer != null && message.getMiniModel().currentPlayer.equals(playerUsername)) ||
                    (message.getMiniModel().getPlayer() != null && message.getMiniModel().getPlayer().getUsername().equals(playerUsername))) { //the condition check that the client of this listener has to receive the update
                    try{
                        client.update(message);
                    }catch(RemoteException e){
                        System.out.println("Error from the listener");
                    }
            }
    }

    /**
     * @return The username of the player associated with this listener
     */
    public String getPlayerUsername() {
        return playerUsername;
    }

}
