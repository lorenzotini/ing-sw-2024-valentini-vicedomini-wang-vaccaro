package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

/**
 * The ContinueGameMessage class represents a message indicating that the game may continue
 * after the disconnection a player, resuming the game flow
 * It extends the {@link Message} class and implements the reportUpdate method.
 */
public class ContinueGameMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param miniModel mini model
     */
    public ContinueGameMessage(MiniModel miniModel) {
        super(miniModel);
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method resumes the match in the view
     *
     * @param client The VirtualView to report the update to
     * @param view   The View to report the update to
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.resumeTheMatch();
    }
}
