package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;

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
        this.listenSocket = new ServerSocket(3000);
        this.console = console;
    }



    public void runServer() throws IOException, InterruptedException {
        System.out.println("Server Socket avviato e in ascolto\n");
        Socket clientSocket = null;

        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8);

            final ClientHandler handler = new ClientHandler(console, this, new BufferedReader(socketRx), new BufferedWriter(socketTx));
            synchronized (this.clients) {
                clients.add(handler);
            }


            /*new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            */
            handler.runVirtualView();
        }
    }

}
