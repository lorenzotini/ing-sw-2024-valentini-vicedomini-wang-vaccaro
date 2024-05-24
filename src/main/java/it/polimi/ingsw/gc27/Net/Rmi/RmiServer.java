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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServer {

    private final List<VirtualView> clients = Collections.synchronizedList(new ArrayList<>());    //clients of different games
    private final Map<VirtualView, Long> clientsPing = new HashMap<>();
    private final GigaController console;
    final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();

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
            e.printStackTrace();
        }

        // Start the thread that checks if the clients are alive
        new Thread(() -> {
            try {
                areClientsAlive();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // Start the thread that executes the commands
        new Thread(() -> {
            try {
                executeCommands();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void receiveCommand(Command command) {
        commands.add(command);
    }

    // TODO promemoria: ha senso che in caso di disconnessione, il client rimanga nella lista fino alla fine della partita, così da
    // TODO potersi riconnettere. Alla fine della partita invece, verranno rimossi tutti i client dalla lista.
    @Override
    public void areClientsAlive() throws RemoteException {

        while (true) {

            synchronized (clients) {
                for(VirtualView client : clients) {
                    String username =  console.getUsername(client);
                    if (clientsPing.get(client) == 0) {
                        clientsPing.put(client, System.currentTimeMillis());
                    } else if (System.currentTimeMillis() - clientsPing.get(client) > 10000) {
                        System.out.println("Timeout for client expired - rmi - " + client);
                        try{
                            console.userToGameController(username).suspendPlayer(console.getPlayer(username));
                        } catch (NullPointerException e){
                            // do nothing
                        }
                        clients.remove(client);
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
        clientsPing.replace(client, System.currentTimeMillis());
    }

    private void executeCommands() throws InterruptedException, IOException {
        while (true) {
            Command comm = commands.take();
            comm.execute(console);
        }
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
    public void disconnect(VirtualView client) throws RemoteException {

    }

    @Override
    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {
        this.console.welcomePlayer(client);
    }

}
