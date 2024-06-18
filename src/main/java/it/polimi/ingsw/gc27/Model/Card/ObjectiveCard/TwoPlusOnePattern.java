package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.View.ColourControl;

public class TwoPlusOnePattern extends ObjectiveCard {
    public final int OBJECTIVE_POINTS = 3;
    private final Kingdom firstColour;
    private final Kingdom secondColour;
    private final int x;
    private final int y;

    /**
     * constructor matching super {@link ObjectiveCard}
     * first colour is the first colour looking from top to bottom the patter chosen (the top colour)
     * second colour is the second colour looking from top to bottom the patter chosen (the bottom colour)
     * x and y are the indexes in which the minority card is placed ex. x=1 && y=1 the minority card is placed on top right
     * @param id card's id
     * @param front front face
     * @param back back face
     * @param firstColour first colour looking from top to bottom
     * @param secondColour second colour looking from top to bottom
     * @param x index
     * @param y index
     */
    public TwoPlusOnePattern(int id, FrontFace front, BackFace back, Kingdom firstColour, Kingdom secondColour, int x, int y) {
        super(id, front, back);
        this.firstColour = firstColour;
        this.secondColour = secondColour;
        this.x = x;
        this.y = y;
    }

    /**
     * this method returns the points scored according to the two plus one pattern (L shaped) objective card
     * it iterates throughout all the player's manuscript and finds the given pattern of the objective card
     * @param manuscript is the player's field
     * @return int
     */
    @Override
    public int calculateObjectivePoints(Manuscript manuscript) {
        int field_dim = Manuscript.FIELD_DIM;
        int[][] checked = new int[field_dim][field_dim];
        int points = 0;
        int xMax, xMin, yMax, yMin;

        Face[][] field = manuscript.getField();

        xMax = manuscript.getxMax();
        xMin = manuscript.getxMin();
        yMax = manuscript.getyMax();
        yMin = manuscript.getyMin();


        for(int j = yMin; j <= yMax; j++) {
            for(int i = xMin; i<= xMax; i++){
                if(field[i][j] != null) {
                    if(this.x == 1 && this.y == 1) { // one fungi two animal
                        if (field[i][j].getColour().equals(this.firstColour) && i >=1 && j <= (field_dim - 4) && checked[i][j] == 0) {
                            if(field[i - 1][j + 1] != null && field[i - 1][j + 3] != null) {
                                if (field[i - 1][j + 1].getColour().equals(this.secondColour) && field[i - 1][j + 3].getColour().equals(this.secondColour)) {
                                    points = points + OBJECTIVE_POINTS;
                                    checked[i - 1][j + 1] = 1;
                                    checked[i - 1][j + 3] = 1;
                                }
                            }
                        }
                    } else if (this.x == 1 && this.y == -1) { //one plant two fungi
                        if (field[i][j].getColour().equals(this.firstColour) && i <= (field_dim-2) && j <= (field_dim - 4) && checked[i][j] == 0) {
                            if(field[i][j + 2] != null && field[i + 1][j + 3]!= null){
                                if (field[i][j + 2].getColour().equals(this.firstColour) && field[i + 1][j + 3].getColour().equals(this.secondColour)) {
                                    points = points + OBJECTIVE_POINTS;
                                    checked[i][j + 2] = 1;
                                    checked[i + 1][j + 3] = 1;
                                }
                            }
                        }
                    } else if (this.x == -1 && this.y == 1) { //one animal two insect
                        if (field[i][j].getColour().equals(this.firstColour) && i <= (field_dim-2) && j <= (field_dim -4) && checked[i][j] == 0) {
                            if(field[i + 1][j + 1]!= null && field[i + 1][j + 3]!= null) {
                                if (field[i + 1][j + 1].getColour().equals(this.secondColour) && field[i + 1][j + 3].getColour().equals(this.secondColour)) {
                                    points = points + OBJECTIVE_POINTS;
                                    checked[i + 1][j + 1] = 1;
                                    checked[i + 1][j + 3] = 1;
                                }
                            }
                        }
                    } else if (this.x == -1 && this.y == -1) { //one insect two plant
                        if (field[i][j].getColour().equals(this.firstColour) && i >=1 && j <= (field_dim - 4) && checked[i][j] == 0) {
                            if(field[i][j + 2]!= null && field[i -1][j + 3]!= null){
                                if (field[i][j + 2].getColour().equals(this.firstColour) && field[i -1][j + 3].getColour().equals(this.secondColour)) {
                                    points = points + OBJECTIVE_POINTS;
                                    checked[i][j + 2] = 1;
                                    checked[i - 1][j + 3] = 1;
                                }
                            }
                        }
                    }
                    checked[i][j] = 1;
                }
            }
        }

        return points;

    }


    /**
     * changes the colour of the string provided
     * @param s initial string
     * @return colored string
     */
    @Override
    protected String paintString(String s) {
        return this.firstColour.toColourControl() + s + ColourControl.RESET;
    }

    /**
     * @return miniaturized image on the card
     */
    private String miniImg(String colour){
        return colour + "███" + ColourControl.RESET;
    }

    /**
     * transforms the objective card to an equivalent string printable on the Tui terminal
     * @return the card in string form
     */
    @Override
    public String toCliCard(){

        String firstColImg = miniImg(this.firstColour.toColourControl());
        String secondColImg = miniImg(this.secondColour.toColourControl());

        String ws = " ";

        String comb = x + String.valueOf(y);

        switch(comb){
            case "11":
                return paintString("╔═════════════════╗\n") +
                        paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + "      " + secondColImg + paintString(" ║\n") +
                        paintString("║           ") + firstColImg + paintString("   ║\n") +
                        paintString("║           ") + firstColImg + paintString("   ║\n") +
                        paintString("╚═════════════════╝");

            case "1-1":
                return paintString("╔═════════════════╗\n") +
                        paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + "    " + firstColImg + paintString("   ║\n") +
                        paintString("║           ") + firstColImg + paintString("   ║\n") +
                        paintString("║             ") + secondColImg + paintString(" ║\n") +
                        paintString("╚═════════════════╝");

            case "-11":
                return paintString("╔═════════════════╗\n") +
                        paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + "  " + secondColImg + paintString("     ║\n") +
                        paintString("║           ") + firstColImg + paintString("   ║\n") +
                        paintString("║           ") + firstColImg + paintString("   ║\n") +
                        paintString("╚═════════════════╝");

            case "-1-1":
                return paintString("╔═════════════════╗\n") +
                        paintString("║ ") + "pts: " + this.getObjPointsMap().get(this.getClass()) + "    " + firstColImg + paintString("   ║\n") +
                        paintString("║           ") + firstColImg + paintString("   ║\n") +
                        paintString("║         ") + secondColImg + paintString("     ║\n") +
                        paintString("╚═════════════════╝");

            default:
                return "Error in TwoPlusOnePattern toCliCard()";

        }

    }

}