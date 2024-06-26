package it.polimi.ingsw.gc27.Model.Game;

import java.io.Serializable;

/**
 * The Placement class represents a placement with x and y coordinates
 * This class is used to encapsulate the coordinates of a placement and provide
 * getters to access these coordinates
 * The Placement class implements the Serializable to allow instances
 * of this class to be serialized
 */
public class Placement implements Serializable {

    private int x;
    private int y;

    /**
     * Constructs a Placement with the specified x and y coordinates
     * @param x The x-coordinate of the placement
     * @param y The y-coordinate of the placement
     */
    public Placement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return The x-coordinate of the placement.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y-coordinate of the placement.
     */
    public int getY() {
        return y;
    }

}
