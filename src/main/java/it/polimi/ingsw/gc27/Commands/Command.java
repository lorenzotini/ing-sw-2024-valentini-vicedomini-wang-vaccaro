package it.polimi.ingsw.gc27.Commands;


import it.polimi.ingsw.gc27.Controller.GameController;

import java.io.Serializable;

/**
 * Command interface represents all the command to be executed in the game, according to the command pattern.
 * Implementations of this interface should define the behavior of the command
 * when executed and provide information about the player who invoked the command.
 */
public interface Command extends Serializable {

    /**
     * Executes the command
     *
     * @param gc the GameController that controls the game
     * @throws InterruptedException if the command execution is interrupted
     */
    void execute(GameController gc) throws InterruptedException;

    /**
     * Gets the name of the player who invoked this command
     *
     * @return the name of the player
     */
    String getPlayerName();
}
