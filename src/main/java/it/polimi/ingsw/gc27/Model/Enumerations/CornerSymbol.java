package it.polimi.ingsw.gc27.Model.Enumerations;

import it.polimi.ingsw.gc27.View.ColourControl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * The CornerSymbol enum represents the different symbols that can appear in the cards' corners.
 * Each symbol has a corresponding string representation for CLI output.
 * This enum is serializable, allowing it to be written to an output stream and read back.
 * Objective cards have EMPTY corners by default.
 */
public enum CornerSymbol implements Serializable {

    /**
     * Represents an empty corner.
     */
    EMPTY(" "),

    /**
     * Represents a plant symbol.
     */
    PLANT(ColourControl.GREEN_BOLD + "P" + ColourControl.RESET),

    /**
     * Represents an animal symbol.
     */
    ANIMAL(ColourControl.CYAN_BOLD + "A" + ColourControl.RESET),

    /**
     * Represents an insect symbol.
     */
    INSECT(ColourControl.PURPLE_BOLD + "I" + ColourControl.RESET),

    /**
     * Represents a fungi symbol.
     */
    FUNGI(ColourControl.RED_BOLD + "F" + ColourControl.RESET),

    /**
     * Represents a quill symbol.
     */
    QUILL(ColourControl.YELLOW_BOLD + "q" + ColourControl.RESET),

    /**
     * Represents an inkwell symbol.
     */
    INKWELL(ColourControl.YELLOW_BOLD + "i" + ColourControl.RESET),

    /**
     * Represents a manuscript symbol.
     */
    MANUSCRIPT(ColourControl.YELLOW_BOLD + "m" + ColourControl.RESET),

    /**
     * Represents a black symbol.
     */
    BLACK(ColourControl.BLACK_BOLD + "■" + ColourControl.RESET);

    /**
     * The string representation of the symbol for CLI output.
     */
    private final String initials;

    /**
     * Constructs a new CornerSymbol with the given string representation.
     *
     * @param initials The string representation of the symbol.
     */
    CornerSymbol(String initials) {
        this.initials = initials;
    }

    /**
     * Returns the string representation of the symbol for CLI output.
     *
     * @return The string representation of the symbol.
     */
    public String toCliString() {
        return initials;
    }

    /**
     * Returns a list of all the CornerSymbol values.
     *
     * @return A list of all the CornerSymbol values.
     */
    public static List<CornerSymbol> valuesList() {
        return Arrays.asList(CornerSymbol.values());
    }

}
