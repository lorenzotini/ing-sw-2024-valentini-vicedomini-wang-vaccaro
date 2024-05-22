package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Net.VirtualView;
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




    public Gui(){

        new Thread(()->{
            try {

                //welcomePlayer(client);
                //mainApp.init();

                //messages.add("new");
                //this.read();


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/StarterScene.fxml")); //root node
        Scene scene;
        scene = new Scene(fxmlLoader.load(),1200,800);
        //stage.setFullScreen(true);
        stage.setTitle("Codex Naturalis");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(){
        launch();
    }



    @Override
    public void run() throws IOException {
        //da qui inizia la partita vera e propria
    }



    @Override
    public void welcomePlayer(VirtualView client)  {
        //nella scena 2 on clickButton mi dice se manderò "new" oppure "id"
//        messages.add("new");
//        this.read();





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


}
