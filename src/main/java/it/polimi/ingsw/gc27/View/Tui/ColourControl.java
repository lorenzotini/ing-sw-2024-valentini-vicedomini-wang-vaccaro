package it.polimi.ingsw.gc27.View.Tui;

import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;

public class ColourControl {

    //how to use ---> ex. type : "ColourControl.RED"
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String BOLD = "\u001B[1m";

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN


    // Background
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW

    // High Intensity
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE



    public static String get(PawnColour pawnColour){
        return switch (pawnColour) {
            case RED -> RED_BRIGHT;
            case YELLOW -> YELLOW_BRIGHT;
            case GREEN -> GREEN_BRIGHT;
            case BLUE -> BLUE_BRIGHT;
            case BLACK -> "";
        };
    }

}

