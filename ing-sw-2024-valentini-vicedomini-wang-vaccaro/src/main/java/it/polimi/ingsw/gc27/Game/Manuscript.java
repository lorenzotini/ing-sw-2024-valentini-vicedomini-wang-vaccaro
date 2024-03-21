package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Enumerations.*;

public class Manuscript {
    public final int FIELD_DIM = 85;
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
        setxMax(FIELD_DIM/2);
        setxMin(FIELD_DIM/2);
        setyMax(FIELD_DIM/2);
        setyMin(FIELD_DIM/2);
    }
    public void addCard(Card card, Face face, int x, int y){
        if(isValidPlacement(x, y)){
            // set to "hidden" the corners covered by the added card and count them
            int numCoveredCorners = 0;
            for(int i = -1; i <= 1; i = i + 2){
                for(int j = -1; j <= 1; j = j + 2){
                    if(field[x + i][y + j] != null){
                        field[x + i][y + j].getCorner(-i, j).setHidden(true);
                        numCoveredCorners++;
                    }
                }
            }
            Face copy = face.copy(face);
            field[x][y] = copy;
            addPoints(player, card, face, board, numCoveredCorners);
        }else{
            System.err.println("Error: invalid position");
        }
    }

    public boolean isValidPlacement(int x, int y){
        boolean isValid = true;
        for(int i = -1; i <= 1; i = i + 2){
            for(int j = -1; j <= 1; j = j + 2){
                if(field[x + i][y + j] != null && (field[x + i][y + j].getCorner(-i, j).isBlack() || field[x + i][y + j].getCorner(-i, j).isHidden())){
                    isValid = false;
                    break;
                }
            }
            if(!isValid){
                break;
            }
        }
        return isValid;
    }


    // getter e setter
    public void setxMax(int x) {
        this.xMax = x;
    }
    public void setyMax(int y) {
        this.yMax = y;
    }
    public void setxMin(int x) {
        this.xMin = x;
    }
    public void setyMin(int y) {
        this.yMin = y;
    }

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

    public Face[][] getField() {
        return field;
    }
    // getter e setter


    public int countCornerSymbol(CornerSymbol x) {
        int count = 0;

        for(int i = xMin ; i <= xMax; i++) {
            for(int j = yMin ; j <= yMax; j++) {
                if(field[i][j] != null) {
                    if(!field[i][j].getCornerLL().isHidden()){
                        if(field[i][j].getCornerLL().getSymbol().equals(x)){
                            count = count + 1;
                        }
                    }
                    if(!field[i][j].getCornerUL().isHidden()){
                        if(field[i][j].getCornerUL().getSymbol().equals(x)){
                            count = count + 1;
                        }
                    }
                    if(!field[i][j].getCornerUR().isHidden()){
                        if(field[i][j].getCornerUR().getSymbol().equals(x)){
                            count = count + 1;
                        }
                    }
                    if(!field[i][j].getCornerLR().isHidden()){
                        if(field[i][j].getCornerLR().getSymbol().equals(x)){
                            count = count + 1;
                        }
                    }

                }
            }
        }

        return count;
    }



    public int countBackSymbol(Kingdom x) {
        int count = 0;

        for(int i = xMin ; i <= xMax; i++) {
            for(int j = yMin ; j <= yMax; j++) {
                if(field[i][j] != null && field[i][j] instanceof BackFace) {
                    if(((BackFace) field[i][j]).getPermanentResources().contains(x)){
                        count++;
                    }
                }
            }
        }
        return count;
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



}







