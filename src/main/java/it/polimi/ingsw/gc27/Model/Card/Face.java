package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.io.Serializable;
import java.util.*;

public abstract class Face implements Serializable {
    private Kingdom colour;
    //UR = upper right, LL = lower left, etc...
    private Corner cornerUR;
    private Corner cornerUL;
    private Corner cornerLR;
    private Corner cornerLL;

    public Face(Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL) {
        this.colour = colour;
        this.cornerUR = cornerUR;
        this.cornerUL = cornerUL;
        this.cornerLR = cornerLR;
        this.cornerLL = cornerLL;
    }

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


    //per il momento ritorna null se i parametri passati non sono validi
    //Use indexes as cartesian coordinates ==> (1, 1) for UR, (-1, 1) for UL, (-1, -1) for LL, (1, -1) for LR
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

    public abstract Face copy(Face face);

    public abstract ArrayList<Kingdom> getPermanentResources();
}
