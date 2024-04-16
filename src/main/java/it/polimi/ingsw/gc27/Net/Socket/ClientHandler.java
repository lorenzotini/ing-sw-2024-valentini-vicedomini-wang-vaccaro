package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class ClientHandler implements VirtualView {

    final SocketServer server;
    final BufferedReader input;
    final Player player;
    final PrintWriter output;

    public ClientHandler(GameController controller, SocketServer server, BufferedReader input, BufferedWriter output) throws RemoteException {
        this.output = new PrintWriter(output)
        this.server = server;
        this.input = input;
        this.player = this.server.welcomePlayer(this);
    }

    public void runVirtualView() throws IOException {
        String command;
        // Read message type
        while ((command = input.readLine()) != null) {
            // Read message and perform action
            Object[] commands = CommandParser.parseCommand(command);
            switch (commands[0].toString().toLowerCase()) {
                case "addcard":
                    if(commands[2].equals("front")){
                        server.addCard(player, player.getHand().get((int)commands[1]), player.getHand().get((int)commands[1]).getFront(), (int)commands[3], (int)commands[4]);
                    }else{
                        server.addCard(player, player.getHand().get((int)commands[1]), player.getHand().get((int)commands[1]).getBack(), (int)commands[3], (int)commands[4]);
                    }
                    break;
                case "drawresourcecard":
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
        }

    }

    @Override
    public void showUpdate(String update) throws RemoteException {

    }

    @Override
    public void show(String s) throws RemoteException {

    }

    @Override
    public String read() throws RemoteException {
        return null;
    }
}



