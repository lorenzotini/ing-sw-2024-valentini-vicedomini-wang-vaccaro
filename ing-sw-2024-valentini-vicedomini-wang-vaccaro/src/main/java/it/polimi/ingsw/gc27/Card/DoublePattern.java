package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class DoublePattern extends ObjectiveCard{
    //  NOTA: DEVE CONTENERE INKWELL, MANUSCRIPT O QUILL
    private CornerSymbol cornerSymbol;

    public DoublePattern(FrontFace front, BackFace back, int objectivePoints, CornerSymbol cornerSymbol) {
        super(front, back, objectivePoints);
        this.cornerSymbol = cornerSymbol;
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {

    }
}
