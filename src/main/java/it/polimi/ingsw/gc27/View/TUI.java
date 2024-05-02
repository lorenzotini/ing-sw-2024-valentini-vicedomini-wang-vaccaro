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

                case "addstarter":
                    //System.out.println(MyCli.showStarter(client.getMiniModel().getStarter()));
                    System.out.println("What side do you want to play? (front or back)");
                    String side = scan.next();
                    if(side.equalsIgnoreCase("front")) {
                        server.addStarter(client.getUsername(), true);
                    }else if(side.equalsIgnoreCase("back")){
                        server.addStarter(client.getUsername(), false);
                    }else{
                        System.out.println("Invalid face: abort");
                    }
                    break;

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
                    String line = scan.next();
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
                    System.out.println("Invalid command1");
                    break;

            }
        }
    }
}
