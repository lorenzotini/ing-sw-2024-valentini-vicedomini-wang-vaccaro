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
import javafx.scene.paint.Paint;
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
    private final BlockingQueue<String> messages = new LinkedBlockingQueue<>();
    private final HashMap<String, Scene> pathSceneMap = new HashMap<>(); //maps path to scene
    private final HashMap<String, GenericController> pathContrMap = new HashMap<>(); //maps path to controller of the scene

    //setters and getters
    /**
     * Sets the primary stage for the GUI.
     *
     * @param stage the {@link Stage} to set as the primary stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    /**
     * Returns the current controller being used in the GUI.
     *
     * @return the current {@link GenericController}
     */
    public GenericController getCurrentController() {
        return currentController;
    }

    /**
     * Returns the client instance implementing the {@link VirtualView} interface.
     *
     * @return the client instance
     */
    public VirtualView getClient() {
        return client;
    }

    /**
     * Checks if the game is currently active.
     *
     * @return true if the game is on, false otherwise
     */
    public boolean isGameOn() {
        return isGameOn;
    }

    /**
     * Sets the client instance implementing the {@link VirtualView} interface.
     *
     * @param client the client instance to set
     */
    @Override
    public void setClient(VirtualView client) {
        this.client = client;
    }

    /**
     * Sets the reconnected status of the client.
     *
     * @param reconnected true if the client is reconnected, false otherwise
     */
    public void setReconnected(boolean reconnected) {
        isReconnected = reconnected;
    }
    //end setters and getters

    //singleton pattern
    /**
     * Private constructor for the {@link Gui} class.
     * Implements the singleton pattern to ensure that only one instance of {@link Gui} is created.
     * This constructor is private to prevent direct instantiation.
     */
    private Gui() {
    }


    /**
     * Returns the singleton instance of the {@link Gui} class. If the instance does not exist, it creates a new one.
     * This method is thread-safe, ensuring that only one instance of the {@link Gui} class is created.
     *
     * @return the singleton instance of the {@link Gui} class
     */
    public static Gui getInstance() {
        synchronized (Gui.class) {
            if (gui == null)
                gui = new Gui();
            return gui;
        }
    }

    //loads scenes and controllers in hashmaps
    /**
     * Initializes the application by loading all scenes and their corresponding controllers.
     * This method loads each scene specified in {@link ScenePaths}, creates a new Scene object,
     * and maps each scene path to its respective Scene and Controller.
     * The current controller is set to the controller for the starter scene.
     *
     * @throws IOException if an I/O error occurs during loading of the FXML files
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

    //start of the game after initialization

    /**
     * Executes the initial setup of the game scenes.
     * This method sets the starter cards and chat in the StarterCardScene, and the objective cards and chat
     * in the ObjectiveCardScene if the player is not reconnected.
     */
    @Override
    public void run() {

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
    }

    /**
     * Switches the current scene to the scene specified by the given path.
     * This method updates the scene on the JavaFX application thread to ensure thread safety.
     * Change the currentController to the controller of the new scene;
     *
     * @param scenePath the path of the scene to switch to
     * @throws IOException if an I/O error occurs during the scene switch
     */
    public void switchScene(String scenePath) throws IOException {
        Platform.runLater(() -> {
            stage.setScene(pathSceneMap.get(scenePath));
            stage.show();
            currentController = getControllerFromName(scenePath);
        });
    }

    /**
     * @param path is the path to the xml scene
     * @return the SceneController of the path to the xml given
     */
    public GenericController getControllerFromName(String path) {
        return pathContrMap.get(path);
    }

    /**
     * Not used by this implementation
     *
     * @param phrase not used by this implementation
     */
    @Override
    public void showString(String phrase) {
        System.out.println(phrase);
    }

    /**
     * Suspends the game by setting the game state to active and notifying the current controller
     * to suspend the game.
     *
     * @param string a string parameter (not used in the current implementation)
     */
    public void suspendedGame(String string) {
        currentController.suspendeGame();
        isGameOn = false;
    }

    /**
     * Set isGameOn to true and advice that the game can continue
     */
    @Override
    public void resumeTheMatch() {
        isGameOn = true;
        currentController.otherPlayerReconnected();
    }

    /**
     * Displays the given hand of resource cards in the current GUI controller if it is an instance of {@link ManuscriptSceneController}.
     * This method updates the hand view in the controller using the latest data from the {@link MiniModel}.
     *
     * @param hand the hand of resource cards to be displayed
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
     * Displays the given manuscript in the current GUI controller.
     * This method updates the manuscript view and counters in the controller using the latest data
     * from the {@link MiniModel}.
     *
     * @param manuscript the client manuscript data to be displayed
     */
    @Override
    public void show(ClientManuscript manuscript) {

        if (Gui.getInstance().getCurrentController() instanceof ManuscriptSceneController controller) {
            try {
                MiniModel miniModel = client.getMiniModel();

                HashMap<String, ClientManuscript> map = new HashMap<>(miniModel.getManuscriptsMap());
                // update manuscripts
                for (Map.Entry<String, ClientManuscript> element : map.entrySet()) {
                    controller.overwriteManuscript(miniModel, element.getKey(), false);
                }

                //update counters
                controller.overwriteCounters(miniModel);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Displays the given board as the MainClient Board.
     * Notify the ManuscriptSceneController to update the view.
     *
     * @param board the new to be displayed
     */
    @Override
    public void show(ClientBoard board) {
        if (currentController instanceof ManuscriptSceneController)
            ((ManuscriptSceneController) currentController).updateBoard(board);
    }

    /**
     * Displays the given chat in the current GUI controller.
     * This method updates the chat view in the controller using the latest data from the {@link MiniModel}.
     * When a message is added to the chat, it is updated in all scenes where the chat is displayed.
     *
     * @param chat the client chat data to be displayed
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
     * Displays the given market in the current GUI controller if it is an instance of {@link ManuscriptSceneController}.
     * This method updates the market view in the controller using the latest data from the {@link MiniModel}.
     *
     * @param market the client market data to be displayed
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
     * Notify that the manuscript of another player has been updated
     *
     * @param manuscript the ones been updated
     */
    @Override
    public void updateManuscriptOfOtherPlayer(ClientManuscript manuscript) {
        show(manuscript);
    }

    /**
     * Take a string from the messages queue, added before by the stringFromSceneController(...) method
     *
     * @return the string by the queue with most priority
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
     * Add a string to the messages queue, used to talk with the server.
     * These will be taken by the read.
     *
     * @param string the input to send to the server
     */
    public void stringFromSceneController(String string) {
        messages.add(string);
    }

    /**
     * Handles an "Ok" acknowledgment by logging the message and passing it to the current controller.
     * If a current controller is set, it logs the "Ok" message and delegates the handling to the controller's
     * method.
     *
     * @param string the acknowledgment message to be handled
     */
    @Override
    public void okAck(String string) {
        // if a OkMessage arrives when the currentController is null, it means that the server is up but gui is not ready yet
        if (currentController == null) {
            serverIsUp = true;
            return;
        }
        currentController.receiveOk(string);
    }

    /**
     * Handles a "Ko" acknowledgment by logging the message and passing it to the current controller.
     * If a current controller is set, it logs the "Ko" message and delegates the handling to the controller's
     * method.
     *
     * @param string the acknowledgment message to be handled
     */
    @Override
    public void koAck(String string) {
        if (currentController != null) {
            currentController.receiveKo(string);
        }
    }

    /**
     * Displays the winners of the game by updating the ending scene with the score board.
     * This method ensures that the user interface is updated on the JavaFX application thread.
     * The scene is switched to the ending scene.
     */
    @Override
    public void showWinners() {

        Platform.runLater(() -> {
            try {
                ((EndingSceneController) Gui.getInstance().getControllerFromName(ScenePaths.ENDING.getValue()))
                        .changeWinnersLabel(client.getMiniModel().getBoard().getScoreBoard());
                if (currentController instanceof ManuscriptSceneController) {
                    switchScene(ScenePaths.ENDING.getValue());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Adds the chat message as the last chat message to the specified chat tab.
     * This method updates the user interface to display the new message, aligning it appropriately
     * based on the sender and receiver.
     * If the message is a global message, the sender's name is displayed. The method runs
     * on the JavaFX application thread to ensure thread safety when updating the UI components.
     *
     * @param message the chat message to be added
     * @param chatTab the tab in which the message should be displayed
     */
    public void addLastChatMessage(ChatMessage message, Tab chatTab) {
        Platform.runLater(() -> {

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
     * Given a chatTab return the next space where add a message
     *
     * @param chatTab the tab where add new message
     * @return the Box where you can add a new message
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

    public boolean isReconnected() {
        return isReconnected;
    }

}