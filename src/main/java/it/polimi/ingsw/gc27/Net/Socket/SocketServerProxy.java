package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServerProxy implements VirtualServer {
    final PrintWriter output;
    final BufferedReader input;
    final BlockingQueue<String> commands = new LinkedBlockingQueue<>();
    final VirtualView client;

    public SocketServerProxy(VirtualView client, String ipAddress, int port){
        this.client=client;
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
        this.output = new PrintWriter(new BufferedWriter(socketTx));
        new Thread(()-> {
            try {
                while(true) {
                    commands.add(input.readLine());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();
        new Thread(()->{
            try{
                runVirutalServer();
            }catch(InterruptedException | IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    private void runVirutalServer() throws IOException, InterruptedException {

        String line;
        // Read message type
        while ((line = commands.take()) != null) {
            Object[] commands = CommandParser.parseCommandFromServer(line);
            switch(commands[0].toString()) {
                case "show":
                    client.show(commands[1].toString());
                    break;
                case "setUsername":
                    client.setUsername(commands[1].toString());
                    break;
                case "runCli":
                    new Thread(() -> {
                        try {
                            //client.runCli();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    break;

                case "read":
                    String sr = client.read();
                    sendData(sr);
                    break;

                default:
                    System.out.println("damn god");
                    break;
            }
        }
    }
    @Override
    public void connect(VirtualView client) throws RemoteException {
        output.println("connect");
        output.flush();
    }

    @Override
    public void addCard(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) throws RemoteException {
        output.println("addCard");
        output.println(playerName);
        output.println(handCardIndex);
        if (isFrontFace) {
            output.println(1);
        } else {
            output.println(0);
        }
        output.println(x);
        output.println(y);
        output.flush();
    }

    @Override
    public void drawResourceCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException{
        output.println("drawResourceCard");
        output.println(playerName);
        output.println(fromDeck);
        output.println(faceUpCardIndex);
        output.flush();
    }


    @Override
    public void drawGoldCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException{
        output.println("drawResourceCard");
        output.println(playerName);
        output.println(fromDeck);
        output.println(faceUpCardIndex);
        output.flush();
    }
    @Override
    public void welcomePlayer(VirtualView client) throws RemoteException {
        output.println("welcomeplayer");
        output.flush();
    }

    @Override
    public void addStarter(String playerName, boolean isFront) throws IOException, InterruptedException {
        output.println("addStarter" + isFront);
        output.flush();
    }

    public void sendData(String s){
        output.println(s);
        output.flush();
    }
}
