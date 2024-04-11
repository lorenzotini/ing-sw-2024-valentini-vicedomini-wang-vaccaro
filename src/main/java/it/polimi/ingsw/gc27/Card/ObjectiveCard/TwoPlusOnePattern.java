package it.polimi.ingsw.gc27.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Card.BackFace;
import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.FrontFace;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Game.Manuscript;

public class TwoPlusOnePattern extends ObjectiveCard {
    public final int OBJECTIVE_POINTS = 3;
    private final Kingdom firstColour;
    private final Kingdom secondColour;
    private final int x;
    private final int y;

    public TwoPlusOnePattern(int id, FrontFace front, BackFace back, Kingdom firstColour, Kingdom secondColour, int x, int y) {
        super(id, front, back);
        this.firstColour = firstColour;
        this.secondColour = secondColour;
        this.x = x;
        this.y = y;
    }

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


        for(int i = xMin; i<= xMax; i++) {
            for(int j = yMin; j <= yMax; j++){
                if(field[i][j] != null) {
                    if(this.x == 1 && this.y == 1) {
                        if (field[i][j].getColour().equals(this.firstColour) && i >=1 && j <= (field_dim - 4) && checked[i][j] == 0) {
                            if (field[i - 1][j + 1].getColour().equals(this.secondColour) && field[i - 1][j + 3].getColour().equals(this.secondColour)) {
                                points = points + 3;
                                checked[i - 1][j + 1] = 1;
                                checked[i - 1][j + 3] = 1;
                            }
                        }
                    } else if (this.x == 1 && this.y == -1) {
                        if (field[i][j].getColour().equals(this.firstColour) && i <= (field_dim-2) && j <= (field_dim - 4) && checked[i][j] == 0) {
                            if (field[i][j + 2].getColour().equals(this.firstColour) && field[i + 1][j + 3].getColour().equals(this.secondColour)) {
                                points = points + 3;
                                checked[i][j + 2] = 1;
                                checked[i + 1][j + 3] = 1;
                            }
                        }
                    } else if (this.x == -1 && this.y == 1) {
                        if (field[i][j].getColour().equals(this.firstColour) && i <= (field_dim-2) && j <= (field_dim -4) && checked[i][j] == 0) {
                            if (field[i + 1][j + 1].getColour().equals(this.secondColour) && field[i + 1][j + 3].getColour().equals(this.secondColour)) {
                                points = points + 3;
                                checked[i + 1][j + 1] = 1;
                                checked[i + 1][j + 3] = 1;
                            }
                        }
                    } else if (this.x == -1 && this.y == -1) {
                        if (field[i][j].getColour().equals(this.firstColour) && i >=1 && j <= (field_dim - 4) && checked[i][j] == 0) {
                            if (field[i][j + 2].getColour().equals(this.firstColour) && field[i -1][j + 3].getColour().equals(this.secondColour)) {
                                points = points + 3;
                                checked[i][j + 2] = 1;
                                checked[i - 1][j + 3] = 1;
                            }
                        }
                    }
                    checked[i][j] = 1;
                }
            }
        }

        return points;
    }
}