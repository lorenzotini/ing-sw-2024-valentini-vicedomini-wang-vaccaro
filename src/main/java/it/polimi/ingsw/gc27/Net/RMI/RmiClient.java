package it.polimi.ingsw.gc27.Net.RMI;

import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    protected RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the game CODEX NATURALIS!\n" +
                "Enter the IP address of the server you want to connect to (enter \"\" for default):");
        String ipAddress = scan.nextLine();
        if(ipAddress.equals("")){
            ipAddress = "localhost";
        }
        System.out.println("Enter the port number of the server you want to connect to (enter 0 for default):");
        int port = scan.nextInt();
        if(port == 0){
            port = 1234;
        }

        Registry registry = LocateRegistry.getRegistry(ipAddress, port);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

        new RmiClient(server).run();
    }
    @Override
    public void showUpdate(String mex) throws RemoteException {
        // TODO Attenzione! Questo può causare data race con il thread dell'interfaccia o un altro thread
        System.out.println(mex);
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
    private void run() throws RemoteException {
        this.server.connect(this);
        runCli();
    }

    private void runCli() throws RemoteException {
        server.welcomePlayer(this);
        System.out.println("New player added");
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scan.nextLine();
            switch (command) {
                case "addCard 0 up 45 43":
                    //server.addCard();
                    break;
                case "drawCard":
                    //server.drawCard();
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
