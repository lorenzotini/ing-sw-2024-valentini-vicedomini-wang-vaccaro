package it.polimi.ingsw.gc27.Net.Socket;

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

/**
 * Proxy server that manages the connection between the client and the remote server using sockets.
 * It implements the VirtualServer interface and provides methods to communicate with the server.
 * It checks if the connection works.
 * */
public class SocketServerProxy implements VirtualServer {

    //in case same RemoteException would being called they'd be ignored because the ping system is going to do what
    // the catch of them would do
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private final SocketClient client;
    private Socket serverSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean flag = true;
    private int TIME_COUNT = 5;

    /**
     * Constructs a SocketServerProxy and establishes a connection to the server.
     * Here starts the threads that listen from server and send the update to the client.
     *
     * @param client    the VirtualView client to communicate with
     * @param ipAddress the IP address of the server
     * @param port      the port number of the server
     */
    public SocketServerProxy(SocketClient client, String ipAddress, int port) {
        this.client = client;
        do {
            try {
                serverSocket = new Socket(ipAddress, port);
                input = new ObjectInputStream(serverSocket.getInputStream());
                output = new ObjectOutputStream(serverSocket.getOutputStream());
                break;
            } catch (UnknownHostException e) {
                System.out.println("Don't know about host " + ipAddress);
            } catch (IOException e) {
                System.out.println("Couldn't get I/O for the connection to " + port);
            }
            System.out.println("Retrying...");
            //todo: gui
            try{
                Thread.sleep(2000);
            }catch(InterruptedException es){
                System.out.println("Interrupted Thread");
            }
        }while(true);
        new Thread(() -> {
            try {
                listenFromRemoteServer();
            } catch (ClassNotFoundException e) {
                System.out.println("The call of listenFromRemoteServer in S.S. had a problem, check");
            }
        }).start();

        new Thread(() -> {
            try {
                Message mess;
                while (true) {
                    mess = messages.take();
                    if (mess instanceof StringMessage) {
                        String message = mess.getString();
                        if (message.equals("read")) {
                            String toBeSent = client.read();
                            output.writeObject(toBeSent);
                            output.reset();
                            output.flush();
                        } else {
                            client.show(message);
                        }
                    } else if (mess instanceof OkMessage || mess instanceof KoMessage) {
                        client.update(mess);
                    } else {
                        client.update(mess);
                        break;
                    }
                }
                runVirtualServer();
            } catch (InterruptedException | IOException e) {
                System.out.println("Error with the connection, probably the server is down");
            }

        }).start();

    }

    /**
     * Checks if the server is alive by monitoring ping responses.
     * If the server is not responsive for a specified number of attempts, the client is closed.
     */
    private void checkServerIsAlive() {
        int count = 0;
        while (count < 5) {
            if (flag) {
                count = 0;
                flag = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread exception");
                }
            } else {
                System.out.println("Ping not received: " + count);
                //TODO togliere questa print poco prima dell'esame, tipo 15 min prima

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread exception: 2");
                }
                count++;
                if (count >= TIME_COUNT) {
                    System.out.println("Connection to the server was dropped");

                    client.close();

                }
            }
        }
    }

    /**
     * Runs the virtual server by continuously listening for messages and sending ping commands.
     *
     * @throws InterruptedException if the thread is interrupted while waiting for messages
     */
    private void runVirtualServer() throws InterruptedException {
        Message message;
        new Thread(this::checkServerIsAlive).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    output.writeObject(new PingCommand());
                    output.reset();
                } catch (IOException e ) {
                    System.out.println("Probably the server is down: Ping");
                }catch(InterruptedException e){
                    System.out.println("Thread exception");
                }
            }
        }).start();

        // Read message type
        while (true) {
            message = messages.take();

            client.update(message);

        }
    }

    /**
     * This method is required by the {@link VirtualServer} interface,
     * but it is not used in this implementation.
     */
    @Override
    public void connect(VirtualView client) throws RemoteException {

    }

    /**
     *It's called once for client, to enter a game.
     *The connection has already established.
     * @param client the VirtualView client that send the welcome message.
     *
     * @throws IOException if there is an issue with the output stream, such as a disconnection
     *                     or stream corruption, which prevents the message from being sent.
     */
    @Override
    public void welcomePlayer(VirtualView client) throws IOException {

        output.writeObject("welcomeplayer");
        output.reset();
        output.flush();
    }

    /**
     * every time is called sand an object, which is the action the client want to do
     * through the output channel
     * @param command is not modified, only sent
     */
    @Override
    public void receiveCommand(Command command) {
        try {
            output.writeObject(command);
            output.reset();
            output.flush();
        } catch (IOException e) {
            System.out.println("Probably the server is down: receiveCommand");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ies){
                System.out.println("Thread exception");
            }
            //there is a Connection problem, eventually take by the ping System
        }
    }

    /**
     * Used in the RmiConnection, has to be in the VirtualServer interface
     * @param client
     * @throws RemoteException
     */
    @Override
    public void receivePing(VirtualView client) throws RemoteException {

    }

    /**
     * Continuously listens for messages from a remote server. This method reads objects
     * from the input stream, the object are sent from the server (ClientHandler).
     * The messages are processed in a loop:
     * - If a PingMessage is received, a flag is set to true indicating the server is alive.
     * - All other messages are added to a messages queue for further processing.
     *
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    public void listenFromRemoteServer() throws ClassNotFoundException {
        int count=0;
        while (true) {
            Message mess = null;

            try {
                mess = (Message) input.readObject();
            } catch (IOException e) {
                System.out.println("Probably the server is down receive Message, close the game");
                count++;
                if(count>TIME_COUNT){
                    client.close();
                }
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException ies){
                    System.out.println("Thread exception");
                }
                continue;
            }
            if (mess instanceof PingMessage) {
                flag = true;
            } else {
                messages.add(mess);
            }
        }

    }
}
