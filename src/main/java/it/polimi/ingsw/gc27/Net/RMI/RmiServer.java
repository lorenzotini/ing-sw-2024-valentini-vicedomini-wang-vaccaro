package it.polimi.ingsw.gc27.Net.RMI;

public class RmiServer implements VirtualServer {
    final GameController controller;
    final List<VirtualView> clients = new ArrayList<>();

    public RmiServer(GameController controller) {
        this.controller = controller;
    }
}
