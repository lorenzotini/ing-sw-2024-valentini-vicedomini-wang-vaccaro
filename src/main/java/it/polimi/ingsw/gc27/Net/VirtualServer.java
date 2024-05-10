package it.polimi.ingsw.gc27.Net;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote{

    int DEFAULT_PORT_NUMBER_RMI = 1234;

    int DEFAULT_PORT_NUMBER_SOCKET = 3000;

    void connect(VirtualView client) throws RemoteException;

    void addCard(String playerName, int handCardIndex, boolean isFrontFace, int x, int y) throws RemoteException;

    void drawResourceCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws RemoteException;

    void drawGoldCard(String playerName, boolean fromDeck, int faceUpCardIndex) throws IOException;

    void welcomePlayer(VirtualView client) throws IOException, InterruptedException;

    void addStarter(String playerName, boolean isFront) throws IOException, InterruptedException;

    void chooseObjective(String playerName, int objectiveIndex) throws IOException, InterruptedException;

}