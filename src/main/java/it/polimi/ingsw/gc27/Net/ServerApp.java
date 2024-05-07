package it.polimi.ingsw.gc27.Net;


import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Net.Rmi.RmiServer;
import it.polimi.ingsw.gc27.Net.Socket.SocketServer;

import java.io.IOException;

public class ServerApp {

    // TODO maybe make serverApp singleton
    private final GigaController console = new GigaController();
    private SocketServer socketServer;
    private RmiServer rmiServer;

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerApp serverApp = new ServerApp();

        serverApp.rmiServer = new RmiServer(serverApp.console);
        serverApp.socketServer = new SocketServer(serverApp.console);

        new Thread(() -> {
            try {
                serverApp.rmiServer.runServer();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        serverApp.socketServer.runServer();

    }
}
