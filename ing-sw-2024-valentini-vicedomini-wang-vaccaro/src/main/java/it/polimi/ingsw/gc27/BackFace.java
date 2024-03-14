package it.polimi.ingsw.gc27;

public class BackFace extends  Face{
    private ArrayList<Kingdom> permanentResources;

    public BackFace(Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL, ArrayList<Kingdom> permanentResources) {
        super(colour, cornerUR, cornerUL, cornerLR, cornerLL);
        this.permanentResources = permanentResources;
    }
}