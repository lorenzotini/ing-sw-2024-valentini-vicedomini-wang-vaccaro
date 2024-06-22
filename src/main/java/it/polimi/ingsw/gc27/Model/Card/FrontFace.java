package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * The FrontFace class represents the front face of a card
 */
public class FrontFace extends Face {
    /**
     * constructor matching super {@link Face}
     * @param imagePath the path of the image converted to string
     * @param colour the colour of the card
     * @param cornerUR corner upper right
     * @param cornerUL corner upper  left
     * @param cornerLR corner lower right
     * @param cornerLL corner lower left
     */
    public FrontFace(String imagePath, Kingdom colour, Corner cornerUR, Corner cornerUL, Corner cornerLR, Corner cornerLL) {
        super(imagePath, colour, cornerUR, cornerUL, cornerLR, cornerLL);
    }

    /**
     * getter
     * @return arraylist
     */
    @Override
    public ArrayList<Kingdom> getPermanentResources(){
        ArrayList<Kingdom> l = new ArrayList<>();
        l.add(Kingdom.EMPTY);
        return l;
    }
}