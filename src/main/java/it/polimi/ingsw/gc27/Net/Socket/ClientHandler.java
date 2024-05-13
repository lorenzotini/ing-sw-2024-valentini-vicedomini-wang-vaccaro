package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.CommandParser;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements VirtualView {

    final BufferedReader input;
    private Player player ;
    final GigaController console;
    final BlockingQueue<String> commands = new LinkedBlockingQueue<>();
    private GameController controller ;
    final SocketClientProxy client;
    final SocketServer server;
    public ClientHandler(GigaController console, SocketServer server, BufferedReader input, BufferedWriter output) throws IOException {
        this.console = console;
        this.input = input;
        this.server = server;
        this.client = new SocketClientProxy(output);
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
                    this.player = console.welcomePlayer(this);
                    controller = console.userToGameController(player.getUsername());
                    client.runCli();
                    break;

                case "addcard":
                    ResourceCard card = player.getHand().get((int)commands[1]);
                    Face face = commands[2].equals("Front") ? card.getFront() : card.getBack();
                    int x = (int)commands[3];
                    int y = Integer.parseInt(commands[4].toString());
                    // TODO: gestire le eccezioni
                    this.controller.addCard(player, card, face, x, y);
                    break;

                case "drawresourcecard":
                    controller.drawResourceCard(player, commands[1].equals("deck"), (int)commands[2]);
                    break;

                case "drawgoldcard":
                    controller.drawGoldCard(this.player, (boolean)commands[1], (int)commands[2] );
                    break;

                case "addstarter":
                    StarterCard starter = this.player.getStarterCard();
                    Face starterFace = (boolean)commands[1] ? starter.getFront() : starter.getBack();
                    controller.addStarterCard(this.player, this.player.getStarterCard(), starterFace );

                default:
                    client.show("Invalid command");
                    break;
            }
        }

    }

    @Override
    public void show(String s) throws RemoteException {
        client.show(s);
    }


    // TODO CIAO ANDRE, TOGLI QUESTO METODO CHE NON SI PUO' FARE LA SHOW DAL SERVER COME CI HANNO DETTO AL LAB
    @Override
    public void showManuscript(Manuscript manuscript) throws RemoteException {
        client.showManuscript(manuscript);
    }

    @Override
    public void showStarter(StarterCard starterCard) throws RemoteException {

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

    @Override
    public void run() throws IOException, InterruptedException {

    }


    @Override
    public String getUsername() {
        return this.player.getUsername();
    }

    @Override
    public void update(Message message) {

    }
}

