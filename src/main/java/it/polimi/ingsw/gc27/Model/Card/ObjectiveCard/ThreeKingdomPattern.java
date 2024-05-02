package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

public class ThreeKingdomPattern extends ObjectiveCard {
    //ATTENZIONE: IL PARSER NON PRENDE IL CORNERSYMBOL. MODIFICARE METODO O PARLA CON LORI
    public final int OBJECTIVE_POINTS = 2;
    private Kingdom kingdom;

    public ThreeKingdomPattern(int id, FrontFace front, BackFace back, Kingdom kingdom) {
        super(id, front, back);
        this.kingdom = kingdom;
    }

    //ATTENZIONE: IL PARSER NON PRENDE IL CORNERSYMBOL. MODIFICARE METODO O PARLA CON LORI
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {

        int count = manuscript.getCounter(this.kingdom.toCornerSymbol());

        return OBJECTIVE_POINTS*(count/3);
    }

    @Override
    public String toString(){
        return "ThreeKingdPatt   ";
    }
}
