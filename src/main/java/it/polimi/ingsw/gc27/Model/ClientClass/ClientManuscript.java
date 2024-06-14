package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Placement;

import java.util.ArrayList;

public interface ClientManuscript {
    int getCounter(CornerSymbol cs);
    boolean satisfiedRequirement(ResourceCard card);
    ArrayList<Placement> getPlacements();
    boolean isValidPlacement(int x, int y);
    int getManuscriptCounter();
    int getQuillCounter();
    int getInkwellCounter();
    int getPlantCounter();
    int getInsectCounter();
    int getFungiCounter();
    int getAnimalCounter();
    Face[][] getField();
    int getxMax();

    int getyMax();

    int getxMin();

    int getyMin();
}
