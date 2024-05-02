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

    public ObjectiveCard(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }

    public static Map<Class<? extends ObjectiveCard>, Integer> getObjPointsMap() {
        return objPointsMap;
    }

    @Override
    public abstract String toString();

    public abstract int calculateObjectivePoints(Manuscript manuscript);

}
