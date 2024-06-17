package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Net.Commands.ChooseObjectiveCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.SendMessageCommand;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ChooseObjectiveSceneController implements GenericController {
    @FXML
    public Button objButton1;
    @FXML
    public Button objButton2;
    @FXML
    public ImageView obj1;
    @FXML
    public ImageView obj2;
    @FXML
    public TabPane chatTabPane;
    //there is a private hashmap for all the scenes where the chat is displayed
    private HashMap<String, Tab> chatTabHashMapC = new HashMap<>();

    //start chat methods
    public void chatInitObjective() {
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
            } else {
                String myusername = miniModel.getPlayer().getUsername();
                String username = miniModel.getChats().get(i).getChatters().stream()
                        .filter(user -> !user.equals(myusername))
                        .toList().getFirst();
                chatTab.setText(username);
                chatTabHashMapC.put(username, chatTab);

                PawnColour colour = miniModel.getBoard().getColourPlayermap().get(username);
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
            chatContent.setContent(chatMessages); //scrollPane contains Vbox with messages

            chatContent.setPrefHeight(400);
            chatContent.setPrefWidth(Region.USE_COMPUTED_SIZE);
            chatContent.setFitToWidth(true);
            chatContent.setFitToHeight(true);

            HBox messageBox = new HBox(); //used for the textField and the button on the bottom
            TextField sendMessage = new TextField();

            Button sendButton = new Button("Send");
            messageBox.getChildren().addAll(sendMessage, sendButton);
            messageBox.setSpacing(20);
            handleOnActionChat(sendButton, sendMessage);
            handleOnKeyPress(sendMessage);

            sendMessage.setPromptText("Write your message here...");

            // Set HBox growth for sendMessage
            HBox.setHgrow(sendMessage, Priority.ALWAYS);
            sendMessage.setMaxWidth(300);

            // Create a spacer
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            chatContainer.getChildren().addAll(chatContent, messageBox);
            chatTab.setContent(chatContainer);
            chatTabPane.getTabs().add(chatTab);

        }

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

    void handleOnActionChat(Button button, TextField textField) {
        button.setOnAction(event -> {
            sendChatMessage();
            textField.clear();

        });
    }

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

    public void overwriteChat(ClientChat chat, MiniModel miniModel) {
        Platform.runLater(() -> {
            String username = chat.getChatters().stream()
                    .filter(user -> !user.equals(miniModel.getPlayer().getUsername()))
                    .toList().getFirst();
            Tab tab = chatTabHashMapC.get(username);

            Gui.getInstance().addLastChatMessage(chat.getChatMessages().getLast(), tab);
            //todo: fare scroll automatico

        });
    }

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


    @FXML
    public void sendObj(ActionEvent event) throws IOException, InterruptedException {
        if (event.getSource().equals(objButton1)) {
            Command comm = new ChooseObjectiveCommand(Gui.getInstance().getClient().getUsername(), 1);
            Gui.getInstance().getClient().sendCommand(comm);
        } else {
            Command comm = new ChooseObjectiveCommand(Gui.getInstance().getClient().getUsername(), 2);
            Gui.getInstance().getClient().sendCommand(comm);
        }
        Platform.runLater(() -> {
            try {
                ManuscriptSceneController manuscriptSceneController = (ManuscriptSceneController) Gui.getInstance().getControllerFromName(ScenePaths.MANUSCRIPT.getValue());
                manuscriptSceneController.init();
                Gui.getInstance().switchScene("/fxml/ManuscriptScene.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void changeImageObj1(String imagePath) {
        Image image = new Image(imagePath);
        obj1.setImage(image);
    }

    public void changeImageObj2(String imagePath) {
        Image image = new Image(imagePath);
        obj2.setImage(image);
    }

    @Override
    public void receiveOk(String ackType) {
    }

    @Override
    public void receiveKo(String ackType) {
    }

    public void fullChatAllocate() throws RemoteException {
        MiniModel miniModel = Gui.getInstance().getClient().getMiniModel();
        String myUsername = miniModel.getPlayer().getUsername();
        for (ClientChat chat : miniModel.getChats()) {
            Tab tab = chatTabHashMapC.get(chat.getChatters().stream()
                    .filter(user -> !user.equals(myUsername))
                    .toList().getFirst());
            for (ChatMessage message : chat.getChatMessages()) {
                Gui.getInstance().addLastChatMessage(message, tab);
            }
        }
    }

}
