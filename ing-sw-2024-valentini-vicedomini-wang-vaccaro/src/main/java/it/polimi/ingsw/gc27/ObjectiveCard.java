package it.polimi.ingsw.gc27;

import Enumerations.ObjectiveRequirementType;

public class ObjectiveCard extends Card{
    private int objectivePoints;
    private ObjectiveRequirementType pattern;
    public ObjectiveCard(FrontFace front, BackFace back) {
        super(front, back);
    }
}
