package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PointsMultiplier;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Model.States.*;
import it.polimi.ingsw.gc27.Net.Commands.*;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.in;
import static java.lang.System.out;

public class Tui implements View {

    private VirtualView client;
    private static final String sws = " "; // single white space
    private static final Queue<String> noCardPrint = new LinkedList<>();
    private Scanner scan = new Scanner(in);

    static {
        noCardPrint.add("╔═════════════════╗");
        noCardPrint.add("║      #####      ║");
        noCardPrint.add("║      #####      ║");
        noCardPrint.add("║      #####      ║");
        noCardPrint.add("╚═════════════════╝");
    }

    @Override
    public void run() throws IOException, InterruptedException {

        showTitle();

        out.println("\nThe game is starting!");
        //TODO svuotare il buffer, altrimenti printa tutti i comandi presi durante l'attesa dei giocatori

        while (true) {

            out.print(client.getMiniModel().getPlayer().getPlayerState() + "\n> ");

            String command = scan.nextLine();
            if (command.equals("\n") || command.isEmpty()){
                command = scan.nextLine();
            }
            switch (command.toLowerCase()) {

                case "help":
                    out.println("\nCommands:");
                    out.println("addstarter - add a starter card to your board");
                    out.println("chooseobj - choose an objective card");
                    out.println("addcard - add a card to your board");
                    out.println("draw - draw a card from the market");
                    out.println("Visualize:");
                    out.println("man - print your manuscript");
                    out.println("hand - print your hand");
                    out.println("obj - print your secret objective");
                    out.println("market - print the market");
                    out.println("board - print the players' score board");
                    break;

                case "addstarter":

                    if(!checkState(InitializingState.class)){
                        break;
                    }

                    out.println(showStarter(client.getMiniModel().getPlayer().getStarterCard()));
                    out.println("\nWhat side do you want to play? (front or back)");
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
                            out.println("\nInvalid face: insert front or back");
                        }
                        // Consume the invalid input to clear the scanner's buffer
                        scan.nextLine();
                    }
                    // Consume the invalid input to clear the scanner's buffer

                    break;

                case "chooseobj":

                    if(!checkState(ChooseObjectiveState.class)){
                        break;
                    }

                    // TODO fatto così chiede quale vuole come se fosse la prima volta anche se l'ha già fatto
                    out.println(showObjectives(client.getMiniModel().getPlayer().getSecretObjectives()));
                    int obj;
                    out.println("\nWhich objective do you want to achieve? (1 or 2)");
                    while (true) {
                        try {
                            obj = scan.nextInt();
                            if (obj == 1 || obj == 2) {
                                Command comm = new ChooseObjectiveCommand(client.getUsername(), obj);
                                client.sendCommand(comm);
                                break;
                            } else {
                                out.println("\nInvalid number, insert 1 or 2");
                            }
                        } catch (InputMismatchException e) {
                            out.println("\nInvalid input. Please enter an integer.");
                        } finally {
                            // Consume the invalid input to clear the scanner's buffer
                            scan.nextLine();
                        }
                    }
                    // Consume the invalid input to clear the scanner's buffer
                    break;

                // TODO creare una soluzione intelligente per gestire gli input di addcard, con while true e try catch vari
                case "addcard":

                    if(!checkState(PlayingState.class)){
                        break;
                    }

                    out.println(showManuscript(client.getMiniModel().getManuscript()));
                    out.println("\nWhich card do you want to add? (choose from 1, 2, 3)");
                    int cardIndex = scan.nextInt() - 1;
                    out.println("\nFront or back?");
                    String face = scan.next();
                    out.println("\nx = ");
                    int x = scan.nextInt();
                    out.println("\ny = ");
                    int y = scan.nextInt();
                    if (face.equalsIgnoreCase("front")) {
                        Command comm = new AddCardCommand(client.getUsername(), cardIndex, true, x, y);
                        client.sendCommand(comm);
                    } else if (face.equalsIgnoreCase("back")) {
                        Command comm = new AddCardCommand(client.getUsername(), cardIndex, false, x, y);
                        client.sendCommand(comm);
                    } else {
                        out.println("\nInvalid face: abort");
                    }
                    break;

                case "draw":

                    if(!checkState(DrawingState.class)){
                        break;
                    }

                    out.println(showMarket(client.getMiniModel().getMarket()));
                    out.println("\nEnter [cardType] [fromDeck] [faceUpIndex] (res/gold, true/false, 0/1)");
                    String line = scan.nextLine();
                    String[] words = line.split(" ");
                    String cardType = words[0];
                    boolean fromDeck = Boolean.parseBoolean(words[1]);
                    int faceUpIndex = Integer.parseInt(words[2]);
                    boolean isGold = cardType.equalsIgnoreCase("gold");
                    Command comm = new DrawCardCommand(client.getUsername(), isGold, fromDeck, faceUpIndex);
                    client.sendCommand(comm);
                    break;

                case "man":
                    out.println("\n" + showManuscript(client.getMiniModel().getManuscript()));
                    break;

                case "hand":
                    out.println("\n" + showHand(client.getMiniModel().getHand()));
                    break;

                case "obj":
                    out.println("\n" + showObjectives(client.getMiniModel().getPlayer().getSecretObjectives()));
                    break;

                case "market":
                    out.println("\n" + showMarket(client.getMiniModel().getMarket()));
                    break;

                case "board":
                    out.println("\n" + showBoard(client.getMiniModel().getBoard()));
                    break;

                default:
                    out.println("\nInvalid command. Type 'help' for a list of commands.");
                    break;

            }

        }

    }

    private boolean checkState(Class<? extends PlayerState> requestedState) throws RemoteException {
        PlayerState playerState = client.getMiniModel().getPlayer().getPlayerState();
        if (requestedState.isInstance(playerState)) {
            return true;
        } else {
            out.println("Wrong state");
            return false;
        }
    }

    @Override
    public void setClient(VirtualView client){
        this.client = client;
    }

    @Override
    public void showString(String phrase) {
        out.println(phrase);
    }

    @Override
    public void show(ArrayList<ResourceCard> hand) {
        out.println(showHand(hand));
    }

    @Override
    public void show(ObjectiveCard secretObj) {
        ArrayList<ObjectiveCard> obj = new ArrayList<>();
        obj.add(secretObj);
        out.println(showObjectives(obj));
    }

    @Override
    public void show(Manuscript manuscript) {
        out.println(showManuscript(manuscript));
    }

    @Override
    public void show(Board board) {
        out.println(showBoard(board));
    }

    @Override
    public void show(Market market) {
        out.println(showMarket(market));
    }

    @Override
    public String read() {
        return scan.nextLine();
    }


    private static Queue<String> fromFaceToCliCard(Face face) throws Exception {

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
            first = colour + "╔═════════════════╗" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + colour + "║" + reset;
            fifth = colour + "╚═════════════════╝" + reset;
        } else if (UR.isHidden() && !UL.isHidden() && !LR.isHidden() && !LL.isHidden()) { // case #2
            first = colour + "╔══════════════" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "║" + reset;
            fifth = colour + "╚═════════════════╝" + reset;
        } else if (!UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()) { // case #3
            first = colour + "══════════════╗" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "║" + reset;
            fifth = colour + "╚═════════════════╝" + reset;
        } else if (!UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()) { // case #4
            first = colour + "╔═════════════════╗" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + sws.repeat(13) + reset;
            fifth = colour + "╚══════════════" + reset;
        } else if (!UR.isHidden() && !UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #5
            first = colour + "╔═════════════════╗" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "║" + reset;
            fifth = colour + "══════════════╝" + reset;
        } else if (UR.isHidden() && UL.isHidden() && !LR.isHidden() && !LL.isHidden()) { // case #6
            first = colour + "═══════════" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + sws.repeat(15) + LR.getSymbol().toCliString() + colour + "║" + reset;
            fifth = colour + "╚═════════════════╝" + reset;
        } else if (!UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #7
            first = colour + "╔═════════════════╗" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(15) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "═══════════" + reset;
        } else if (!UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #8
            first = colour + "══════════════╗" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "║" + reset;
            fifth = colour + "══════════════╝" + reset;
        } else if (UR.isHidden() && !UL.isHidden() && LR.isHidden() && !LL.isHidden()) { // case #9
            first = colour + "╔══════════════" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + sws.repeat(13) + reset;
            fifth = colour + "╚══════════════" + reset;
        } else if (UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #10
            first = colour + "═══════════" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "═══════════" + reset;
        } else if (!UR.isHidden() && UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #11
            first = colour + "══════════════╗" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "═══════════" + reset;
        } else if (UR.isHidden() && !UL.isHidden() && LR.isHidden() && LL.isHidden()) { // case #12
            first = colour + "╔══════════════" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(11) + reset;
            fifth = colour + "═══════════" + reset;
        } else if (UR.isHidden() && UL.isHidden() && LR.isHidden() && !LL.isHidden()) { // case #13
            first = colour + "═══════════" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + colour + sws.repeat(13) + reset;
            fifth = colour + "╚══════════════" + reset;
        } else if (UR.isHidden() && UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #14
            first = colour + "═══════════" + reset;
            second = colour + sws.repeat(11) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "║" + reset;
            fifth = colour + "══════════════╝" + reset;
        } else if (!UR.isHidden() && UL.isHidden() && LR.isHidden() && !LL.isHidden()) { // case #15
            first = colour + "══════════════╗" + reset;
            second = colour + sws.repeat(13) + UR.getSymbol().toCliString() + colour + "║" + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + "║" + LL.getSymbol().toCliString() + sws.repeat(13) + reset;
            fifth = colour + "╚══════════════" + reset;
        } else if (UR.isHidden() && !UL.isHidden() && !LR.isHidden() && LL.isHidden()) { // case #16
            first = colour + "╔══════════════" + reset;
            second = colour + "║" + UL.getSymbol().toCliString() + sws.repeat(13) + reset;
            third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
            fourth = colour + sws.repeat(13) + LR.getSymbol().toCliString() + colour + "║" + reset;
            fifth = colour + "══════════════╝" + reset;
        }

        Queue<String> lines = new LinkedList<>();
        lines.add(first);
        lines.add(second);
        lines.add(third);
        lines.add(fourth);
        lines.add(fifth);
        return lines;

    }

    // isPermanent true is used to print the permanent resources of a card. If false, the goldCard requirements are printed
    private static String constructResources(ArrayList<String> resources, boolean isPermanent) {
        String start = isPermanent ? " " : "";
        String end = isPermanent ? " " : "";

        String permRes = "";
        for (int i = 0; i < resources.size(); i++) {
            permRes = permRes + resources.get(i);
        }

        switch (resources.size()) {
            case 0:
                start = start + "        ";
                end = "       " + end;
                break;
            case 1:
                start = start + "       ";
                end = "       " + end;
                break;
            case 2:
                start = start + "      ";
                end = "       " + end;
                break;
            case 3:
                start = start + "      ";
                end = "      " + end;
                break;
            case 4:
                start = start + "     ";
                end = "      " + end;
                break;
            case 5:
                start = start + "     ";
                end = "     " + end;
                break;
            default:
                out.println("Unexpected value: " + resources.size());
        }

        String line = start + permRes + end;
        return line;
    }

    private static String printxAxis(int xMin, int xMax) {
        StringBuilder xAxis = new StringBuilder(sws.repeat(10));
        for (int i = xMin; i <= xMax; i++) {
            if (i / 10 == 0) {
                xAxis.append(i).append(sws.repeat(14));
            } else {
                xAxis.append(i).append(sws.repeat(13));
            }
        }
        return xAxis.toString();
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

    public static String showManuscript(Manuscript manuscript) {

        StringBuffer sb = new StringBuffer();

        sb.append("\n");

        Face[][] field = manuscript.getField();
        int xMin = manuscript.getxMin() - 1;
        int xMax = manuscript.getxMax() + 1;
        int yMin = manuscript.getyMin() - 1;
        int yMax = manuscript.getyMax() + 1;

        sb.append(printxAxis(xMin, xMax));

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

        // Counters
        sb.append("\nCounters: " + "\nAnimal     -> " + manuscript.getAnimalCounter() + "\nFungi      -> " + manuscript.getFungiCounter() + "\nInsect     -> " + manuscript.getInsectCounter() + "\nPlant      -> " + manuscript.getPlantCounter() + "\ninkwell    -> " + manuscript.getInkwellCounter() + "\nquill      -> " + manuscript.getQuillCounter() + "\nmanuscript -> " + manuscript.getManuscriptCounter() + "\n");

        return sb.toString();

    }

    private static String writePoints(Card card, boolean isFront) {

        // Starter cards OR Back face OR points = 0 have no points
        if (card instanceof StarterCard || !isFront || (isFront && ((ResourceCard) card).getCardPoints() == 0)) {
            return "               ";
        }

        // Front face of a GoldCard
        if (card instanceof GoldCard) {
            if (((GoldCard) card).getPointsMultiplier().equals(PointsMultiplier.EMPTY))
                return ColourControl.RESET + "       " + ColourControl.YELLOW_BACKGROUND + ((ResourceCard) card).getCardPoints() + ColourControl.RESET + "       ";
            return ColourControl.RESET + "      " + ColourControl.YELLOW_BACKGROUND + ((ResourceCard) card).getCardPoints() + "|" + ((GoldCard) card).getPointsMultiplier().toString() + ColourControl.RESET + "      ";
        } else { // Front face of a ResourceCard
            return ColourControl.RESET + "       " + ColourControl.YELLOW_BACKGROUND + ((ResourceCard) card).getCardPoints() + ColourControl.RESET + "       ";
        }

    }

    private static String writeRequirements(Card card, boolean isFront) {

        // Only front face of GoldCard has requirements
        if (card instanceof GoldCard && isFront) {
            return constructResources(((GoldCard) card).getRequirements().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), false);
        }

        // Default
        return "               ";

    }

    protected static Queue<String> toCliCard(Card card, boolean isFront) {

        Face face = isFront ? card.getFront() : card.getBack();

        Corner UR = face.getCornerUR();
        Corner UL = face.getCornerUL();
        Corner LR = face.getCornerLR();
        Corner LL = face.getCornerLL();

        String colour = face.getColour().toColourControl() + ColourControl.BOLD;   // colour and bold
        String reset = ColourControl.RESET;

        String first = colour + "╔═════════════════╗" + reset;
        String second = colour + "║" + UL.getSymbol().toCliString() + writePoints(card, isFront) + UR.getSymbol().toCliString() + colour + "║" + reset;
        String third = colour + "║" + constructResources(face.getPermanentResources().stream().map(o -> o.toCornerSymbol().toCliString()).collect(Collectors.toCollection(ArrayList::new)), true) + colour + "║" + reset;
        String fourth = colour + "║" + LL.getSymbol().toCliString() + writeRequirements(card, isFront) + LR.getSymbol().toCliString() + colour + colour + "║" + reset;
        String fifth = colour + "╚═════════════════╝" + reset;

        Queue<String> lines = new LinkedList<>();
        lines.add(first);
        lines.add(second);
        lines.add(third);
        lines.add(fourth);
        lines.add(fifth);

        return lines;

    }

    public static String showStarter(StarterCard card) {
        return ("\nStarter Front:" +
                "\n" + toCliCard(card, true).stream().collect(Collectors.joining("\n")) +
                "\nStarter Back:" +
                "\n" + toCliCard(card, false).stream().collect(Collectors.joining("\n")));
    }

    public static String showObjectives(ArrayList<ObjectiveCard> secretObjectives) {

        String printedObjectives = "\n";
        int i = 1;
        for (ObjectiveCard o : secretObjectives) {
            printedObjectives += "Objective" + (secretObjectives.size() == 1 ? "" : " " + i) + ": \n" + o.toCliCard() + "\n\n";
            i++;
        }

        return printedObjectives;

    }

    public static String showBoard(Board board) {
        return ("\nRed: " + ColourControl.RED_BACKGROUND_BRIGHT + board.getPointsRedPlayer() + ColourControl.RESET +
                "\nYellow: " + ColourControl.YELLOW_BACKGROUND_BRIGHT + board.getPointsYellowPlayer() + ColourControl.RESET +
                "\nGreen: " + ColourControl.GREEN_BACKGROUND_BRIGHT + board.getPointsGreenPlayer() + ColourControl.RESET +
                "\nBlue: " + ColourControl.BLUE_BACKGROUND_BRIGHT + board.getPointsBluePlayer() + ColourControl.RESET);
    }

    public static String showHand(ArrayList<ResourceCard> hand) {

        String printedHand = "\n";

        ArrayList<Queue<String>> cards = new ArrayList<>();

        for (var card : hand) {
            cards.add(toCliCard(card, true));
        }

        int numLinesToPrint = cards.getFirst().size();

        for (int i = 0; i < numLinesToPrint; i++) {
            for (var card : cards) {
                printedHand += card.remove() + "  ";
            }
            printedHand += "\n";
        }

        // Print the numbers of the cards
        printedHand += sws.repeat(9) + "1" + sws.repeat(20) + "2" + sws.repeat(20) + "3" + "\n";

        return printedHand;

    }

    public static String showMarket(Market market) {

        String printedMarket = "\n";

        Queue<String> resourceDeckTop;
        Queue<String> goldDeckTop;

        try {
            resourceDeckTop = toCliCard(market.getResourceDeck().getLast(), false);
        } catch (NoSuchElementException e) {
            resourceDeckTop = noCardPrint;
        }

        try {
            goldDeckTop = toCliCard(market.getGoldDeck().getLast(), false);
        } catch (NoSuchElementException e) {
            goldDeckTop = noCardPrint;
        }

        Queue<String> resourceOne = toCliCard(market.getFaceUp(false)[0], true);
        Queue<String> resourceTwo = toCliCard(market.getFaceUp(false)[1], true);
        Queue<String> goldOne = toCliCard(market.getFaceUp(true)[0], true);
        Queue<String> goldTwo = toCliCard(market.getFaceUp(true)[1], true);

        int numLinesToPrint = resourceDeckTop.size();

        printedMarket += "Resources:\n";

        for (int i = 0; i < numLinesToPrint; i++) {
            printedMarket += resourceDeckTop.remove() + "   " + resourceOne.remove() + "  " + resourceTwo.remove() + "\n";
        }

        printedMarket += "\nGolds:\n";

        for (int i = 0; i < numLinesToPrint; i++) {
            printedMarket += goldDeckTop.remove() + "   " + goldOne.remove() + "  " + goldTwo.remove() + "\n";
        }

        return printedMarket;

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

}