package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.Corner;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Tui {

    private VirtualView client;
    private VirtualServer server;
    private static String sws = " "; // single white space

    public Tui(VirtualView client, VirtualServer server) throws IOException, InterruptedException {
        this.client = client;
        this.server = server;
    }

    public void runCli() throws IOException, RemoteException, InterruptedException {

        Scanner scan = new Scanner(System.in);

        while (true) {

            System.out.print("> ");
            String command = scan.nextLine();

            switch (command.toLowerCase()) {

                case "help":
                    System.out.println("Commands:");
                    System.out.println("addstarter - add a starter card to your board");
                    System.out.println("chooseobj - choose an objective card");
                    System.out.println("addcard - add a card to your board");
                    System.out.println("draw - draw a card from the market");
                    break;

                case "addstarter":
                    //System.out.println(Tui.showStarter(client.getMiniModel().getStarter()));
                    System.out.println("What side do you want to play? (front or back)");
                    while(true){
                        String side = scan.next();
                        if(side.equalsIgnoreCase("front")) {
                            server.addStarter(client.getUsername(), true);
                            break;
                        }else if(side.equalsIgnoreCase("back")){
                            server.addStarter(client.getUsername(), false);
                            break;
                        }else{
                            System.out.println("Invalid face: insert front or back");
                        }
                        // Consume the invalid input to clear the scanner's buffer
                        scan.nextLine();
                    }
                    // Consume the invalid input to clear the scanner's buffer
                    scan.nextLine();
                    break;

                /*case "chooseobj":
                    //System.out.println(Tui.showObjective(client.getMiniModel().getSecretObjectives()));
                    int obj;
                    // Ask for connection type
                    System.out.println("Which objective do you want to choose? (1, 2)");
                    while(true){
                        try {
                            obj = scan.nextInt();
                            if(obj == 1){
                                server.chooseObjective(client.getUsername(), 0);
                                break;
                            } else if(obj == 2){
                                server.chooseObjective(client.getUsername(), 1);
                                break;
                            } else {
                                System.out.println("Invalid number, insert 1 or 2");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter an integer.");
                        } finally {
                            // Consume the invalid input to clear the scanner's buffer
                            scan.nextLine();
                        }
                    }
                    // Consume the invalid input to clear the scanner's buffer
                    scan.nextLine();
                    break;*/

                // TODO creare una soluzione intelligente per gestire gli input di addcard, con while true e try catch vari
                case "addcard":
                    System.out.println("Which card do you want to add? (choose from 0, 1, 2)");
                    int cardIndex = scan.nextInt();
                    System.out.println("Front or back?");
                    String face = scan.next();
                    System.out.println("x = ");
                    int x = scan.nextInt();
                    System.out.println("y = ");
                    int y = scan.nextInt();
                    if(face.equalsIgnoreCase("front")) {
                        server.addCard(client.getUsername(), cardIndex, true, x, y);
                    }else if(face.equalsIgnoreCase("back")){
                        server.addCard(client.getUsername(), cardIndex, false, x, y);
                    }else{
                        System.out.println("Invalid face: abort");
                    }
                    break;

                case "draw":
                    //System.out.println(Tui.showMarket());
                    System.out.println("enter [cardType] [fromDeck] [faceUpIndex] (res/gold, true/false, 0/1)");
                    String line = scan.nextLine();
                    String[] words = line.split(" ");
                    String cardType = words[0];
                    boolean fromDeck = Boolean.parseBoolean(words[1]);
                    int faceUpIndex = Integer.parseInt(words[2]);
                    if(cardType.equalsIgnoreCase("res")){
                        server.drawResourceCard(client.getUsername(), fromDeck, faceUpIndex);
                    } else if(cardType.equalsIgnoreCase("gold")) {
                        server.drawGoldCard(client.getUsername(), fromDeck, faceUpIndex);
                    }
                    break;

                default:
                    System.out.println("Invalid command. Type 'help' for a list of commands.");
                    break;
            }

        }
    }

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
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #2
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #3
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #4
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString()+ sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && LL.isHidden()){ // case #5
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        }else if(UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()){ // case #6
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        }else if(!UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #7
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()){ // case #8
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #9
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString()+ sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #10
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(!UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #11
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()){ // case #12
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && LR.isHidden() && !LL.isHidden()){ // case #13
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + colour + sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        }else if(UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #14
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
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

    public static String showFace(Face face){

        Corner UR = face.getCornerUR();
        Corner UL = face.getCornerUL();
        Corner LR = face.getCornerLR();
        Corner LL = face.getCornerLL();

        String colour = face.getColour().toColourControl() + ColourControl.BOLD;   // colour and bold
        String reset = ColourControl.RESET;

        String first = colour + "╭-----------------╮" + reset;
        String second = colour + "|" + UL.getSymbol().toCliString() + "               " + UR.getSymbol().toCliString() + colour + "|" + reset;
        String third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
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

        o1.toCliCard();
        o2.toCliCard();

        return ("Objective 1: \n" + o1.toCliCard() + "\n\nObjective 2: \n" + o2.toCliCard());

    }

    public static void showTitle() {
        System.out.println("\n" +
                " ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                "██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                "██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                "██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                "╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                " ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n" +
                "                                                                                                                    \n");
    }

    public static void main(String[] args) {
        System.out.println(ColourControl.CYAN_BACKGROUND_BRIGHT + "       " + ColourControl.RESET);


    }

}
