package it.polimi.ingsw.gc27.Card;

public class ResourceCard extends Card {
    private int cardPoints;
    public ResourceCard(int id, int cardPoints, FrontFace front, BackFace back) {
        super(id, front, back);
        this.cardPoints = cardPoints;
    }

    public int getCardPoints() {
        return cardPoints;
    }
    public void setCardPoints(int x) {
        this.cardPoints = x;
    }

}
