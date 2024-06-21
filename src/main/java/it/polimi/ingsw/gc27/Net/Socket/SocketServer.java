package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Net.VirtualServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The SocketServer class manage the connection with the client when they want to create a connection
 * Then create a personal ClientHandler for each client.
 */
public class SocketServer {


    private final ServerSocket listenSocket;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final GigaController console;

    /**
     * Constructs a new SocketServer that listens on the default port and uses the specified GigaController.
     * @param console the GigaController to be used for handling client connections and interaction
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public SocketServer(GigaController console) throws IOException {
        this.listenSocket = new ServerSocket(VirtualServer.DEFAULT_PORT_NUMBER_SOCKET);
        this.console = console;
    }

    /**
     * Starts the server to listen for client connections. When a client connects, a new ClientHandler is created
     * to manage the communication with that client.
     *
     * @throws IOException if an I/O error occurs when waiting for a connection
     * */
    public void runServer() throws IOException {

        Socket clientSocket;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            ClientHandler handler = new ClientHandler(console, this, clientSocket);
            synchronized (this.clients) {
                clients.add(handler);
                System.out.println("Client connected - socket - " + clientSocket.getInetAddress().getHostAddress());
            }
        }
    }

    /**
     * Disconnects the specified client handler, removing it from the list of active clients.
     *
     * @param handler the ClientHandler to be disconnected
     */
    public void disconnect(ClientHandler handler){
        synchronized (this.clients) {
            clients.remove(handler);
        }
    }

}
