package it.polimi.ingsw.gc27.Model.Enumerations;

import java.io.Serializable;

public enum CornerSymbol implements Serializable {
    EMPTY(" "),
    PLANTKINGDOM("\u001B[32m" + "\u001B[1m" + "P" + "\u001B[0m"),
    ANIMALKINGDOM("\u001B[36m" + "\u001B[1m" + "A" + "\u001B[0m"),
    INSECTKINGDOM("\u001B[35m" + "\u001B[1m" + "I" + "\u001B[0m"),
    FUNGIKINGDOM("\u001B[31m" + "\u001B[1m" + "F" + "\u001B[0m"),
    QUILL("\u001B[33m" + "\u001B[1m" + "q" + "\u001B[0m"),
    INKWELL("\u001B[33m" + "\u001B[1m" + "i" + "\u001B[0m"),
    MANUSCRIPT("\u001B[33m" + "\u001B[1m" + "m" + "\u001B[0m");
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