package it.polimi.ingsw.gc27.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;


/**
 * PingCommand class represents a command to ping the server
 * Implements the Command interface. {@link Command}
 */
public class PingCommand implements Command {

    /**
     * Intended to be empty, as the server does not need to do anything when pinged
     *
     * @param gc game controller
     */
    @Override
    public void execute(GameController gc) {
    }

    /**
     * Command used to ping the server, no player is involved
     *
     * @return null
     */
    @Override
    public String getPlayerName() {
        return null;
    }
}
