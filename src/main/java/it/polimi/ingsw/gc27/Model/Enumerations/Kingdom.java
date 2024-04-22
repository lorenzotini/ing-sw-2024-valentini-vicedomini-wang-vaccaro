package it.polimi.ingsw.gc27.Model.Enumerations;

import it.polimi.ingsw.gc27.View.ColourControl;
public enum Kingdom {
    EMPTY,
    PLANTKINGDOM,
    ANIMALKINGDOM,
    INSECTKINGDOM,
    FUNGIKINGDOM;

    public CornerSymbol toCornerSymbol(){
        return switch (this) {
            case FUNGIKINGDOM -> CornerSymbol.FUNGIKINGDOM;
            case PLANTKINGDOM -> CornerSymbol.PLANTKINGDOM;
            case ANIMALKINGDOM -> CornerSymbol.ANIMALKINGDOM;
            case INSECTKINGDOM -> CornerSymbol.INSECTKINGDOM;
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
