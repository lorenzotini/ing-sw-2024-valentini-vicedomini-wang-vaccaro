package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

public abstract class ObjectiveCard extends Card {
    public ObjectiveCard(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }

    public abstract int calculateObjectivePoints(Manuscript manuscript);

}
