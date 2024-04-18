package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Net.RMI.RmiClient;
import it.polimi.ingsw.gc27.Net.RMI.RmiServer;
import it.polimi.ingsw.gc27.Net.Socket.SocketClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Scanner scan = new Scanner(System.in);
        int connectionChoose;
        System.out.println("Welcome to the game CODEX NATURALIS!\n" +

                "choose the connection type: 0 for RMI, 1 for Socket\n");
        do{
            if(scan.hasNextInt() ){
                connectionChoose = scan.nextInt();
                if(connectionChoose == 0 || connectionChoose ==1)
                    break;
                else{
                    System.out.println("Invalid number, insert new value");
                }
            }
            else {
                System.out.println("Invalid input, insert new value");
            }
        }while(true);


        System.out.println("Enter the IP address of the server you want to connect to (enter \"\" for default):");
        String ipAddress = scan.nextLine();
        if(ipAddress.isEmpty()){
            ipAddress = "localhost";
        }
        System.out.println("Enter the port number of the server you want to connect to (enter 0 for default):");
        int port = scan.nextInt();
        if(port == 0) {
            if (connectionChoose == 0){
                port = 1234;
                new RmiClient(ipAddress, port).run();
            }
            else {
                port = 80;
                new SocketClient(ipAddress,port).run();
            }
        }
    }
}