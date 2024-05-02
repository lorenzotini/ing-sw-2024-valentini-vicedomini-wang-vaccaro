package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Net.RMI.RmiClient;
import it.polimi.ingsw.gc27.Net.Socket.SocketClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClient  {

    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {

        Scanner scan = new Scanner(System.in);
        int connectionChoice;
        int port;

        System.out.println("Welcome to the game CODEX NATURALIS!");

        // Ask for connection type
        System.out.println("\nChoose the connection type: 0 for RMI, 1 for Socket");
        while(true){
            try {
                connectionChoice = scan.nextInt();
                if(connectionChoice == 0 || connectionChoice == 1) {
                    break;
                } else{
                    System.out.println("Invalid number, insert new value");
                }
            } catch (InputMismatchException  e) {
                System.out.println("Invalid input. Please enter an integer.");
            } finally {
                // Consume the invalid input to clear the scanner's buffer
                scan.nextLine();
            }
        }

        // TODO definire un controllo sul formato dell'ip
        // Ask for port number
        System.out.println("\nEnter the IP address of the server you want to connect to (press Enter for default):");
        String ipAddress = scan.nextLine();
        if(ipAddress.isEmpty()){
            ipAddress = "localhost";
        }

        // Ask for port number
        System.out.println("\nEnter the port number of the server you want to connect to (enter 0 for default):");
        while(true){
            try {
                port = scan.nextInt();
                if((port > 1024 && port < 65536) || port == 0) {
                    port = (port == 0 ? VirtualServer.DEFAULT_PORT_NUMBER : port);
                    break;
                } else{
                    System.out.println("Invalid number, insert new value");
                }
            } catch (InputMismatchException  e) {
                System.out.println("Invalid input. Please enter an integer.");
            } finally {
                // Consume the invalid input to clear the scanner's buffer
                scan.nextLine();
            }
        }

        // Run the client
        if(connectionChoice == 0){
            new RmiClient(ipAddress, port).run();
        } else {
            new SocketClient(ipAddress,port).run();
        }

    }
}