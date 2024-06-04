package it.polimi.ingsw.gc27.Model.Enumerations;

import it.polimi.ingsw.gc27.View.ColourControl;

import java.io.Serializable;

public enum Kingdom implements Serializable {
    EMPTY,
    PLANTKINGDOM,
    ANIMALKINGDOM,
    INSECTKINGDOM,
    FUNGIKINGDOM;

    public CornerSymbol toCornerSymbol(){
        return switch (this) {
            case FUNGIKINGDOM -> CornerSymbol.FUNGI;
            case PLANTKINGDOM -> CornerSymbol.PLANT;
            case ANIMALKINGDOM -> CornerSymbol.ANIMAL;
            case INSECTKINGDOM -> CornerSymbol.INSECT;
            case EMPTY -> CornerSymbol.EMPTY;
        };
        //aggiungere Ecception
    }

    public String toColourControl(){
        return switch (this) {
            case FUNGIKINGDOM -> ColourControl.RED;
            case PLANTKINGDOM -> ColourControl.GREEN;
            case ANIMALKINGDOM -> ColourControl.CYAN;
            case INSECTKINGDOM -> ColourControl.PURPLE;
            case EMPTY -> ColourControl.YELLOW;
        };
    }



}
