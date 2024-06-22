package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.Tui.ColourControl;

/**
 * The DoublePattern class represents a specific type of objective card
 * This class is used to check for and score a "DoublePattern" objective in the game
 */
public class DoublePattern extends ObjectiveCard {
    public final int OBJECTIVE_POINTS = 2;
    private CornerSymbol cornerSymbol;

    /**
     * constructor matching super {@link ObjectiveCard}
     * @param id card's id
     * @param front front face
     * @param back back face
     * @param cornerSymbol the objective symbol counted
     */
    public DoublePattern(int id, FrontFace front, BackFace back, CornerSymbol cornerSymbol) {
        super(id, front, back);
        this.cornerSymbol = cornerSymbol;
    }

    /**
     * this method returns the points scored according to the double symbol pattern objective card
     * it iterates throughout all the player's manuscript and finds the given symbol of the objective card
     * @param manuscript is the player's field
     * @return int
     */
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count;

        count = manuscript.getCounter(this.cornerSymbol);

        return OBJECTIVE_POINTS*(count/2);
    }

    /**
     * changes the colour of the string provided
     * @param s initial string
     * @return colored string
     */
    @Override
    protected String paintString(String s) {
        return ColourControl.YELLOW + s + ColourControl.RESET;
    }

    /**
     * transforms the objective card to an equivalent string printable on the Tui terminal
     * @return the card in string form
     */
    @Override
    public String toCliCard(){
        String first = paintString("╔═════════════════╗");
        String second = paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + paintString("          ║");
        String third = paintString("║                 ║");
        String fourth = paintString("║ " + this.cornerSymbol.toCliString().repeat(2)) + paintString("              ║");
        String fifth = paintString("╚═════════════════╝");
        return first + "\n" + second + "\n" + third + "\n" + fourth + "\n" + fifth;
    }

}
