package it.polimi.ingsw.gc27;
import java.util.ArrayList;
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
    }

    public void addCard(Card card, Face face, int x, int y){
        int numCoveredCorners = isValidPlacement(x, y);
        if(numCoveredCorners > 0){
            Face copy = face.copy(face);
            field[x][y] = copy;
        }else{
            System.err.println("Error: invalid position");
        }
        addPoints(player, card, face, baord, numCoveredCorners);
    }

    //returns the number of corners covered by the added card
    public int isValidPlacement(int x, int y){
        int count = 0;
        for(int i = -1; i <= 1; i = i + 2){
            for(int j = -1; j <= 1; j = j + 2){
                if(field[x + i][y + j] != null && (field[x + i][y + j].getCorner(-i, j).isBlack() || field[x + i][y + j].getCorner(-i, j).isHidden())){
                    return 0;
                }
                if(field[x + i][y + j] != null){
                    count++;
                }
            }
        }
        return count;
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
}
