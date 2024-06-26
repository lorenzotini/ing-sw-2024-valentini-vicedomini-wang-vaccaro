package it.polimi.ingsw.gc27.View.Tui;

import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.ClientClass.*;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Enumerations.PointsMultiplier;
import it.polimi.ingsw.gc27.Model.Game.*;
import it.polimi.ingsw.gc27.Model.States.*;
import it.polimi.ingsw.gc27.Net.Commands.*;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * The Tui class represents the Text User Interface of the game.
 * It provides methods for running the game, setting the client, displaying game status,
 * and handling user input.It communicates with the rest of the system by sending commands
 * to the client.
 * This class implements the View interface.
 */
public class Tui implements View {

    private VirtualView client;
    private static final String sws = " "; // single white space
    private static final Queue<String> noCardPrint = new LinkedList<>();
    private final Scanner scan = new Scanner(in);
    boolean alreadyPrintedWinners = false;
    boolean isEndingState = false;
    private static final ArrayList<String> chatters = new ArrayList<>();


    static {
        // create a card for when the deck is empty
        noCardPrint.add("╔═════════════════╗");
        noCardPrint.add("║      #####      ║");
        noCardPrint.add("║      #####      ║");
        noCardPrint.add("║      #####      ║");
        noCardPrint.add("╚═════════════════╝");

        // add the global chat
        chatters.addFirst("Global");
    }

    /**
     * Runs the game by displaying the title and starting the game interface.
     * The game loop continuously reads user input and handles it based on the current game state.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void run() {
        //TODO svuotare il buffer, altrimenti printa tutti i comandi presi durante l'attesa dei giocatori

        showTitle();
        showString("\nThe game is starting!");

        try {

            chatters.addAll(client.getMiniModel().getOtherPlayersUsernames());

            while (true) {

                MiniModel miniModel = client.getMiniModel();

                TimeUnit.MILLISECONDS.sleep(400);

                out.print(miniModel.getPlayer().getPlayerState() + "\n> ");


                // skip the \n char when entering an input
                scan.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                String command = scan.nextLine();

                switch (command.toLowerCase()) {

                    case "help":
                        out.println("╔═══ Commands ══════════════════════════════════╗");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "addstarter" + ColourControl.RESET + " - add a starter card to your board ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "chooseobj" + ColourControl.RESET + " - choose an objective card          ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "addcard" + ColourControl.RESET + " - add a card to your board            ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "draw" + ColourControl.RESET + " - draw a card from the market            ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "sendmessage" + ColourControl.RESET + " - send a message in chat          ║");
                        out.println("╔═══ Visualize ═════════════════════════════════╗");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "man" + ColourControl.RESET + " - print your manuscript                   ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "hand" + ColourControl.RESET + " - print your hand                        ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "obj" + ColourControl.RESET + " - print your secret objective             ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "market" + ColourControl.RESET + " - print the market                     ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "board" + ColourControl.RESET + " - print the players' score board        ║");
                        out.println("║ " + ColourControl.YELLOW_BOLD + "showchat" + ColourControl.RESET + " - show the global or private chat    ║");
                        out.println("╚═══════════════════════════════════════════════╝");
                        break;

                    case "addstarter":
                        synchronized (this) {
                            if (!checkState(InitializingState.class)) {
                                break;
                            }

                            out.println(showStarter(miniModel.getPlayer().getStarterCard()));
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
                        }
                        break;

                    case "chooseobj":
                        synchronized (this) {
                            if (!checkState(ChooseObjectiveState.class)) {
                                break;
                            }
                            out.println(showObjectives(miniModel.getPlayer().getSecretObjectives()));
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
                        }
                        break;

                    // TODO creare una soluzione intelligente per gestire gli input di addcard, con while true e try catch vari
                    case "addcard":
                        synchronized (this) {
                            if (!checkState(PlayingState.class)) {
                                break;
                            }
                            try {
                                out.println(showManuscript(miniModel.getManuscript()));
                                out.println(showHand(miniModel.getHand()));
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
                            } catch (InputMismatchException e) {
                                out.println("\nInvalid input. Please enter an integer.");
                            }
                        }
                        break;

                    case "draw":
                        synchronized (this) {
                            if (!checkState(DrawingState.class)) {
                                break;
                            }
                            do {
                                out.println(showMarket(miniModel.getMarket()));
                                out.println("\nEnter [cardType] [fromDeck] [faceUpIndex] (res/gold, true/false, 0/1)");
                                String line = scan.nextLine();
                                String[] words = line.split(" ");
                                if (words.length == 3) {
                                    try {
                                        String cardType = words[0];
                                        boolean fromDeck = check(words[1], "true", "false");
                                        int faceUpIndex = Integer.parseInt(words[2]);
                                        boolean isGold = check(cardType,"gold","res");
                                        Command comm = new DrawCardCommand(client.getUsername(), isGold, fromDeck, faceUpIndex);
                                        client.sendCommand(comm);
                                        break;
                                    } catch (Exception e) {
                                        out.println("Invalid format");
                                    }
                                }
                            } while (true);
                        }
                        break;

                    case "man":
                        synchronized (this) {
                            do {
                                out.println("Which manuscript? " + miniModel.getOtherPlayersUsernames());
                                String person = scan.nextLine();
                                if (person.equals("mine")) {
                                    out.println("\n" + showManuscript(miniModel.getManuscript()));
                                    break;
                                } else if (miniModel.checkOtherUsername(person)) {
                                    out.println("\n" + showManuscript(miniModel.getManuscriptsMap().get(person)));
                                    break;
                                }
                            } while (true);
                        }
                        break;

                    case "hand":
                        showString("\n" + showHand(miniModel.getHand()));
                        break;

                    case "obj":
                        showString("\n" + showObjectives(miniModel.getPlayer().getSecretObjectives()));
                        break;

                    case "market":
                        showString("\n" + showMarket(miniModel.getMarket()));
                        break;

                    case "showchat":
                        synchronized (this) {
                            do {
                                out.println("\nWhich chat? " + chatters);
                                String person = scan.nextLine();
                                if (person.equalsIgnoreCase("global")) {
                                    printChat(miniModel.getChats().getFirst());
                                    break;
                                } else if (miniModel.checkOtherUsername(person)) {
                                    printChat(miniModel.getChat(person));
                                    break;
                                }
                            } while (true);
                        }
                        break;
                    case "board":
                        synchronized (this) {
                            out.println("\n" + showBoard(miniModel.getBoard()));
                        }
                        break;
                    case "sendmessage":
                        synchronized (this) {
                            String receiver;
                            boolean f;
                            do {

                                out.println("\nChat available with: \n@Global");
                                for (String u : miniModel.getOtherPlayersUsernames()) {
                                    out.println("@" + u);
                                }
                                out.println("Choose one");
                                receiver = scan.nextLine();
                                if (receiver.equals("\n") || receiver.isEmpty()) {
                                    receiver = scan.nextLine();
                                    break;
                                }

                                f = miniModel.checkOtherUsername(receiver) || receiver.equalsIgnoreCase("global");

                            } while (!f);
                            if (receiver.equalsIgnoreCase("global")) {
                                receiver = receiver.toLowerCase();
                            }
                            out.println("\nContent:");
                            String mess = scan.nextLine();
                            client.sendCommand(new SendMessageCommand(miniModel.getPlayer(), receiver, mess));
                            out.println("\n");
                        }
                        break;
                    default:
                        synchronized (this) {
                            out.println("\nInvalid command. Type 'help' for a list of commands.");
                        }
                        break;
                }
            }
        } catch (InterruptedException e) {
            out.println("Interrupted during sleep");
        } catch (IOException e) {
            out.println("Problem with the net during tui run");
        }

    }

    private boolean checkState(Class<? extends PlayerState> requestedState) throws RemoteException {
        PlayerState playerState = client.getMiniModel().getPlayer().getPlayerState();
        if (requestedState.isInstance(playerState)) {
            return true;
        } else {
            synchronized (this) {
                out.println("Wrong state");
            }
            return false;
        }
    }

    /**
     * Sets the client for this Tui instance.
     *
     * @param client the VirtualView client to be set
     */
    @Override
    public void setClient(VirtualView client) {
        this.client = client;
    }

    /**
     * Notifies the suspension of the game by displaying a message to the user.
     *
     * @param string the message to be displayed
     */
    @Override
    public void suspendedGame(String string) {
        synchronized (this) {
            out.println(string);
        }
    }

    /**
     * Notifies that the game is resuming by displaying a message to the user.
     */
    @Override
    public void resumeTheMatch() {
        synchronized (this) {
            out.println("The game can resume, play");
        }
    }

    /**
     * Displays a generic message to the user. Plus, when the message is to notify the user that it's their turn to play, it prints the game status (e.g. manuscript, market, etc.).
     *
     * @param phrase the message to be displayed
     */
    @Override
    public void showString(String phrase) {
        synchronized (this) {
            out.println(phrase);
            try {
                if (phrase.equals("It's your turn to play")) {
                    MiniModel miniModel = client.getMiniModel();
                    setupToYourRound(miniModel);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Displays the game status, including the board, market, manuscript, and hand.
     *
     * @param miniModel the MiniModel to be displayed
     */
    public void setupToYourRound(MiniModel miniModel) {
        synchronized (this) {
            out.println("\n" + "<------------------------------------------------------------>" + "\n");
            out.println(showBoard(miniModel.getBoard()));
            out.println(showMarket(miniModel.getMarket()));
            out.println(showManuscript(miniModel.getManuscript()));
            out.println(showHand(miniModel.getHand()));
        }
    }

    /**
     * Displays the hand of the player.
     *
     * @param hand the hand to be displayed
     */
    @Override
    public void show(ArrayList<ResourceCard> hand) {
        if (!isEndingState) {
            synchronized (this) {
                out.println(showHand(hand));
            }
        }
    }

    /**
     * Displays the manuscript of the player.
     *
     * @param manuscript the manuscript to be displayed
     */
    @Override
    public void show(ClientManuscript manuscript) {
        synchronized (this) {
            if (!isEndingState) {
                out.println(showManuscript(manuscript));
            }
        }
    }

    /**
     * This method is not used in the Tui class. It is empty on purpose.
     *
     * @param board the board to be displayed
     */
    @Override
    public void show(ClientBoard board) {

    }

    /**
     * This method is not used in the Tui class. It is empty on purpose.
     *
     * @param chat the chat to be displayed
     */
    @Override
    public void show(ClientChat chat) {
    }

    /**
     * This method is not used in the Tui class. It is empty on purpose.
     *
     * @param market the market to be displayed
     */
    @Override
    public void show(ClientMarket market) {

    }

    /**
     * This method is not used in the Tui class. It is empty on purpose.
     *
     * @param manuscript the manuscript of another player to be displayed
     */
    @Override
    public void updateManuscriptOfOtherPlayer(ClientManuscript manuscript) {

    }

    /**
     * Reads input from the client using a {@Link Scanner}.
     *
     * @return the input read from the client
     */
    @Override
    public String read() {
        return scan.nextLine();
    }

    private void printChat(ClientChat chat) {
        synchronized (this) {
            String username;
            username = chat.getChatters().stream()
                    .filter(user -> {
                        try {
                            return !user.equals(client.getUsername());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).toList()
                    .getFirst();
            out.println("\nChat with " + username + ":");
            if (chat.getChatMessages().isEmpty()) {
                out.println("No messages yet\n");
            } else {
                for (ChatMessage c : chat.getChatMessages()) {
                    out.println(c.getSender() + ":< " + c.getContent() + " >");
                }
                out.println("\n");
            }
        }
    }

    /**
     * This method is not used in the Tui class. It is empty on purpose.
     */
    @Override
    public void okAck(String string) {

    }

    /**
     * Notifies the user that the command was not successful.
     *
     * @param string the message to be displayed
     */
    @Override
    public void koAck(String string) {
        synchronized (this) {
            out.println(string);
        }
    }

    /**
     * Declare the winner(s) of the game.
     */
    @Override
    public void showWinners() {
        synchronized (this) {
            try {
                showWinnersToEveryone(client.getMiniModel().getBoard().getScoreBoard());
                if (client.getMiniModel().getPlayer().getPlayerState() instanceof EndingState) {
                    isEndingState = true;
                }
            } catch (RemoteException e) {
                throw new RuntimeException("Problem with the net");
            }
        }
    }

    private void showWinnersToEveryone(Map<String, Integer> scoreBoard) {
        if (!this.alreadyPrintedWinners) {
            List<Map.Entry<String, Integer>> entryList = new ArrayList<>(scoreBoard.entrySet());

            // Sort the entries by value in descending order
            entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Create a new LinkedHashMap to maintain the sorted order
            Map<String, Integer> sortedScoreBoard = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : entryList) {
                sortedScoreBoard.put(entry.getKey(), entry.getValue());
            }
            int maxPoints = sortedScoreBoard.values().iterator().next();
            StringBuilder sb = new StringBuilder();


            Map.Entry<String, Integer> entry = sortedScoreBoard.entrySet().iterator().next();
            sb.append(entry.getKey());

            sortedScoreBoard.remove(entry.getKey(), entry.getValue());


            boolean moreThanOneWinner = false;


            while (sortedScoreBoard.entrySet().iterator().hasNext()) {
                entry = sortedScoreBoard.entrySet().iterator().next();
                if (entry.getValue().equals(maxPoints) && entry.getKey() != null) {
                    sb.append(" and ").append(entry.getKey());
                    sortedScoreBoard.remove(entry.getKey(), entry.getValue());
                    moreThanOneWinner = true;
                } else {
                    break;
                }
            }


            synchronized (this) {
                if (moreThanOneWinner) {
                    out.println("The Winners are...");
                } else {
                    out.println("The Winner is...");
                }

                out.println("\uD83D\uDF32 \uD83D\uDF32 \uD83D\uDF32  " + sb + "  \uD83D\uDF32 \uD83D\uDF32 \uD83D\uDF32");

                out.println("Highest score: " + maxPoints + " pts");

                out.println("\nOther Scores:");
            }
            if (sortedScoreBoard.entrySet().iterator().hasNext()) {
                entry = sortedScoreBoard.entrySet().iterator().next();
                if (entry.getKey() != null) {
                    out.print(entry.getKey() + ":");

                    out.print(" " + entry.getValue().toString() + " pts" + "\n");

                }
                sortedScoreBoard.remove(entry.getKey(), entry.getValue());
            }
            if (sortedScoreBoard.entrySet().iterator().hasNext()) {
                entry = sortedScoreBoard.entrySet().iterator().next();
                if (entry.getKey() != null) {
                    out.print(entry.getKey() + ":");

                    out.print(" " + entry.getValue().toString() + " pts" + "\n");
                }
                sortedScoreBoard.remove(entry.getKey(), entry.getValue());
            }
            if (sortedScoreBoard.entrySet().iterator().hasNext()) {
                entry = sortedScoreBoard.entrySet().iterator().next();
                if (entry.getKey() != null) {
                    out.print(entry.getKey() + ":");

                    out.print(" " + entry.getValue().toString() + " pts" + "\n");
                }
                sortedScoreBoard.remove(entry.getKey(), entry.getValue());
            }
            this.alreadyPrintedWinners = true;
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

    private static String countWhiteSpaces(boolean first, boolean middle, ClientManuscript manuscript, int i, int j, int line) {
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

    private static String showManuscript(ClientManuscript manuscript) {

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
        sb.append("\nCounters: " + "\nAnimal     -> " + manuscript.getAnimalCounter() + "\nFungi      -> " + manuscript.getFungiCounter() + "\nInsect     -> " + manuscript.getInsectCounter() + "\nPlant      -> " + manuscript.getPlantCounter() + "\nInkwell    -> " + manuscript.getInkwellCounter() + "\nQuill      -> " + manuscript.getQuillCounter() + "\nManuscript -> " + manuscript.getManuscriptCounter() + "\n");

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

    private static Queue<String> toCliCard(Card card, boolean isFront) {

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

    private static String showStarter(StarterCard card) {
        return ("\nStarter Front:" +
                "\n" + toCliCard(card, true).stream().collect(Collectors.joining("\n")) +
                "\nStarter Back:" +
                "\n" + toCliCard(card, false).stream().collect(Collectors.joining("\n")));
    }

    private static String showObjectives(ArrayList<ObjectiveCard> secretObjectives) {

        String printedObjectives = "\n";
        int i = 1;
        for (ObjectiveCard o : secretObjectives) {
            printedObjectives += "Objective" + (secretObjectives.size() == 1 ? "" : " " + i) + ": \n" + o.toCliCard() + "\n\n";
            i++;
        }

        return printedObjectives;

    }

    private String showBoard(ClientBoard board) {
        String printedBoard = "";
        for (Map.Entry<String, PawnColour> entry : board.getColourPlayerMap().entrySet()) {
            printedBoard += ColourControl.get(entry.getValue()) + entry.getKey() + ColourControl.RESET + ": " + board.getPointsOf(entry.getValue()) + " points\n";
        }
        return printedBoard;
    }

    private static String showHand(ArrayList<ResourceCard> hand) {

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

    private static String showMarket(ClientMarket market) {

        String printedMarket = "\n";

        Queue<String> resourceDeckTop;
        Queue<String> goldDeckTop;
        Queue<String> resourceOne;
        Queue<String> resourceTwo;
        Queue<String> goldOne;
        Queue<String> goldTwo;

        // TODO gestire il comando che mi chiede questa carta che non si può prendere
        try {
            resourceDeckTop = toCliCard(market.getResourceDeck().getLast(), false);
        } catch (NoSuchElementException e) {
            resourceDeckTop = copyNoCardCli();
        }

        try {
            goldDeckTop = toCliCard(market.getGoldDeck().getLast(), false);
        } catch (NoSuchElementException e) {
            goldDeckTop = copyNoCardCli();
        }

        try {
            resourceOne = toCliCard(market.getFaceUp(false)[0], true);
        } catch (NullPointerException e) {
            resourceOne = copyNoCardCli();
        }

        try {
            resourceTwo = toCliCard(market.getFaceUp(false)[1], true);
        } catch (NullPointerException e) {
            resourceTwo = copyNoCardCli();
        }

        try {
            goldOne = toCliCard(market.getFaceUp(true)[0], true);
        } catch (NullPointerException e) {
            goldOne = copyNoCardCli();
        }

        try {
            goldTwo = toCliCard(market.getFaceUp(true)[1], true);
        } catch (NullPointerException e) {
            goldTwo = copyNoCardCli();
        }

        int numLinesToPrint = noCardPrint.size();

        printedMarket += "Resource cards:\n";

        for (int i = 0; i < numLinesToPrint; i++) {
            printedMarket += resourceDeckTop.remove() + "   " + resourceOne.remove() + "  " + resourceTwo.remove() + "\n";
        }

        printedMarket += "\nGold cards:\n";

        for (int i = 0; i < numLinesToPrint; i++) {
            printedMarket += goldDeckTop.remove() + "   " + goldOne.remove() + "  " + goldTwo.remove() + "\n";
        }

        return printedMarket;

    }

    private static Queue<String> copyNoCardCli() {
        Queue<String> copy = new LinkedList<>();
        for (String line : Tui.noCardPrint) {
            copy.add(line);
        }
        return copy;
    }

    private boolean check(String f, String conf1, String conf2){
        if(f.equalsIgnoreCase(conf1)){
            return true;

        }else if (f.equalsIgnoreCase(conf2)){
            return false;
        }else{
            throw new InputMismatchException("invalid input");
        }
    }

    private void showTitle() {
        synchronized (this){
            out.println("\n" +
                    " ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                    "██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                    "██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                    "██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                    "╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                    " ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n"
            );
        }
    }

}