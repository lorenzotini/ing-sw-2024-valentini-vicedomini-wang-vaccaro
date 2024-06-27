package it.polimi.ingsw.gc27.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

/**
 * AddStarterCommand class represents a command to add a starter card to a player's manuscript
 * Implements Command interface. {@link Command}
 */
public class AddStarterCommand implements Command {

    private final String playerName;
    private final boolean isFront;

    /**
     * Constructs an AddStarterCommand with the specified parameters
     *
     * @param playerName the name of the player
     * @param isFront    true if the front face of the starter card, false if the back face
     */
    public AddStarterCommand(String playerName, boolean isFront) {
        this.playerName = playerName;
        this.isFront = isFront;
    }

    /**
     * Executes the command to add the starter card to the game for the specified player
     *
     * @param gc the GameController that controls the game
     */
    @Override
    public void execute(GameController gc) {
        try {
            Player player = gc.getPlayer(playerName);
            StarterCard starter = player.getStarterCard();
            Face face = isFront ? starter.getFront() : starter.getBack();
            gc.addStarterCard(player, starter, face);
        } catch (Exception e) {
            System.out.println("incredible exceptione find me if you can ");
        }

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
