package it.polimi.ingsw.gc27;
import java.util.*;
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
            addPoints(player, card, face, baord, numCoveredCorners);
        }else{
            System.err.println("Error: invalid position");
        }
    }

    //returns the number of corners covered by the added card
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
    public void setyMax(int y) {
        this.yMax = y;
    }
    public void setxMin(int x) {
        this.xMin = x;
    }
    public void setyMin(int y) {
        this.yMin = y;
    }




    public void addPointsObjective(ObjectiveCard objective, Board board, Player player) {
        int points = 0;
        int count = 0;
        int min = 0;
        int[][] checked = new int[81][81];

        if(objective.getPattern().equals(ObjectiveRequirementType.THREEPLANTKINGDOM) ||
                objective.getPattern().equals(ObjectiveRequirementType.THREEFUNGIKINGDOM) ||
                objective.getPattern().equals(ObjectiveRequirementType.THREEINSECTKINGDOM) ||
                objective.getPattern().equals(ObjectiveRequirementType.THREEANIMALKINGDOM))
        {
            count = countCornerSymbol(objective.getPattern().getCornerSymbol());
            count = count + countBackSymbol(objective.getPattern().getKingdom());
            points = 2*(count/3);
        }


        if(objective.getPattern().equals(ObjectiveRequirementType.DOUBLEINKWELL) ||
                objective.getPattern().equals(ObjectiveRequirementType.DOUBLEMANUSCRIPT) ||
                objective.getPattern().equals(ObjectiveRequirementType.DOUBLEQUILL))
        {
            count = countCornerSymbol(objective.getPattern().getCornerSymbol());
            points = 2*(count/2);
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.EACHDIFFERENTTYPE)) {
            count = countCornerSymbol(CornerSymbol.QUILL);
            if(count<=min) {
                min = count;
            }
            count = countCornerSymbol(CornerSymbol.MANUSCRIPT);
            if(count<=min) {
                min = count;
            }
            count = countCornerSymbol(CornerSymbol.INKWELL);
            if(count<=min) {
                min = count;
            }
            points = 3*min;
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.BLUELADDER)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.ANIMALKINGDOM) && i >= 2 && j >= 2 && checked[i][j] == 0) {
                            if (field[i - 1][j - 1].getColour().equals(Kingdom.ANIMALKINGDOM) && field[i - 2][j - 2].getColour().equals(Kingdom.ANIMALKINGDOM)) {
                                points = points + 2;
                                checked[i - 1][j - 1] = 1;
                                checked[i - 2][j - 2] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.REDLADDER)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.FUNGIKINGDOM) && i >= 2 && j >= 2 && checked[i][j] == 0) {
                            if (field[i - 1][j - 1].getColour().equals(Kingdom.FUNGIKINGDOM) && field[i - 2][j - 2].getColour().equals(Kingdom.FUNGIKINGDOM)) {
                                points = points + 2;
                                checked[i - 1][j - 1] = 1;
                                checked[i - 2][j - 2] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.GREENLADDER)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.PLANTKINGDOM) && i <=78 && j <=78 && checked[i][j] == 0) {
                            if (field[i + 1][j + 1].getColour().equals(Kingdom.PLANTKINGDOM) && field[i + 2][j + 2].getColour().equals(Kingdom.PLANTKINGDOM)) {
                                points = points + 2;
                                checked[i + 1][j + 1] = 1;
                                checked[i + 2][j + 2] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.PURPLELADDER)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.INSECTKINGDOM) && i <=78 && j <=78 && checked[i][j] == 0) {
                            if (field[i + 1][j + 1].getColour().equals(Kingdom.INSECTKINGDOM) && field[i + 2][j + 2].getColour().equals(Kingdom.INSECTKINGDOM)) {
                                points = points + 2;
                                checked[i + 1][j + 1] = 1;
                                checked[i + 2][j + 2] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONELOWERLEFT)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.PLANTKINGDOM) && i >=1 && j >=3 && checked[i][j] == 0) {
                            if (field[i][j - 2].getColour().equals(Kingdom.PLANTKINGDOM) && field[i -1][j -3].getColour().equals(Kingdom.INSECTKINGDOM)) {
                                points = points + 3;
                                checked[i][j -2] = 1;
                                checked[i - 1][j -3] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONELOWERRIGHT)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.FUNGIKINGDOM) && i <=79 && j >=3 && checked[i][j] == 0) {
                            if (field[i][j - 2].getColour().equals(Kingdom.FUNGIKINGDOM) && field[i + 1][j -3].getColour().equals(Kingdom.PLANTKINGDOM)) {
                                points = points + 3;
                                checked[i][j -2] = 1;
                                checked[i + 1][j - 3] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONEUPPERLEFT)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.ANIMALKINGDOM) && i <=79 && j >=3 && checked[i][j] == 0) {
                            if (field[i + 1][j - 1].getColour().equals(Kingdom.INSECTKINGDOM) && field[i + 1][j - 3].getColour().equals(Kingdom.INSECTKINGDOM)) {
                                points = points + 3;
                                checked[i + 1][j - 1] = 1;
                                checked[i + 1][j - 3] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }

        if(objective.getPattern().equals(ObjectiveRequirementType.TWOPLUSONEUPPERRIGHT)) {
            for(int i = xMin; i<= xMax; i++) {
                for(int j = yMax; j >= yMin; j--){
                    if(field[i][j] != null) {
                        if (field[i][j].getColour().equals(Kingdom.FUNGIKINGDOM) && i >=1 && j >=3 && checked[i][j] == 0) {
                            if (field[i - 1][j - 1].getColour().equals(Kingdom.ANIMALKINGDOM) && field[i - 1][j - 3].getColour().equals(Kingdom.ANIMALKINGDOM)) {
                                points = points + 3;
                                checked[i - 1][j - 1] = 1;
                                checked[i - 1][j - 3] = 1;
                            }
                        }
                        checked[i][j] = 1;
                    }
                }
            }
        }



    }



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
}






















