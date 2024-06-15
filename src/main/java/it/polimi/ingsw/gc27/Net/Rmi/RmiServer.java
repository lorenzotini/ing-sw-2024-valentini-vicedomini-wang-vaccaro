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

public class RmiServer implements VirtualServer {

    private final List<VirtualView> clients = Collections.synchronizedList(new ArrayList<>());    //clients of different games
    private final Map<VirtualView, Long> clientsPing = new HashMap<>();
    private final GigaController console;
    private static final int MAX_PING_TIME = 500000;

    public RmiServer(GigaController controller) {
        this.console = controller;
    }

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
    @Override
    public void receiveCommand(Command command) {
        console.addCommandToGameController(command);
    }

    public void areClientsAlive()  {
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
                    try{
                        client.pingFromServer();
                    }catch (RemoteException e){
                        iterator.remove();
                        console.removeReferences(client);
                    }
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

    @Override
    public void receivePing(VirtualView client) throws RemoteException {
        clientsPing.replace((VirtualView) client, System.currentTimeMillis());
    }
    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients) {
            this.clients.add(client);
            this.clientsPing.put(client, System.currentTimeMillis());
        }
        System.out.println("Client connected - rmi - " + client.toString());
    }

    @Override
    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {
        this.console.welcomePlayer(client);
    }

}
