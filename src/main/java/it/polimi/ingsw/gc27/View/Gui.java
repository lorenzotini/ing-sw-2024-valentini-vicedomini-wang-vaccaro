package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.GUI.ChooseGameSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static javafx.application.Application.launch;




public class Gui extends Application implements View {

    private VirtualView client;
    final BlockingQueue<String> messages= new LinkedBlockingQueue<>();
    final BlockingQueue<String> messagesReceived= new LinkedBlockingQueue<>();

    //creo un thread che comunica con il server inviandogli i messaggi



    public Gui(){}
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/StarterScene.fxml")); //root node
        Scene scene;
        scene = new Scene(fxmlLoader.load(),1600,900);
        //stage.setFullScreen(true);
        stage.setTitle("Codex Naturalis");
        stage.setScene(scene);
        stage.show();
    }





    @Override
    public void run() {

    }



    @Override
    public void welcomePlayer(VirtualView client) throws InterruptedException {
        // the message received when the game id is not found
        String gameNotFound = "\nGame not found. Please enter a valid game id or 'new' to start a new game";
        // the message received when the number of players is not valid
        String invalidNumOfPlayers = "\nInvalid number of players, insert a value between 2-4";
        // the message received when creating a new game, next step is to choose the number of players
        String newGameChosen = "\nHow many player? there will be? (2-4)";
        //


        //nella scena 2 on clickButton mi dice se manderò "new" oppure "id"
        ChooseGameSceneController controller= new ChooseGameSceneController();
        controller.setGui(this);

        this.read(); // 2 possible input "new" or id
        String m = messagesReceived.take();

        while(m.equals(gameNotFound)) { // continue the loop until a valid game id
            this.read();
            m = messagesReceived.take();
        }





//        if(this.buttonReceiver();){
//            messages.add("new");
//            this.read();
//        }else if (button2){
//
//            messages.add(id);
//        }


//        new Thread(() -> {
//            while (true) {
//
//            }
//        }).start();
    }



    @Override
    public void setClient(VirtualView client) {
        this.client=client;

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

    public void stringFromSceneController(String string){
        messages.add(string);
    }

    public void changeScene() {

    }



}
