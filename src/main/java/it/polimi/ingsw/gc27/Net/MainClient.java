package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Controller.IpChecker;
import it.polimi.ingsw.gc27.Net.Rmi.RmiClient;
import it.polimi.ingsw.gc27.Net.Socket.SocketClient;
import it.polimi.ingsw.gc27.View.Tui;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class MainClient  {

    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {

        Scanner scan = new Scanner(System.in);

        // Default values
        int connectionChoice = 0;

        // TODO gestire caso gui
        // If no arguments, then use default values
        if(args.length == 0){

            System.out.println("No arguments provided. Using default values (rmi, cli).");

        } else {

            // Help message
            if(args[0].equals("--help") || args[0].equals("-h")) {
                System.out.println("[options]:\n"
                        + "--rmi: use RMI connection\n"
                        + "--socket: use Socket connection\n"
                        + "--help, -h: show this help message\n"
                        + "If no options are provided, the default connection type is RMI.\n"
                        + "If no options are provided, the default interface is CLI.\n");
                return;
            }

            //
            for(String arg : args){
                if(arg.equals("--rmi")){
                    connectionChoice = 0;
                    System.out.println("Using RMI connection.");
                } else if (arg.equals("--socket")) {
                    connectionChoice = 1;
                    System.out.println("Using Socket connection.");
                }
            }

        }


        Tui.showTitle();

        // Ask for connection type
//        System.out.println("\nChoose the connection type: 0 for RMI, 1 for Socket");
//        while(true){
//            try {
//                connectionChoice = scan.nextInt();
//                if(connectionChoice == 0 || connectionChoice == 1) {
//                    break;
//                } else {
//                    System.out.println("Invalid number, insert new value");
//                }
//            } catch (InputMismatchException  e) {
//                System.out.println("Invalid input. Please enter an integer.");
//            } finally {
//                // Consume the invalid input to clear the scanner's buffer
//                scan.nextLine();
//            }
//        }


        // Ask for ip addess
        System.out.println("\nEnter the IP address of the server you want to connect to (press Enter for localhost):");
        String ipAddress = scan.nextLine();
        if (ipAddress.equals("\n")){
            ipAddress = scan.nextLine();
        }
        while(true){
            if(ipAddress.isEmpty()){
                ipAddress = "localhost";
                break;
            } else if (!IpChecker.checkIp(ipAddress)) {
                System.out.println("Invalid IP address. Use the x.x.x.x format or press enter for localhost.");
                ipAddress = scan.nextLine();
            } else {
                break;
            }
        }

        // Run the client
        if(connectionChoice == 0){
            new RmiClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER_RMI).runClient();
        } else {
            new SocketClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER_SOCKET).runClient();
        }

    }
}