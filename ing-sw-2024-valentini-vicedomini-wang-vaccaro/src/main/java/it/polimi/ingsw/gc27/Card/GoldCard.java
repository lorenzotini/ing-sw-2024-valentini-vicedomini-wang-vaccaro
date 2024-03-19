package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.*;

import java.util.List;

public class GoldCard extends ResourceCard {
    private List<Kingdom> requirements;
    private PointsMultiplier pointsMultiplier;
    public GoldCard(FrontFace front, BackFace back) {
        super(front, back);
    }

    public List<Kingdom> getRequirements() {
        return requirements;
    }
    public PointsMultiplier getPointsMultiplier() {
        return pointsMultiplier;
    }
    public void setRequirements(List<Kingdom> requirements) {
        this.requirements = requirements;
    }
    public void setPointsMultiplier(PointsMultiplier pointsMultiplier) {
        this.pointsMultiplier = pointsMultiplier;
    }
}
