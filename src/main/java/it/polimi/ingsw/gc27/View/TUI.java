package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TUI {
    private VirtualView myself;
    private VirtualServer server;

    public TUI(VirtualView client, VirtualServer server) throws IOException, InterruptedException {
        myself = client;
        this.server = server;
        runCli();
    }
    public void runCli() throws IOException, RemoteException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scan.nextLine();
            switch (command/*[0].toString()*/.toLowerCase()) {
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
                        server.addCard(myself.getUsername(), cardIndex, true, x, y);
                    }else if(face.equalsIgnoreCase("back")){
                        server.addCard(myself.getUsername(), cardIndex, false, x, y);
                    }else{
                        System.out.println("Invalid face: abort");
                    }
                    break;
                case "askstarter":
                    server.askStarter(myself.getUsername());

                    break;
                default:
                    System.out.println("Invalid command1");
                    break;
            }
        }
    }
}
