package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

public class DoublePattern extends ObjectiveCard {
    //  NOTA: DEVE CONTENERE INKWELL, MANUSCRIPT O QUILL
    public final int OBJECTIVE_POINTS = 2;
    private CornerSymbol cornerSymbol;

    public DoublePattern(int id, FrontFace front, BackFace back, CornerSymbol cornerSymbol) {
        super(id, front, back);
        this.cornerSymbol = cornerSymbol;
    }
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count;

        count = manuscript.getCounter(this.cornerSymbol);

        return OBJECTIVE_POINTS*(count/2);
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "    ";
    }
}
