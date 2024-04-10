package it.polimi.ingsw.gc27.Game;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Enumerations.*;
import java.util.*;

public class Manuscript {
    public static final int FIELD_DIM = 85;
    // use a matrix to represent the whole manuscript/play field
    private Face[][] field;
    private int xMax;
    private int yMax;
    private int xMin;
    private int yMin;
    private int animalCounter;
    private int fungiCounter;
    private int insectCounter;
    private int plantCounter;
    private int inkwellCounter;
    private int quillCounter;
    private int manuscriptCounter;
    public Manuscript(){
        //initialize the matrix and place the starter card at its centre
        field = new Face[FIELD_DIM][FIELD_DIM];
        //field[FIELD_DIM/2][FIELD_DIM/2] = myStarter;
        setxMax(FIELD_DIM/2);
        setxMin(FIELD_DIM/2);
        setyMax(FIELD_DIM/2);
        setyMin(FIELD_DIM/2);
    }
    public Manuscript(){
        field = new Face[FIELD_DIM][FIELD_DIM];
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

    public void setField(Face[][] field) {
        this.field = field;
    }
    public Face getFace(int x, int y) {
        return this.field[x][y];
    }
    public void setFace(Face face, int x, int y) {
        //credo bisogna aggiungere un eccezione
        field[x][y] = face;
    }

    public int getAnimalCounter() {return animalCounter;}

    public void setAnimalCounter(int animalCounter) {this.animalCounter = animalCounter;}

    public int getFungiCounter() {return fungiCounter;}

    public void setFungiCounter(int fungiCounter) {this.fungiCounter = fungiCounter;}

    public int getInsectCounter() {return insectCounter;}

    public void setInsectCounter(int insectCounter) {this.insectCounter = insectCounter;}

    public int getPlantCounter() {return plantCounter;}

    public void setPlantCounter(int plantCounter) {this.plantCounter = plantCounter;}

    public int getInkwellCounter() {return inkwellCounter;}

    public void setInkwellCounter(int inkwellCounter) {this.inkwellCounter = inkwellCounter;}

    public int getQuillCounter() {return quillCounter;}

    public void setQuillCounter(int quillCounter) {this.quillCounter = quillCounter;}

    public int getManuscriptCounter() {return manuscriptCounter;}

    public void setManuscriptCounter(int manuscriptCounter) {this.manuscriptCounter = manuscriptCounter;}
    // end getter e setter

    public boolean isValidPlacement(int x, int y){
        boolean isValid = false;
        for(int i = -1; i <= 1; i = i + 2){
            for(int j = -1; j <= 1; j = j + 2){
                if(field[x + i][y + j] != null && (field[x + i][y + j].getCorner(-i, j).isBlack() || field[x + i][y + j].getCorner(-i, j).isHidden())){
                    return false;
                }
                if(field[x + i][y + j] != null){
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    // substituted by getCounter()
    /*public int countCornerSymbol(CornerSymbol x) {
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
    }*/

    // substituted by getCounter()
    /*public int countBackSymbol(Kingdom x) {
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
    }*/

    public boolean satisfiedRequirement(ResourceCard card) {
        if(card instanceof GoldCard) {
            ArrayList<Kingdom> toBeVerified = ((GoldCard) card).getRequirements();
            Kingdom tracciato;
            while (!toBeVerified.isEmpty()) {
                tracciato = toBeVerified.getFirst();
                toBeVerified.removeFirst();
                int count = 1;

                for (Kingdom kin : toBeVerified) {
                    if (tracciato.equals(kin)) {
                        count++;
                    }
                }
                int counted = getCounter(tracciato.toCornerSymbol());
                if (counted < count)
                    return false;
            }
            return true;
        }else{
            //if dynamic type is ResourceCard, then it has no requirements.
            return true;
        }
    }

    public int getCounter(CornerSymbol cs){
        return switch (cs){
            case EMPTY -> 0;
            case PLANTKINGDOM -> plantCounter;
            case ANIMALKINGDOM -> animalCounter;
            case INSECTKINGDOM -> insectCounter;
            case FUNGIKINGDOM -> fungiCounter;
            case QUILL -> quillCounter;
            case INKWELL -> inkwellCounter;
            case MANUSCRIPT -> manuscriptCounter;
        };
    }
    public void decreaseCounter(CornerSymbol cs){
        switch (cs){
            case PLANTKINGDOM -> plantCounter--;
            case ANIMALKINGDOM -> animalCounter--;
            case FUNGIKINGDOM -> fungiCounter--;
            case INSECTKINGDOM -> insectCounter--;
            case MANUSCRIPT -> manuscriptCounter--;
            case QUILL -> quillCounter--;
            case INKWELL -> inkwellCounter--;
            case EMPTY -> {}   // do nothing
            case null, default -> throw new NullPointerException();
        }
    }
    public void increaseCounter(CornerSymbol cs){
        switch (cs){
            case PLANTKINGDOM -> plantCounter++;
            case ANIMALKINGDOM -> animalCounter++;
            case FUNGIKINGDOM -> fungiCounter++;
            case INSECTKINGDOM -> insectCounter++;
            case MANUSCRIPT -> manuscriptCounter++;
            case QUILL -> quillCounter++;
            case INKWELL -> inkwellCounter++;
            case EMPTY -> {}   // do nothing
            case null, default -> throw new NullPointerException();
        }
    }

}