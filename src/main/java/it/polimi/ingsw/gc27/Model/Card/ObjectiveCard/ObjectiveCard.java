package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

import java.util.HashMap;
import java.util.Map;

/**
 * objective cards are non-playable cards essential for the game,
 * they give points to the player at the end of the match if all the criteria are met
 */
public abstract class ObjectiveCard extends Card {


    protected static Map<Class<? extends ObjectiveCard>, Integer> objPointsMap = new HashMap<>();

    static {
        objPointsMap.put(DifferentPattern.class, 3);
        objPointsMap.put(DoublePattern.class, 2);
        objPointsMap.put(LadderPattern.class, 2);
        objPointsMap.put(ThreeKingdomPattern.class, 2);
        objPointsMap.put(TwoPlusOnePattern.class, 3);
    }

    /**
     * constructor for the objective card, super {@link Card}
     *
     * @param id    the card's id
     * @param front the card's front face
     * @param back  the card's back face
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
     * method to be implemented and overridden by a concrete class,
     * which returns a coloured string based on the input
     *
     * @param s the input string to be processed
     * @return coloured string
     */
    protected abstract String paintString(String s);

    /**
     * method to be implemented and overridden by a concrete class,
     * which converts the card details to a string suitable for Tui interface display
     *
     * @return a string representation of the card
     */
    public abstract String toCliCard();

    /**
     * method to be implemented and overridden by a concrete class,
     * which calculates the points earned by achieving the objective, based on the given manuscript
     *
     * @param manuscript the manuscript used to evaluate the objective
     * @return the points scored for achieving the objective
     */
    public abstract int calculateObjectivePoints(Manuscript manuscript);

}
