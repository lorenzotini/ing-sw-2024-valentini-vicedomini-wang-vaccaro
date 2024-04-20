package it.polimi.ingsw.gc27.Net.RMI;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.Initializer;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServer {

    final static int DEFAULT_PORT_NUMBER = 1234;
    private ArrayList<GameController> controllersList;
    private List<RmiClient> clients = new ArrayList<>();    //clients of different games
    final BlockingQueue<String> updates = new LinkedBlockingQueue<>();

    public RmiServer(GameController controller) {
        this.controller = controller;
    }

    public static void main( String[] args ) throws RemoteException, InterruptedException {
        // Initialize gc
        Initializer init = new Initializer();
        GameController gc = new GameController(init.initialize());
        String name = "VirtualServer";
        System.out.println("Hello from Server!");

        VirtualServer stub = null;
        VirtualServer obj = new RmiServer(gc);

        try {
            stub = (VirtualServer) UnicastRemoteObject.exportObject(obj, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // Bind the remote object's stub2 in the registry
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(DEFAULT_PORT_NUMBER);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("Server ready");
        }
        ((RmiServer)obj).broadcastUpdateThread();
    }
    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients){
            this.clients.add(client);
        }
        System.err.println("new client connected");
    }
    @Override
    public void addCard(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) throws RemoteException {
        Player player = this.controller.getGame().getPlayer(playerName);
        ResourceCard card = player.getHand().get(handCardIndex);
        Face face = isFrontFace ? card.getFront() : card.getBack();

        // TODO: gestire le eccezioni
        this.controller.addCard(player, card, face, x, y);
        // TODO: gestire meglio gli updates
        try{
            updates.put("UPDATE - Added card from player: " + player.getUsername());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void drawResourceCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        Player player = this.controller.getGame().getPlayer(playerName);
        // TODO: gestire le eccezioni
        this.controller.drawResourceCard(player, fromDeck, faceUpCardIndex);
        // TODO: gestire meglio gli updates
        try{
            updates.put("UPDATE - Drawn card from player: " + player.getUsername());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void drawGoldCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException {
        Player player = this.controller.getGame().getPlayer(playerName);
        // TODO: gestire le eccezioni
        this.controller.drawGoldCard(player, fromDeck, faceUpCardIndex);
        // TODO: gestire meglio gli updates
        try{
            updates.put("UPDATE - Drawn card from player: " + player.getUsername());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void welcomePlayer(VirtualView client) throws IOException{
        // TODO: gestire le eccezioni
        Player p = this.controller.welcomePlayer(client);
        // TODO: gestire meglio gli updates
        try{
            updates.put("UPDATE - Created player: " + p.getUsername());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }
    private void broadcastUpdateThread() throws InterruptedException, RemoteException {
        while(true){
            String update = updates.take();
            synchronized (this.clients){
                for(var c: clients){
                    c.showUpdate(update);
                }
            }
        }
    }
}
