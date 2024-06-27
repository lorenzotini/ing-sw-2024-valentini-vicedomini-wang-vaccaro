package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientBoard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientManuscript;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientMarket;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The View interface represents the collection of methods for displaying and interacting with game-related information.
 * Implementing classes are expected to provide methods to display various aspects of the game state and handle user interactions.
 * Different implementations of the View interface can be used to provide different user interfaces, such as a command line interface or a graphical user interface.
 */
public interface View {

    /**
     * Starts the view, typically initializing any necessary resources and displaying the initial state of the game.
     *
     * @throws IOException if an I/O error occurs
     */
    void run() throws IOException;

    /**
     * Sets the client associated with this view.
     *
     * @param client the virtual view client to set
     */
    void setClient(VirtualView client);

    /**
     * Signals that the game has been suspended with the given message.
     *
     * @param string the message indicating the game suspension
     */
    void suspendedGame(String string);

    /**
     * Resumes the match after suspension.
     */
    void resumeTheMatch();

    /**
     * Displays a string phrase to the user.
     *
     * @param phrase the string phrase to display
     */
    void showString(String phrase);

    /**
     * Displays the hand of resource cards to the user.
     *
     * @param hand the list of resource cards to display
     */
    void show(ArrayList<ResourceCard> hand);

    /**
     * Displays the manuscript of the current player to the user.
     *
     * @param manuscript the manuscript to display
     */
    void show(ClientManuscript manuscript);

    /**
     * Displays the board to the user.
     *
     * @param board the board to display
     */
    void show(ClientBoard board);

    /**
     * Displays the market to the user.
     *
     * @param market the market to display
     */
    void show(ClientMarket market);

    /**
     * Updates the manuscript of another player.
     *
     * @param manuscript the manuscript of the other player to update
     */
    void updateManuscriptOfOtherPlayer(ClientManuscript manuscript);

    /**
     * Displays a chat message or chat history to the user.
     *
     * @param chat the chat message or history to display
     */
    void show(ClientChat chat);

    /**
     * Reads user input from the view.
     *
     * @return the user input as a string
     */
    String read();

    /**
     * Signals an acknowledgment of successful operation with the given message.
     *
     * @param string the acknowledgment message
     */
    void okAck(String string);

    /**
     * Signals an acknowledgment of unsuccessful operation with the given message.
     *
     * @param string the acknowledgment message
     */
    void koAck(String string);

    /**
     * Displays the winners of the game to the user.
     */
    void showWinners();

}