package it.polimi.ingsw.gc27;
import java.util.ArrayList;

public class Manuscript {
    public final int FIELD_DIM = 51;
    // use a matrix to represent the whole manuscript/play field
    private Face[][] field;
    public Manuscript(Face myStarter){
        //initialize the matrix and place the starter card at its centre
        field = new Face[FIELD_DIM][FIELD_DIM];
        field[FIELD_DIM/2][FIELD_DIM/2] = myStarter;
    }
    public void placeCard(Face cardFace, int x, int y){

    };
}
