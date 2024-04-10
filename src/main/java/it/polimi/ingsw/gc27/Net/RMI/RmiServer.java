package it.polimi.ingsw.gc27.Net.RMI;

import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.GoldCard;
import it.polimi.ingsw.gc27.Card.ResourceCard;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Game.Market;
import it.polimi.ingsw.gc27.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServer {
    final GameController controller;
    final List<VirtualView> clients = new ArrayList<>();
    final BlockingQueue<String> updates = new LinkedBlockingQueue<>();
    public RmiServer(GameController controller) {
        this.controller = controller;
    }
    @Override
    public void connect(VirtualView client) throws RemoteException {
        System.err.println("new client connected");
        synchronized (this.clients){
            this.clients.add(client);
        }
    }
    @Override
    public void addCard(Player player, ResourceCard card, Face face, int x, int y) {
        // TODO: gestire le eccezioni
        this.controller.addCard(player, card, face, x, y);
        // TODO: gestire meglio gli updates
        try{
            updates.put("Added card from player: " + player.getUsername());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void drawCard(Market market, Player player, ArrayList<ResourceCard> deck, ResourceCard card, int faceUpCardIndex){
        // TODO: gestire le eccezioni
        this.controller.drawCard(market, player, deck, card, faceUpCardIndex);
        // TODO: gestire meglio gli updates
        try{
            updates.put("Added card from player: " + player.getUsername());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void drawCard(Market market, Player player, ArrayList<GoldCard> deck, GoldCard card, int faceUpCardIndex){
        // TODO: gestire le eccezioni
        this.controller.drawCard(market, player, deck, card, faceUpCardIndex);
        // TODO: gestire meglio gli updates
        try{
            updates.put("Added card from player: " + player.getUsername());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    private void broadcastUpdateThread() throws InterruptedException {
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
