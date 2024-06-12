package it.polimi.ingsw.gc27.View.GUI.UserData;

public class HandCardData {

    private int handIndex;
    private boolean isFront;

    public HandCardData(int handIndex, boolean isFront) {
        this.handIndex = handIndex;
        this.isFront = isFront;
    }

    public int getHandIndex() {
        return handIndex;
    }

    public void setHandIndex(int handIndex) {
        this.handIndex = handIndex;
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        this.isFront = front;
    }

    public void changeIsFront(){
        this.isFront = !this.isFront;
    }

}
