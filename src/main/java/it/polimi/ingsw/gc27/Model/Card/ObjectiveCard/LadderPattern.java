package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.Tui.ColourControl;

public class LadderPattern extends ObjectiveCard {
    public final int OBJECTIVE_POINTS = 2;
    private final Kingdom kingdom;
    private final boolean upscaling;

    /**
     * constructor of this concrete class matching the super class {@link ObjectiveCard}
     * @param id card's id
     * @param front front face
     * @param back back face
     * @param kingdom colour of the pattern
     * @param upscaling if the pattern is ascending or descending ladder
     */
    public LadderPattern(int id, FrontFace front, BackFace back, Kingdom kingdom, boolean upscaling) {
        super(id, front, back);
        this.kingdom = kingdom;
        this.upscaling = upscaling;
    }

    /**
     * this method returns the points scored according to the ladder pattern objective card
     * it iterates throughout all the player's manuscript and finds the given pattern of the objective card
     * @param manuscript is the player's field
     * @return int
     */
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int field_dim = Manuscript.FIELD_DIM;
        int[][] checked = new int[field_dim][field_dim];
        int points = 0;
        int up;
        int xMax, xMin, yMax, yMin;

        Face[][] field = manuscript.getField();

        xMax = manuscript.getxMax();
        xMin = manuscript.getxMin();
        yMax = manuscript.getyMax();
        yMin = manuscript.getyMin();

        if(upscaling) {
            up = -1;
        } else {
            up = 1;
        }

        for(int j = yMin; j <= yMax; j++) {
            for(int i = xMin; i<= xMax; i++){
                if(field[i][j] != null) {
                    if(upscaling) {
                        if (field[i][j].getColour().equals(this.kingdom) && i >= 2 && j <= (field_dim - 3) && checked[i][j] == 0) {
                            points = getPoints(checked, points, up, field, i, j);
                        }
                    } else {
                        if (field[i][j].getColour().equals(this.kingdom) && i <= (field_dim - 3) && j <= (field_dim - 3) && checked[i][j] == 0) {
                            points = getPoints(checked, points, up, field, i, j);
                        }
                    }
                    checked[i][j] = 1;
                }
            }
        }

        return points;
    }

    private int getPoints(int[][] checked, int points, int up, Face[][] field, int i, int j) {
        if(field[i + (1 * up)][j + 1] != null && field[i + (2 * up)][j + 2] != null) {
            if (field[i + (1 * up)][j + 1].getColour().equals(this.kingdom) && field[i + (2 * up)][j + 2].getColour().equals(this.kingdom)) {
                points = points + OBJECTIVE_POINTS;
                checked[i + (1 * up)][j + 1] = 1;
                checked[i + (2 * up)][j + 2] = 1;
            }
        }
        return points;
    }

    /**
     * @return miniaturized image on the card
     */
    private String miniImg(){
        String img = "";
        img = this.kingdom.toColourControl() + "███" + ColourControl.RESET;
        return img;
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

        String img = miniImg();

        String ws = " ";
        int sp = this.upscaling ? 4 : 0;

        String first = paintString("╔═════════════════╗");
        String second = paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + "   " +ws.repeat(sp) + img + ws.repeat(4-sp) + paintString("║");
        String third = paintString("║            ") + img + paintString("  ║");
        String fourth = paintString("║          ") + ws.repeat(4-sp) + img + ws.repeat(sp) + paintString("║");
        String fifth =  paintString("╚═════════════════╝");

        return first + "\n" + second + "\n" + third + "\n" + fourth + "\n" + fifth;

    }

}
