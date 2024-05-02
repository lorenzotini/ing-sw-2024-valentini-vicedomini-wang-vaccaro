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
                case "addstarter":
                    //System.out.println(MyCli.showStarter(client.getMiniModel().getStarter()));
                    System.out.print("What side do you want to play? (front or back)");
                    String side = scan.next();
                    if(side.equalsIgnoreCase("front")) {
                        server.addStarter(client.getUsername(), true);
                    }else if(side.equalsIgnoreCase("back")){
                        server.addStarter(client.getUsername(), false);
                    }else{
                        System.out.println("Invalid face: abort");
                    }
                    break;
                default:
                    System.out.println("Invalid command1");
                    break;
            }
        }
    }
}
