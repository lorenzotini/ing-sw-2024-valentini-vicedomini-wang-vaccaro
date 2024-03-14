package it.polimi.ingsw.gc27;

public abstract class Face {
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

    public void setColour(Kingdom colour) {
        this.colour = colour;
    }

    public Corner getCornerUR() {
        return cornerUR;
    }

    public void setCornerUR(Corner cornerUR) {
        this.cornerUR = cornerUR;
    }

    public Corner getCornerUL() {
        return cornerUL;
    }

    public void setCornerUL(Corner cornerUL) {
        this.cornerUL = cornerUL;
    }

    public Corner getCornerLR() {
        return cornerLR;
    }

    public void setCornerLR(Corner cornerLR) {
        this.cornerLR = cornerLR;
    }

    public Corner getCornerLL() {
        return cornerLL;
    }

    public void setCornerLL(Corner cornerLL) {
        this.cornerLL = cornerLL;
    }
}
