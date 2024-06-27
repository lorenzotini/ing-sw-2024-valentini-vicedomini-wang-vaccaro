package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Messages.GenericErrorMessage;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;

/**
 * AddCardCommand class represents a command to add a card to a player's hand.
 * Implements Command interface. {@link Command}
 */
public class AddCardCommand implements Command {

    private final String playerName;
    private final int handCardIndex;
    private final boolean isFrontFace;
    private final int x;
    private final int y;

    /**
     * Constructs an AddCardCommand with the specified parameters
     *
     * @param playerName    the name of the player
     * @param handCardIndex the index of the card in the player's hand
     * @param isFrontFace   true if the front face of the card is to be used, false otherwise
     * @param x             the x-coordinate for card placement
     * @param y             the y-coordinate for card placement
     */
    public AddCardCommand(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) {
        this.playerName = playerName;
        this.handCardIndex = handCardIndex;
        this.isFrontFace = isFrontFace;
        this.x = x;
        this.y = y;
    }

    /**
     * Executes the command to add the card to the game at the specified position
     *
     * @param gc the GameController that controls the game
     */
    @Override
    public void execute(GameController gc) {
        Player player = gc.getPlayer(playerName);
        ResourceCard card;
        try {
            card = player.getHand().get(handCardIndex);
            Face face = isFrontFace ? card.getFront() : card.getBack();
            gc.addCard(player, card, face, x, y);
        } catch (IndexOutOfBoundsException e) {
            gc.getGame().notifyObservers(new GenericErrorMessage("Invalid request: incorrect card number or placement", new MiniModel(player)));
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
