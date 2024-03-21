package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FaceTest {

    @Test
    void getColour() {
        Corner cornerUR=null;
        cornerUR.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Corner cornerUL=null;
        cornerUL.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Corner cornerLR=null;
        cornerLR.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Corner cornerLL=null;
        cornerLL.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Face face= new Face(Kingdom.PLANTKINGDOM, cornerUR, cornerUL, cornerLR, cornerLL  ) {

            @Override
            public Face copy(Face face) {
                return null;
            }

            @Override
            public ArrayList<Kingdom> getPermanentResources() {
                return null;
            }
        }

        assertEquals((BackFace)Face.getColour(), Kingdom.PLANTKINGDOM);//??
    }

    @Test
    void getCornerUR() {
        Corner cornerUR=null;
        cornerUR.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Corner cornerUL=null;
        cornerUL.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Corner cornerLR=null;
        cornerLR.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Corner cornerLL=null;
        cornerLL.setSymbol(CornerSymbol.ANIMALKINGDOM);
        Face face= new Face(Kingdom.PLANTKINGDOM, cornerUR, cornerUL, cornerLR, cornerLL  ) {

            @Override
            public Face copy(Face face) {
                return null;
            }

            @Override
            public ArrayList<Kingdom> getPermanentResources() {
                return null;
            }
        }
        assertEquals((BackFace)Face.getColour(), Kingdom.PLANTKINGDOM);
    }

    @Test
    void getCornerUL() {
    }

    @Test
    void getCornerLR() {
    }

    @Test
    void getCornerLL() {
    }

    @Test
    void getCorner() {
    }

    @Test
    void copy() {
    }

    @Test
    void getPermanentResources() {
    }
}