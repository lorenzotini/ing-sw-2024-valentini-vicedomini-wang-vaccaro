package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class DifferentPattern extends ObjectiveCard{
    public DifferentPattern(FrontFace front, BackFace back, int objectivePoints) {
        super(front, back, objectivePoints);
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count = 0;
        int points = 0;
        int min = 0;

        count = manuscript.countCornerSymbol(CornerSymbol.QUILL);
        if(count<=min) {
            min = count;
        }
        count = manuscript.countCornerSymbol(CornerSymbol.MANUSCRIPT);
        if(count<=min) {
            min = count;
        }
        count = manuscript.countCornerSymbol(CornerSymbol.INKWELL);
        if(count<=min) {
            min = count;
        }
        points = 3*min;
        return points;
    }
}
