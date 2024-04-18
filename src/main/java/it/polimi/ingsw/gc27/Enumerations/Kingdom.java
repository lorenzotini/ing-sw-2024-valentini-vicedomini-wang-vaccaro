package it.polimi.ingsw.gc27.Enumerations;

import java.io.Serializable;

public enum Kingdom implements Serializable {
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



}
