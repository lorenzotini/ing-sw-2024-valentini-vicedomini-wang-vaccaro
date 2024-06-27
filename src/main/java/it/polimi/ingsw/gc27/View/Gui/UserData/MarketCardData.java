package it.polimi.ingsw.gc27.View.Gui.UserData;

/**
 * This class represents the data used in accessing or modifying market card data in particular whether it is gold, from the deck and the face up card index.
 */
public class MarketCardData {

    public boolean isGold;
    public boolean fromDeck;
    public int faceUpCardIndex;

    /**
     * Constructs a new MarketCardData instance.
     *
     * @param isGold          True if the card is gold, false if it is not, therefore a regular Resource card.
     * @param fromDeck        True if the card is from the deck, false if it is not.
     * @param faceUpCardIndex The index of the face up card.
     */
    public MarketCardData(boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        this.isGold = isGold;
        this.fromDeck = fromDeck;
        this.faceUpCardIndex = faceUpCardIndex;
    }

}
