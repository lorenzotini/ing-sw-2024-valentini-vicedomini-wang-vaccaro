package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Face implements Serializable {

    private String imagePath;
    private Kingdom colour;
    //UR = upper right, LL = lower left, etc...
    private Corner cornerUR;
    private Corner cornerUL;
    private Corner cornerLR;
    private Corner cornerLL;

    /**
     * constructor
     * @param imagePath the path of the image converted to string
     * @param colour the colour of the card
     * @param cornerUR corner upper right
     * @param cornerUL corner upper  left
     * @param cornerLR corner lower right
     * @param cornerLL corner lower left
     */
    public Face(String imagePath, Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL) {
        this.imagePath = imagePath;
        this.colour = colour;
        this.cornerUR = cornerUR;
        this.cornerUL = cornerUL;
        this.cornerLR = cornerLR;
        this.cornerLL = cornerLL;
    }

    /**
     * gets the needed corner
     * it uses indexes as cartesian coordinates ==> (1, 1) for UR, (-1, 1) for UL, (-1, -1) for LL, (1, -1) for LR
     * @param i index
     * @param j index
     * @return the corner needed
     */
    public Corner getCorner(int i, int j) {
        if(i == 1 && j == 1){
            return cornerUR;
        }else if(i == -1 && j == 1){
            return cornerUL;
        }else if(i == 1 && j == -1){
            return cornerLR;
        }else if(i == -1 && j == -1){
            return cornerLL;
        }else{
            System.err.println("Error: indexes must be 1 or -1");
            return null;
        }
    }

    /**
     * getters
     */
    public Kingdom getColour() {
        return colour;
    }

    public Corner getCornerUR() {
        return cornerUR;
    }

    public Corner getCornerUL() {
        return cornerUL;
    }
    public Corner getCornerLR() {
        return cornerLR;
    }
    public Corner getCornerLL() {
        return cornerLL;
    }
    public String getImagePath() {
        return imagePath;
    }

    /**
     * method overwritten by the concrete classes
     * @return the permanent resource
     */
    public abstract ArrayList<Kingdom> getPermanentResources();
}
