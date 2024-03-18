package it.polimi.ingsw.gc27;
import java.util.ArrayList;
import it.polimi.ingsw.gc27.Enumerations.*;
public class Manuscript {
    public final int FIELD_DIM = 81;
    // use a matrix to represent the whole manuscript/play field
    private Face[][] field;
    private int xMax;
    private int yMax;
    private int xMin;
    private int yMin;
    public Manuscript(Face myStarter){
        //initialize the matrix and place the starter card at its centre
        field = new Face[FIELD_DIM][FIELD_DIM];
        field[FIELD_DIM/2][FIELD_DIM/2] = myStarter;
    }
    public void placeCard(Face cardFace, int x, int y){
        this.field[x][y] = cardFace;
    };

    public int getxMax() {
        return xMax;
    }
    public int getyMax() {
        return yMax;
    }
    public int getxMin() {
        return xMin;
    }
    public int getyMin() {
        return yMin;
    }
    public void setxMax(int x) {
        this.xMax = x;
    }
    public void setyMax(int x) {
        this.yMax = x;
    }
    public void setxMin(int x) {
        this.xMin = x;
    }
    public void setyMin(int x) {
        this.yMin = x;
    }

    /**
     *
     * @param player
     
     * @param board
     * @param points
     */
    public void setPointsPlayer(Player player, Board board, int points){

        PawnColour pawncolour = player.getPawnColour();
        switch(pawncolour) {
            case BLUE:
                board.setPointsBluePlayer(board.getPointsBluePlayer() + points);
                break;
            case RED:
                board.setPointsRedPlayer(board.getPointsRedPlayer() + points);
                break;
            case GREEN:
                board.setPointsGreenPlayer(board.getPointsGreenPlayer() + points);
                break;
            case YELLOW:
                board.setPointsYellowPlayer(board.getPointsYellowPlayer() + points);
                break;
        }
    }

    /**
     *
     * @param card
     * @param x
     * @param y
     * @param board
     * @return
     */
    public int countCoveredCorners(ResourceCard card, int x, int y, Board board){
        int count=0;
        Face ULFace = field[x-1][y+1];
        if(ULFace.getCornerLR().isHidden()){
            count++;
        }
        Face URFace = field[x+1][y+1];
        if(URFace.getCornerLL().isHidden()){
            count++;
        }
        Face LLFace = field[x-1][y-1];
        if(LLFace.getCornerUR().isHidden()){
            count++;
        }
        Face LRFace = field[x+1][y-1];
        if(LRFace.getCornerUL().isHidden()){
            count++;
        }
        return count;

    }

    /**
     *
     * @param player
     * @param card
     * @param face
     * @param board
     * @param numCoveredCorners
     */
    public void addPoints(Player player, ResourceCard card, Face face, Board board, int numCoveredCorners){
        if(face instanceof FrontFace){
            if (card instanceof ResourceCard){
                setPointsPlayer(player, board, card.getCardPoints());
            }
            int points;
            if (card instanceof GoldCard){
                if(((GoldCard)card).getPointsMultiplier().equals(PointsMultiplier.CORNER)){
                    points = card.getCardPoints() * numCoveredCorners;
                }
                else{
                    points = card.getCardPoints() * countCornerSymbol(((GoldCard)card).getPointsMultiplier());
                }
            setPointsPlayer(player, board, points);
        }



    }

}



