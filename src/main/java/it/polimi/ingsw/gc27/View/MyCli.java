package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class MyCli {


    private static ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
    private static ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
    private static ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
    private static String sws = " "; // single white space

    public static Queue<String> fromFaceToCliCard(Face face) throws Exception {

        Corner UR = face.getCornerUR();
        Corner UL = face.getCornerUL();
        Corner LR = face.getCornerLR();
        Corner LL = face.getCornerLL();

        String colour = face.getColour().toColourControl() + ColourControl.BOLD;   // colour and bold
        String reset = ColourControl.RESET;

        String first = "";
        String second = "";
        String third = "";
        String fourth = "";
        String fifth = "";

        if(!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()) {  // case #1
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #2
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #3
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #4
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString()+ sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && LL.isHidden()){ // case #5
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        }else if(UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #6
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #7
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()){ // case #8
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #9
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString()+ sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #10
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #11
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #12
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #13
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + colour + sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #14
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + MyCli.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "|" + reset;
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
                System.out.println("Unexpected value: " + permanentResources.size());
        }

        String line = start + permRes + end;
        return line;
    }

    private static void printxAxis(int xMin, int xMax){
        String xAxis = sws.repeat(10);
        for(int i = xMin; i <= xMax; i++){
            if(i / 10 == 0){
                xAxis = xAxis + i + sws.repeat(14);
            }else{
                xAxis = xAxis + i + sws.repeat(13);
            }
        }
        System.out.println(xAxis);
    }

    private static String printyAxis(int line, int j){
        if(line == 3){
            return j / 10 == 0 ? (sws + j) : String.valueOf(j);
        } else {
            return sws.repeat(2);
        }
    }

    public static String countWhiteSpaces(boolean first, boolean middle, Manuscript manuscript, int i, int j, int line){
        String ws_11 = sws.repeat(11);
        String ws_15 = sws.repeat(15);

        // Allows the player to know if the placement is valid
        if(line == 3 && manuscript.isValidPlacement(i, j)){
            return sws.repeat(9) + "@" + sws.repeat(5);
        }

        if(!middle){
            return ws_15;
        }

        return first ? ws_11 : ws_15;
    }

    public static String printManuscript(Manuscript manuscript){

        StringBuffer sb = new StringBuffer();

        Face[][] field = manuscript.getField();
        int xMin = manuscript.getxMin() -1;
        int xMax = manuscript.getxMax() +1;
        int yMin = manuscript.getyMin() -1;
        int yMax = manuscript.getyMax() +1;

        printxAxis(xMin, xMax);

        // translate the manuscript into a matrix of string representing cards
        Queue<String>[][] matrix = new Queue[Manuscript.FIELD_DIM][Manuscript.FIELD_DIM];
        for(int i = xMin; i <= xMax; i++){
            for(int j = yMin; j <= yMax; j++){
                if(field[i][j] != null){
                    try{
                        matrix[i][j] = fromFaceToCliCard(field[i][j]);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        boolean first;
        boolean middle;

        for(int j = yMin; j <= yMax; j++) {
            for (int line = 1; line <= 3; line++) {
                sb.append(printyAxis(line, j));
                first = true;
                middle = false;
                for (int i = xMin; i <= xMax; i++) {
                    if(matrix[i][j-1] != null && matrix[i][j-1].peek() != null){
                        sb.append(matrix[i][j-1].remove());
                        first = true;
                        middle = true;
                        continue;
                    }
                    // if the card is null, print white spaces and go to next card on the same row
                    if(matrix[i][j] == null){
                        String ws = countWhiteSpaces(first, middle, manuscript, i, j, line);
                        sb.append(ws);
                        first = false;
                        continue;
                    }
                    if(matrix[i][j] != null){
                        sb.append(matrix[i][j].remove());
                        middle = true;
                        first = true;
                    }else if(matrix[i][j+1] != null){
                        sb.append(matrix[i][j+1].remove());
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    // example test
    public static void main(String[] args) {
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        ObjectiveCard o1 = objectiveDeck.get(0);
        ObjectiveCard o2 = objectiveDeck.get(1);
        ArrayList<ObjectiveCard> secretObjectives = new ArrayList<>();
        secretObjectives.add(o1);
        secretObjectives.add(o2);
        System.out.println(showObjective(secretObjectives));
    }

    public static String showFace(Face face){

        Corner UR = face.getCornerUR();
        Corner UL = face.getCornerUL();
        Corner LR = face.getCornerLR();
        Corner LL = face.getCornerLL();

        String colour = face.getColour().toColourControl() + ColourControl.BOLD;   // colour and bold
        String reset = ColourControl.RESET;

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

    public static String showObjective(ArrayList<ObjectiveCard> secretObjectives){

        ObjectiveCard o1 = secretObjectives.get(0);
        ObjectiveCard o2 = secretObjectives.get(1);

        String first = "╭-----------------╮    ╭-----------------╮";
        String second = "| Points: " + o1.getObjPointsMap().get(o1.getClass()) + "       |    " + "| Points: " + o2.getObjPointsMap().get(o2.getClass()) + "       |";
        String third = "|" + o1 + "|  "+"  |" + o2 + "|";
        String fourth = "|                 |    |                 |";
        String fifth =  "╰-----------------╯    ╰-----------------╯";

        return (first + "\n" + second +"\n" + third+"\n"+ fourth+"\n"+fifth);
    }

}