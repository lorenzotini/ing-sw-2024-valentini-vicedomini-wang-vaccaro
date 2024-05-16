package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.nio.file.Path;
import java.util.ArrayList;

public class FrontFace extends Face {
    public FrontFace(Path imagePath, Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL) {
        super(imagePath, colour, cornerUR, cornerUL, cornerLR, cornerLL);
    }

    @Override
    public ArrayList<Kingdom> getPermanentResources(){
        ArrayList<Kingdom> l = new ArrayList<>();
        l.add(Kingdom.EMPTY);
        return l;
    }
}