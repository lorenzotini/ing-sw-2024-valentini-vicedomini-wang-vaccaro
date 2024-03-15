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
}
