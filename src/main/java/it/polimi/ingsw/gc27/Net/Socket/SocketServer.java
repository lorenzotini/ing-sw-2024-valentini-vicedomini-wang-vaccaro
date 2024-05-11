package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Net.VirtualServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {


    final ServerSocket listenSocket;
    final List<ClientHandler> clients = new ArrayList<>();
    final GigaController console ;

    public SocketServer( GigaController console) throws IOException {
        this.listenSocket = new ServerSocket(VirtualServer.DEFAULT_PORT_NUMBER_SOCKET);
        this.console = console;
    }

    public void runServerS() throws  IOException {
        Socket clientSocket = null;

        while ((clientSocket = this.listenSocket.accept()) != null){

            //TODO non passare ObjectInputStream ma potrei passare magari direttamente il clietSocket
            
            ObjectInputStream socketRx = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream socketTx = new ObjectOutputStream(clientSocket.getOutputStream());
            ClientHandler handler = new ClientHandler(console, this, socketRx , socketTx );

            synchronized (this.clients) {
                clients.add(handler);
            }

            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            //once done this cycle client and server are connected
        }
    }
    public void runServer() throws IOException, InterruptedException {
        Socket clientSocket = null;

        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8);
            //damn
            ClientHandler handler = new ClientHandler(console, this, new BufferedReader(socketRx), new BufferedWriter(socketTx));
            synchronized (this.clients) {
                clients.add(handler);
            }


            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        }
    }

}
