package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class LadderPattern extends ObjectiveCard{
    private Kingdom kingdom;
    private boolean upscaling;

    public LadderPattern(FrontFace front, BackFace back, int objectivePoints, Kingdom kingdom, boolean upscaling) {
        super(front, back, objectivePoints);
        this.kingdom = kingdom;
        this.upscaling = upscaling;
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {

    }
}
