package it.polimi.ingsw.gc27.Net.Rmi;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * The RmiServer class implements the VirtualServer interface and represents the server in the RMI network.
 * It manages the connections of the clients, receives commands from them, and checks if the clients are alive.
 * It also contains a GigaController instance for handling game logic.
 */
public class RmiServer implements VirtualServer {

    /**
     * List of connected clients.
     */
    private final List<VirtualView> clients = Collections.synchronizedList(new ArrayList<>());    //clients of different games

    /**
     * Map of clients and their last ping time.
     */
    private final Map<VirtualView, Long> clientsPing = new HashMap<>();

    /**
     * The GigaController instance for handling game logic.
     */
    private final GigaController console;

    /**
     * Maximum time in milliseconds a client can be inactive before being considered disconnected.
     */
    private static final int MAX_PING_TIME = 5000;


    /**
     * Constructs a new RmiServer with the given GigaController.
     *
     * @param controller The GigaController for handling game logic.
     */
    public RmiServer(GigaController controller) {
        this.console = controller;
    }

    /**
     * Starts the RMI server and begins listening for client connections.
     *
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    public void runServer() throws IOException, InterruptedException {

        String name = "VirtualServer";
        VirtualServer stub;
        Registry registry;

        try {
            stub = (VirtualServer) UnicastRemoteObject.exportObject(this, 0);
            registry = LocateRegistry.createRegistry(DEFAULT_PORT_NUMBER_RMI);
            registry.rebind(name, stub);
        } catch (RemoteException e) {
            System.err.println("There is a problem locating the server, please restart");
            System.exit(0);
        }

        // Start the thread that checks if the clients are alive
        new Thread(this::areClientsAlive).start();
        //new Thread(sayYouAreAlive()).start();

        // Start the thread that executes the commands
    }

    /**
     * Receives a command from a client and adds it to the GigaController.
     *
     * @param command The command to receive.
     */
    @Override
    public void receiveCommand(Command command) {
        console.addCommandToGameController(command);
    }

    /**
     * Checks if the clients are alive by pinging them and removes any clients that have not responded within the maximum ping time.
     */
    public void areClientsAlive() {
        while (true) {
            synchronized (clients) {
                Iterator<VirtualView> iterator = clients.iterator();
                while (iterator.hasNext()) {
                    VirtualView client = iterator.next();
                    if (clientsPing.get(client) == 0) {
                        clientsPing.put(client, System.currentTimeMillis());
                    } else if (System.currentTimeMillis() - clientsPing.get(client) > MAX_PING_TIME) {
                        System.out.println("Timeout for client expired - rmi - " + client);
                        // Remove all the references to the client
                        iterator.remove();
                        console.removeReferences(client);
                    }
                    new Thread(()->{
                        try {
                            client.pingFromServer();
                        } catch (RemoteException e) {
//                        iterator.remove();
//                        console.removeReferences(client);
                        }
                    }).start();
                }
            }

            try {
                Thread.sleep(1000); // Sleep for 1 second before next check
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Receives a ping from a client and updates the client's last ping time.
     *
     * @param client The client sending the ping.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void receivePing(VirtualView client) throws RemoteException {
        clientsPing.replace((VirtualView) client, System.currentTimeMillis());
    }

    /**
     * Connects a client to the server and adds it to the list of clients.
     *
     * @param client The client to connect.
     * @throws RemoteException If a remote access error occurs.
     */
    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients) {
            this.clients.add(client);
            this.clientsPing.put(client, System.currentTimeMillis());
        }
        System.out.println("Client connected - rmi - " + client.toString());
    }

    /**
     * Welcomes a player to the server by adding them to the GigaController.
     *
     * @param client The client representing the player
     */
    @Override
    public void welcomePlayer(VirtualView client)  {
        this.console.welcomePlayer(client);
    }

}
