package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Placement;

import java.util.ArrayList;

/**
 * The ClientManuscript interface provides methods for accessing and manipulating manuscript data,
 * including counters of any Corner Symbol, placements, the manuscript field and
 * the vertical and horizontal maximum extension of the manuscript
 */
public interface ClientManuscript {

    /**
     * Gets the number of the specified CornerSymbol
     *
     * @param cs the CornerSymbol
     * @return the number of the specified CornerSymbol
     */
    int getCounter(CornerSymbol cs);

    /**
     * Checks if the given ResourceCard or GoldCard (subclass of ResourceCard {@link it.polimi.ingsw.gc27.Model.Card.GoldCard}) satisfies the requirements.
     *
     * @param card the ResourceCard/GoldCard to check
     * @return true if the requirements are satisfied, false otherwise.
     */
    boolean satisfiedRequirement(ResourceCard card);

    /**
     * Gets the list of valid placements in the manuscript.
     *
     * @return an ArrayList of Placement objects representing the placements.
     */
    ArrayList<Placement> getPlacements();

    /**
     * Checks if the placement at the specified coordinates is valid
     *
     * @param x the x-coordinate of the placement
     * @param y the y-coordinate of the placement
     * @return true if the placement is valid, false otherwise
     */
    boolean isValidPlacement(int x, int y);

    /**
     * Gets the counter for manuscripts.
     *
     * @return the manuscript counter value.
     */
    int getManuscriptCounter();

    /**
     * Gets the counter for quills.
     *
     * @return the quill counter value.
     */
    int getQuillCounter();

    /**
     * Gets the counter for inkwells.
     *
     * @return the inkwell counter value.
     */
    int getInkwellCounter();

    /**
     * Gets the counter for plants.
     *
     * @return the plant counter value.
     */
    int getPlantCounter();

    /**
     * Gets the counter for insects.
     *
     * @return the insect counter value.
     */
    int getInsectCounter();

    /**
     * Gets the counter for fungi.
     *
     * @return the fungi counter value.
     */
    int getFungiCounter();

    /**
     * Gets the counter for animals.
     *
     * @return the animal counter value.
     */
    int getAnimalCounter();

    /**
     * Gets the field of faces representing the manuscript
     *
     * @return a 2D array of Face objects representing the field.
     */
    Face[][] getField();

    /**
     * Gets the maximum x-coordinate value of the manuscript
     *
     * @return the maximum x-coordinate value.
     */
    int getxMax();

    /**
     * Gets the maximum y-coordinate value of the manuscript
     *
     * @return the maximum y-coordinate value.
     */
    int getyMax();

    /**
     * Gets the minimum x-coordinate value of the manuscript
     *
     * @return the minimum x-coordinate value.
     */
    int getxMin();

    /**
     * Gets the minimum y-coordinate value of the manuscript
     *
     * @return the minimum y-coordinate value.
     */
    int getyMin();
}
