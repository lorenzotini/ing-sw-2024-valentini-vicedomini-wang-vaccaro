package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.nio.file.Path;
import java.util.ArrayList;

public class BackFace extends Face {
    private ArrayList<Kingdom> permanentResources;

    /**
     * constructor matching super {@link Face}
     * @param imagePath the path of the image converted to string
     * @param colour the colour of the card
     * @param cornerUR corner upper right
     * @param cornerUL corner upper  left
     * @param cornerLR corner lower right
     * @param cornerLL corner lower left
     * @param permanentResources the resource (symbol) on the back of the card
     */
    public BackFace(String imagePath, Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL, ArrayList<Kingdom> permanentResources) {
        super(imagePath, colour, cornerUR, cornerUL, cornerLR, cornerLL);
        this.permanentResources = permanentResources;
    }

    /**
     * getter
     * @return arraylist
     */
    @Override
    public ArrayList<Kingdom> getPermanentResources(){
        return permanentResources;
    }
}