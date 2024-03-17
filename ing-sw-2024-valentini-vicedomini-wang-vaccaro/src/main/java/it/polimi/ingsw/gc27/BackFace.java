package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.Enumerations.Kingdom;

import java.util.ArrayList;

public class BackFace extends  Face{
    private ArrayList<Kingdom> permanentResources;

    public BackFace(Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL, ArrayList<Kingdom> permanentResources) {
        super(colour, cornerUR, cornerUL, cornerLR, cornerLL);
        this.permanentResources = permanentResources;
    }
    @Override
    public Face copy(Face face){
        return new BackFace(
                face.getColour(),
                face.getCorner(1, 1),
                face.getCorner(-1, 1),
                face.getCorner(1, -1),
                face.getCorner(-1, -1),
                face.getPermanentResources());
    }
    @Override
    public ArrayList<Kingdom> getPermanentResources(){
        return permanentResources;
    }
}