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

/**
 * The Gui class is responsible for managing the graphical user interface of the game
 * It implements the View interface and follows the singleton pattern to ensure only one instance is used
 * This class handles scene switching, user interactions, and updates from the game server, displaying
 * every update in the current scene
 */
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

    /**
     * Private constructor to enforce singleton pattern.
     */
    private Gui() {
    }

    /**
     * Returns the singleton instance of the Gui class
     * @return The singleton instance of the Gui class
     */
    public static Gui getInstance() {
        synchronized (Gui.class) {
            if (gui == null)
                gui = new Gui();
            return gui;
        }
    }

    /**
     * Creates all the scenes and the respective controllers and
     * Loads scenes and controllers into the hashmaps
     * @throws IOException If an I/O error occurs
     */
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

    /**
     * Starts the game after initialization, in case of disconnection
     * handling reconnection automatically {@link View}
     */
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

    /**
     * Switches the scene to the specified scene path and displays
     * @param scenePath The path of the scene to switch to
     * @throws IOException If an I/O error occurs
     */
    public void switchScene(String scenePath) throws IOException {
        Platform.runLater(() -> {
            stage.setScene(pathSceneMap.get(scenePath));
            stage.show();
            currentController = getControllerFromName(scenePath);
        });
    }

    /**
     * Gets the controller associated with the specified path
     * @param path The fil path of the scene
     * @return The controller associated with the specified path
     */
    public GenericController getControllerFromName(String path) {
        return pathContrMap.get(path);
    }

    /**
     * Displays a string message to the console {@link View}
     * @param phrase The message to display
     */
    @Override
    public void showString(String phrase) {
        System.out.println(phrase);
    }

    /**
     * Suspends the game and updates the current controller
     * @param string the string shown
     */
    public void suspendedGame(String string){
        currentController.suspendeGame();
        isGameOn = false;
    }

    /**
     * Resumes the match and updates the current controller {@link View}
     */
    @Override
    public void resumeTheMatch() {
        isGameOn = true;
        currentController.reconnectPlayer();
    }

    /**
     * Displays the player's hand if the current scene is the manuscript scene {@link View}
     * @param hand The player's hand to display
     */
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

    /**
     * Displays the specified ObjectiveCard {@link View}
     * @param objectiveCard The ObjectiveCard to display
     */
    @Override
    public void show(ObjectiveCard objectiveCard) {

    }

    /**
     * Displays the specified ClientManuscript {@link View}
     * @param manuscript The ClientManuscript to display
     */
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

    /**
     * Displays the specified ClientBoard {@link View}
     * @param board The ClientBoard to display
     */
    @Override
    public void show(ClientBoard board) {
        if (currentController instanceof ManuscriptSceneController)
            ((ManuscriptSceneController) currentController).updateBoard(board);
    }

    /**
     * Displays the specified ClientChat {@link View}
     * @param chat The ClientChat to display
     */
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

    /**
     * Displays the specified ClientMarket {@link View}
     * @param market The ClientMarket to display
     */
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

    /**
     * Shows the updates the manuscript of another player {@link View}
     * @param manuscript The ClientManuscript to show
     */
    @Override
    public void updateManuscriptOfOtherPlayer(ClientManuscript manuscript) {
        show(manuscript);
    }

    /**
     * Reads a message from the message blocking queue {@link View}
     * @return The message from the blocking queue
     */
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

    /**
     * Adds a message from the scene controller to the message queue
     * @param string The message to add
     */
    public void stringFromSceneController(String string) {
        messages.add(string);
    }

    /**
     * Handles the acknowledgment of an OK message {@link View}
     * @param string The message to handle
     */
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

    /**
     * Handles the acknowledgment of a KO message {@link View}
     * @param string The message to handle
     */
    @Override
    public void koAck(String string) {
        if (currentController != null) {
            System.out.println("\nKo " + currentController.toString() + string);
            currentController.receiveKo(string);
        }
    }

    /**
     * Displays the winner/winners of the game {@link View}
     */
    @Override
    public void showWinners() {

        Platform.runLater(() -> {
            try {
                ((EndingSceneController)Gui.getInstance().getControllerFromName(ScenePaths.ENDING.getValue()))
                        .changeWinnersLabel(client.getMiniModel().getBoard().getScoreBoard());
                if(currentController instanceof ManuscriptSceneController){
                    switchScene(ScenePaths.ENDING.getValue());

                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * Adds the last chat message to the specified chat tab
     * @param message The chat message to add
     * @param chatTab The chat tab to add the message to
     */
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


    /**
     * Gets the VBox containing chat messages from the specified chat tab
     * @param chatTab The chat tab to get the messages from
     * @return The VBox containing chat messages
     */
    private VBox getChatMessagesVBox(Tab chatTab) {
        VBox chatContainer = (VBox) chatTab.getContent();
        ScrollPane chatContent = (ScrollPane) chatContainer.getChildren().getFirst();
        chatContent.setVvalue(1.0); //non so se si mette qui
        return (VBox) chatContent.getContent();
    }

    /**
     * Returns whether  the server is up
     * @return true if the server is up, false otherwise
     */
    public boolean isServerIsUp() {
        return serverIsUp;
    }

    /**
     * Returns whether the game is  on
     * @return true if the game is on, false otherwise
     */
    public boolean isGameOn() {
        return isGameOn;
    }

    /**
     * Sets the stage for the GUI
     * @param stage The stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the current controller
     * @return The current controller
     */
    public GenericController getCurrentController() {
        return currentController;
    }

    /**
     * Gets the client (VirtualView)
     * @return The client (VirtualView)
     */
    public VirtualView getClient() {
        return client;
    }

    /**
     * Sets the client (VirtualView) {@link View}
     * @param client The client (VirtualView) to set
     */
    @Override
    public void setClient(VirtualView client) {
        this.client = client;
    }

    /**
     * Sets the reconnected status (true or false)
     * @param reconnected The reconnected status to set
     */
    public void setReconnected(boolean reconnected) {
        isReconnected = reconnected;
    }
}