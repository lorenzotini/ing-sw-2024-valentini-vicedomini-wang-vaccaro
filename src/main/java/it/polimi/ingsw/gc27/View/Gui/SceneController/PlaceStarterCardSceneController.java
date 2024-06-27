package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Net.Commands.AddStarterCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.SendMessageCommand;
import it.polimi.ingsw.gc27.View.Gui.ScenePaths;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Controller class for the PlaceStarterCard scene in the GUI.
 * Handles the interaction for selecting the front or back of a starter card,
 * and includes chat functionality.
 */
public class PlaceStarterCardSceneController extends GenericController{

    @FXML
    public ImageView frontStarter;
    @FXML
    public ImageView backStarter;
    @FXML
    public Button frontStarterButton;
    @FXML
    public Button backStarterButton;
    @FXML
    public TabPane chatTabPane;
    @FXML
    public Label gameSuspendedLabel;
    @FXML
    public TitledPane chatTitledPaneStarter;

    //there is a private hashmap for all the scenes where the chat is displayed
    private HashMap<String, Tab> chatTabHashMapP= new HashMap<>();

    // start chat methods

    /**
     * Initializes the chat functionality for the starter card scene.
     * Sets up tabs for global and private chats, message containers, and send button actions.
     */
    public void chatInitStarter(){
            MiniModel miniModel;
            do {
                try {
                    miniModel = Gui.getInstance().getClient().getMiniModel();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }while(miniModel.getMarket() == null);

            for (int i = 0; i < miniModel.getChats().size(); i++) {
                Tab chatTab = new Tab(); //a tab for each chat
                if(i==0) {
                    chatTab.setText("Global");
                    chatTabHashMapP.put("global", chatTab);
                    chatTab.getStyleClass().add("tab-global");
                }
                else {
                    String myusername = miniModel.getPlayer().getUsername();
                    String username = miniModel.getChats().get(i).getChatters().stream()
                            .filter(user -> !user.equals(myusername))
                            .toList().getFirst();
                    chatTab.setText(username);
                    chatTabHashMapP.put(username, chatTab);

                    PawnColour colour = miniModel.getBoard().getColourPlayerMap().get(username);
                    switch (colour){

                        case BLUE: chatTab.getStyleClass().add("tab-colour-blue");
                        case YELLOW: chatTab.getStyleClass().add("tab-colour-yellow");
                        case GREEN: chatTab.getStyleClass().add("tab-colour-green");
                        case RED: chatTab.getStyleClass().add("tab-colour-red");
                    }

                }
                VBox chatContainer = new VBox(); //contains che messages of a chat
                ScrollPane chatContent = new ScrollPane();
                VBox chatMessages = new VBox();
                chatMessages.getStyleClass().add("vbox-background");
                chatContainer.getStyleClass().add("vbox-background");
                chatContent.getStyleClass().add("vbox-background");


                //chatContainer.setDisable(true);
                //chatMessages.setDisable(true);
                chatContainer.setPrefHeight(500);
                chatContent.setContent(chatMessages); //scrollPane contains Vbox with messages
                chatContent.setPrefHeight(450);
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

            chatTitledPaneStarter.setOnMouseClicked(event -> {
                Platform.runLater(() -> {
                    chatTitledPaneStarter.toFront();
                    //circleChat.setVisible(false);
                });
            });

    }

    private void handleOnKeyPress(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendChatMessage();
                textField.clear();
                event.consume();
            }
        });
    }

    void handleOnActionChat(Button button, TextField textField){
        button.setOnAction(event -> {
            sendChatMessage();
            textField.clear();

        });
    }

    void sendChatMessage(){
        try {
            Tab currentTab = chatTabPane.getSelectionModel().getSelectedItem();
            String receiver = currentTab.getText();
            String content = getSendMessageFieldFromTab(currentTab).getText();
            if(!content.trim().isEmpty()) {
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
     * @param chat the chat where the message is sent
     * @param miniModel the miniModel of the player
     */
    public void overwriteChat(ClientChat chat, MiniModel miniModel) {
        Platform.runLater(() -> {
            String username = chat.getChatters().stream()
                    .filter(user -> !user.equals(miniModel.getPlayer().getUsername()))
                    .toList().getFirst();
            Tab tab = chatTabHashMapP.get(username);

            Gui.getInstance().addLastChatMessage(chat.getChatMessages().getLast(), tab);
            //todo: fare scroll automatico

        });
    }
    private VBox getChatMessagesVBox(Tab chatTab) {
        VBox chatContainer = (VBox) chatTab.getContent();
        ScrollPane chatContent = (ScrollPane) chatContainer.getChildren().getFirst();
        chatContent.setVvalue(1.0); //non so se si mette qui
        return (VBox) chatContent.getContent();
    }
    private  TextField getSendMessageFieldFromTab(Tab tab) {
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
        }return null;
    }
    //end chat methods

    /**
     * Handles the event of selecting the front or back starter card.
     *
     * @param event The mouse event.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    public void sendStarter(MouseEvent event) throws IOException {
        if(event.getSource().equals(frontStarterButton)){
            Command comm = new AddStarterCommand(Gui.getInstance().getClient().getUsername(), true);
            Gui.getInstance().getClient().sendCommand(comm);
        } else {
            Command comm = new AddStarterCommand(Gui.getInstance().getClient().getUsername(), false);
            Gui.getInstance().getClient().sendCommand(comm);
        }
        if(Gui.getInstance().isGameOn()) {
            Platform.runLater(() -> {
                try {
                    ((ChooseObjectiveSceneController) Gui.getInstance()
                            .getControllerFromName(ScenePaths.CHOOSEOBJ.getValue())).fullChatAllocate();
                    Gui.getInstance().switchScene("/fxml/ChooseObjectiveScene.fxml");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }else{
            gameSuspendedLabel.setVisible(true);
        }
    }

    /**
     * Sets the label used to notify the game suspension to not visible
     */
    @Override
    public void otherPlayerReconnected(){
        gameSuspendedLabel.setVisible(false);
    }

    /**
     * Changes the image of the front starter card.
     *
     * @param imagePath The path of the image to set.
     */
    public void changeImageFront(String imagePath) {
        Image image = new Image(imagePath);
        frontStarter.setImage(image);
    }

    /**
     * Changes the image of the back starter card.
     *
     * @param imagePath The path of the image to set.
     */
    public void changeImageBack(String imagePath) {
        Image image = new Image(imagePath);
        backStarter.setImage(image);
    }




}
