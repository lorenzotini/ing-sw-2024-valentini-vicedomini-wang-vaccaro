package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Game.Manuscript;

public abstract class ObjectiveCard extends Card {
    public ObjectiveCard(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }

    public abstract int calculateObjectivePoints(Manuscript manuscript);

}
