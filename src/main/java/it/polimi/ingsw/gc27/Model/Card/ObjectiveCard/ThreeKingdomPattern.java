package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.ColourControl;

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
    protected String paintString(String s) {
        return this.kingdom.toColourControl() + s + ColourControl.RESET;
    }

    @Override
    public String toCliCard(){
        String kingdom = this.kingdom.toCornerSymbol().toCliString();
        String first =  paintString("╔═════════════════╗");
        String second = paintString("║ ") + "pts: " + this.OBJECTIVE_POINTS + paintString("          ║");
        String third = paintString("║          ") + kingdom + paintString("      ║");
        String fourth = paintString("║         ") + kingdom + " " + kingdom + paintString("     ║");
        String fifth = paintString("╚═════════════════╝");
        return first + "\n" + second + "\n" + third + "\n" + fourth + "\n" + fifth;
    }

}
