package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerTest {

    @Test
    void isHidden() {
        Corner corner = new Corner();
        Assertions.assertFalse(corner.isHidden());
    }

    @Test
    void setHidden() {
        Corner corner = new Corner();
        corner.setHidden(true);
        assertTrue(corner.isHidden());
    }

    @Test
    void isBlack() {
        Corner corner = new Corner();
        assertFalse(corner.isBlack());
    }

    @Test
    void setBlack(){
        Corner corner = new Corner();
        corner.setBlack(true);
        assertTrue(corner.isBlack());
    }

    @Test
    void getSymbol() {
        Corner corner = new Corner();
        assertNull(corner.getSymbol());
    }
    @Test
    void setSymbol(){
        Corner corner = new Corner();
        corner.setSymbol(CornerSymbol.PLANTKINGDOM);
        assertEquals(corner.getSymbol(), CornerSymbol.PLANTKINGDOM);

    }
}