package it.polimi.ingsw.gc27.View.Gui.UserData;

/**
 * This class represents the data for a hand card, including its index in the hand
 * and whether it is facing front or back.
 */
public class HandCardData {

    private int handIndex;
    private boolean isFront;

    /**
     * Constructs a new HandCardData instance.
     *
     * @param handIndex The index of the card in the hand.
     * @param isFront   True if the card is facing front, false if it is facing back.
     */
    public HandCardData(int handIndex, boolean isFront) {
        this.handIndex = handIndex;
        this.isFront = isFront;
    }

    /**
     * Gets the index of the card in the hand.
     *
     * @return The index of the card in the hand.
     */
    public int getHandIndex() {
        return handIndex;
    }

    /**
     * Sets the index of the card in the hand.
     *
     * @param handIndex The new index of the card in the hand.
     */
    public void setHandIndex(int handIndex) {
        this.handIndex = handIndex;
    }

    /**
     * Checks if the card is face up.
     *
     * @return True if the card is facing up, false if it is facing down.
     */
    public boolean isFront() {
        return isFront;
    }

    /**
     * Sets whether the card is face up.
     *
     * @param front True if the card is face up, false if it faces down.
     */
    public void setFront(boolean front) {
        this.isFront = front;
    }

    /**
     * Toggles the orientation of the card between front and back.
     */
    public void changeIsFront() {
        this.isFront = !this.isFront;
    }

}
