package it.polimi.ingsw.gc27.Enumerations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointsMultiplierTest {

    @Test
    void toCornerSymbolTest() {
        assertNull(PointsMultiplier.CORNER.toCornerSymbol());
        assertNull(PointsMultiplier.EMPTY.toCornerSymbol());
    }


}