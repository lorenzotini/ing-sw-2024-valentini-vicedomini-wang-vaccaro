package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.StringMessage;
import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.PingCommand;
import it.polimi.ingsw.gc27.Net.VirtualServer;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServerProxy implements VirtualServer {

    final BlockingQueue<String> commands = new LinkedBlockingQueue<>();
    final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    final BlockingQueue<String> messageString = new LinkedBlockingQueue<>();
    final VirtualView client;
    Socket serverSocket;
    boolean flag = true;
    ObjectInputStream input;
    ;
    ObjectOutputStream output;

    public SocketServerProxy(VirtualView client, String ipAddress, int port) {
        this.client = client;

        try {
            serverSocket = new Socket(ipAddress, port);
            input = new ObjectInputStream(serverSocket.getInputStream());
            output = new ObjectOutputStream(serverSocket.getOutputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + ipAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + port);
            System.exit(1);
        }
        new Thread(() -> {
            try {
                while (true) {
                    listenFromRemoteServer();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                Message mess;
                while ((mess = messages.take()) instanceof StringMessage) {
                    String message = mess.takeString();
                    if (message.equals("read")) {
                        String toBeSent = client.read();
                        output.writeObject(toBeSent);
                        output.reset();
                        output.flush();
                    } else {
                        client.show(message);
                    }
                }
                client.update(mess);

                runVirtualServer();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }

        }).start();

    }

    private void runVirtualServer() throws IOException, InterruptedException {
        Message message;

        new Thread(() -> {
            while (true){
                try {
                    output.writeObject(new PingCommand());
                    output.reset();
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // Read message type
        while (true) {
            message = messages.take();
            client.update(message);
        }
    }


    @Override
    public void connect(VirtualView client) throws RemoteException {
    }

    @Override
    public void disconnect(VirtualView client) throws RemoteException {

    }

    @Override
    public void welcomePlayer(VirtualView client) throws IOException {
        output.writeObject("welcomeplayer");
        output.reset();
        output.flush();
    }

    @Override
    public void receiveCommand(Command command) {
        try {
            output.writeObject(command);
            output.reset();
            output.flush();
        } catch (IOException e) {
            //there is a Connection problem
        }
    }

    @Override
    public void areClientsAlive() throws RemoteException {

    }

    @Override
    public void receivePing(VirtualView client) throws RemoteException {

    }


    public void sendData(String s) throws IOException {
        output.writeObject(s);
        output.reset();
        output.flush();
    }

    public void listenFromRemoteServer() throws IOException, ClassNotFoundException {
        while (true) {
            //Message mess = (Message)input.readObject();
            messages.add((Message) input.readObject());

        }
    }
}
