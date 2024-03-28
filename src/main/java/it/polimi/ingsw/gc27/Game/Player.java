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

    public Manuscript getManuscript() {
        return manuscript;
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
    public void addCard(Game game, ResourceCard card, Face face, int x, int y){
        // set to "hidden" the corners covered by the added card and count them
        Manuscript m = this.manuscript;
        int numCoveredCorners = 0;
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

        this.manuscript.getField()[x][y] = face;

        //calculate the points earned with the card, in case of face up goldCard
        int points;
        if(face instanceof FrontFace){
            if (card instanceof GoldCard){
                if(((GoldCard)card).getPointsMultiplier().equals(PointsMultiplier.CORNER)){
                    points = card.getCardPoints() * numCoveredCorners;
                }
                else{
                    points = card.getCardPoints() * this.manuscript.getCounter(((GoldCard) card).getPointsMultiplier().toCornerSymbol());
                }
                game.addPoints(this, points);
            }else {
                points = card.getCardPoints();
                game.addPoints(this, points);
            }
        }
    }

}
