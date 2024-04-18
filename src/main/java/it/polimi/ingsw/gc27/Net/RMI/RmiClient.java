package it.polimi.ingsw.gc27.Net.RMI;

import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

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

    protected RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }
    public RmiClient(String ipAddress, int port) throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        this.server = (VirtualServer) registry.lookup("VirtualServer");
        this.run();
    }
    public static void main(String[] args) throws IOException, NotBoundException{
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the game CODEX NATURALIS!\n" +
                "Enter the IP address of the server you want to connect to (enter \"\" for default):");
        String ipAddress = scan.nextLine();
        if(ipAddress.isEmpty()){
            ipAddress = "localhost";
        }
        System.out.println("Enter the port number of the server you want to connect to (enter 0 for default):");
        int port = scan.nextInt();
        if(port == 0){
            port = RmiServer.DEFAULT_PORT_NUMBER;
        }

        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

        new RmiClient(server).run();
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
    public String read() throws RemoteException{
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        this.username = username;
    }

    public void run() throws IOException {
        this.server.connect(this);
        runCli();
    }

    private void runCli() throws IOException {
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
                /*case "drawresourcecard":
                    if(commands[1].equals("deck")){
                        server.drawResourceCard(player, true, (int)commands[2]);
                    }else{
                        server.drawResourceCard(player, false, (int)commands[2]);
                    }
                    break;
                case "drawgoldcard":
                    if(commands[1].equals("deck")){
                        server.drawGoldCard(player, true, (int)commands[2]);
                    }else{
                        server.drawGoldCard(player, false, (int)commands[2]);
                    }
                    break;*/
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
