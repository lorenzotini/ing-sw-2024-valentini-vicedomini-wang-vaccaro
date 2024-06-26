package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.Tui.ColourControl;

/**
 * The ThreeKingdomPattern class represents a specific type of objective card
 * This class is used to check for and score a "ThreeKingdomPattern" objective in the game
 */
public class ThreeKingdomPattern extends ObjectiveCard {
    public final int OBJECTIVE_POINTS = 2;
    private final Kingdom kingdom;

    /**
     * constructor matching super {@link ObjectiveCard}
     * @param id card's id
     * @param front front face
     * @param back back face
     * @param kingdom symbol counted
     */
    public ThreeKingdomPattern(int id, FrontFace front, BackFace back, Kingdom kingdom) {
        super(id, front, back);
        this.kingdom = kingdom;
    }

    /**
     * this method returns the points scored according to the three kingdom (three of the same basic symbol) pattern objective card
     * it iterates throughout all the player's manuscript and finds the given symbol of the objective card
     * @param manuscript is the player's field
     * @return int
     */
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {

        int count = manuscript.getCounter(this.kingdom.toCornerSymbol());

        return OBJECTIVE_POINTS*(count/3);
    }

    /**
     * changes the colour of the string provided
     * @param s initial string
     * @return colored string
     */
    @Override
    protected String paintString(String s) {
        return this.kingdom.toColourControl() + s + ColourControl.RESET;
    }

    /**
     * transforms the objective card to an equivalent string printable on the Tui terminal
     * @return the card in string form
     */
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
