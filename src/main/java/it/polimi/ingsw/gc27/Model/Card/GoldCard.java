package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PointsMultiplier;

import java.util.ArrayList;
import java.util.List;

public class GoldCard extends ResourceCard {
    private List<Kingdom> requirements;
    private PointsMultiplier pointsMultiplier;
    public GoldCard(int id, int cardPoints, FrontFace front, BackFace back, ArrayList<Kingdom> requirements, PointsMultiplier pointsMultiplier) {
        super(id, cardPoints, front, back);
        this.requirements = requirements;
        this.pointsMultiplier = pointsMultiplier;
    }

    public PointsMultiplier getPointsMultiplier() {
        return pointsMultiplier;
    }

    public ArrayList<Kingdom> getRequirements(){
        return new ArrayList<Kingdom>(requirements);
    }

}
