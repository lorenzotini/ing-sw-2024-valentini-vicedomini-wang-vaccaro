package it.polimi.ingsw.gc27.View.Gui;

/**
 * The class represents a point in a 2D space with coordinates (x, y) and an associated count.
 * It provides methods to retrieve and modify the coordinates and the counter of the point.
 */
public class Point {
    int x;
    int y;
    int count;

    /**
     * Constructs a Point object with the specified coordinates and initializes the count to 0.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.count = 0;
    }

    /**
     * Returns the x-coordinate of the point.
     *
     * @return the x-coordinate of the point
     */
    public int getx(){
        return this.x;
    }

    /**
     * Returns the y-coordinate of the point.
     *
     * @return the y-coordinate of the point
     */
    public int gety(){
        return this.y;
    }

    /**
     * Increments the count associated with the point by 1.
     */
    public void incrementCount(){
        this.count++;
    }

    /**
     * Returns the current count associated with the point.
     *
     * @return the count associated with the point
     */
    public int getCount(){
        return this.count;
    }
}
