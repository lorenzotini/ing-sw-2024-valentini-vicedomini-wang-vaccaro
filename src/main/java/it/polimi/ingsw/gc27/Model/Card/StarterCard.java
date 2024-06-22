package it.polimi.ingsw.gc27.Model.Card;

/**
 * The StarterCard class represents the first card played that initializes the whole playing field
 */
public class StarterCard extends Card {

    /**
     * constructor matching super {@link Card}
     * @param id card's id
     * @param front front face
     * @param back back face
     */
    public StarterCard(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }
}
