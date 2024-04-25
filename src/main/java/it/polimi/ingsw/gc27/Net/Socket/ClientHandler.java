package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.RemoteException;

public class ClientHandler implements VirtualView {

    final BufferedReader input;
    final Player player = new Player();
    final GameController controller;
    final SocketClientProxy client;
    final SocketServer server;
    public ClientHandler(GameController controller, SocketServer server, BufferedReader input, BufferedWriter output) throws IOException {
        this.controller = controller;
        this.input = input;
        this.server = server;
        this.client = new SocketClientProxy(output);
        //this.player = this.controller.initializePlayer(this);
    }

    public void runVirtualView() throws IOException {
        String command;
        // Read message type
        while ((command = input.readLine()) != null) {
            // Read message and perform action
            Object[] commands = CommandParser.parseCommand(command);
            switch (commands[0].toString().toLowerCase()) {
                case "addcard":
                    new Thread(() -> {
                        ResourceCard card = player.getHand().get((int)commands[1]);
                        Face face = commands[2].equals("Front") ? card.getFront() : card.getBack();
                        int x = (int)commands[3];
                        int y = Integer.parseInt(commands[4].toString());
                        // TODO: gestire le eccezioni
                        this.controller.addCard(player, card, face, x, y);
                    }).start();
                    break;
                case "drawresourcecard":
                    new Thread(() -> {
                        controller.drawResourceCard(player, commands[1].equals("deck"), (int)commands[2]);
                    }).start();

                    break;
                case "drawgoldcard":
                    new Thread(()->{
                        controller.drawGoldCard(player, commands[1].equals("deck"), (int)commands[2]);
                    }).start();

                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }

    }


    @Override
    public void showUpdate(String update) throws RemoteException {

    }

    @Override
    public void show(String s) throws RemoteException {
        client.show(s);
    }

    @Override
    public String read() throws IOException {
        String str ;
        str = input.readLine();
        return str;
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        client.setUsername(username);
    }

}

