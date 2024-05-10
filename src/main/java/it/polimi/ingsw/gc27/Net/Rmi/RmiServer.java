package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RmiServer implements VirtualServer {

    private List<VirtualView> clients = new ArrayList<>();    //clients of different games
    private GigaController console;
    //final BlockingQueue<String> updates = new LinkedBlockingQueue<>();

    public RmiServer(GigaController controller) {
        this.console = controller;
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients){
            this.clients.add(client);
        }
        System.err.println("new client connected");
    }

    public void addStarter(String playerName, boolean isFront) throws IOException, InterruptedException {
        Player player = this.console.getPlayer(playerName);
        StarterCard starter = player.getStarterCard();
        Face face = isFront ? starter.getFront() : starter.getBack();
        // TODO: gestire le eccezioni
        this.console.userToGameController(playerName).addStarterCard(player, starter,face);
        // TODO: gestire meglio gli updates
    }

    @Override
    public void chooseObjective(String playerName, int objectiveIndex) throws IOException, InterruptedException {
        Player player = this.console.getPlayer(playerName);
        this.console.userToGameController(playerName).chooseObjectiveCard(player, objectiveIndex);
    }

    @Override
    public void addCard(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) throws RemoteException {
        Player player = this.console.getPlayer(playerName);
        ResourceCard card = player.getHand().get(handCardIndex);
        Face face = isFrontFace ? card.getFront() : card.getBack();
        // TODO: gestire le eccezioni
        this.console.userToGameController(playerName).addCard(player, card, face, x, y);
        // TODO: gestire meglio gli updates
    }

    @Override
    public void drawResourceCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        Player player = this.console.getPlayer(playerName);
        // TODO: gestire le eccezioni
        this.console.userToGameController(playerName).drawResourceCard(player, fromDeck, faceUpCardIndex);
        // TODO: gestire meglio gli updates
    }

    @Override
    public void drawGoldCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        Player player = this.console.getPlayer(playerName);
        // TODO: gestire le eccezioni
        this.console.userToGameController(playerName).drawGoldCard(player, fromDeck, faceUpCardIndex);
        // TODO: gestire meglio gli updates
    }

    @Override
    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {
        // TODO: gestire le eccezioni
        this.console.welcomePlayer(client);
        // TODO: gestire meglio gli updates
    }

    /*private void broadcastUpdateThread() throws InterruptedException, RemoteException {
        while(true){
            String update = updates.take();
            synchronized (this.clients){
                for(var c: clients){
                    c.showUpdate(update);
                }
            }
        }
    }*/

    public void runServer() throws RemoteException, InterruptedException {

        String name = "VirtualServer";

        VirtualServer stub = null;
        
        try {
            stub = (VirtualServer) UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // Bind the remote object's stub in the registry
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(DEFAULT_PORT_NUMBER_RMI);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("Server ready");
        }

    }
}
