package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.ColourControl;

public class DifferentPattern extends ObjectiveCard {

    public final int OBJECTIVE_POINTS = 3;
    public DifferentPattern(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }

    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count;
        int points;
        int min = 9999;

        count = manuscript.getCounter(CornerSymbol.QUILL);
        if(count<=min) {
            min = count;
        }
        count = manuscript.getCounter(CornerSymbol.MANUSCRIPT);
        if(count<=min) {
            min = count;
        }
        count = manuscript.getCounter(CornerSymbol.INKWELL);
        if(count<=min) {
            min = count;
        }
        points = OBJECTIVE_POINTS * min;
        return points;
    }

    @Override
    protected String paintString(String s) {
        return ColourControl.YELLOW + s + ColourControl.RESET;
    }

    @Override
    public String toCliCard(){
        String first = paintString("╔═════════════════╗");
        String second = paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + paintString("          ║");
        String third = paintString("║                 ║");
        String fourth = paintString("║ QMI             ║");
        String fifth =  paintString("╚═════════════════╝");
        return first + "\n" + second + "\n" + third + "\n" + fourth + "\n" + fifth;
    }

}
