package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.MainApp;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Net.VirtualView;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Gui implements View{
    final BlockingQueue<String> messages= new LinkedBlockingQueue<>();
    final BlockingQueue<String> messagesReceived= new LinkedBlockingQueue<>();
    private MainApp mainApp;
    //creo un thread che comunica con il server inviandogli i messaggi


    public Gui(){
        MainApp mainApp = new MainApp();
        new Thread(()->{
            try {
                MainApp.launch(MainApp.class);
                //mainApp.init();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    @Override
    public void run() throws IOException {
        //da qui inizia la partita vera e propria
    }

    @Override
    public void welcomePlayer(VirtualView client)  {
        new Thread(() -> {
            while (true) {

            }
        }).start();
    }

    @Override
    public void setClient(VirtualView client) {

    }

    @Override
    public void showString(String phrase) {
        messagesReceived.add(phrase);
    }

    @Override
    public void show(ArrayList<ResourceCard> hand) {

    }

    @Override
    public void show(ObjectiveCard objectiveCard) {

    }

    @Override
    public void show(Manuscript manuscript) {

    }

    @Override
    public void show(Board board) {

    }

    @Override
    public void show(Market market) {

    }

    @Override
    public String read() {
        String mess="";
        try{
             mess=messages.take();}
        catch (InterruptedException e){}

        return mess;
    }


}
