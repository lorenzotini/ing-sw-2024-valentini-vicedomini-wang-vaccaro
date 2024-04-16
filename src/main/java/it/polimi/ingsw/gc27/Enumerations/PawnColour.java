package it.polimi.ingsw.gc27.Enumerations;

import java.io.Serializable;

public enum PawnColour implements Serializable {
    BLUE,
    YELLOW,
    GREEN,
    RED,
    BLACK;
    public static PawnColour fromStringToPawnColour(String s){
        return switch (s.toLowerCase()) {
            case "blue" -> PawnColour.BLUE;
            case "yellow" -> PawnColour.YELLOW;
            case "green" -> PawnColour.GREEN;
            case "red" -> PawnColour.RED;
            default -> throw new IllegalArgumentException("Invalid color: " + s);
        };
    }
}
