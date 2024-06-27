package it.polimi.ingsw.gc27.Model.Enumerations;

import it.polimi.ingsw.gc27.View.Tui.ColourControl;

import java.io.Serializable;

/**
 * The Kingdom enum represents the different kingdoms that can exist in the game.
 * Each kingdom has a corresponding CornerSymbol and a color for CLI output.
 * This enum is serializable, allowing it to be written to an output stream and read back.
 */
public enum Kingdom implements Serializable {

    /**
     * Represents an empty kingdom.
     */
    EMPTY,

    /**
     * Represents a plant kingdom.
     */
    PLANTKINGDOM,

    /**
     * Represents an animal kingdom.
     */
    ANIMALKINGDOM,

    /**
     * Represents an insect kingdom.
     */
    INSECTKINGDOM,

    /**
     * Represents a fungi kingdom.
     */
    FUNGIKINGDOM;

    /**
     * Converts this Kingdom to its corresponding CornerSymbol.
     *
     * @return The corresponding CornerSymbol.
     */
    public CornerSymbol toCornerSymbol() {
        return switch (this) {
            case FUNGIKINGDOM -> CornerSymbol.FUNGI;
            case PLANTKINGDOM -> CornerSymbol.PLANT;
            case ANIMALKINGDOM -> CornerSymbol.ANIMAL;
            case INSECTKINGDOM -> CornerSymbol.INSECT;
            case EMPTY -> CornerSymbol.EMPTY;
        };
        //TODO:aggiungere Ecception
    }

    /**
     * Converts this Kingdom to its corresponding color for CLI output.
     *
     * @return The corresponding ANSI color escape codes for CLI output.
     */
    public String toColourControl() {
        return switch (this) {
            case FUNGIKINGDOM -> ColourControl.RED;
            case PLANTKINGDOM -> ColourControl.GREEN;
            case ANIMALKINGDOM -> ColourControl.CYAN;
            case INSECTKINGDOM -> ColourControl.PURPLE;
            case EMPTY -> ColourControl.YELLOW;
        };
    }


}
