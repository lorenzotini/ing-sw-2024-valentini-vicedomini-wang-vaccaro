package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Commands.ChooseObjectiveCommand;
import it.polimi.ingsw.gc27.Commands.Command;
import it.polimi.ingsw.gc27.Commands.SendMessageCommand;
import it.polimi.ingsw.gc27.View.Gui.ScenePaths;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * third scene of initialization, players can choose their secret objective card from the two objective cards displayed
 */
public class ChooseObjectiveSceneController extends GenericController {
    /** button used to select the first objective card */
    @FXML
    public Button objButton1;

    /** button used to select the second objective card */
    @FXML
    public Button objButton2;

    /** shows the image of the first objective card */
    @FXML
    public ImageView obj1;

    /** shows the image of the second objective card */
    @FXML
    public ImageView obj2;

    /** pane that contains all the chats of the player */
    @FXML
    public TabPane chatTabPane;
    @FXML
    public Label gameSuspendedLabel;
    @FXML
    public TitledPane chatTitledPane;
    @FXML
    private Circle circleChat;
    //there is a private hashmap for all the scenes where the chat is displayed

    /** maps the username of the other player  of the chat and the correspondent tab
     *  if the chat is global the string mapped to the tab is "global"
     */
    private HashMap<String, Tab> chatTabHashMapC = new HashMap<>();

    /**
     * invoked by Gui, it builds the chat in the ChooseObjectiveScene
     * creates a tab for each chat and adds them to chatTabPane,
     * maps the tab and the chat
     */
    //start chat methods
    public void chatInitObjective() {
        circleChat.getStyleClass().add("circle-chat");
        circleChat.setVisible(false);
        MiniModel miniModel;
        do {
            try {
                miniModel = Gui.getInstance().getClient().getMiniModel();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } while (miniModel.getMarket() == null);

        for (int i = 0; i < miniModel.getChats().size(); i++) {
            Tab chatTab = new Tab(); //a tab for each chat
            if (i == 0) {
                chatTab.setText("Global");
                chatTabHashMapC.put("global", chatTab);
                chatTab.getStyleClass().add("tab-global");
            } else {
                String myusername = miniModel.getPlayer().getUsername();
                String username = miniModel.getChats().get(i).getChatters().stream()
                        .filter(user -> !user.equals(myusername))
                        .toList().getFirst();
                chatTab.setText(username);
                chatTabHashMapC.put(username, chatTab);

                PawnColour colour = miniModel.getBoard().getColourPlayerMap().get(username);
                switch (colour) {

                    case BLUE:
                        chatTab.getStyleClass().add("tab-colour-blue");
                    case YELLOW:
                        chatTab.getStyleClass().add("tab-colour-yellow");
                    case GREEN:
                        chatTab.getStyleClass().add("tab-colour-green");
                    case RED:
                        chatTab.getStyleClass().add("tab-colour-red");
                }
            }
            VBox chatContainer = new VBox(); //contains che messages of a chat
            ScrollPane chatContent = new ScrollPane();
            VBox chatMessages = new VBox();
            chatMessages.getStyleClass().add("vbox-background");
            chatContainer.getStyleClass().add("vbox-background");
            chatContent.getStyleClass().add("vbox-background");

            chatContent.setContent(chatMessages); //scrollPane contains Vbox with messages
            chatContent.setPrefHeight(400);
            chatContent.setPrefWidth(Region.USE_COMPUTED_SIZE);
            chatContent.setFitToWidth(true);
            chatContent.setFitToHeight(true);

            HBox messageBox = new HBox(); //used for the textField and the button on the bottom
            TextField sendMessage = new TextField();

            Button sendButton = new Button("Send");
            sendButton.getStyleClass().add("send-button");
            messageBox.getChildren().addAll(sendMessage, sendButton);
            messageBox.setSpacing(10);
            messageBox.setMinHeight(33);
            messageBox.setMaxHeight(33);
            messageBox.setPadding(new Insets(5,5,5,5));
            handleOnActionChat(sendButton, sendMessage);
            handleOnKeyPress(sendMessage);

            sendMessage.setPromptText("Write your message here...");

            // Set HBox growth for sendMessage
            HBox.setHgrow(sendMessage, Priority.ALWAYS);
            sendMessage.setMinHeight(24);
            sendMessage.setMaxHeight(24);
            sendMessage.setMaxWidth(300);
            sendMessage.getStyleClass().add("text-field-chat");

            // Create a spacer
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            chatContainer.getChildren().addAll(chatContent, messageBox);
            chatTab.setContent(chatContainer);
            chatTabPane.getTabs().add(chatTab);
            chatTabPane.getStyleClass().add("tab-pane-chat");

        }
        chatTitledPane.setExpanded(false);
        chatTitledPane.setOnMouseClicked(event -> {
            Platform.runLater(() -> {
                chatTitledPane.toFront();
                circleChat.setVisible(false);
            });
        });

    }

    /**
     * allows to send the message in the chat by clicking the "enter" button on the keyboard
     * @param textField the textfield where the message is written
     */
    private void handleOnKeyPress(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendChatMessage();
                textField.clear();
                event.consume();
            }
        });
    }

    /**
     * allows to send the message in the chat by clicking the "send" button
     * @param button the button used to send the message
     * @param textField the textfield where the message is written
     */
    void handleOnActionChat(Button button, TextField textField) {
        button.setOnAction(event -> {
            sendChatMessage();
            textField.clear();

        });
    }

    /**
     * collects information about the chat, the sender and the receiver of the message from the tab
     * and sends the SendMessageCommand
     */
    void sendChatMessage() {
        try {
            Tab currentTab = chatTabPane.getSelectionModel().getSelectedItem();
            String receiver = currentTab.getText();
            String content = getSendMessageFieldFromTab(currentTab).getText();
            if (!content.trim().isEmpty()) {
                Command command = new SendMessageCommand(Gui.getInstance().getClient().getMiniModel().getPlayer(), receiver, content);
                Gui.getInstance().getClient().sendCommand(command);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method implemented from {@link GenericController},
     * invoked by Gui when a message is sent by a player in the chat
     * @param chat the chat to be updated
     * @param miniModel the minimodel to be updated
     */
    public void overwriteChat(ClientChat chat, MiniModel miniModel) {
        Platform.runLater(() -> {
            String username = chat.getChatters().stream()
                    .filter(user -> !user.equals(miniModel.getPlayer().getUsername()))
                    .toList().getFirst();
            Tab tab = chatTabHashMapC.get(username);
            if (!chat.getChatMessages().getLast().getSender().equals(miniModel.getPlayer().getUsername())) {
                circleChat.setVisible(true);
            }
            Gui.getInstance().addLastChatMessage(chat.getChatMessages().getLast(), tab);
            //todo: fare scroll automatico

        });
    }

    /**
     * used to extract the textfield from the tab the chat using nodes system of javaFX
     * @param tab the tab where the textfield is
     * @return the textfield associated with the chat
     */
    private TextField getSendMessageFieldFromTab(Tab tab) {
        if (tab.getContent() instanceof VBox) {
            VBox chatContainer = (VBox) tab.getContent();
            if (chatContainer.getChildren().size() > 1 && chatContainer.getChildren().get(1) instanceof HBox) {
                HBox messageBox = (HBox) chatContainer.getChildren().get(1);
                for (javafx.scene.Node node : messageBox.getChildren()) {
                    if (node instanceof TextField) {
                        return (TextField) node;
                    }
                }
            }
        }
        return null;
    }
    //end chat methods

    /**
     * created and sends ChooseObjectiveCommand according which of the two buttons is pressed
     * invokes the method init() of ManuscriptSceneController to initialize the graphic of the scene
     * invokes Gui method to switch scene to ManuscriptScene
     * @param event the event that triggers the method
     * @throws IOException if the scene is not found
     */
    @FXML
    public void sendObj(MouseEvent event) throws IOException {

        if (event.getSource().equals(objButton1)) {
            Command comm = new ChooseObjectiveCommand(Gui.getInstance().getClient().getUsername(), 1);
            Gui.getInstance().getClient().sendCommand(comm);
        } else {
            Command comm = new ChooseObjectiveCommand(Gui.getInstance().getClient().getUsername(), 2);
            Gui.getInstance().getClient().sendCommand(comm);
        }
        objButton2.setOnMouseClicked(null);
        objButton2.setOnMouseClicked(null);
        if(Gui.getInstance().isGameOn()) {
            Platform.runLater(() -> {
                try {
                    ManuscriptSceneController manuscriptSceneController = (ManuscriptSceneController) Gui.getInstance().getControllerFromName(ScenePaths.MANUSCRIPT.getValue());
                    manuscriptSceneController.init();
                    Gui.getInstance().switchScene("/fxml/ManuscriptScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }else{
            gameSuspendedLabel.setVisible(true);
        }
    }
    @Override
    public void otherPlayerReconnected(){
        gameSuspendedLabel.setVisible(false);
    }

    /**
     * invoked by Gui, it sets the image of the first objective card from the image path given as a parameter
     * @param imagePath the path of the image
     */
    public void changeImageObj1(String imagePath) {
        Image image = new Image(imagePath);
        obj1.setImage(image);
    }

    /**
     * invoked by Gui, it sets the image of the second objective card from the image path given as a parameter
     * @param imagePath the path of the image
     */
    public void changeImageObj2(String imagePath) {
        Image image = new Image(imagePath);
        obj2.setImage(image);
    }

    /**
     * invoked by Gui in order to send a string to this scene controller,
     * sets what is showed in the actionFeedback label displayed in ManuscriptSceneController
     * before the stage changes scene from this one to the manuscript scene
     * @param ackType the string to be sent
     */
    @Override
    public void receiveOk(String ackType) {
        MiniModel miniModel;

        try {
            miniModel=Gui.getInstance().getClient().getMiniModel();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(()->{
            String feedback;
            feedback= miniModel.getPlayer().getPlayerState().toStringGUI();
            ManuscriptSceneController manuscriptSceneController = (ManuscriptSceneController) Gui.getInstance().getControllerFromName(ScenePaths.MANUSCRIPT.getValue());
            manuscriptSceneController.actionFeedback.setText(feedback);
            manuscriptSceneController.feedbackTextFlow.setTextAlignment(TextAlignment.RIGHT);
        });
    }

    /**
     * allocates all the messages of all the chats of a player
     * @throws RemoteException if the connection is lost
     */
    public void fullChatAllocate() throws RemoteException {
        MiniModel miniModel = Gui.getInstance().getClient().getMiniModel();
        String myUsername = miniModel.getPlayer().getUsername();
        ArrayList<ClientChat> chats = miniModel.getChats();
        for (ClientChat chat : chats) {
            Tab tab = chatTabHashMapC.get(chat.getChatters().stream()
                    .filter(user -> !user.equals(myUsername))
                    .toList().getFirst());
            for (ChatMessage message : chat.getChatMessages()) {
                Gui.getInstance().addLastChatMessage(message, tab);
            }
        }
    }

}
