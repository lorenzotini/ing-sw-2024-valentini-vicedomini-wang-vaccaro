package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.Enumerations.Kingdom;

public class ResourceCard extends Card{
    private int cardPoints;
    public ResourceCard(FrontFace front, BackFace back) {
        super(front, back);
    }

    public int getCardPoints() {
        return cardPoints;
    }
    public void setCardPoints(int x) {
        this.cardPoints = x;
    }
}
