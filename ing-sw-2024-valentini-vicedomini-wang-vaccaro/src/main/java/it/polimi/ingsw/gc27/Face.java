package it.polimi.ingsw.gc27;

public abstract class Face {
    //UR = upper right, LL = lower left, etc...
    private Corner URCorner;
    private Corner ULCorner;
    private Corner LRCorner;
    private Corner LLCorner;

    public Face(Corner URCorner, Corner ULCorner, Corner LRCorner, Corner LLCorner) {
        this.URCorner = URCorner;
        this.ULCorner = ULCorner;
        this.LRCorner = LRCorner;
        this.LLCorner = LLCorner;
    }
}
