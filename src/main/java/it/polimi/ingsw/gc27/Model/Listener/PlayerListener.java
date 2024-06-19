package it.polimi.ingsw.gc27.Model.Listener;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.Serializable;
import java.rmi.RemoteException;

public class PlayerListener implements Serializable {

    VirtualView client;
    String playerUsername;

    public PlayerListener(VirtualView client, Player player) {
        this.client = client;
        this.playerUsername = player.getUsername();
    }


    /**
     * @param message doesn't  modify message, but decide if it has to be sent or not to that client
     * @throws RemoteException print details
     */
    public void update(Message message) {
        try {
            if (message.getMiniModel() == null ||
                    (message.getMiniModel().currentPlayer == null && message.getMiniModel().getPlayer() == null) ||
                    (message.getMiniModel().currentPlayer != null && message.getMiniModel().currentPlayer.equals(playerUsername)) ||
                    (message.getMiniModel().getPlayer() != null && message.getMiniModel().getPlayer().getUsername().equals(playerUsername))) {
                //the condition check that the client of this listener has to receive the update

                client.update(message);

            }
        } catch (RemoteException e) {
            System.out.println(e.detail);
        }
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

}
