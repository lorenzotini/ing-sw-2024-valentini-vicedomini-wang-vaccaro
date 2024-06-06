package it.polimi.ingsw.gc27.View.GUI.UserData;

public class MarketCardData {

    public boolean isGold;
    public boolean fromDeck;
    public int faceUpCardIndex;

    public MarketCardData(boolean isGold, boolean fromDeck, int faceUpCardIndex) {
        this.isGold = isGold;
        this.fromDeck = fromDeck;
        this.faceUpCardIndex = faceUpCardIndex;
    }

}
