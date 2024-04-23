package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.*;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Enumerations.PointsMultiplier;
import it.polimi.ingsw.gc27.Model.States.PlayerStates.InitializingState;
import it.polimi.ingsw.gc27.Model.States.PlayerStates.PlayerState;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String username;
    private ArrayList<ResourceCard> hand;
    private Manuscript manuscript;
    private PawnColour pawnColour;
    private ObjectiveCard secretObjective;
    private PlayerState playerState;

    public Player() {
        hand = new ArrayList<>(3);
    }

    public Player(String username, Manuscript manuscript, PawnColour pawnColour) {
        this.username = username;
        this.manuscript = manuscript;
        this.pawnColour = pawnColour;
        this.hand = new ArrayList<>();
        // TODO this.playerState = new InitializingState();
     }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public PlayerState getPlayerState() {
        return playerState;
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
    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }
    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
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
    public void setHand(ArrayList<ResourceCard> hand) {
        this.hand = hand;
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
    public void addCard(Game game, Card card, Face face, int x, int y) {

        Manuscript m = this.manuscript;

        //update manuscript ends in order to show only the used part when displayed
        if(x > m.getxMax()){
            m.setxMax(x);
        } else if(x < m.getxMin()) {
            m.setxMin(x);
        }
        if(y > m.getyMax()){
            m.setyMax(y);
        } else if(y < m.getyMin()) {
            m.setyMin(y);
        }

        //momentaneamente il metodo prende una card e gestisce separatamente se è una starter. In futuro provare a riscrivere con un design pattern
        if(card instanceof StarterCard){
            if(m.getField()[Manuscript.FIELD_DIM/2][Manuscript.FIELD_DIM/2] == null){
                m.getField()[x][y] = face;
                m.setxMin(Manuscript.FIELD_DIM/2);
                m.setxMax(Manuscript.FIELD_DIM/2);
                m.setyMin(Manuscript.FIELD_DIM/2);
                m.setyMax(Manuscript.FIELD_DIM/2);

                //increase counter permanent resources if the starter is placed face down
                if(face instanceof BackFace){
                    ArrayList<Kingdom> permRes = card.getBack().getPermanentResources();
                    for(Kingdom resource : permRes){
                        m.increaseCounter(resource.toCornerSymbol());
                    }
                }

                for(int i = -1; i <= 1; i = i + 2){
                    for(int j = -1; j <= 1; j = j + 2){
                        m.increaseCounter(m.getField()[x][y].getCorner(i, -j).getSymbol());
                    }
                }
                return;
            }else {
                System.err.println("The starter card already exists");
                return;
            }
        }


        m.getField()[x][y] = face;

        // set to "hidden" the corners covered by the added card and count them
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



        //calculate the points earned with the card, in case of face up goldCard
        int points;
        if(face instanceof FrontFace){
            if (card instanceof GoldCard){
                if(((GoldCard)card).getPointsMultiplier().equals(PointsMultiplier.EMPTY)){
                    points = ((GoldCard)card).getCardPoints();
                }
                else if(((GoldCard)card).getPointsMultiplier().equals(PointsMultiplier.CORNER)){
                    points = ((GoldCard)card).getCardPoints() * numCoveredCorners;
                }
                else{
                    points = ((GoldCard)card).getCardPoints() * this.manuscript.getCounter(((GoldCard) card).getPointsMultiplier().toCornerSymbol());
                }
                //game.addPoints(this, points);
            }else {
                points= ((ResourceCard)card).getCardPoints();
            }
            game.addPoints(this, points);
        }
    }

}
