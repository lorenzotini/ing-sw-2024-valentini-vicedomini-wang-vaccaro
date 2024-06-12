package it.polimi.ingsw.gc27.Model.Game;

import java.io.Serializable;

public class Placement implements Serializable {

    private int x;
    private int y;

    public Placement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
