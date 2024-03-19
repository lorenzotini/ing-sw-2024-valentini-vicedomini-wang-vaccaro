package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class ThreeKingdomPattern extends ObjectiveCard{
    private Kingdom kingdom;

    public ThreeKingdomPattern(FrontFace front, BackFace back, int objectivePoints, Kingdom kingdom) {
        super(front, back, objectivePoints);
        this.kingdom = kingdom;
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {

    }
}
