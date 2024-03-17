package it.polimi.ingsw.gc27.Enumerations;

import static it.polimi.ingsw.gc27.Enumerations.Kingdom.*;
import static it.polimi.ingsw.gc27.Enumerations.CornerSymbol.*;
public enum ObjectiveRequirementType {
    
    THREEPLANTKINGDOM(CornerSymbol.PLANTKINGDOM, Kingdom.PLANTKINGDOM),
    THREEANIMALKINGDOM(CornerSymbol.ANIMALKINGDOM , Kingdom.ANIMALKINGDOM ),
    THREEINSECTKINGDOM(CornerSymbol.INSECTKINGDOM , Kingdom.INSECTKINGDOM ),
    THREEFUNGIKINGDOM(CornerSymbol.FUNGIKINGDOM , Kingdom.FUNGIKINGDOM ),

    BLUELADDER(CornerSymbol.EMPTY , Kingdom.ANIMALKINGDOM ),
    REDLADDER(CornerSymbol.EMPTY , Kingdom.FUNGIKINGDOM ),
    PURPLELADDER(CornerSymbol.EMPTY , Kingdom.INSECTKINGDOM ),
    GREENLADDER(CornerSymbol.EMPTY , Kingdom.PLANTKINGDOM ),

    DOUBLEQUILL(CornerSymbol.QUILL , Kingdom.EMPTY ),
    DOUBLEMANUSCRIPT(CornerSymbol.MANUSCRIPT , Kingdom.EMPTY ),
    DOUBLEINKWELL(CornerSymbol.INKWELL , Kingdom.EMPTY ),
    EACHDIFFERENTTYPE(CornerSymbol.EMPTY , Kingdom.EMPTY ),

    TWOPLUSONEUPPERRIGHT(CornerSymbol.EMPTY , Kingdom.EMPTY ),
    TWOPLUSONEUPPERLEFT(CornerSymbol.EMPTY , Kingdom.EMPTY ),
    TWOPLUSONELOWERLEFT(CornerSymbol.EMPTY , Kingdom.EMPTY ),
    TWOPLUSONELOWERRIGHT(CornerSymbol.EMPTY , Kingdom.EMPTY );


    private CornerSymbol cornerSymbol;
    private Kingdom kingdom;

    ObjectiveRequirementType(CornerSymbol cornerSymbol, Kingdom kingdom) {
        this.cornerSymbol = cornerSymbol;
        this.kingdom = kingdom;
    }
    public CornerSymbol getCornerSymbol() {
        return cornerSymbol;
    }
    public Kingdom getKingdom() {
        return kingdom;
    }

}
