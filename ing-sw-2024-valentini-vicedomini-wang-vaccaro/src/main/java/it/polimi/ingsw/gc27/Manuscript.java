package it.polimi.ingsw.gc27;
import java.util.*;
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


    public void addPointsObjective(ObjectiveCard objective, Board board, Player player) {
        int points = 0;
        int count = 0;

        if(objective.getPattern().equals(ObjectiveRequirementType.THREEPLANTKINGDOM)) {
            count = countCornerSymbol();
        }
        if(objective.getPattern().equals(ObjectiveRequirementType.THREEANIMALKINGDOM)) {

        }
        if(objective.getPattern().equals(ObjectiveRequirementType.THREEINSECTKINGDOM)) {

        }
        if(objective.getPattern().equals(ObjectiveRequirementType.THREEFUNGIKINGDOM)) {

        }


        if(objective.getPattern().equals(ObjectiveRequirementType.DOUBLEINKWELL) ||
                objective.getPattern().equals(ObjectiveRequirementType.DOUBLEMANUSCRIPT) ||
                objective.getPattern().equals(ObjectiveRequirementType.DOUBLEQUILL)
        ) {

        }


        if(objective.getPattern().equals(ObjectiveRequirementType.EACHDIFFERENTTYPE)) {

        }


        if(objective.getPattern().equals(ObjectiveRequirementType.BLUELADDER) ||
                objective.getPattern().equals(ObjectiveRequirementType.GREENLADDER) ||
                objective.getPattern().equals(ObjectiveRequirementType.PURPLELADDER) ||
                objective.getPattern().equals(ObjectiveRequirementType.REDLADDER)
        ) {

        }

        if(objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONELOWERLEFT) ||
                objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONELOWERRIGHT) ||
                objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONEUPPERLEFT) ||
                objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONEUPPERRIGHT)
        ) {

        }



    }

    public int countCornerSymbol(CornerSymbol x) {
        int count = 0;

        for(int i = (FIELD_DIM/2) - xMin ; i <= (FIELD_DIM/2) + xMax; i++) {
            for(int j = (FIELD_DIM/2) - yMin ; j <= (FIELD_DIM/2) + yMax; j++) {
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

        for(int i = (FIELD_DIM/2) - xMin ; i <= (FIELD_DIM/2) + xMax; i++) {
            for(int j = (FIELD_DIM/2) - yMin ; j <= (FIELD_DIM/2) + yMax; j++) {
                if(field[i][j] != null && field[i][j] instanceof BackFace) {
                    if(((BackFace) field[i][j]).getPermanentResources().contains(x)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
}






















