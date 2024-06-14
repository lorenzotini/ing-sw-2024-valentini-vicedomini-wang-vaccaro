package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientManuscript;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.io.Serializable;
import java.util.ArrayList;

public class Manuscript implements Serializable, ClientManuscript {

    public static final int FIELD_DIM = 85;
    private Face[][] field;  // use a matrix to represent the whole manuscript/play field
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
    private String lastPlacedCardPath;
    private ArrayList<Placement> placements = new ArrayList<>(); // save the placement order

    public Manuscript() {
        this.field = new Face[FIELD_DIM][FIELD_DIM];
        this.xMax = FIELD_DIM / 2;
        this.xMin = FIELD_DIM / 2;
        this.yMax = FIELD_DIM / 2;
        this.yMin = FIELD_DIM / 2;
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
    @Override
    public int getxMax() {
        return xMax;
    }
    @Override
    public int getyMax() {
        return yMax;
    }
    @Override
    public int getxMin() {
        return xMin;
    }
    @Override
    public int getyMin() {
        return yMin;
    }
    @Override
    public Face[][] getField() {
        return field;
    }
    @Override
    public int getAnimalCounter() {
        return animalCounter;
    }
    @Override
    public int getFungiCounter() {
        return fungiCounter;
    }
    @Override
    public int getInsectCounter() {
        return insectCounter;
    }

    @Override
    public int getPlantCounter() {
        return plantCounter;
    }

    @Override
    public int getInkwellCounter() {
        return inkwellCounter;
    }

    @Override
    public int getQuillCounter() {
        return quillCounter;
    }

    @Override
    public int getManuscriptCounter() {
        return manuscriptCounter;
    }

    public String getLastPlacedCardPath() {
        return lastPlacedCardPath;
    }

    public void setLastPlacedCardPath(String lastPlacedCardPath) {
        this.lastPlacedCardPath = lastPlacedCardPath;
    }

    // end getter e setter
    @Override
    public boolean isValidPlacement(int x, int y) {
        boolean isValid = false;
        if (field[x][y] != null) {
            return false;
        }
        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                if (field[x + i][y + j] != null && (field[x + i][y + j].getCorner(-i, j).isBlack() || field[x + i][y + j].getCorner(-i, j).isHidden())) {
                    return false;
                }
                if (field[x + i][y + j] != null) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }
    @Override
    public ArrayList<Placement> getPlacements() {
        return placements;
    }
    @Override
    public boolean satisfiedRequirement(ResourceCard card) {
        if (card instanceof GoldCard) {
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
        } else {
            //if dynamic type is ResourceCard, then it has no requirements.
            return true;
        }
    }
    @Override
    public int getCounter(CornerSymbol cs) {
        return switch (cs) {
            case EMPTY, BLACK -> 0;
            case PLANT -> plantCounter;
            case ANIMAL -> animalCounter;
            case INSECT -> insectCounter;
            case FUNGI -> fungiCounter;
            case QUILL -> quillCounter;
            case INKWELL -> inkwellCounter;
            case MANUSCRIPT -> manuscriptCounter;
        };
    }

    public void decreaseCounter(CornerSymbol cs) {
        switch (cs) {
            case PLANT -> plantCounter--;
            case ANIMAL -> animalCounter--;
            case FUNGI -> fungiCounter--;
            case INSECT -> insectCounter--;
            case MANUSCRIPT -> manuscriptCounter--;
            case QUILL -> quillCounter--;
            case INKWELL -> inkwellCounter--;
            case EMPTY -> {
            }   // do nothing
            case null, default -> throw new NullPointerException();
        }
    }

    public void increaseCounter(CornerSymbol cs) {
        switch (cs) {
            case PLANT -> plantCounter++;
            case ANIMAL -> animalCounter++;
            case FUNGI -> fungiCounter++;
            case INSECT -> insectCounter++;
            case MANUSCRIPT -> manuscriptCounter++;
            case QUILL -> quillCounter++;
            case INKWELL -> inkwellCounter++;
            case EMPTY, BLACK -> {
            }   // do nothing
            case null, default -> throw new NullPointerException();
        }
    }

}