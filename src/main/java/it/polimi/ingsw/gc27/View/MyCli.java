package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class MyCli {

    private static ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    private static ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
    private static ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);

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
                }
            }
        }

        boolean first;
        boolean middle;

        for(int j = yMin; j <= yMax; j++) {
            for (int line = 1; line <= 3; line++) {
                first = true;
                middle = false;
                for (int i = xMin; i <= xMax; i++) {
                    if(matrix[i][j-1] != null && matrix[i][j-1].peek() != null){
                        System.out.print(matrix[i][j-1].remove());
                        first = true;
                        middle = true;
                        continue;
                    }
                    // if the card is null, print white spaces and go to next card on the same row
                    if(matrix[i][j] == null){
                        String ws = countWhiteSpaces(first, middle);
                        System.out.print(ws);
                        first = false;
                        continue;
                    }
                    if(matrix[i][j] != null){
                        System.out.print(matrix[i][j].remove());
                        middle = true;
                        first = true;
                    }else if(matrix[i][j+1] != null){
                        System.out.print(matrix[i][j+1].remove());
                    }
                }
                System.out.println();
            }
        }

        // last line of all
        int j = yMax;
        for(int n = 0; n <= 1; n++){
            first = true;
            middle = false;
            for(int i = xMin; i <= xMax; i++){
                if(matrix[i][j-1] != null && matrix[i][j-1].peek() != null){
                    System.out.print(matrix[i][j-1].remove());
                    first = true;
                    middle = true;
                    continue;
                }
                // if the card is null, print white spaces and go to next card on the same row
                if(matrix[i][j] == null){
                    first = true;
                    String ws = countWhiteSpaces(first, middle);
                    System.out.print(ws);
                    first = false;
                    continue;
                }
                if(matrix[i][j] != null){
                    System.out.print(matrix[i][j].remove());
                    middle = true;
                    first = true;
                }
            }
            System.out.println();
        }
    }

    public static String countWhiteSpaces(boolean first, boolean middle){
        String ws_11 = "           ";
        String ws_15 = "               ";

        if(!middle){
            return ws_15;
        }

        if(first){
            return ws_11;
        } else if(!first){
            return ws_15;
        } else {
            return "XXXXXX";
        }
    }

    // example test
    public static void main(String[] args) {

        Manuscript m = new Manuscript();

        //TEST 6
        // #0
        m.getField()[40][40] = starterDeck.get(0).getBack();
        m.getField()[40][40].getCornerLR().setHidden(true);

        // #1
        m.getField()[41][41] = resourceDeck.get(20).getBack();
        m.getField()[41][41].getCornerLR().setHidden(true);

        // #2
        m.getField()[42][42] = resourceDeck.get(21).getBack();
        m.getField()[42][42].getCornerLR().setHidden(true);

        // #3
        m.getField()[43][43] = resourceDeck.get(22).getBack();
        m.getField()[43][43].getCornerLR().setHidden(true);
        m.getField()[43][43].getCornerUR().setHidden(true);

        m.getField()[44][42] = resourceDeck.get(23).getBack();
        m.getField()[44][42].getCornerUR().setHidden(true);

        m.getField()[45][41] = resourceDeck.get(24).getBack();

        // #4
        m.getField()[44][44] = resourceDeck.get(25).getBack();

        m.setxMin(40);
        m.setyMin(40);
        m.setxMax(45);
        m.setyMax(44);

        MyCli.printManuscript(m);
    }
    public static String showFace(Face face){

            Corner UR = face.getCornerUR();
            Corner UL = face.getCornerUL();
            Corner LR = face.getCornerLR();
            Corner LL = face.getCornerLL();

            String colour = face.getColour().toColourControl() + "\u001B[1m";   // colour and bold
            String reset = "\u001B[0m";
            String first = colour + "╭-----------------╮" + reset;
            String second = colour + "|" + UL.getSymbol().toCliString() + "               " + UR.getSymbol().toCliString() + colour + "|" + reset;
            String third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            String fourth = colour + "|" + LL.getSymbol().toCliString() + "               " + LR.getSymbol().toCliString() + colour + colour + "|" + reset;
            String fifth = colour + "╰-----------------╯" + reset;
        return (first + "\n" + second +"\n" + third+"\n"+ fourth+"\n"+fifth);
    }
    public static String showStarter(StarterCard card ){
        return ("Starter Front:"+"\n"+ showFace(card.getFront( )) +("\nStarter Back:")+"\n"+showFace(card.getBack()));

    }

}