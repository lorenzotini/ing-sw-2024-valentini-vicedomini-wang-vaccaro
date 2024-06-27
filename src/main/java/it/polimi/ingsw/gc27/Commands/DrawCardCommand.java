package it.polimi.ingsw.gc27.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

/**
 * DrawCardCommand class represents a command to draw a card for a player.
 * Implements the Command interface. {@link Command}
 */
public class DrawCardCommand implements Command {

    private final String playerName;
    private final boolean fromDeck;
    private final boolean isGold;
    private final int faceUpCardIndex;

    /**
     * Constructs a DrawCardCommand with the specified parameters
     *
     * @param playerName      the name of the player
     * @param isGold          true if the card is a gold card, false otherwise
     * @param fromDeck        true if the card is drawn from the deck, false if drawn from face-up cards
     * @param faceUpCardIndex the index of the face-up card to be drawn (ignored if drawing from the deck)
     */
    public DrawCardCommand(String playerName, boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        this.playerName = playerName;
        this.isGold = isGold;
        this.fromDeck = fromDeck;
        this.faceUpCardIndex = faceUpCardIndex;
    }

    /**
     * Executes the command to draw a card for the specified player
     *
     * @param gc the GameController that controls the game
     * @throws InterruptedException if the command execution is interrupted
     */
    @Override
    public void execute(GameController gc) throws InterruptedException {

        Player player = gc.getPlayer(playerName);
        // TODO: gestire le eccezioni
        gc.drawCard(player, isGold, fromDeck, faceUpCardIndex);
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
