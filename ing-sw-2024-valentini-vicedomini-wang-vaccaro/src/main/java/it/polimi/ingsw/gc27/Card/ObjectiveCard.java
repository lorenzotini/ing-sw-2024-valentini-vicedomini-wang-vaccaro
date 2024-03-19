package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Enumerations.ObjectiveRequirementType;
import it.polimi.ingsw.gc27.Game.Manuscript;

public abstract class ObjectiveCard extends Card {
    private int objectivePoints;
    public ObjectiveCard(FrontFace front, BackFace back, int objectivePoints) {
        super(front, back);
        this.objectivePoints = objectivePoints;
    }

    public abstract int calculateObjectivePoints(Manuscript manuscript);
    public void setObjectivePoints(int objectivePoints) {
        this.objectivePoints = objectivePoints;
    }
    public int getObjectivePoints() {
        return objectivePoints;
    }

}
