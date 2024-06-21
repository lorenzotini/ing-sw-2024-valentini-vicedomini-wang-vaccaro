package it.polimi.ingsw.gc27.Model.Enumerations;

import java.io.Serializable;

/**
 * The PawnColour enum represents the different colors that a pawn can have in the game.
 * Each color is represented as a separate enum constant.
 * This enum is serializable, allowing it to be written to an output stream and read back.
 */
public enum PawnColour implements Serializable {

    /**
     * Represents a blue pawn.
     */
    BLUE,

    /**
     * Represents a yellow pawn.
     */
    YELLOW,

    /**
     * Represents a green pawn.
     */
    GREEN,

    /**
     * Represents a red pawn.
     */
    RED,

    /**
     * Represents a black pawn.
     */
    BLACK;

    /**
     * Converts a string to its corresponding PawnColour.
     *
     * @param s The string representation of the color.
     * @return The corresponding PawnColour.
     * @throws IllegalArgumentException If the string does not represent a valid color.
     */
    public static PawnColour fromStringToPawnColour(String s){
        return switch (s.toLowerCase()) {
            case "blue" -> PawnColour.BLUE;
            case "yellow" -> PawnColour.YELLOW;
            case "green" -> PawnColour.GREEN;
            case "red" -> PawnColour.RED;
            default -> throw new IllegalArgumentException("Invalid color: " + s);
        };
    }

    public String getPathImage(){
        return switch (this){
            case BLUE -> "Images/utility/blue_pawn.png";
            case RED -> "Images/utility/red_pawn.png";
            case GREEN -> "Images/utility/green_pawn.png";
            case YELLOW -> "Images/utility/yellow_pawn.png";
            case BLACK -> null;
        };
    }

}
