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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServer {

    private final List<VirtualView> clients = Collections.synchronizedList(new ArrayList<>());    //clients of different games
    private final GigaController console;
    final BlockingQueue<Command> commands = new LinkedBlockingQueue<>();

    public RmiServer(GigaController controller) {
        this.console = controller;
    }

    public void runServer() throws IOException, InterruptedException {

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
            //System.err.println("Server ready");
        }

        //areClientsAlive();
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

    public void receiveCommand(Command command){
        commands.add(command);
    }

    @Override
    public void areClientsAlive() throws RemoteException {
        while(true){
            synchronized (clients){
                Iterator<VirtualView> iterator = clients.iterator();
                while (iterator.hasNext()){
                    VirtualView client = iterator.next();
//                    try {
//                        if(client.getLastPing() == 0){
//                            client.setLastPing(System.currentTimeMillis());
//                        }
//                        else if(System.currentTimeMillis() - client.getLastPing() > 10000){
//                            // Declare disconnected the client
//                            iterator.remove(); // Safely remove the client using the iterator
//                            disconnect(client);
//                            System.out.println("Timeout for client expired");
//                        }
//                    } catch (RemoteException e) { // If the client is abruptly disconnected
//                        iterator.remove(); // Safely remove the client using the iterator
//                        disconnect(client);
//                        System.out.println("Client abruptly disconnected");
//                    }
                }
            }
        }
    }

    @Override
    public void receivePing(VirtualView client) throws RemoteException {
        client.setLastPing(System.currentTimeMillis());
    }

    private void executeCommands() throws InterruptedException, IOException {
        while(true){
            Command comm = commands.take();
            comm.execute(console);
        }
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        synchronized (this.clients){
            this.clients.add(client);
        }
        System.out.println("new client connected");
        try {
            client.show("ciaoo");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect(VirtualView client) throws RemoteException {
        System.out.println("client disconnected GAGAGAGAGA");
    }

    @Override
    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {
        // TODO: gestire le eccezioni
        this.console.welcomePlayer(client);
        // TODO: gestire meglio gli updates
    }

}
