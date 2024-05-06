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

        System.out.println("Welcome to the game CODEX NATURALIS!");

        // Ask for connection type
        System.out.println("\nChoose the connection type: 0 for RMI, 1 for Socket");
        while(true){
            try {
                connectionChoice = scan.nextInt();
                if(connectionChoice == 0 || connectionChoice == 1) {
                    break;
                } else {
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
        // Ask for ip addess
        System.out.println("\nEnter the IP address of the server you want to connect to (press Enter for localhost):");
        String ipAddress = scan.nextLine();
        if(ipAddress.isEmpty()){
            ipAddress = "localhost";
        }

        // Run the client
        if(connectionChoice == 0){
            new RmiClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER).run();
        } else {
            new SocketClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER).run();
        }

    }
}