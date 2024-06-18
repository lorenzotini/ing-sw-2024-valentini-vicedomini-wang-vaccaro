package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.ClientClass.*;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.GUI.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    //public HashMap<String, Tab> chatTabHashMap= new HashMap<>();
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

                    // sets the starter cards and the chat in StarterCardScene
                    StarterCard starter = this.client.getMiniModel().getPlayer().getStarterCard();
                    PlaceStarterCardScene contr = (PlaceStarterCardScene) getControllerFromName(ScenePaths.PLACESTARTER.getValue());
                    contr.changeImageFront(getClass().getResource(starter.getFront().getImagePath()).toExternalForm());
                    contr.changeImageBack(getClass().getResource(starter.getBack().getImagePath()).toExternalForm());
                    contr.chatInitStarter();
                    switchScene("/fxml/PlaceStarterCardScene.fxml");

                    // sets the objective card and the chat in ObjectiveCardScene
                    ObjectiveCard objectiveCard1 = this.client.getMiniModel().getPlayer().getSecretObjectives().get(0);
                    ObjectiveCard objectiveCard2 = this.client.getMiniModel().getPlayer().getSecretObjectives().get(1);
                    ChooseObjectiveSceneController contr2 = (ChooseObjectiveSceneController) getControllerFromName(ScenePaths.CHOOSEOBJ.getValue());
                    contr2.changeImageObj1(getClass().getResource(objectiveCard1.getFront().getImagePath()).toExternalForm());
                    contr2.changeImageObj2(getClass().getResource(objectiveCard2.getFront().getImagePath()).toExternalForm());
                    contr2.chatInitObjective();

                    // sets the chat in manuscriptScene
                    ManuscriptSceneController manuscriptSceneController = (ManuscriptSceneController) Gui.getInstance().getControllerFromName(ScenePaths.MANUSCRIPT.getValue());
                    manuscriptSceneController.chatInitManuscript();
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
        System.out.println(phrase);
        messagesReceived.add(phrase);
    }

    @Override
    public void show(ArrayList<ResourceCard> hand) {
        ManuscriptSceneController controller = (ManuscriptSceneController) Gui.getInstance().getCurrentController();
        MiniModel miniModel;
        try {
            miniModel = client.getMiniModel();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        controller.overwriteHand(miniModel);
    }

    @Override
    public void show(ObjectiveCard objectiveCard) {

    }

    @Override
    public void show(ClientManuscript manuscript) {

        Platform.runLater(() -> {
            if(Gui.getInstance().getCurrentController() instanceof ManuscriptSceneController controller) {
//                controller.manuscriptCard.setImage(controller.handCard.getImage());
//                controller.handCard.setImage(null);
//                controller.manuscriptCard.toFront();
//                controller.manuscriptCard.setOnDragDropped(null);  // disable further drops
//                controller.manuscriptCard.setOnDragOver(null);
//                controller.setNewAvailablePositions();
//
                try {

                    MiniModel miniModel = client.getMiniModel();

                    // update manuscripts
                    for(Map.Entry<String, ClientManuscript> element :  miniModel.getManuscriptsMap().entrySet()){
                        controller.overwriteManuscript(miniModel, element.getKey(), false);
                    }

                    //update counters
                    controller.overwriteCounters(miniModel);

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

//    @Override
//    public void updateManuscriptOfOtherPlayer(ClientManuscript manuscript) {
//        Platform.runLater(() -> {
//            if(Gui.getInstance().getCurrentController() instanceof ManuscriptSceneController controller) {
//                MiniModel miniModel = null;
//                try {
//                    miniModel = client.getMiniModel();
//                } catch (RemoteException e) {
//                    throw new RuntimeException(e);
//                }
//                for(Map.Entry<String, ClientManuscript> element :  miniModel.getManuscriptsMap().entrySet()){
//                    controller.overwriteManuscript(miniModel, element.getKey());
//                }
//            }
//       });
//    }

    @Override
    public void show(ClientBoard board) {

    }

    @Override
    public void show(ClientChat chat) {
        MiniModel miniModel;
        try {
            miniModel = client.getMiniModel();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        // when a message is added to the chat, it is updated in all the scenes the chat is displayed
        GenericController controller = currentController;
        controller.overwriteChat(chat, miniModel);
    }

    @Override
    public void show(ClientMarket market) {
        ManuscriptSceneController controller = (ManuscriptSceneController) Gui.getInstance().getCurrentController();
        MiniModel miniModel;
        try {
            miniModel = client.getMiniModel();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        controller.overwriteMarket(miniModel);
    }

    @Override
    public void updateManuscriptOfOtherPlayer(ClientManuscript manuscript) {
        show(manuscript);
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
        if(currentController instanceof ManuscriptSceneController){
            ((ManuscriptSceneController) currentController).getActionFeedback().setText(string);
        }
    }

    @Override
    public void koAck(String string) {
        System.out.println("\nKo " +currentController.toString() + string);
        currentController.receiveKo(string);
        if(currentController instanceof ManuscriptSceneController){
            ((ManuscriptSceneController) currentController).getActionFeedback().setText(string);
        }
        //error handler
    }

    @Override
    public void showWinners() {

        Platform.runLater(()->{
            try {
                if(currentController instanceof ManuscriptSceneController){
                    switchScene(ScenePaths.ENDING.getValue());
                }

                if (currentController instanceof EndingSceneController){
                    ((EndingSceneController) Gui.getInstance().getCurrentController()).changeWinnersLabel(client.getMiniModel().getBoard().getScoreBoard());
                }

            } catch (Exception e){

            }
        });

    }

    public void addLastChatMessage(ChatMessage message, Tab chatTab) {
        Platform.runLater(()-> {
            VBox vbox = getChatMessagesVBox(chatTab);

            Text text = new Text();
            text.setText(message.getContent());
            text.getStyleClass().add("text");
            TextFlow textFlow = new TextFlow(text);
            textFlow.setMaxWidth(240);
            textFlow.setTextAlignment(TextAlignment.LEFT);
            HBox hbox = new HBox(textFlow);

            String username;
            try {
                username = Gui.getInstance().getClient().getUsername();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Text textName;
            HBox hBoxName = null;
            if (message.getReceiver().equalsIgnoreCase("global")) {
                textName = new Text(message.getSender());
                hBoxName = new HBox(textName);
                hBoxName.setPadding(new Insets(0, 3, 0, 3));
                textName.getStyleClass().add("text-name");
            }

            if (message.getSender().equals(username)) {
                hbox.setAlignment(Pos.CENTER_RIGHT);
                textFlow.getStyleClass().add("text-flow-receiver");
                if (hBoxName != null)
                    hBoxName.setAlignment(Pos.CENTER_RIGHT);
            } else {
                hbox.setAlignment(Pos.CENTER_LEFT);
                textFlow.getStyleClass().add("text-flow-sender");
                if (hBoxName != null)
                    hBoxName.setAlignment(Pos.CENTER_LEFT);
            }
            hbox.setPadding(new Insets(5, 5, 5, 5));
            if (hBoxName != null) {
                vbox.getChildren().add(hBoxName);
            }
            vbox.getChildren().add(hbox);
        });
    }
    private VBox getChatMessagesVBox(Tab chatTab) {
        VBox chatContainer = (VBox) chatTab.getContent();
        ScrollPane chatContent = (ScrollPane) chatContainer.getChildren().getFirst();
        chatContent.setVvalue(1.0); //non so se si mette qui
        return (VBox) chatContent.getContent();
    }

}
