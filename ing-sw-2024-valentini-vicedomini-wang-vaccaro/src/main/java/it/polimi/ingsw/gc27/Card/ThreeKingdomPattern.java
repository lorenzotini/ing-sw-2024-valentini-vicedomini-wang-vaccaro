package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class ThreeKingdomPattern extends ObjectiveCard{
    private Kingdom kingdom;
    private CornerSymbol cornerSymbol;

    public ThreeKingdomPattern(FrontFace front, BackFace back, int objectivePoints, Kingdom kingdom, CornerSymbol cornerSymbol) {
        super(front, back, objectivePoints);
        this.kingdom = kingdom;
        this.cornerSymbol = cornerSymbol;
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count = 0;
        int points = 0;

        count = manuscript.countCornerSymbol(this.cornerSymbol);
        count = count + manuscript.countBackSymbol(this.kingdom);
        points = 2*(count/3);

        return points;
    }
}
