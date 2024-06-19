
package it.polimi.ingsw.gc27.Model.Enumerations;

import java.io.Serializable;

/**
 * The PointsMultiplier enum represents the different multipliers that can be applied to card points in the game.
 * Each multiplier is represented as a separate enum constant.
 * This enum is serializable, allowing it to be written to an output stream and read back.
 */
public enum PointsMultiplier implements Serializable {

    /**
     * Represents a corner multiplier.
     */
    CORNER,

    /**
     * Represents a quill multiplier.
     */
    QUILL,

    /**
     * Represents an inkwell multiplier.
     */
    INKWELL,

    /**
     * Represents a manuscript multiplier.
     */
    MANUSCRIPT,

    /**
     * Represents an empty multiplier.
     */
    EMPTY;

    /**
     * Converts this PointsMultiplier to its string representation.
     *
     * @return The string representation of the multiplier.
     */
    public String toString(){
        return switch (this) {
            case CORNER -> "c";
            case QUILL -> "q";
            case INKWELL -> "i";
            case MANUSCRIPT -> "m";
            case EMPTY -> " ";
        };
    }

    /**
     * Converts this PointsMultiplier to its corresponding CornerSymbol.
     *
     * @return The corresponding CornerSymbol.
     * @throws IllegalArgumentException If the PointsMultiplier does not have a corresponding CornerSymbol.
     */
    public CornerSymbol toCornerSymbol() {
        try{
            return switch (this) {
                case QUILL -> CornerSymbol.QUILL;
                case INKWELL-> CornerSymbol.INKWELL;
                case MANUSCRIPT -> CornerSymbol.MANUSCRIPT;
                default -> throw new IllegalArgumentException();
            };
        } catch (IllegalArgumentException e){
            System.err.println("You can't convert this points multiplier to a corner symbol: " + this);
            e.printStackTrace();
            return null;
        }
    }

}
