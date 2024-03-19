package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class TwoPlusOnePattern extends ObjectiveCard{
    private Kingdom firstColour;
    private Kingdom secondColour;
    private int i;
    private int j;

    public TwoPlusOnePattern(FrontFace front, BackFace back, int objectivePoints, Kingdom firstColour, Kingdom secondColour, int i, int j) {
        super(front, back, objectivePoints);
        this.firstColour = firstColour;
        this.secondColour = secondColour;
        this.i = i;
        this.j = j;
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {

    }
}