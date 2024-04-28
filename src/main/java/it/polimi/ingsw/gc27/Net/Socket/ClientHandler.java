package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Controller.GigaController;
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
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements VirtualView {

    final BufferedReader input;
    private Player player ;
    final GigaController console;
    final BlockingQueue<String> commands = new LinkedBlockingQueue<>();
    final GameController controller = null;
    final SocketClientProxy client;
    final SocketServer server;
    public ClientHandler(GigaController console, SocketServer server, BufferedReader input, BufferedWriter output) throws IOException {
        this.console = console;
        this.input = input;
        this.server = server;
        this.client = new SocketClientProxy(output);
        System.out.println("Sono vivo\n");
        new Thread(()->{
            while(true){
                try {
                    commands.put(input.readLine());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }}).start();

    }

    public void runVirtualView() throws IOException, InterruptedException {


        //this.player = console.welcomePlayer(this);
        String command;

        // Read message type
        while ((command = commands.take()) != null) {
            // Read message and perform action
            Object[] commands = CommandParser.parseCommand(command);
            switch (commands[0].toString().toLowerCase()) {
                case "welcomeplayer":
                    System.out.println("avvio welcome");
                    this.player = console.welcomePlayer(this);
                    client.runCli();
                    break;
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
                    client.show("Invalid command");
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
    public String read() throws IOException, InterruptedException {
        String str ;
        client.read();
        if((str = commands.take()).equals("\n")){
            str = commands.take();
        }
        return str;
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        client.setUsername(username);
    }

}

