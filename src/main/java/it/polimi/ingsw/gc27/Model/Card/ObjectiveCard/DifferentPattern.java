package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.Tui.ColourControl;

/**
 * The DifferentPattern class represents a specific type of objective card
 * This class is used to check for and score a "DifferentPattern" objective in the game
 */
public class DifferentPattern extends ObjectiveCard {

    public final int OBJECTIVE_POINTS = 3;

    /**
     * constructor matching super {@link ObjectiveCard}
     *
     * @param id    card's id
     * @param front front face
     * @param back  back face
     */
    public DifferentPattern(int id, FrontFace front, BackFace back) {
        super(id, front, back);
    }

    /**
     * this method returns the points scored according to the different (all three of the special symbol) pattern objective card
     * it iterates throughout all the player's manuscript and finds the given symbols of the objective card
     *
     * @param manuscript is the player's field
     * @return int
     */
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int count;
        int points;
        int min = 9999; // arbitrary big number that prevents bugs

        count = manuscript.getCounter(CornerSymbol.QUILL);
        if (count <= min) {
            min = count;
        }
        count = manuscript.getCounter(CornerSymbol.MANUSCRIPT);
        if (count <= min) {
            min = count;
        }
        count = manuscript.getCounter(CornerSymbol.INKWELL);
        if (count <= min) {
            min = count;
        }
        points = OBJECTIVE_POINTS * min;
        return points;
    }

    /**
     * changes the colour of the string provided
     *
     * @param s initial string
     * @return colored string
     */
    @Override
    protected String paintString(String s) {
        return ColourControl.YELLOW + s + ColourControl.RESET;
    }

    /**
     * transforms the objective card to an equivalent string printable on the Tui terminal
     *
     * @return the card in string form
     */
    @Override
    public String toCliCard() {
        String first = paintString("╔═════════════════╗");
        String second = paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + paintString("          ║");
        String third = paintString("║                 ║");
        String fourth = paintString("║ QMI             ║");
        String fifth = paintString("╚═════════════════╝");
        return first + "\n" + second + "\n" + third + "\n" + fourth + "\n" + fifth;
    }

}
