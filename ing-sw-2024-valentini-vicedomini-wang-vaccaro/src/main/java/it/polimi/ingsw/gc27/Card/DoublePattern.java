package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class DoublePattern extends ObjectiveCard{
    //  NOTA: DEVE CONTENERE INKWELL, MANUSCRIPT O QUILL
    public final int OBJECTIVE_POINTS = 2;
    private CornerSymbol cornerSymbol;

    public DoublePattern(int id, FrontFace front, BackFace back, CornerSymbol cornerSymbol) {
        super(id, front, back);
        this.cornerSymbol = cornerSymbol;
    }
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count = 0;
        int points = 0;

        count = manuscript.countCornerSymbol(this.cornerSymbol);
        points = 2*(count/2);

        return points;
    }
}
