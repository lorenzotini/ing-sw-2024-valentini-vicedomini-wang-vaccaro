package it.polimi.ingsw.gc27.View.Gui.UserData;

/**
 * This class represents the data used in accessing or modifying manuscript data in particular x and y coordinates.
 */
public class ManuscriptCardData {

    public int x;
    public int y;

    /**
     * Constructs a new ManuscriptCardData instance.
     *
     * @param x The x coordinate of a card.
     * @param y The y coordinate of a card.
     */

    public ManuscriptCardData(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
