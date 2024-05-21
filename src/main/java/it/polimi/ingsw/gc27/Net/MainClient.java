package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Controller.IpChecker;
import it.polimi.ingsw.gc27.Net.Rmi.RmiClient;
import it.polimi.ingsw.gc27.Net.Socket.SocketClient;
import it.polimi.ingsw.gc27.View.Gui;
import it.polimi.ingsw.gc27.View.Tui;
import it.polimi.ingsw.gc27.View.View;

import java.io.IOException;
import java.rmi.NotBoundException;

public class MainClient  {

    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {

        // Default values
        String ipAddress = "localhost";
        View view = new Tui();
        int connectionChoice = 0;
        VirtualView client;

        // If no arguments, then use default values
        if(args.length == 0){

            view = new Tui();
            System.out.println("No arguments provided. Using default values (localhost, rmi, tui).");

        } else {

            // Help message
            if(args[0].equals("--help") || args[0].equals("-h")) {
                System.out.println("[options]:\n"
                        + "ip_address: the IP address of the server you want to connect to\n"
                        + "--rmi: use RMI connection\n"
                        + "--socket: use Socket connection\n"
                        + "--tui: use RMI connection\n"
                        + "--gui: use Socket connection\n"
                        + "--help, -h: show this help message\n"
                        + "If no options are provided, the default ipAddress is localhost.\n"
                        + "If no options are provided, the default connection type is RMI.\n"
                        + "If no options are provided, the default interface is TUI.\n");
                return;
            }


            // Check if the IP address is valid
            boolean ipPresent = false;
            if (!args[0].startsWith("--") && IpChecker.checkIp(args[0])) {
                ipAddress = args[0];
                 ipPresent = true;
            } else if (!args[0].startsWith("--") && !IpChecker.checkIp(args[0])){
                System.out.println("Invalid IP address. Please use the x.x.x.x format.");
                return;
            }

            // Parse arguments
            for(String arg : args){
                if(ipPresent){
                    ipPresent = false;
                    continue;
                }
                switch (arg){
                    case("--rmi"):
                        connectionChoice = 0;
                        break;

                    case("--socket"):
                        connectionChoice = 1;
                        break;

                    case ("--gui"):
                        view = new Gui();
                        break;

                    case ("--tui"):
                        view = new Tui();
                        break;

                    default:
                        System.out.println("Invalid argument. Use --help for help.");
                        return;
                }
            }

        }

<<<<<<< HEAD

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
        while(true){
            if(ipAddress.isEmpty()){
                ipAddress = "localhost";
                break;
            } else if (!IpChecker.checkIp(ipAddress)) {
                System.out.println("Invalid IP address. Use the x.x.x.x format or press enter for localhost.");
                ipAddress = scan.nextLine();
            }
            else{
                break;
            }
        if(ipAddress.isEmpty()){
            ipAddress = "localhost";
        }

=======
>>>>>>> main
        // Run the client
        if(connectionChoice == 0){
            client = new RmiClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER_RMI, view);
        } else {
            client = new SocketClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER_SOCKET, view);
        }

        view.setClient(client);
        client.runClient();

    }

}