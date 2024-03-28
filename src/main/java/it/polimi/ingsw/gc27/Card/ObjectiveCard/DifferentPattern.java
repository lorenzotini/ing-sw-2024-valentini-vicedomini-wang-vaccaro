package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Card.BackFace;
import it.polimi.ingsw.gc27.Card.FrontFace;
import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class DifferentPattern extends ObjectiveCard {
    public final int OBJECTIVE_POINTS = 3;
    public DifferentPattern(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }


    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count;
        int points;
        int min = 0;

        count = manuscript.getCounter(CornerSymbol.QUILL);
        if(count<=min) {
            min = count;
        }
        count = manuscript.getCounter(CornerSymbol.MANUSCRIPT);
        if(count<=min) {
            min = count;
        }
        count = manuscript.getCounter(CornerSymbol.INKWELL);
        if(count<=min) {
            min = count;
        }
        points = 3*min;
        return points;
    }
}
