package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The ClosingGameMessage class represents a message indicating that the game is closing.
 * It extends the {@link Message} class and implements the reportUpdate method.
 */
public class ClosingGameMessage extends Message{

    /**
     * constructor matching super {@link Message}
     * @param string the string message
     */
    public ClosingGameMessage(String string){
        super(string);
    }

    /**
     * Reports the update to the specified VirtualView and View.
     * This method acknowledges the closure, displays the message, and closes the client connection.
     * @param client The VirtualView to report the update to
     * @param view The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.koAck(this.string);
        view.showString(this.string);
        try {
            client.close();
        } catch (RemoteException e){
            System.out.println("Error while closing game");
        }
    }

}
