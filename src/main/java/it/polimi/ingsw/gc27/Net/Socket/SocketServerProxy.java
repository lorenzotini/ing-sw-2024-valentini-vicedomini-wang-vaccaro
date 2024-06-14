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

public class SocketServerProxy implements VirtualServer {

    //in case same RemoteException would being called they'd be ignored because the ping system is going to do what
    // the catch of them would do
    final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    final VirtualView client;
    Socket serverSocket;
    ObjectInputStream input;
    ObjectOutputStream output;
    boolean flag = true;
    private int TIME_COUNT = 100;

    public SocketServerProxy(VirtualView client, String ipAddress, int port) {
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
            try{
                Thread.sleep(2000);
            }catch(InterruptedException es){
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

    private void checkServerIsAlive() {
        int count = 0;
        while (count < 5) {
            if (flag) {
                count = 0;
                flag = false;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("ping non ricevuto " + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                if (count == TIME_COUNT) {
                    System.out.println("il server è caduto rilanciare il client");

                    try {
                        client.close();
                    } catch (RemoteException e) {
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void runVirtualServer() throws InterruptedException {
        Message message;
        new Thread(this::checkServerIsAlive).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    output.writeObject(new PingCommand());
                    output.reset();
                } catch (IOException | InterruptedException e) {
                    System.out.println("The server has been disconnected");
                    break;
                }
            }
        }).start();

        // Read message type
        while (true) {
            message = messages.take();
            try {
                client.update(message);
            } catch (IOException e) {

            }
        }
    }

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
     * <p>This method should be run in a separate thread, as it enters an infinite loop,
     * listening for messages until the thread is interrupted or an error occurs or the clients is
     * closed, due to the end of the game.</p>
     *
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    public void listenFromRemoteServer() throws ClassNotFoundException {
        while (true) {
            Message mess = null;
            try {
                mess = (Message) input.readObject();
            } catch (IOException e) {
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
