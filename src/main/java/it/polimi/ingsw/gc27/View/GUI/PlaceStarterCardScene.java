package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Net.Commands.AddStarterCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.SendMessageCommand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import  it.polimi.ingsw.gc27.View.Gui;
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

//sixth scene, allows the player to choose front or back of starterCard
public class PlaceStarterCardScene implements GenericController{

    @FXML
    public ImageView frontStarter;
    @FXML
    public ImageView backStarter;
    @FXML
    public Button frontStarterButton;
    @FXML
    public Button backStarterButton;
    @FXML
    public TabPane chatTabPaneStarter;
    private HashMap<String, Tab> chatTabHashMapP= new HashMap<>();

    // start chat methods
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
                    chatTabHashMapP.put("Global", chatTab);
                }
                else {
                    String myusername = miniModel.getPlayer().getUsername();
                    String username = miniModel.getChats().get(i).getChatters().stream()
                            .filter(user -> !user.equals(myusername))
                            .toList().getFirst();
                    chatTab.setText(username);
                    chatTabHashMapP.put(username, chatTab);

                    PawnColour colour = miniModel.getBoard().getColourPlayermap().get(username);
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
                chatTabPaneStarter.getTabs().add(chatTab);
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
    void handleOnActionChat(Button button, TextField textField){
        button.setOnAction(event -> {
            sendChatMessage();
            textField.clear();

        });
    }
    void sendChatMessage(){
        try {
            Tab currentTab = chatTabPaneStarter.getSelectionModel().getSelectedItem();
            String receiver = currentTab.getText();
            String content = getSendMessageFieldFromTab(currentTab).getText();
            Command command = new SendMessageCommand(Gui.getInstance().getClient().getMiniModel().getPlayer(), receiver, content);
            Gui.getInstance().getClient().sendCommand(command);
            System.out.println("\nmessaggio " +content+ " mandato a "+ receiver);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void overwriteChat(ClientChat chat, MiniModel miniModel){
            Platform.runLater(()-> {
                if (chat.getChatters().size() == 1) {
                    Tab tab = chatTabHashMapP.get("Global");
                    VBox vbox = getChatMessagesVBox(tab);

                    Text textName = new Text(chat.getChatMessages().getLast().getSender());
                    textName.getStyleClass().add("text-name");
                    PawnColour colour = miniModel.getBoard().getColourPlayermap().get(chat.getChatMessages().getLast().getSender());
                    switch (colour){ //does not work
                        case BLUE: textName.getStyleClass().add("tab-colour-blue");
                        case YELLOW: textName.getStyleClass().add("tab-colour-yellow");
                        case GREEN: textName.getStyleClass().add("tab-colour-green");
                        case RED: textName.getStyleClass().add("tab-colour-red");
                    }
                    HBox hBoxName = new HBox(textName);
                    hBoxName.setPadding(new Insets(0, 3, 0, 3));


                    Text text = new Text();
                    text.setText(chat.getChatMessages().getLast().getContent());
                    text.getStyleClass().add("text");
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setMaxWidth(240);

                    textFlow.setTextAlignment(TextAlignment.LEFT);
                    HBox hbox = new HBox(textFlow);

                    if (chat.getChatMessages().getLast().getSender().equals(miniModel.getPlayer().getUsername())) {
                        hbox.setAlignment(Pos.CENTER_RIGHT);
                        hBoxName.setAlignment(Pos.CENTER_RIGHT);
                        textFlow.getStyleClass().add("text-flow-sender");

                    }
                    //other players send the message
                    else {
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        hBoxName.setAlignment(Pos.CENTER_LEFT);
                        textFlow.getStyleClass().add("text-flow-receiver");

                    }

                    hbox.setPadding(new Insets(1, 4, 1, 5));
                    vbox.getChildren().add(hBoxName);
                    vbox.getChildren().add(hbox);
                } else {
                    String username = chat.getChatters().stream()
                            .filter(user -> !user.equals(miniModel.getPlayer().getUsername()))
                            .toList().getFirst();
                    Tab tab = chatTabHashMapP.get(username);

                    VBox vbox = getChatMessagesVBox(tab);

                    Text text = new Text();
                    text.setText(chat.getChatMessages().getLast().getContent());
                    text.getStyleClass().add("text");

                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setMaxWidth(240);
                    textFlow.setTextAlignment(TextAlignment.LEFT);
                    HBox hbox = new HBox(textFlow);

                    if (chat.getChatMessages().getLast().getSender().equals(username)) {
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        textFlow.getStyleClass().add("text-flow-receiver");

                    } else {
                        hbox.setAlignment(Pos.CENTER_RIGHT);
                        textFlow.getStyleClass().add("text-flow-sender");
                    }
                    hbox.setPadding(new Insets(5, 5, 5, 5));
                    vbox.getChildren().add(hbox);

                    //todo: fare scroll automatico
                }

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

    @FXML
    public void sendStarter(ActionEvent event) throws IOException, InterruptedException {
        if(event.getSource().equals(frontStarterButton)){
            //Gui.getInstance().stringFromSceneController("front");
            Command comm = new AddStarterCommand(Gui.getInstance().getClient().getUsername(), true);
            Gui.getInstance().getClient().sendCommand(comm);
        } else {
            Command comm = new AddStarterCommand(Gui.getInstance().getClient().getUsername(), false);
            Gui.getInstance().getClient().sendCommand(comm);
            //Gui.getInstance().stringFromSceneController("back");
        }
        Platform.runLater(()->{
            try {
                Gui.getInstance().switchScene("/fxml/ChooseObjectiveScene.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void changeImageFront(String imagePath) {
        Image image = new Image(imagePath);
        frontStarter.setImage(image);
    }

    public void changeImageBack(String imagePath) {
        Image image = new Image(imagePath);
        backStarter.setImage(image);
    }

    @Override
    public void receiveOk(String ackType) {


    }

    @Override
    public void receiveKo(String ackType) {

    }
}
