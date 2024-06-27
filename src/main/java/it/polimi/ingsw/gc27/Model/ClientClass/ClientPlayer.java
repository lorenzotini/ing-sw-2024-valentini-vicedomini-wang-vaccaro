package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.States.PlayerState;

import java.util.ArrayList;

/**
 * The ClientPlayer interface provides methods for accessing player-related data,
 * including player state, pawn colour, manuscript, objectives, username, hand, and starter card.
 */
public interface ClientPlayer {
    /**
     * Gets the current state of the player
     * @return the PlayerState representing the current state of the player.
     */
    PlayerState getPlayerState();

    /**
     * Gets the pawn colour chosen by the player
     * @return the PawnColour representing the player's pawn colour.
     */
    PawnColour getPawnColour();

    /**
     * Gets the manuscript of the player
     * @return the Manuscript
     */
    Manuscript getManuscript();

    /**
     * Gets the secret objective cards chosen by the player
     * @return an ArrayList of ObjectiveCards containing only the player's secret objectives
     */
    ArrayList<ObjectiveCard> getSecretObjectives();

    /**
     * Gets the username of the player
     * @return a String representing the player's username.
     */
    String getUsername();

    /**
     * Gets the player's hand
     * @return an ArrayList of ResourceCards representing the player's hand.
     */
    ArrayList<ResourceCard> getHand();

    /**
     * Gets the starter card assigned to the player
     * @return the StarterCard representing the player's starter card.
     */
    StarterCard getStarterCard();
}
