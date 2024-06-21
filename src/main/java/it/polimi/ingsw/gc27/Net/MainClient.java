package it.polimi.ingsw.gc27.Net;

import it.polimi.ingsw.gc27.Controller.IpChecker;
import it.polimi.ingsw.gc27.Net.Socket.SocketClient;
import it.polimi.ingsw.gc27.MainApp;
import it.polimi.ingsw.gc27.Net.Rmi.RmiClient;
import it.polimi.ingsw.gc27.View.Gui;
import it.polimi.ingsw.gc27.View.Tui;
import it.polimi.ingsw.gc27.View.View;
import javafx.application.Application;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * The MainClient class represents the main client application.
 * It initializes and runs the RMI or Socket client based on the provided arguments, and connects to the server.
 * It also contains a VirtualView instance for handling client view.
 */
public class MainClient {

    /**
     * The main method that starts the client application.
     *
     * @param args The command-line arguments.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the RMI object is not found.
     * @throws InterruptedException If the operation is interrupted.
     */
    public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {

        // TODO rimuovere le eccezioni dalla firma del main
        // Default values
        String ipAddress = "localhost";
        View view = new Tui();

        int connectionChoice = 0;
        VirtualView client;

        // If no arguments, then use default values
        if (args.length == 0) {

            view = new Tui();
            System.out.println("No arguments provided. Using default values (localhost, rmi, tui).");

        } else {

            // Help message
            if (args[0].equals("--help") || args[0].equals("-h")) {
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
            } else if (!args[0].startsWith("--") && !IpChecker.checkIp(args[0])) {
                System.out.println("Invalid IP address. Please use the x.x.x.x format.");
                return;
            }

            // Parse arguments
            for (String arg : args) {
                if (ipPresent) {
                    ipPresent = false;
                    continue;
                }
                switch (arg) {
                    case ("--rmi"):
                        connectionChoice = 0;
                        break;

                    case ("--socket"):
                        connectionChoice = 1;
                        break;

                    case ("--gui"):
                        view = Gui.getInstance();
                        new Thread(() -> {
                            Application.launch(MainApp.class);
                        }).start();
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

        // Run the client
        if (connectionChoice == 0) {
            try {
                client = new RmiClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER_RMI, view);
            } catch (IOException e) {
                return;
            }
        } else {
            client = new SocketClient(ipAddress, VirtualServer.DEFAULT_PORT_NUMBER_SOCKET, view);
        }
        view.setClient(client);
        client.runClient();

    }

}