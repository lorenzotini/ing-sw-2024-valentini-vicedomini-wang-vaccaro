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
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServer {

    private final List<VirtualView> clients = new ArrayList<>();    //clients of different games
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

        executeCommands();

    }

    public void receiveCommand(Command command){
        commands.add(command);
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
    }

    @Override
    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {
        // TODO: gestire le eccezioni
        this.console.welcomePlayer(client);
        // TODO: gestire meglio gli updates
    }

}
