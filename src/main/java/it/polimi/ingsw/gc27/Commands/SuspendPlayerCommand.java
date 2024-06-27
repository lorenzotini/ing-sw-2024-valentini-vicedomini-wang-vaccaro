package it.polimi.ingsw.gc27.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;

/**
 * SuspendPlayerCommand class represents a command to suspend a player from the game.
 * Implements the Command interface. {@link Command}
 */
public class SuspendPlayerCommand implements Command {
    private final String player;

    /**
     * Constructs a SuspendPlayerCommand with the specified player username
     *
     * @param username the username of the player to be suspended
     */
    public SuspendPlayerCommand(String username) {
        this.player = username;
    }

    /**
     * Executes the SuspendPlayerCommand to suspend the specified player
     *
     * @param gc the GameController that controls the game
     */
    @Override
    public void execute(GameController gc) {
        gc.getGame().removeObserver(player);
        gc.suspendPlayer(gc.getPlayer(player));
    }

    /**
     * Gets the name of the player who invoked this command
     *
     * @return the name of the player
     */
    @Override
    public String getPlayerName() {
        return player;
    }
}
