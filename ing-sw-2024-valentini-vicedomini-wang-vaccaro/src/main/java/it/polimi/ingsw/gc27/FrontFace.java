package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.Enumerations.Kingdom;

import java.util.ArrayList;

public class FrontFace extends Face{
    public FrontFace(Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL) {
        super(colour, cornerUR, cornerUL, cornerLR, cornerLL);
    }
    @Override
    public Face copy(Face face){
        return new FrontFace(
                face.getColour(),
                face.getCorner(1, 1),
                face.getCorner(-1, 1),
                face.getCorner(1, -1),
                face.getCorner(-1, -1));
    }
    @Override
    public ArrayList<Kingdom> getPermanentResources(){
        return null;
    }
}
