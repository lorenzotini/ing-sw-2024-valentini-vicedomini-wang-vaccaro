package it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;

import it.polimi.ingsw.gc27.Model.Card.BackFace;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.FrontFace;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

public class LadderPattern extends ObjectiveCard {
    public final int OBJECTIVE_POINTS = 2;
    private final Kingdom kingdom;
    private final boolean upscaling;

    public LadderPattern(int id, FrontFace front, BackFace back, Kingdom kingdom, boolean upscaling) {
        super(id, front, back);
        this.kingdom = kingdom;
        this.upscaling = upscaling;
    }

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
                points = points + 2;
                checked[i + (1 * up)][j + 1] = 1;
                checked[i + (2 * up)][j + 2] = 1;
            }
        }
        return points;
    }
}
