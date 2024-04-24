package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;
/*
public class SocketClient implements Runnable, VirtualView {
    final BufferedReader input;
    final SocketServerProxy server;

    public static void main(String[] args) throws UnknownHostException, IOException  {
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
    }
    public SocketClient(BufferedReader input, BufferedWriter output){
        this.input = input;
        this.server = new SocketServerProxy(output);
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
            runCli();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    private void runVirtualServer() throws IOException {
        String line;
        // Read message type
        while ((line = input.readLine()) != null) {
            // Read message and perform action


        }
    }
    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");

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
    public String read() throws RemoteException {
        return null;
    }

}*/
