package it.polimi.ingsw.gc27.Net.RMI;

import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.MainClient;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.MyCli;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;
    private String username;
    public RmiClient(String ipAddress, int port) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");
    }
    @Override
    public void showUpdate(String message) throws RemoteException {
        // TODO Attenzione! Questo può causare data race con il thread dell'interfaccia o un altro thread
        System.out.println(message);
    }
    @Override
    public void show(String message) throws RemoteException{
        System.out.println(message);
    }

    @Override
    public void showManuscript(Manuscript manuscript) throws RemoteException {
        MyCli.printManuscript(manuscript);
    }

    @Override
    public String read() throws RemoteException{
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        this.username = username;
    }

    public void run() throws IOException, InterruptedException {
        this.server.connect(this);

        runCli();
    }



    public void runCli() throws IOException, RemoteException, InterruptedException {
        //parlare con lore per farlo spostare
        server.welcomePlayer(this);
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scan.nextLine();
            //Object[] commands = CommandParser.parseCommand(command);
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
                        server.addCard(username, cardIndex, true, x, y);
                    }else if(face.equalsIgnoreCase("back")){
                        server.addCard(username, cardIndex, false, x, y);
                    }else{
                        System.out.println("Invalid face: abort");
                    }
                    break;
                case "askstarter":
                    server.askStarter(username);
                    break;
                default:
                    System.out.println("Invalid command1");
                    break;
            }
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
