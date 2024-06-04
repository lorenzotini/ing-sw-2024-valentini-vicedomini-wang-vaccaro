package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.GUI.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Gui implements View {
    private boolean isReconnected=false;

    private static Gui gui = null;

    private Stage stage;

    private VirtualView client;
    final BlockingQueue<String> messages = new LinkedBlockingQueue<>();
    final BlockingQueue<String> messagesReceived = new LinkedBlockingQueue<>();
    private final HashMap<String, Scene> pathSceneMap = new HashMap<>(); //maps path to scene
    private final HashMap<String, GenericController> pathContrMap = new HashMap<>(); //maps path to controller of the scene

    public GenericController getCurrentController() {
        return currentController;
    }

    public void setCurrentController(GenericController currentController) {
        this.currentController = currentController;
    }

    private GenericController currentController;

    //setters and getters
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public VirtualView getClient() {
        return client;
    }
    @Override
    public void setClient(VirtualView client) {
        this.client = client;
    }
    public BlockingQueue<String> getMessagesReceived() {return messagesReceived;}
    public void setReconnected(boolean reconnected) {
        isReconnected = reconnected;
    }
    //end setters and getters

    //singleton pattern
    private Gui() {
    }
    public static Gui getInstance() {
        synchronized (Gui.class) {
            if (gui == null)
                gui = new Gui();
            return gui;
        }
    }

    //loads scenes and controllers in hashmaps
    public void initializing() throws IOException {
        for (String path : ScenePaths.valuesList()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            pathSceneMap.put(path, new Scene(root));
            GenericController contr = loader.getController();
            pathContrMap.put(path, contr);
        }
        currentController=getControllerFromName(ScenePaths.STARTER.getValue());
    }

    //start of the game after initialization
    @Override
    public void run() throws IOException {

        Platform.runLater(() -> {
            try {
                if (!isReconnected) {
                    //set the starter cards
                    StarterCard starter = this.client.getMiniModel().getPlayer().getStarterCard();
                    PlaceStarterCardScene contr = (PlaceStarterCardScene) getControllerFromName(ScenePaths.PLACESTARTER.getValue());
                    contr.changeImageFront(getClass().getResource(starter.getFront().getImagePath()).toExternalForm());
                    contr.changeImageBack(getClass().getResource(starter.getBack().getImagePath()).toExternalForm());
                    switchScene("/fxml/PlaceStarterCardScene.fxml");

                    ObjectiveCard objectiveCard1 = this.client.getMiniModel().getPlayer().getSecretObjectives().get(0);
                    ObjectiveCard objectiveCard2 = this.client.getMiniModel().getPlayer().getSecretObjectives().get(1);
                    ChooseObjectiveSceneController contr2 = (ChooseObjectiveSceneController) getControllerFromName(ScenePaths.CHOOSEOBJ.getValue());
                    contr2.changeImageObj1(getClass().getResource(objectiveCard1.getFront().getImagePath()).toExternalForm());
                    contr2.changeImageObj2(getClass().getResource(objectiveCard2.getFront().getImagePath()).toExternalForm());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void switchScene(String scenePath) throws IOException {
        Platform.runLater(()->{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
            Parent root= null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(pathSceneMap.get(scenePath));
            stage.show();
            currentController = getControllerFromName(scenePath);

        });
    }

    public GenericController getControllerFromName(String path) {
        return pathContrMap.get(path);
    }




    public void welcomePlayer(VirtualView client) throws InterruptedException {
        // the message received when the game id is not found
        String gameNotFound = "\nGame not found. Please enter a valid game id or 'new' to start a new game";

        // the message received when the number of players is not valid
        String invalidNumOfPlayers = "\nInvalid number of players, insert a value between 2-4";

        // the message received when creating a new game, next step is to choose the number of players
        String newGameChosen = "\nHow many player? there will be? (2-4)";

        // the message received when joining an already existing game
        String joiningGame = "\nJoining game";

        // the message when a game is created, the id is specified
        String gameCreated = "\nGame created with id";

        // the message received when the game is full
        String gameIsFull = "\nGame is full. Restarting...";

        //nella scena 2 on clickButton mi dice se manderò "new" oppure "id"
        ChooseGameSceneController controller = new ChooseGameSceneController();
        //controller.init();
        //controller.setGui(this);

         // 2 possible input "new" or id
        String m = messagesReceived.take();

        if (m.equals(newGameChosen)) { // if the input is "new", therefore created a new game

            m = messagesReceived.take();

            while (m.equals(invalidNumOfPlayers)) { // invalid num of players, ask again

                m = messagesReceived.take();
            }

            if (m.contains(gameCreated)) {
                // change the scene to LobbyScene, where the player waits for the other players to join

            }

        } else { // if the input is a game id, therefore joining an already existing game
            while (m.equals(gameNotFound)) { // continue the loop until a valid game id

                m = messagesReceived.take();
            }
            if (m.contains(joiningGame)) {
                // change the scene to LobbyScene, where the player waits for the other players to join
            } else if (m.equals(gameIsFull)) {
                welcomePlayer(this.client); // if the game is full restart the process
            }
        }

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
        String mess = "";
        try {
            mess = messages.take();
        } catch (InterruptedException e) {

        }
        return mess;
    }


    public void stringFromSceneController(String string) {
        messages.add(string);
    }

    @Override
    public void okAck(String string) {
        System.out.println("\nOk " +currentController.toString() + string);
        currentController.receiveOk(string);
    }

    @Override
    public void koAck(String string) {
        System.out.println("\nKo " +currentController.toString() + string);
        currentController.receiveKo(string);
        //error handler
    }



}
