package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Card.Corner;
import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Game.Manuscript;

import java.util.ArrayList;

public class MyCli {
    private static ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    private static ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
    public static CliCard emptyCard(){
        return new CliCard("                   ",
                         "                   ",
                           "                   ",
                          "                   ",
                           "                   ");
    }
    public static CliCard fromFaceToCliCard(Face face){

        Corner UR = face.getCornerUR();
        Corner UL = face.getCornerUL();
        Corner LR = face.getCornerLR();
        Corner LL = face.getCornerLL();

        String first = "";
        String second = "";
        String third = "";
        String fourth = "";
        String fifth = "";

        if(!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()){  // case #1
            first = "╭-----------------╮";
            second = "|"+UL.getSymbol().toCliString()+"               "+UR.getSymbol().toCliString()+"|";
            third = "|                 |";
            fourth = "|"+LL.getSymbol().toCliString()+"               "+LR.getSymbol().toCliString()+"|";
            fifth = "╰-----------------╯";
        }else if(!UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()){
            first = "╭-----------------╮";
            second = "|"+UL.getSymbol().toCliString()+"               "+UR.getSymbol().toCliString()+"|";
            third = "|                 |";
            fourth = "|"+LL.getSymbol().toCliString()+"             ";
            fifth = "╰--------------";
        }

        return new CliCard(first, second, third, fourth, fifth);
    }
    public static void printManuscript(Manuscript manuscript){

        Face[][] field = manuscript.getField();
        int xMin = manuscript.getxMin();
        int xMax = manuscript.getxMax();
        int yMin = manuscript.getyMin();
        int yMax = manuscript.getyMax();

        // translate the manuscript into a matrix of string representing cards
        CliCard[][] matrix = new CliCard[Manuscript.FIELD_DIM][Manuscript.FIELD_DIM];
        for(int i = 0; i < Manuscript.FIELD_DIM; i++){
            for(int j = 0; j < Manuscript.FIELD_DIM; j++){
                if(field[i][j] != null){
                    matrix[i][j] = fromFaceToCliCard(field[i][j]);
                }else{
                    matrix[i][j] = emptyCard();
                }
            }
        }

        // print
        for(int i = yMin; i <= yMax; i++) {
            for (int line = 0; line < 5; line++) {
                for (int j = xMin; j <= xMax; j++) {
                    switch (line) {
                        case 0:
                            System.out.print(matrix[i][j].firstLine);
                            break;
                        case 1:
                            System.out.print(matrix[i][j].secondLine);
                            break;
                        case 2:
                            System.out.print(matrix[i][j].thirdLine);
                            break;
                        case 3:
                            System.out.print(matrix[i][j].fourthLine);
                            break;
                        case 4:
                            System.out.print(matrix[i][j].fifthLine);
                            break;
                    }
                    if(line == 3){
                        System.out.print(matrix[i+1][j+1].firstLine);
                    }
                    if(line == 4){
                        System.out.print(matrix[i+1][j+1].secondLine);
                    }
                }
                System.out.println();
            }
        }
    }


    public static void main(String[] args) {

        Manuscript m = new Manuscript();
        resourceDeck.get(0).getBack().getCornerLR().setHidden(true);
        m.getField()[42][42] = resourceDeck.get(0).getBack();
        m.getField()[43][43] = resourceDeck.get(1).getFront();
        m.setxMin(42);
        m.setyMin(42);
        m.setxMax(43);
        m.setyMax(43);

        MyCli.printManuscript(m);
    }
}

//////////////////////////////////////////////////
/*


            0              1
   ╭-----------------╮
   |A               P|
0  |                 |
   |F             ╭-----------------╮
   ╰--------------| A             P |
1                 |                 |
   ╭-----------------╮            I |
   |A               P|--------------╯
2  |                 |
   |F               I|
   ╰-----------------╯


*/