package it.polimi.ingsw.gc27.Model.Enumerations;

import java.io.Serializable;

public enum PawnColour implements Serializable {
    BLUE,
    YELLOW,
    GREEN,
    RED;

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
        };
    }

}
