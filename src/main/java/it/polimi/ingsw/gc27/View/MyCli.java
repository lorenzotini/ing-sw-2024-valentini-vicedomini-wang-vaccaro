package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Game.Manuscript;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class MyCli {

    private static ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    private static ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
    private static ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);

    public static Queue<String> emptyCard(int i , int j, Face[][] field){
        String first;
        String second;
        String third;
        String fourth;
        String fifth;
        Queue<String> lines = new LinkedList<>();

        if (field[i+1][j] == null && field[i][j+1] == null && field[i-1][j] == null && field[i][j-1] == null) {
            first = "           ";
            second = "           ";
            third = "           ";
            fourth = "           ";
            fifth = "           ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
            lines.add(fourth);
            lines.add(fifth);
        } else if(field[i+1][j] != null && field[i][j+1] != null && field[i-1][j] != null && field[i][j-1] != null){
            third = "           ";
            lines.add(third);
        } else if(field[i+1][j] != null && field[i][j+1] != null && field[i-1][j] != null && field[i][j-1] == null){
            first = "           ";
            second = "           ";
            third = "           ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        } else if(field[i+1][j] != null && field[i][j+1] != null && field[i-1][j] == null && field[i][j-1] != null){
            third = "               ";
            lines.add(third);
        } else if(field[i+1][j] != null && field[i][j+1] == null && field[i-1][j] != null && field[i][j-1] != null){
            first = "           ";
            second = "           ";
            third = "           ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        } else if(field[i+1][j] == null && field[i][j+1] != null && field[i-1][j] != null && field[i][j-1] != null){
            third = "               ";
            lines.add(third);
        } else {
            first = "               ";
            second = "               ";
            third = "               ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        }

        return lines;

    }

    public static Queue<String> fromFaceToCliCard(Face face){

        Corner UR = face.getCornerUR();
        Corner UL = face.getCornerUL();
        Corner LR = face.getCornerLR();
        Corner LL = face.getCornerLL();

        String colour = face.getColour().toColourControl() + "\u001B[1m";   // colour and bold
        String reset = "\u001B[0m";

        String first = "";
        String second = "";
        String third = "";
        String fourth = "";
        String fifth = "";

        if(!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()) {  // case #1
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + "               " + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + "               " + LR.getSymbol().toCliString() + colour + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #2
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + "             " + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + "               " + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #3
            first = colour + "--------------╮" + reset;
            second = colour + "             " + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + "               " + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #4
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + "               " + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString()+ "             " + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && LL.isHidden()){ // case #5
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + "               " + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "             " + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        }else if(UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #6
            first = colour + "-----------" + reset;
            second = colour + "           " + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + "               " + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #7
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + "               " + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "           " + reset;
            fifth = colour + "-----------" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()){ // case #8
            first = colour + "--------------╮" + reset;
            second = colour + "             " + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "             " + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #9
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + "             " + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString()+ "             " + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #10
            first = colour + "-----------" + reset;
            second = colour + "           " + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "           " + reset;
            fifth = colour + "-----------" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #11
            first = colour + "--------------╮" + reset;
            second = colour + "             " + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "           " + reset;
            fifth = colour + "-----------" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #12
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + "             " + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "           " + reset;
            fifth = colour + "-----------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #13
            first = colour + "-----------" + reset;
            second = colour + "           " + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + colour + "             " + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #14
            first = colour + "-----------" + reset;
            second = colour + "           " + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "             " + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        }

        Queue<String> lines = new LinkedList<>();
        lines.add(first);
        lines.add(second);
        lines.add(third);
        lines.add(fourth);
        lines.add(fifth);
        return lines;

    }

    public static String constructString(ArrayList<String> permanentResources) {
        String start = "";
        String end = "";

        String permRes = "";
        for (int i = 0; i < permanentResources.size(); i++) {
            permRes = permRes + permanentResources.get(i);
        }

        switch (permanentResources.size()) {
            case 0:
                start = start + "         ";
                end = "        " + end;
                break;
            case 1:
                start = start + "        ";
                end = "        " + end;
                break;
            case 2:
                start = start + "       ";
                end = "        " + end;
                break;
            case 3:
                start = start + "       ";
                end = "       " + end;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + permanentResources.size());
        }

        String line = start + permRes + end;
        return line;
    }

    public static boolean isMatrixEmpty(Queue<String>[][] matrix) {
        for (Queue<String>[] row : matrix) {
            for (Queue<String> queue : row) {
                if (queue != null && !queue.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printManuscript(Manuscript manuscript){

        Face[][] field = manuscript.getField();
        int xMin = manuscript.getxMin();
        int xMax = manuscript.getxMax();
        int yMin = manuscript.getyMin();
        int yMax = manuscript.getyMax();

        // translate the manuscript into a matrix of string representing cards
        Queue<String>[][] matrix = new Queue[Manuscript.FIELD_DIM][Manuscript.FIELD_DIM];
        for(int i = xMin; i <= xMax; i++){
            for(int j = yMin; j <= yMax; j++){
                if(field[i][j] != null){
                    matrix[i][j] = fromFaceToCliCard(field[i][j]);
                }else{
                    matrix[i][j] = emptyCard(i, j, field);
                }
            }
        }

        // print
        loop:
        for(int j = yMin; j <= yMax; j++) {
            for (int line = 1; line <= 5; line++) {
                for (int i = xMin; i <= xMax; i++) {
                    if(isMatrixEmpty(matrix)){
                        break loop;
                    }
                    switch (line) {
                        case 1, 2, 3:
                            if(matrix[i][j].peek() != null){
                                System.out.print(matrix[i][j].remove());
                            }else{
                                System.out.print(matrix[i][j+1].remove());
                            }
                            break;
                        case 4, 5:
                            if(matrix[i][j].peek() != null){
                                System.out.print(matrix[i][j].remove());
                            }else if(matrix[i][j+1].peek() != null){
                                System.out.print(matrix[i][j+1].remove());
                            }
                            if(matrix[i+1][j+1] != null){
                                if(matrix[i+1][j+1].peek() != null){
                                    System.out.print(matrix[i+1][j+1].remove());
                                }else if(matrix[i+1][j+2] != null && matrix[i+1][j+2].peek() != null){
                                    System.out.print(matrix[i+1][j+2].remove());
                                }
                            }
                            i++;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + line);
                    }
                }
                System.out.println();
            }
        }
    }

    // example test
    public static void main(String[] args) {

        Manuscript m = new Manuscript();

        m.getField()[41][41] = resourceDeck.get(10).getFront();
        m.getField()[41][41].getCornerLR().setHidden(true);

        m.getField()[43][41] = resourceDeck.get(1).getFront();
        m.getField()[43][41].getCornerLR().setHidden(true);
        m.getField()[43][41].getCornerLL().setHidden(true);

        m.getField()[42][42] = resourceDeck.get(2).getFront();
        m.getField()[42][42].getCornerLL().setHidden(true);

        m.getField()[44][42] = resourceDeck.get(24).getFront();

        m.getField()[41][43] = resourceDeck.get(4).getFront();
        m.getField()[41][43].getCornerLR().setHidden(true);

        m.getField()[43][43] = starterDeck.get(2).getBack();
        m.getField()[43][43].getCornerLL().setHidden(true);
        m.getField()[43][43].getCornerUR().setHidden(true);
        m.getField()[43][43].getCornerUL().setHidden(true);
        m.getField()[43][43].getCornerLR().setHidden(true);

        m.getField()[42][44] = resourceDeck.get(34).getFront();

        m.getField()[44][44] = resourceDeck.get(5).getFront();

        m.setxMin(41);
        m.setyMin(41);
        m.setxMax(44);
        m.setyMax(44);

        MyCli.printManuscript(m);
    }
}

//////////////////////////////////////////////////
/*


            41              42             43             44
   ╭-----------------╮           ╭-----------------╮
   |A               P|           |A               P|
41 |                 |           |                 |
   |F             ╭-----------------╮           ╭-----------------╮
   ╰--------------| A             P |-----------|A               P|
42                |                 |           |                 |
   ╭-----------------╮            I |-----------|X               X|
   |A               P|--------------╯           ╰-----------------╯
43 |                 |           |                 |
   |F             ╭-----------------╮           ╭-----------------╮
   ╰--------------|A               P|-----------|A               P|
44                |                 |           |                 |
                  |X               X|           |X               X|
                  ╰-----------------╯           ╰-----------------╯


|                 |
|        A        |
|       AB        |
|       ABC       |


*/
//////////////////////////////////////////////////