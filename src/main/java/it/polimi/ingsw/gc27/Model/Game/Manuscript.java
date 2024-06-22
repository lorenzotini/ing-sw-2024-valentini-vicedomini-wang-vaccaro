package it.polimi.ingsw.gc27.Model.Game;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientManuscript;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the manuscript or playing field of a player in the game.
 */
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

    /**
     * constructor for the manuscript/playing field
     * it sets the dimension of the matrix and the indexes that show
     * the max extension of the matrix
     */
    public Manuscript() {
        this.field = new Face[FIELD_DIM][FIELD_DIM];
        this.xMax = FIELD_DIM / 2;
        this.xMin = FIELD_DIM / 2;
        this.yMax = FIELD_DIM / 2;
        this.yMin = FIELD_DIM / 2;
    }

    /**
     * checks if the placement of a specific card is valid or not
     * @param x position index
     * @param y position index
     * @return boolean
     */
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

    /**
     * getter for playable placements
     * @return arraylist of placement
     */
    @Override
    public ArrayList<Placement> getPlacements() {
        return placements;
    }

    /**
     * indicates if a card meets the requirements to be played and placed
     * @param card the specific card
     * @return boolean
     */
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

    /**
     * getter that returns the amount of symbols present in the manuscript
     * @param cs corner symbol
     * @return the number of symbol in the manuscript
     */
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

    /**
     * decreases the amount of symbols present in the manuscript
     * @param cs corner symbol
     */
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

    /**
     * increases the amount of symbols present in the manuscript
     * @param cs corner symbol
     */
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

    /**
     * Gets the maximum X coordinate of the manuscript
     * @return The maximum X coordinate
     */
    @Override
    public int getxMax() {
        return xMax;
    }

    /**
     * Sets the maximum X coordinate of the manuscript
     * @param x The maximum X coordinate to set
     */
    public void setxMax(int x) {
        this.xMax = x;
    }

    /**
     * Gets the maximum Y coordinate of the manuscript
     * @return The maximum Y coordinate
     */
    @Override
    public int getyMax() {
        return yMax;
    }

    /**
     * Sets the maximum Y coordinate of the manuscript
     * @param y The maximum Y coordinate to set
     */
    public void setyMax(int y) {
        this.yMax = y;
    }

    /**
     * Gets the minimum X coordinate of the manuscript
     * @return The minimum X coordinate
     */
    @Override
    public int getxMin() {
        return xMin;
    }

    /**
     * Sets the minimum X coordinate of the manuscript
     * @param x The minimum X coordinate to set
     */
    public void setxMin(int x) {
        this.xMin = x;
    }

    /**
     * Gets the minimum Y coordinate of the manuscript
     * @return The minimum Y coordinate
     */
    @Override
    public int getyMin() {
        return yMin;
    }

    /**
     * Sets the minimum Y coordinate of the manuscript
     * @param y The minimum Y coordinate to set
     */
    public void setyMin(int y) {
        this.yMin = y;
    }

    /**
     * Gets the matrix representing the manuscript
     *
     * @return The manuscript
     */
    @Override
    public Face[][] getField() {
        return field;
    }

    /**
     * Gets the counter for the number of animal symbols in the manuscript
     * @return The count of animal symbols
     */
    @Override
    public int getAnimalCounter() {
        return animalCounter;
    }

    /**
     * Gets the counter for the number of fungi symbols in the manuscript
     * @return The count of fungi symbols
     */
    @Override
    public int getFungiCounter() {
        return fungiCounter;
    }

    /**
     * Gets the counter for the number of insect symbols in the manuscript
     * @return The count of insect symbols
     */
    @Override
    public int getInsectCounter() {
        return insectCounter;
    }

    /**
     * Gets the counter for the number of plant symbols in the manuscript
     * @return The count of plant symbols
     */
    @Override
    public int getPlantCounter() {
        return plantCounter;
    }

    /**
     * Gets the counter for the number of inkwell symbols in the manuscript
     * @return The count of inkwell symbols
     */
    @Override
    public int getInkwellCounter() {
        return inkwellCounter;
    }

    /**
     * Gets the counter for the number of quill symbols in the manuscript
     * @return The count of quill symbols
     */
    @Override
    public int getQuillCounter() {
        return quillCounter;
    }

    /**
     * Gets the counter for the number of manuscript symbols in the manuscript
     * @return The count of manuscript symbols
     */
    @Override
    public int getManuscriptCounter() {
        return manuscriptCounter;
    }

    /**
     * Gets the path of the last placed card on the manuscript
     * @return The path of the last placed card
     */
    public String getLastPlacedCardPath() {
        return lastPlacedCardPath;
    }

    /**
     * Sets the path of the last placed card on the manuscript
     * @param lastPlacedCardPath The path of the last placed card to set
     */
    public void setLastPlacedCardPath(String lastPlacedCardPath) {
        this.lastPlacedCardPath = lastPlacedCardPath;
    }

}