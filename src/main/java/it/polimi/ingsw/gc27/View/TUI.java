package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TUI {
    private VirtualView client;
    private VirtualServer server;

    public TUI(VirtualView client, VirtualServer server) throws IOException, InterruptedException {
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
                    //System.out.println(MyCli.showStarter(client.getMiniModel().getStarter()));
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
                    //System.out.println(MyCli.showObjective(client.getMiniModel().getSecretObjectives()));
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
                    //System.out.println(MyCli.showMarket());
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
}
