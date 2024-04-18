package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {


    final ServerSocket listenSocket;
    final GameController controller;
    final List<ClientHandler> clients = new ArrayList<>();

    public SocketServer(ServerSocket listenSocket, GameController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
    }

    /*public void runServerSocket(int port ) throws IOException {
        int portNumber = Integer.parseInt(port);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine, outputLine;
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }*/
    private void runServer() throws IOException {
        Socket clientSocket = null;
        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            ClientHandler handler = new ClientHandler(this.controller, this, new BufferedReader(socketRx), new BufferedWriter(socketTx));
            synchronized (this.clients) {
                clients.add(handler);
            }
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

}
