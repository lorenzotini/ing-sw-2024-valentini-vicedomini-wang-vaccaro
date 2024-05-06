package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.ColourControl;

public class DoublePattern extends ObjectiveCard {
    //  NOTA: DEVE CONTENERE INKWELL, MANUSCRIPT O QUILL
    public final int OBJECTIVE_POINTS = 2;
    private CornerSymbol cornerSymbol;

    public DoublePattern(int id, FrontFace front, BackFace back, CornerSymbol cornerSymbol) {
        super(id, front, back);
        this.cornerSymbol = cornerSymbol;
    }
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count;

        count = manuscript.getCounter(this.cornerSymbol);

        return OBJECTIVE_POINTS*(count/2);
    }

    @Override
    protected String paintString(String s) {
        return ColourControl.YELLOW + s + ColourControl.RESET;
    }

    @Override
    public String toCliCard(){
        String first = paintString("╭-----------------╮");
        String second = paintString("| ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + paintString("          |");
        String third = paintString("|                 |");
        String fourth = paintString("| " + this.cornerSymbol.toCliString().repeat(2)) + paintString("              |");
        String fifth = paintString("╰-----------------╯");
        return first + "\n" + second + "\n" + third + "\n" + fourth + "\n" + fifth;
    }

}
