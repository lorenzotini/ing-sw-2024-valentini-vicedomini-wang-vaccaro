package it.polimi.ingsw.gc27.Model.Enumerations;

import it.polimi.ingsw.gc27.View.ColourControl;

import java.io.Serializable;

public enum CornerSymbol implements Serializable {
    EMPTY(" "),
    PLANTKINGDOM(ColourControl.GREEN_BOLD + "P" + ColourControl.RESET),
    ANIMALKINGDOM(ColourControl.CYAN_BOLD + "A" + ColourControl.RESET),
    INSECTKINGDOM(ColourControl.PURPLE_BOLD + "I" + ColourControl.RESET),
    FUNGIKINGDOM(ColourControl.RED_BOLD + "F" + ColourControl.RESET),
    QUILL(ColourControl.YELLOW_BOLD + "q" + ColourControl.RESET),
    INKWELL(ColourControl.YELLOW_BOLD + "i" + ColourControl.RESET),
    MANUSCRIPT(ColourControl.YELLOW_BOLD + "m" + ColourControl.RESET),
    BLACK(ColourControl.BLACK_BOLD + "■" + ColourControl.RESET);

    private final String initials;

    CornerSymbol(String initials) {
        this.initials = initials;
    }

    public String toCliString() {
        return initials;
    }
}


// "\u001B[0m" reset
// "\u001B[1m" bold
// "\u001B[4m" underline
// "\u001B[7m" inverted colors
// "\u001B[4Xm" (replace X with the color code) background color