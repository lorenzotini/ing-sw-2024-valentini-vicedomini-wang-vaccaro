package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.nio.file.Path;
import java.util.ArrayList;

public class BackFace extends Face {
    private ArrayList<Kingdom> permanentResources;

    public BackFace(Path imagePath, Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL, ArrayList<Kingdom> permanentResources) {
        super(imagePath, colour, cornerUR, cornerUL, cornerLR, cornerLL);
        this.permanentResources = permanentResources;
    }

    @Override
    public ArrayList<Kingdom> getPermanentResources(){
        return permanentResources;
    }
}