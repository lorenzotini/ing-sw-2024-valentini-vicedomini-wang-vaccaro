package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * The SetUsernameMessage class represents a message used to set a player's username.
 * It extends the {@link Message} class and contains a string representing the username.
 */
public class SetUsernameMessage extends Message {

    /**
     * constructor matching super {@link Message}
     * @param username player's username
     */
    public SetUsernameMessage(String username) {
        super(username);
    }


    /**
     * Reports the update to the specified VirtualView and View
     * This method sets the username on the client side using the provided username string
     * @param client The VirtualView to report the update to.
     * @param view The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        try {
            client.setUsername(this.string);
        } catch (IOException e) {
            System.out.println("Error while setting the username: " + e);
        }
    }

}
