package it.polimi.ingsw.gc27.Model.Card;

import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PointsMultiplier;

import java.util.ArrayList;
import java.util.List;

/**
 * The GoldCard class represents a card that gives points based on certain requirements
 * only if all the criteria are met you can play this card
 */
public class GoldCard extends ResourceCard {
    private List<Kingdom> requirements;
    private PointsMultiplier pointsMultiplier;

    /**
     * constructor matching super {@link Card}
     *
     * @param id               card's id
     * @param cardPoints       points given if played
     * @param front            front face
     * @param back             back face
     * @param requirements     requirements needed to be played
     * @param pointsMultiplier how many points it gives
     */
    public GoldCard(int id, int cardPoints, FrontFace front, BackFace back, ArrayList<Kingdom> requirements, PointsMultiplier pointsMultiplier) {
        super(id, cardPoints, front, back);
        this.requirements = requirements;
        this.pointsMultiplier = pointsMultiplier;
    }

    /**
     * Gets the points multiplier of the card
     *
     * @return the points multiplier
     */
    public PointsMultiplier getPointsMultiplier() {
        return pointsMultiplier;
    }

    /**
     * Gets the requirements needed to play the card
     *
     * @return a list of requirements
     */
    public ArrayList<Kingdom> getRequirements() {
        return new ArrayList<>(requirements);
    }

}
