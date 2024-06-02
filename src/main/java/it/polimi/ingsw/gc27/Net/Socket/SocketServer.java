package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Net.VirtualServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {


    final ServerSocket listenSocket;
    final List<ClientHandler> clients = new ArrayList<>();
    final GigaController console;

    public SocketServer(GigaController console) throws IOException {
        this.listenSocket = new ServerSocket(VirtualServer.DEFAULT_PORT_NUMBER_SOCKET);
        this.console = console;
    }

    public void runServer() throws IOException, InterruptedException {

        Socket clientSocket;

        while ((clientSocket = this.listenSocket.accept()) != null) {

            ClientHandler handler = new ClientHandler(console, this, clientSocket);
            synchronized (this.clients) {
                clients.add(handler);
                System.out.println("Client connected - socket - " + clientSocket.getInetAddress().getHostAddress());
            }
            //this is gonna be deleted, if you find this and is after the 5 june, delete this
//            new Thread(() -> {
//                try {
//                    handler.runVirtualView();
//                } catch (IOException | InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }).start();

        }
    }
    public void disconnect(ClientHandler handler){
        synchronized (this.clients) {
            clients.remove(handler);
        }
    }

}
