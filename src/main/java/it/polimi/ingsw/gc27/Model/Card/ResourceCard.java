package it.polimi.ingsw.gc27.Model.Card;

/**
 * The ResourceCard class represents a card that provides resources and occasionally points in the game
 */
public class ResourceCard extends Card {
    private int cardPoints;

    /**
     * constructor matching super {@link Card}
     *
     * @param id         card's id
     * @param cardPoints points scored if played
     * @param front      front face
     * @param back       back face
     */
    public ResourceCard(int id, int cardPoints, FrontFace front, BackFace back) {
        super(id, front, back);
        this.cardPoints = cardPoints;
    }

    /**
     * Gets the card points
     *
     * @return the points
     */
    public int getCardPoints() {
        return cardPoints;
    }

}
