package it.polimi.ingsw.gc27.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

/**
 * ChooseObjectiveCommand class represents a command to choose an objective card for a player
 * Implements the Command interface. {@link Command}
 */
public class ChooseObjectiveCommand implements Command {

    private final String playerName;
    private final int objectiveIndex;

    /**
     * Constructs a ChooseObjectiveCommand with the specified parameters
     *
     * @param playerName     the name of the player
     * @param objectiveIndex the index of the chosen objective card
     */
    public ChooseObjectiveCommand(String playerName, int objectiveIndex) {
        this.playerName = playerName;
        this.objectiveIndex = objectiveIndex;
    }

    /**
     * Executes the command to choose an objective card for the specified player.
     *
     * @param gc the GameController that controls the game
     */
    @Override
    public void execute(GameController gc) {
        Player player = gc.getPlayer(playerName);
        gc.chooseObjectiveCard(player, objectiveIndex);
    }

    /**
     * Gets the name of the player who invoked this command
     *
     * @return the name of the player
     */
    @Override
    public String getPlayerName() {
        return this.playerName;
    }

}