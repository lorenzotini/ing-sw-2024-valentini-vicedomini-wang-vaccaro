package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Net.Commands.*;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.in;
import static java.lang.System.out;

public class Tui implements View {

    private VirtualView client;
    private static final String sws = " "; // single white space

    public Tui(VirtualView client) throws IOException, InterruptedException {
        this.client = client;
    }

    @Override
    public void run() throws IOException, InterruptedException {

        Scanner scan = new Scanner(in);

        while (true) {

            out.print("> ");
            String command = scan.nextLine();

            switch (command.toLowerCase()) {

                case "help":
                    out.println("Commands:");
                    out.println("addstarter - add a starter card to your board");
                    out.println("chooseobj - choose an objective card");
                    out.println("addcard - add a card to your board");
                    out.println("draw - draw a card from the market");
                    break;

                case "addstarter":
                    //out.println(Tui.showStarter(client.getMiniModel().getStarter()));
                    out.println("What side do you want to play? (front or back)");
                    while (true) {
                        String side = scan.next();
                        if (side.equalsIgnoreCase("front")) {
                            Command comm = new AddStarterCommand(client.getUsername(), true);
                            client.sendCommand(comm);
                            break;
                        } else if (side.equalsIgnoreCase("back")) {
                            Command comm = new AddStarterCommand(client.getUsername(), false);
                            client.sendCommand(comm);
                            break;
                        } else {
                            out.println("Invalid face: insert front or back");
                        }
                        // Consume the invalid input to clear the scanner's buffer
                        scan.nextLine();
                    }
                    // Consume the invalid input to clear the scanner's buffer
                    scan.nextLine();
                    break;

                case "chooseobj":
                    //out.println(Tui.showObjective(client.getMiniModel().getSecretObjectives()));
                    int obj;
                    out.println("Which objective do you want to achieve? (1 or 2)");
                    while (true) {
                        try {
                            obj = scan.nextInt();
                            if (obj == 1 || obj == 2) {
                                Command comm = new ChooseObjectiveCommand(client.getUsername(), obj);
                                client.sendCommand(comm);
                                break;
                            } else {
                                out.println("Invalid number, insert 1 or 2");
                            }
                        } catch (InputMismatchException e) {
                            out.println("Invalid input. Please enter an integer.");
                        } finally {
                            // Consume the invalid input to clear the scanner's buffer
                            scan.nextLine();
                        }
                    }
                    // Consume the invalid input to clear the scanner's buffer
                    scan.nextLine();
                    break;

                // TODO creare una soluzione intelligente per gestire gli input di addcard, con while true e try catch vari
                case "addcard":
                    out.println("Which card do you want to add? (choose from 0, 1, 2)");
                    int cardIndex = scan.nextInt();
                    out.println("Front or back?");
                    String face = scan.next();
                    out.println("x = ");
                    int x = scan.nextInt();
                    out.println("y = ");
                    int y = scan.nextInt();
                    if (face.equalsIgnoreCase("front")) {
                        Command comm = new AddCardCommand(client.getUsername(), cardIndex, true, x, y);
                        client.sendCommand(comm);
                    } else if (face.equalsIgnoreCase("back")) {
                        Command comm = new AddCardCommand(client.getUsername(), cardIndex, false, x, y);
                        client.sendCommand(comm);
                    } else {
                        out.println("Invalid face: abort");
                    }
                    break;

                case "draw":
                    //out.println(Tui.showMarket());
                    out.println("enter [cardType] [fromDeck] [faceUpIndex] (res/gold, true/false, 0/1)");
                    String line = scan.nextLine();
                    String[] words = line.split(" ");
                    String cardType = words[0];
                    boolean fromDeck = Boolean.parseBoolean(words[1]);
                    int faceUpIndex = Integer.parseInt(words[2]);
                    boolean isGold = cardType.equalsIgnoreCase("gold");
                    Command comm = new DrawCardCommand(client.getUsername(), isGold, fromDeck, faceUpIndex);
                    client.sendCommand(comm);
                    break;

                default:
                    out.println("Invalid command. Type 'help' for a list of commands.");
                    break;
            }

        }

    }

    private static Queue<String> fromFaceToCliCard(Face face) {

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

        if (!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()) {  // case #1
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        } else if (UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()) { // case #2
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        } else if (!UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()) { // case #3
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        } else if (!UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()) { // case #4
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        } else if (!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #5
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        } else if (UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()) { // case #6
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "╰-----------------╯" + reset;
        } else if (!UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #7
            first = colour + "╭-----------------╮" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        } else if (!UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #8
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "|" + reset;
            fifth = colour + "--------------╯" + reset;
        } else if (UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()) { // case #9
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        } else if (UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #10
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        } else if (!UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #11
            first = colour + "--------------╮" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "|" + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        } else if (UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #12
            first = colour + "╭--------------" + reset;
            second = colour + "|" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "-----------" + reset;
        } else if (UR.isHidden() && UL.isHidden() && LR.isHidden() && !LL.isHidden()) { // case #13
            first = colour + "-----------" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "|" + Tui.constructString(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new))) + colour + "|" + reset;
            fourth = colour + "|" + LL.getSymbol().toCliString() + colour + sws.repeat(13) + reset;
            fifth = colour + "╰--------------" + reset;
        } else if (UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #14
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

    private static String constructString(ArrayList<String> permanentResources) {
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
                out.println("Unexpected value: " + permanentResources.size());
        }

        String line = start + permRes + end;
        return line;
    }

    private static void printxAxis(int xMin, int xMax) {
        String xAxis = sws.repeat(10);
        for (int i = xMin; i <= xMax; i++) {
            if (i / 10 == 0) {
                xAxis = xAxis + i + sws.repeat(14);
            } else {
                xAxis = xAxis + i + sws.repeat(13);
            }
        }
        out.println(xAxis);
    }

    private static String printyAxis(int line, int j) {
        if (line == 3) {
            return j / 10 == 0 ? (sws + j) : String.valueOf(j);
        } else {
            return sws.repeat(2);
        }
    }

    private static String countWhiteSpaces(boolean first, boolean middle, Manuscript manuscript, int i, int j, int line) {
        String ws_11 = sws.repeat(11);
        String ws_15 = sws.repeat(15);

        // Allows the player to know if the placement is valid
        if (line == 3 && manuscript.isValidPlacement(i, j)) {
            return sws.repeat(9) + "@" + sws.repeat(5);
        }

        if (!middle) {
            return ws_15;
        }

        return first ? ws_11 : ws_15;
    }

    private static String printManuscript(Manuscript manuscript) {

        StringBuffer sb = new StringBuffer();

        Face[][] field = manuscript.getField();
        int xMin = manuscript.getxMin() - 1;
        int xMax = manuscript.getxMax() + 1;
        int yMin = manuscript.getyMin() - 1;
        int yMax = manuscript.getyMax() + 1;

        printxAxis(xMin, xMax);

        // translate the manuscript into a matrix of string representing cards
        Queue<String>[][] matrix = new Queue[Manuscript.FIELD_DIM][Manuscript.FIELD_DIM];
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                if (field[i][j] != null) {
                    try {
                        matrix[i][j] = fromFaceToCliCard(field[i][j]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        boolean first;
        boolean middle;

        for (int j = yMin; j <= yMax; j++) {
            for (int line = 1; line <= 3; line++) {
                sb.append(printyAxis(line, j));
                first = true;
                middle = false;
                for (int i = xMin; i <= xMax; i++) {
                    if (matrix[i][j - 1] != null && matrix[i][j - 1].peek() != null) {
                        sb.append(matrix[i][j - 1].remove());
                        first = true;
                        middle = true;
                        continue;
                    }
                    // if the card is null, print white spaces and go to next card on the same row
                    if (matrix[i][j] == null) {
                        String ws = countWhiteSpaces(first, middle, manuscript, i, j, line);
                        sb.append(ws);
                        first = false;
                        continue;
                    }
                    if (matrix[i][j] != null) {
                        sb.append(matrix[i][j].remove());
                        middle = true;
                        first = true;
                    } else if (matrix[i][j + 1] != null) {
                        sb.append(matrix[i][j + 1].remove());
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static String showFace(Face face) {

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

        return (first + "\n" + second + "\n" + third + "\n" + fourth + "\n" + fifth);

    }

    public static String showStarter(StarterCard card) {
        return ("Starter Front:" + "\n" + showFace(card.getFront()) + ("\nStarter Back:") + "\n" + showFace(card.getBack()));
    }

    public static String showObjective(ArrayList<ObjectiveCard> secretObjectives) {

        ObjectiveCard o1 = secretObjectives.get(0);
        ObjectiveCard o2 = secretObjectives.get(1);

        o1.toCliCard();
        o2.toCliCard();

        return ("Objective 1: \n" + o1.toCliCard() + "\n\nObjective 2: \n" + o2.toCliCard());

    }

    public static void showTitle() {
        out.println("\n" +
                " ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                "██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                "██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                "██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                "╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                " ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n" +
                "                                                                                                                    \n");
    }

    public static void main(String[] args) {

        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);

        Manuscript m = new Manuscript();

        // #0
        m.getField()[40][40] = starterDeck.get(0).getFront();
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
        m.getField()[38][42] = resourceDeck.get(22).getFront();
        m.getField()[38][42].getCornerLL().setHidden(true);

        // #4
        m.getField()[37][43] = resourceDeck.get(23).getBack();
        m.getField()[37][43].getCornerUL().setHidden(true);

        // #5
        m.getField()[40][38] = resourceDeck.get(0).getFront();
        m.getField()[40][38].getCornerLR().setHidden(true);

        // #6
        m.getField()[41][39] = resourceDeck.get(30).getFront();
        m.getField()[41][39].getCornerLR().setHidden(true);

        // #7
        m.getField()[36][42] = resourceDeck.get(31).getFront();

        // #8
        m.getField()[42][40] = resourceDeck.get(10).getBack();
        m.getField()[42][40].getCornerLL().setHidden(true);

        // #9
        m.getField()[40][42] = resourceDeck.get(11).getFront();
        m.getField()[40][42].getCornerUR().setHidden(true);

        // #10
        m.getField()[41][41] = resourceDeck.get(32).getBack();

        m.setxMin(36);
        m.setyMin(38);
        m.setxMax(42);
        m.setyMax(43);

        out.print(Tui.printManuscript(m));

        /////////////////////////
        Manuscript n = new Manuscript();

        n.getField()[40][40] = goldDeck.get(22).getFront();
        n.getField()[40][40].getCornerLR().setHidden(true);

        n.getField()[41][41] = goldDeck.get(23).getFront();

        n.setxMin(40);
        n.setyMin(40);
        n.setxMax(41);
        n.setyMax(41);

        for (var c : resourceDeck) {
            out.println(showFace(c.getFront()));
            out.println();
            out.println(showFace(c.getBack()));
            out.println();
        }

        for (var c : goldDeck) {
            out.println(showFace(c.getFront()));
            out.println();
            out.println(showFace(c.getBack()));
            out.println();
        }

        for (var c : starterDeck) {
            out.println(showFace(c.getFront()));
            out.println();
            out.println(showFace(c.getBack()));
            out.println();
        }

        //it's used to show the player a message/update from the server on the tui


    }

    public void showString(String phrase) {
        out.println(phrase);
    }


    //TODO implements the general method
    @Override
    public void show(ArrayList<ResourceCard> hand ){

    }

    @Override
    public void show(Manuscript manuscript) {

    }

    @Override
    public void show(Board board) {

    }

    @Override
    public void show(Market market) {


    }

    @Override
    public void startTheGame() {

    }
}