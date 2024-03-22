package it.polimi.ingsw.gc27.Card;

import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class ThreeKingdomPattern extends ObjectiveCard{
    //ATTENZIONE: IL PARSER NON PRENDE IL CORNERSYMBOL. MODIFICARE METODO O PARLA CON LORI
    private CornerSymbol cornerSymbol;
    public final int OBJECTIVE_POINTS = 2;
    private Kingdom kingdom;

    public ThreeKingdomPattern(int id, FrontFace front, BackFace back, Kingdom kingdom) {
        super(id, front, back);
        this.kingdom = kingdom;
    }

    //ATTENZIONE: IL PARSER NON PRENDE IL CORNERSYMBOL. MODIFICARE METODO O PARLA CON LORI
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count = 0;
        int points = 0;

        count = manuscript.countCornerSymbol(this.cornerSymbol);
        count = count + manuscript.countBackSymbol(this.kingdom);
        points = 2*(count/3);

        return points;
    }
}
