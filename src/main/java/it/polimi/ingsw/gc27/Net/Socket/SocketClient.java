package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketClient implements VirtualView, Runnable {
    final BufferedReader input;
    final SocketServerProxy server;
    private String username;
    final BlockingQueue<String> commands = new LinkedBlockingQueue<>();

    /*public static void main(String[] args) throws UnknownHostException, IOException  {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the game CODEX NATURALIS!\n" +
                "Enter the IP address of the server you want to connect to (enter \"\" for default):");
        String serverName = scan.nextLine();
        if(serverName.equals("")){
            serverName = "localhost";
        }
        System.out.println("Enter the port number of the server you want to connect to (enter 0 for default):");
        int serverPort = scan.nextInt();
        if(serverPort == 0){
            serverPort = 1234;
        }

        InputStreamReader socketRx = null;
        OutputStreamWriter socketTx = null;
        try (Socket serverSocket = new Socket(serverName, serverPort)) {
            socketRx = new InputStreamReader(serverSocket.getInputStream());
            socketTx = new OutputStreamWriter(serverSocket.getOutputStream());


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverName);
            System.exit(1);
        }
        new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx)).run();
    }*/
    public SocketClient(BufferedReader input, BufferedWriter output){
        this.input = input;
        this.server = new SocketServerProxy(output);
    }
    public SocketClient(String ipAddress, int port){
        InputStreamReader socketRx = null;
        OutputStreamWriter socketTx = null;
        Socket serverSocket;
        try {
             serverSocket = new Socket(ipAddress, port);
            socketRx = new InputStreamReader(serverSocket.getInputStream());
            socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + ipAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + port);
            System.exit(1);
        }
        this.input = new BufferedReader(socketRx);
        this.server = new SocketServerProxy(new BufferedWriter(socketTx));
        new Thread(()-> {
            try {
                while(true) {
                    commands.add(input.readLine());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

    public void run() {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        try {
            server.welcomePlayer(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    private void runVirtualServer() throws IOException, InterruptedException {
        String line;

        // Read message type
        while ((line = commands.take()) != null) {
            Object[] commands = CommandParser.parseCommandFromServer(line);
            switch(commands[0].toString()) {
                case "show":
                    show(commands[1].toString());
                    break;
                case "setUsername":
                    setUsername(commands[1].toString());
                    break;
                case "runCli":
                    new Thread(() -> {
                        try {
                            runCli();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;

                case "read":
                    String sr = this.read();
                    server.sendData(sr);
                    break;

                default:
                    System.out.println("damn god");
                    break;
            }
        }
    }
    private void runCli() throws RemoteException {


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





    public void showUpdate(String mex) throws RemoteException {
        // TODO Attenzione! Questo può causare data race con il thread dell'interfaccia o un altro thread
        System.out.println(mex);
    }

    @Override
    public void show(String s) throws RemoteException {
        System.out.println(s);
    }

    @Override
    public void showManuscript(Manuscript manuscript) throws RemoteException {

    }

    @Override
    public String read() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        String string;
        while((string = scan.nextLine())=="\n"){};
        return string;
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        this.username=username;
    }


}
