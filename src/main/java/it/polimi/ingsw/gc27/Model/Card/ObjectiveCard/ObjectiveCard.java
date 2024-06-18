package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

import java.util.HashMap;
import java.util.Map;

public abstract class ObjectiveCard extends Card {


    protected static Map<Class<? extends  ObjectiveCard>, Integer> objPointsMap = new HashMap<>();
    static{
        objPointsMap.put(DifferentPattern.class, 3);
        objPointsMap.put(DoublePattern.class, 2);
        objPointsMap.put(LadderPattern.class, 2);
        objPointsMap.put(ThreeKingdomPattern.class, 2);
        objPointsMap.put(TwoPlusOnePattern.class, 3);
    }

    /**
     * constructor for the objective card, super {@link Card}
     * @param id the card's id
     * @param front the card's front face
     * @param back the card's back face
     */

    public ObjectiveCard(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }

    /**
     * returns a map in which each class representing a specific objective card is linked to
     * the amount of points that you score if completed the secret objective
     */
    public static Map<Class<? extends ObjectiveCard>, Integer> getObjPointsMap() {
        return objPointsMap;
    }

    /**
     * methods implemented and overwritten by a concrete class
     */
    protected abstract String paintString(String s);

    public abstract String toCliCard();

    public abstract int calculateObjectivePoints(Manuscript manuscript);

}
