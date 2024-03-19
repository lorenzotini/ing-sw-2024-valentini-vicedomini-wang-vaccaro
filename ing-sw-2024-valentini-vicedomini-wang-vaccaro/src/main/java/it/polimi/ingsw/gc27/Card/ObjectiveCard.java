package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.ObjectiveRequirementType;

public class ObjectiveCard extends Card {
    private int objectivePoints;
    private ObjectiveRequirementType pattern;
    public ObjectiveCard(FrontFace front, BackFace back) {
        super(front, back);
    }

    public void setObjectivePoints(int objectivePoints) {
        this.objectivePoints = objectivePoints;
    }
    public void setPattern(ObjectiveRequirementType pattern) {
        this.pattern = pattern;
    }
    public int getObjectivePoints() {
        return objectivePoints;
    }
    public ObjectiveRequirementType getPattern() {
        return pattern;
    }
}
