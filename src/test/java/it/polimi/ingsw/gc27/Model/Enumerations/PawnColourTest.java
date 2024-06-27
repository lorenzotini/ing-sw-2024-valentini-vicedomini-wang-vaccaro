package it.polimi.ingsw.gc27.Model.Enumerations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnColourTest {
    @Test
    void fromStringToPawnColour() {
        assertEquals(PawnColour.BLUE, PawnColour.fromStringToPawnColour("blue"));
        assertEquals(PawnColour.RED, PawnColour.fromStringToPawnColour("red"));
        assertEquals(PawnColour.YELLOW, PawnColour.fromStringToPawnColour("yellow"));
        assertEquals(PawnColour.GREEN, PawnColour.fromStringToPawnColour("green"));

    }
}