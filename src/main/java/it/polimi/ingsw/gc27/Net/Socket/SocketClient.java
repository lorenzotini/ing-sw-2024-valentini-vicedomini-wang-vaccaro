package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Net.MainClient;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.TUI;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketClient extends MainClient implements VirtualView, Runnable {

    final SocketServerProxy server;
    private String username;




    public SocketClient(String ipAddress, int port) {
        this.server = new SocketServerProxy(this, ipAddress, port);

    }


    public void run() {
        try {
            server.welcomePlayer(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public void runCli() throws IOException, InterruptedException {
        new TUI(this, server);

        /*Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scan.nextLine();
            //Object[] commands = CommandParser.parseCommand(command);
            switch (command/*[0].toString().toLowerCase()) {
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
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
    }*/

}

    @Override
    public String getUsername() {
        return this.username;
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
