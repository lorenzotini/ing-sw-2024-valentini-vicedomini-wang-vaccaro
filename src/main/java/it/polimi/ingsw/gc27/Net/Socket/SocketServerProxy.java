package it.polimi.ingsw.gc27.Net.Socket;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.PingMessage;
import it.polimi.ingsw.gc27.Messages.StringMessage;
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
    private long lastPingFromServer = 0;
    ObjectOutputStream output;
    boolean flag = true;

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
                listenFromRemoteServer();
            } catch (ClassNotFoundException e) {
                System.out.println("The call of listenFromRemoteServer in S.S. had a problem, check");
            }
        }).start();

        new Thread(() -> {
            try {
                Message mess;
                while ((mess = messages.take()) instanceof StringMessage) {
                    String message = mess.getString();
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
                System.out.println("Error with the connection, probably the server is down");
            }

        }).start();

    }

    private void checkServerIsAlive()  {
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
                if (count == 5) {
                    System.out.println("il server è caduto rilanciare il client");

                    try{
                        client.close();
                    }catch (RemoteException e){
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
            while (true){
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
            try{
                client.update(message);
            }catch(IOException e){

            }
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

    public void listenFromRemoteServer() throws ClassNotFoundException {
        while (true) {
//            Message mess = (Message)input.readObject();
//            messages.add(mess);
            Message mess= null ;
            try{
                 mess =(Message) input.readObject();
            }catch(IOException e){
                continue;
            }
            if(mess instanceof PingMessage){
                flag = true;
            }
            else{
                messages.add(mess);
            }
        }

    }
}
