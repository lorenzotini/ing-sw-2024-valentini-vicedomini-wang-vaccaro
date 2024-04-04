package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Enumerations.PointsMultiplier;

import java.util.ArrayList;

public class Player {
    private String username;
    private ArrayList<ResourceCard> hand;
    private Manuscript manuscript;
    private PawnColour pawnColour;
    private Card secretObjective;

    public Player() {
        hand = new ArrayList<>(3);
    }

    public PawnColour getPawnColour() {
        return pawnColour;
    }
    public void setPawnColour(PawnColour pawnColour) {
        this.pawnColour = pawnColour;
    }

    public Manuscript getManuscript() {
        return manuscript;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setManuscript(Manuscript manuscript) {
        this.manuscript = manuscript;
    }

    public ArrayList<ResourceCard> getHand() {
        return hand;
    }

    /**
     * Actually adds the card on the manuscript, covering the corners involved and counts them.
     * Updates the manuscript's counters.
     * Then, calculate the points earned and add them to the board.
     * @param game
     * @param card
     * @param face
     * @param x
     * @param y
     */
    public void addCard(Game game, Card card, Face face, int x, int y){
        Manuscript m = this.manuscript;
        m.getField()[x][y] = face;
        //momentaneamente il metodo prende una card e gestisce separatamente se è una starter. In futuro provare a riscrivere con un design pattern
        if(card instanceof StarterCard){
            for(int i = -1; i <= 1; i = i + 2){
                for(int j = -1; j <= 1; j = j + 2){
                    m.increaseCounter(m.getField()[x][y].getCorner(i, -j).getSymbol());
                }
            }
            return;
        }

        // set to "hidden" the corners covered by the added card and count them
        //Manuscript m = this.manuscript;
        int numCoveredCorners = 0;

        this.manuscript.getField()[x][y] = face;

        for(int i = -1; i <= 1; i = i + 2){
            for(int j = -1; j <= 1; j = j + 2){
                if(m.getField()[x + i][y + j] != null){
                    m.getField()[x + i][y + j].getCorner(-i, j).setHidden(true);
                    numCoveredCorners++;
                    m.decreaseCounter(m.getField()[x + i][y + j].getCorner(-i, j).getSymbol());
                }
                m.increaseCounter(m.getField()[x][y].getCorner(i, -j).getSymbol());
            }
        }
        // increase kingdom counter if backface
        if(face instanceof BackFace){
            m.increaseCounter(face.getColour().toCornerSymbol());
        }



        //calculate the points earned with the card, in case of face up goldCard
        int points;
        if(face instanceof FrontFace){
            if (card instanceof GoldCard){
                if(((GoldCard)card).getPointsMultiplier().equals(PointsMultiplier.CORNER)){
                    points = ((GoldCard)card).getCardPoints() * numCoveredCorners;
                }
                else{
                    points = ((GoldCard)card).getCardPoints() * this.manuscript.getCounter(((GoldCard) card).getPointsMultiplier().toCornerSymbol());
                }
                game.addPoints(this, points);
            }else {
                points = ((ResourceCard)card).getCardPoints();
                game.addPoints(this, points);
            }
        }
    }

}
