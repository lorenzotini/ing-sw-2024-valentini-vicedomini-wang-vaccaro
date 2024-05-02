package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

public class DifferentPattern extends ObjectiveCard {

    public final int OBJECTIVE_POINTS = 3;
    public DifferentPattern(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count;
        int points;
        int min = 99;

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
        points = OBJECTIVE_POINTS*min;
        return points;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + " ";
    }
}
