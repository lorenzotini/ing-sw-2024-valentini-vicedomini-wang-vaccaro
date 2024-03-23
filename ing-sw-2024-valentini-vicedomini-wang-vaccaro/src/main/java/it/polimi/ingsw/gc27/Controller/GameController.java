package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Enumerations.PointsMultiplier;
import it.polimi.ingsw.gc27.Game.Board;
import it.polimi.ingsw.gc27.Game.Game;
import it.polimi.ingsw.gc27.Game.Player;

public class GameController {
    private Game game;

    /**
     *
     * @param card
     * @param face
     * @param x
     * @param y
     * @param player
     *
     * @the fuction add a card to the manuscript of the passed player if it's a valid placemente
     * //maybe the control about isValidPlacement() will bi placed nearer the view
     * @also call the function addPoints to update the points of the player on the board
     */
    public void addCard(ResourceCard card, Face face, int x, int y, Player player){
        if(card instanceof GoldCard && face instanceof FrontFace ){
            if(!player.getManuscript().satisfiedRequirement((GoldCard)card)){

                // non è possibile aggiungere carta
                //si manda un exception
                }

        }
        if(player.getManuscript().isValidPlacement(x, y)){
            // set to "hidden" the corners covered by the added card and count them
            int numCoveredCorners = 0;
            for(int i = -1; i <= 1; i = i + 2){
                for(int j = -1; j <= 1; j = j + 2){
                    Face nearCard = player.getManuscript().getFace(x +i,y+j);
                    if(nearCard != null){
                        nearCard.getCorner(-i, j).setHidden(true);
                        numCoveredCorners++;
                    }

                    /*if(field[x + i][y + j] != null){
                        field[x + i][y + j].getCorner(-i, j).setHidden(true);
                        numCoveredCorners++;
                    }*/

                }
            }
            addPoints(player, card, face, game.getBoard(), numCoveredCorners);
            Face copy = face.copy(face);
            player.getManuscript().setFace(copy, x, y);
            addPoints(player, card, face, game.getBoard(), numCoveredCorners);
            //la vado a implementare stesso nel controller
        }else{
            System.err.println("Error: invalid position");
        }
    }

    public void addPoints(Player player,ResourceCard card, Face face, Board board, int numCoveredCorners){
        if(face instanceof FrontFace){
            if (card instanceof GoldCard){
                int points;
                if(((GoldCard)card).getPointsMultiplier().equals(PointsMultiplier.CORNER)){
                    points = card.getCardPoints() * numCoveredCorners;
                }
                else{
                    points = card.getCardPoints() *
                            (int)((player.getManuscript().countCornerSymbol
                                    (((GoldCard)card).getPointsMultiplier().convertToCornerSymbol())+1)/2);
                }
                setPointsPlayer(player, board, points);
            }
            if (card != null){ //check equivalente a verificare sia una ResourceCard
                setPointsPlayer(player, board, card.getCardPoints());
            }


        }
    }

    public void setPointsPlayer(Player player, Board board, int points) {

        PawnColour pawncolour = player.getPawnColour();
        switch (pawncolour) {
            case BLUE:
                board.setPointsBluePlayer(board.getPointsBluePlayer() + points);
                break;
            case RED:
                board.setPointsRedPlayer(board.getPointsRedPlayer() + points);
                break;
            case GREEN:
                board.setPointsGreenPlayer(board.getPointsGreenPlayer() + points);
                break;
            case YELLOW:
                board.setPointsYellowPlayer(board.getPointsYellowPlayer() + points);
                break;
        }
    }

}
