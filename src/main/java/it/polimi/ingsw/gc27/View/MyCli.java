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

        // no surrounding cards
        if (field[i+1][j] == null && field[i][j+1] == null && field[i-1][j] == null && field[i][j-1] == null) {
            first = "               ";
            second = "               ";
            third = "               ";
            fourth = "               ";
            fifth = "               ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
            //lines.add(fourth);
            //lines.add(fifth);
        } // surrounded by card on every side
        else if(field[i+1][j] != null && field[i][j+1] != null && field[i-1][j] != null && field[i][j-1] != null){
            third = "           ";
            lines.add(third);
        } // free on top
        else if(field[i+1][j] != null && field[i][j+1] != null && field[i-1][j] != null && field[i][j-1] == null){
            first = "           ";
            second = "           ";
            third = "           ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        } // free on the left
        else if(field[i+1][j] != null && field[i][j+1] != null && field[i-1][j] == null && field[i][j-1] != null){
            third = "               ";
            lines.add(third);
        } // free on the bottom
        else if(field[i+1][j] != null && field[i][j+1] == null && field[i-1][j] != null && field[i][j-1] != null){
            first = "           ";
            second = "           ";
            third = "           ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        } // free on the right
        else if(field[i+1][j] == null && field[i][j+1] != null && field[i-1][j] != null && field[i][j-1] != null){
            third = "               ";
            lines.add(third);
        } // card on top
        else if(field[i+1][j] == null && field[i][j+1] == null && field[i-1][j] == null && field[i][j-1] != null){
            first = "               ";
            second = "               ";
            third = "               ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        } // card on the left
        else if(field[i+1][j] == null && field[i][j+1] == null && field[i-1][j] != null && field[i][j-1] == null){
            first = "               ";
            second = "               ";
            third = "               ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        } // card on the bottom
        else if(field[i+1][j] == null && field[i][j+1] != null && field[i-1][j] == null && field[i][j-1] == null){
            first = "               ";
            second = "               ";
            third = "               ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        } // card on the right
        else if(field[i+1][j] != null && field[i][j+1] == null && field[i-1][j] == null && field[i][j-1] == null){
            first = "               ";
            second = "               ";
            third = "               ";
            lines.add(first);
            lines.add(second);
            lines.add(third);
        }// two card near perpendicularly
        else {
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
        // first line of all, then group by 3 lines
        for(int i = xMin; i <= xMax; i++){
            int j = yMin;
            System.out.print(matrix[i][j].remove());
        }
        //System.out.println();

        boolean flag = false;

        loop:
        for(int j = yMin; j <= yMax; j++) {
            for (int line = 1; line <= 3; line++) {
                for (int i = xMin; i <= xMax; i++) {
                    if(isMatrixEmpty(matrix)){
                        break loop;
                    }
                    if(!flag){
                        line = 0;
                        break;
                    }
                    if(j != yMin && matrix[i][j-1].peek() != null){
                        System.out.print(matrix[i][j-1].remove());
                    }else if(matrix[i][j].peek() != null){
                        System.out.print(matrix[i][j].remove());
                    }else if(matrix[i][j+1].peek() != null){
                        System.out.print(matrix[i][j+1].remove());
                    }
                }
                System.out.println();
                flag = true;
            }
        }
        // last line of all
        for(int i = xMin; i <= xMax; i++){
            int j = yMax;
            System.out.print(matrix[i][j].remove());
        }
    }

    // example test
    public static void main(String[] args) {

        Manuscript m = new Manuscript();

        // #0
        m.getField()[40][40] = starterDeck.get(0).getBack();
        m.getField()[40][40].getCornerLL().setHidden(true);
        m.getField()[40][40].getCornerLR().setHidden(true);
        m.getField()[40][40].getCornerUR().setHidden(true);
        m.getField()[40][40].getCornerUL().setHidden(true);

        // #1
        m.getField()[39][41] = resourceDeck.get(20).getBack();
        m.getField()[39][41].getCornerLL().setHidden(true);
        m.getField()[39][41].getCornerLR().setHidden(true);

        // #2
        m.getField()[39][39] = resourceDeck.get(21).getBack();
        m.getField()[39][39].getCornerUR().setHidden(true);

        // #3
        m.getField()[38][42] = resourceDeck.get(22).getBack();
        m.getField()[38][42].getCornerLL().setHidden(true);

        // #4
        m.getField()[37][43] = resourceDeck.get(23).getBack();
        m.getField()[37][43].getCornerUL().setHidden(true);

        // #5
        m.getField()[40][38] = resourceDeck.get(0).getBack();
        m.getField()[40][38].getCornerLR().setHidden(true);

        // #6
        m.getField()[41][39] = resourceDeck.get(30).getBack();
        m.getField()[41][39].getCornerLR().setHidden(true);

        // #7
        m.getField()[36][42] = resourceDeck.get(31).getBack();

        // #8
        m.getField()[42][40] = resourceDeck.get(10).getBack();
        m.getField()[42][40].getCornerLL().setHidden(true);

        // #9
        m.getField()[40][42] = resourceDeck.get(11).getBack();
        m.getField()[40][42].getCornerUR().setHidden(true);

        // #10
        m.getField()[41][41] = resourceDeck.get(32).getBack();

        m.setxMin(36);
        m.setyMin(38);
        m.setxMax(42);
        m.setyMax(43);

        MyCli.printManuscript(m);
    }
}

//////////////////////////////////////////////////

/*
"                              "

            36              37              38              39              40              41              42
                                                                    ╭-----------------╮
                                                                    |A               P|
38                                                                  |                 |
                                                     ╭--------------|F             ╭-----------------╮
                                                     | A            ╰--------------| A             P |
39                                                   |                 |           |                 |
                                      ╭--------------|F                |-----------|               I |
                                      | A            ╰-----------------╯           ╰-----------------╯
40


41


42


43



            37              38             39             40
                                                  ╭-----------------╮
                                                  |A               P|
40                                                |                 |
                                   ╭-----------------╮              |
                                   | A             P |--------------╯
41                                 |                 |
                    ╭-----------------╮            I |
                    |A               P|--------------╯
42                  |                 |
     ╭-----------------╮              |
     |A               P|--------------╯
43   |                 |
     |X               X|
     ╰-----------------╯







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





        // print
        for(int i = xMin; i <= xMax; i++){
            int j = yMin;
            System.out.print(matrix[i][j].remove());
        }
        //System.out.println();

        boolean flag = false;

        loop:
        for(int j = yMin; j <= yMax; j++) {
            for (int line = 1; line <= 3; line++) {
                for (int i = xMin; i <= xMax; i++) {
                    if(isMatrixEmpty(matrix)){
                        break loop;
                    }
                    if(!flag){
                        line = 0;
                        break;
                    }
                    if(j != yMin && matrix[i][j-1].peek() != null){
                        System.out.print(matrix[i][j-1].remove());
                    }else if(matrix[i][j].peek() != null){
                        System.out.print(matrix[i][j].remove());
                    }else if(matrix[i][j+1].peek() != null){
                        System.out.print(matrix[i][j+1].remove());
                    }
                }
                System.out.println();
                flag = true;
            }
        }




*/
//////////////////////////////////////////////////