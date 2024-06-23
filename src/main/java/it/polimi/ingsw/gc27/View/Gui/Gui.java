package it.polimi.ingsw.gc27.View.Gui;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.ClientClass.*;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.Gui.SceneController.*;
import it.polimi.ingsw.gc27.View.View;
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

    private boolean serverIsUp = false;

    private boolean isReconnected = false;

    private static Gui gui = null;

    private boolean isGameOn = true;

    private Stage stage;

    private GenericController currentController;


    private VirtualView client;
    final BlockingQueue<String> messages = new LinkedBlockingQueue<>();
    private final HashMap<String, Scene> pathSceneMap = new HashMap<>(); //maps path to scene
    private final HashMap<String, GenericController> pathContrMap = new HashMap<>(); //maps path to controller of the scene


    //setters and getters
    public boolean isGameOn() {
        return isGameOn;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public GenericController getCurrentController() {
        return currentController;
    }

    public VirtualView getClient() {
        return client;
    }

    @Override
    public void setClient(VirtualView client) {
        this.client = client;
    }

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
        currentController = getControllerFromName(ScenePaths.STARTER.getValue());
    }

    //start of the game after initialization
    @Override
    public void run() {
        Platform.runLater(() -> {
            try {
                if (!isReconnected) {

                    // sets the starter cards and the chat in StarterCardScene
                    StarterCard starter = this.client.getMiniModel().getPlayer().getStarterCard();
                    PlaceStarterCardSceneController contr = (PlaceStarterCardSceneController) getControllerFromName(ScenePaths.PLACESTARTER.getValue());
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

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void switchScene(String scenePath) throws IOException {
        Platform.runLater(() -> {
            stage.setScene(pathSceneMap.get(scenePath));
            stage.show();
            currentController = getControllerFromName(scenePath);
        });
    }

    public GenericController getControllerFromName(String path) {
        return pathContrMap.get(path);
    }

    @Override
    public void showString(String phrase) {
        System.out.println(phrase);
    }

    public void suspendedGame(String string){
        currentController.suspendeGame();
        isGameOn = false;
    }

    @Override
    public void resumeTheMatch() {
        isGameOn = true;
        currentController.reconnectPlayer();
    }

    @Override
    public void show(ArrayList<ResourceCard> hand) {
        if (currentController instanceof ManuscriptSceneController) {
            ManuscriptSceneController controller = (ManuscriptSceneController) Gui.getInstance().getCurrentController();
            MiniModel miniModel;
            try {
                miniModel = client.getMiniModel();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            controller.overwriteHand(miniModel);

        }
    }

    @Override
    public void show(ObjectiveCard objectiveCard) {

    }

    @Override
    public void show(ClientManuscript manuscript) {

        Platform.runLater(() -> {
            if(Gui.getInstance().getCurrentController() instanceof ManuscriptSceneController controller) {
                try {

                    MiniModel miniModel = client.getMiniModel();

                    // update manuscripts
                    for (Map.Entry<String, ClientManuscript> element : miniModel.getManuscriptsMap().entrySet()) {
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

    @Override
    public void show(ClientBoard board) {
        if (currentController instanceof ManuscriptSceneController)
            ((ManuscriptSceneController) currentController).updateBoard(board);
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
        if (currentController instanceof ManuscriptSceneController) {
            ManuscriptSceneController controller = (ManuscriptSceneController) Gui.getInstance().getCurrentController();

            MiniModel miniModel;
            try {
                miniModel = client.getMiniModel();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            controller.overwriteMarket(miniModel);
        }
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
            e.printStackTrace();
        }
        return mess;
    }


    public void stringFromSceneController(String string) {
        messages.add(string);
    }

    @Override
    public void okAck(String string) {
        // if a OkMessage arrives when the currentController is null, it means that the server is up but gui is not ready yet
        if (currentController == null) {
            serverIsUp = true;
            return;
        }
        System.out.println("\nOk " + currentController.toString() + string);
        currentController.receiveOk(string);
    }

    @Override
    public void koAck(String string) {
        if (currentController != null) {
            System.out.println("\nKo " + currentController.toString() + string);
            currentController.receiveKo(string);
        }
    }

    @Override
    public void showWinners() {

        Platform.runLater(() -> {
            try {
                ((EndingSceneController)Gui.getInstance().getControllerFromName(ScenePaths.ENDING.getValue()))
                        .changeWinnersLabel(client.getMiniModel().getBoard().getScoreBoard());
                if(currentController instanceof ManuscriptSceneController){
                    switchScene(ScenePaths.ENDING.getValue());

                }

//                if (currentController instanceof EndingSceneController){
//                    ((EndingSceneController) Gui.getInstance().getCurrentController()).changeWinnersLabel(client.getMiniModel().getBoard().getScoreBoard());
//                }

            } catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    public void addLastChatMessage(ChatMessage message, Tab chatTab) {
        Platform.runLater(() -> {
            MiniModel miniModel;
            try {
                miniModel = Gui.getInstance().getClient().getMiniModel();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

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

//                PawnColour colour = miniModel.getBoard().getColourPlayermap().get(username);
//                switch (colour) {
//                    case BLUE:
//                        textName.getStyleClass().add("text-name");
//                        textName.setFill(Color.WHITE);
//                    case YELLOW:
//                        textName.getStyleClass().add("text-name");
//                        textName.setFill(Color.WHITE);
//                    case GREEN:
//                        textName.getStyleClass().add("text-name");
//                        textName.setFill(Color.WHITE);
//                    case RED:
//                        textName.getStyleClass().add("text-name");
//                        textName.setFill(Color.WHITE);
//                }
//                System.out.println("\n"+ message.getSender() + colour);


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

    public boolean isServerIsUp() {
        return serverIsUp;
    }

}