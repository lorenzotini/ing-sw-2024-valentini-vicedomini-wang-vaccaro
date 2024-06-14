package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientManuscript;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Placement;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.AddCardCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.DrawCardCommand;
import it.polimi.ingsw.gc27.Net.Commands.SendMessageCommand;
import it.polimi.ingsw.gc27.View.GUI.UserData.HandCardData;
import it.polimi.ingsw.gc27.View.GUI.UserData.ManuscriptCardData;
import it.polimi.ingsw.gc27.View.GUI.UserData.MarketCardData;
import it.polimi.ingsw.gc27.Model.ClientClass.*;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ManuscriptSceneController implements GenericController {


    @FXML
    private TextField actionFeedback;
    @FXML
    private TabPane manuscriptTabPane;
    @FXML
    private HBox handCards;
    @FXML
    private VBox counters;
    @FXML
    private HBox marketResources;
    @FXML
    private HBox marketGolds;
    @FXML
    private VBox commonObjectives;
    @FXML
    private TabPane chatTabPane;


    //TODO vedere se è meglio mettere gli attributi privati che ora sono pubblici

    // attributes to handle addCard invocation
    private int handCardIndex;
    private boolean isFront;
    private int x;
    private int y;
    public ImageView manuscriptCard;
    public ImageView handCard;

    // attributes to handle drawCard invocation
    private ImageView marketCard;

    private GridPane grid;
    private HashMap<String, Tab> chatTabHashMap= new HashMap<>();

    public TextField getActionFeedback() {
        return actionFeedback;
    }


    public void init() {

        MiniModel miniModel;
        do {
            try {
                miniModel = Gui.getInstance().getClient().getMiniModel();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }while(miniModel.getMarket() == null);

        // initialize manuscripts' grid panes
        createManuscriptGrids(miniModel);

        // populate manuscripts
        for(Map.Entry<String, ClientManuscript> element :  miniModel.getManuscriptsMap().entrySet()){
            overwriteManuscript(miniModel, element.getKey());
        }

        // populate hand with cards
        overwriteHand(miniModel);

        // market
        overwriteMarket(miniModel);

        // common objectives
        for (int i = 0; i < 2; i++) {
            ImageView commonObjective = new ImageView(new Image(miniModel.getMarket().getCommonObjectives().get(i).getFront().getImagePath()));
            commonObjective.setFitHeight(70);
            commonObjective.setFitWidth(105);
            zoomCardOnHover(commonObjective, 1.2);
            commonObjectives.getChildren().add(commonObjective);
        }

        // chat
        for (int i = 0; i < miniModel.getChats().size(); i++) {
            Tab chatTab = new Tab(); //a tab for each chat
            if(i==0) {
                chatTab.setText("Global");
                chatTabHashMap.put("Global", chatTab);
            }
            else{
                String myusername = miniModel.getPlayer().getUsername();
                String username = miniModel.getChats().get(i).getChatters().stream()
                                .filter(user -> !user.equals(myusername))
                                .toList().getFirst();
                chatTab.setText(username);

                chatTabHashMap.put(username, chatTab);
            }

            VBox chatContainer = new VBox(); //contains che messages of a chat
            ScrollPane chatContent = new ScrollPane();
            VBox chatMessages = new VBox();
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


            //chatTab.setText("Player " + i);
            //chatTab.setContent(chatContent);
            chatTab.setContent(chatContainer);
            chatTabPane.getTabs().add(chatTab);
        }

        // counters
        overwriteCounters(miniModel);

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
    void sendChatMessage(){
        try {
            Tab currentTab = chatTabPane.getSelectionModel().getSelectedItem();
            String receiver = currentTab.getText();
            String content = getSendMessageFieldFromTab(currentTab).getText();
            Command command = new SendMessageCommand(Gui.getInstance().getClient().getMiniModel().getPlayer(), receiver, content);
            Gui.getInstance().getClient().sendCommand(command);
            System.out.println("\nmessaggio " +content+ " mandato a "+ receiver);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    void handleOnActionChat(Button button, TextField textField){
        button.setOnAction(event -> {
            sendChatMessage();
            textField.clear();

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



    void handleDropEventManuscript(ImageView imgView) {

        // DRAG OVER
        imgView.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage() || db.hasFiles()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // DRAG DROPPED
        imgView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage()) {
                ManuscriptCardData manuscriptCardData = (ManuscriptCardData) imgView.getUserData();
                this.x = manuscriptCardData.x;
                this.y = manuscriptCardData.y;
                this.manuscriptCard = imgView;

                sendAddCardCommand();
            }
            event.consume();
        });


    }


    void handleDragDetectedHand(ImageView imgView) {

        // DRAG DETECTED
        imgView.setOnDragDetected(event -> {
            Dragboard db = imgView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cb = new ClipboardContent();

            // copy the selected image, fixing the gigantic zoom when dragging
            Image toAdd = new Image(imgView.getImage().getUrl(), 128 * 1.5, 128, false, false);

            cb.putImage(toAdd);
            db.setContent(cb);
            this.handCard = imgView;

            HandCardData handCardData = (HandCardData) imgView.getUserData();
            this.handCardIndex = handCardData.getHandIndex();
            this.isFront = handCardData.isFront();

            event.consume();
        });

        // DRAG DONE
        imgView.setOnDragDone(Event::consume);

    }

    void handleOnClick(ImageView imgView){
        imgView.setOnMouseClicked(event -> {
            String oldUrl = imgView.getImage().getUrl();
            if(((HandCardData) imgView.getUserData()).isFront()){
                imgView.setImage(new Image(oldUrl.replace("front", "back")));
            }else{
                imgView.setImage(new Image(oldUrl.replace("back", "front")));
            }
            ((HandCardData) imgView.getUserData()).changeIsFront();
            event.consume();
        });
    }

    void handleClickDetectedMarket(ImageView imgView) {

        // DRAG DETECTED
        imgView.setOnMouseClicked(event -> {
            this.marketCard = imgView;
            sendDrawCardCommand();
            System.out.println("CLICK MARKET"+ event);
            event.consume();
        });

    }

    void handleZoom(ScrollPane scrollPane, GridPane grid) {

        Scale scaleTransform = new Scale(1, 1, 0, 0);
        grid.getTransforms().add(scaleTransform);

        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) { // Use Ctrl+Scroll for zooming
                double deltaY = event.getDeltaY();
                double zoomFactor = 1.05;
                if (deltaY < 0) {
                    zoomFactor = 0.95;
                }
                scaleTransform.setX(scaleTransform.getX() * zoomFactor);
                scaleTransform.setY(scaleTransform.getY() * zoomFactor);
                event.consume();
            }
        });

    }

    void zoomCardOnHover(ImageView imgView, double factor) {

        double originalHeight = imgView.getFitHeight();
        double originalWidth = imgView.getFitWidth();

        imgView.setOnMouseEntered(event -> {
            imgView.setFitHeight(originalHeight * factor);
            imgView.setFitWidth(originalWidth * factor);
            event.consume();
        });

        imgView.setOnMouseExited(event -> {
            imgView.setFitHeight(originalHeight);
            imgView.setFitWidth(originalWidth);
            event.consume();
        });

    }

    private void sendAddCardCommand() {
        String username;
        try {
            username = Gui.getInstance().getClient().getUsername();
            Command command = new AddCardCommand(username, this.handCardIndex, this.isFront, this.x, this.y);
            Gui.getInstance().getClient().sendCommand(command);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDrawCardCommand() {
        String username;
        MarketCardData data = (MarketCardData) marketCard.getUserData();
        try {
            username = Gui.getInstance().getClient().getUsername();
            Command command = new DrawCardCommand(username, data.isGold, data.fromDeck, data.faceUpCardIndex);
            Gui.getInstance().getClient().sendCommand(command);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void receiveKo(String ackType) {

    }

    public void overwriteChat(ClientChat chat, MiniModel miniModel){
        Platform.runLater(()->{
            if(chat.getChatters().size()>2){ //nella global
                ClientChat global = miniModel.getChats().getFirst();
                //global.addChatMessage(chat.getChatMessages().getLast());

                //ho la chat ,voglio la tab
                Tab tab= chatTabHashMap.get("Global");

                VBox a= getChatMessagesVBox(tab);

            }
            else{
                String username= chat.getChatters().stream()
                        .filter(user -> !user.equals(miniModel.getPlayer().getUsername()))
                        .toList().getFirst();
                Tab tab= chatTabHashMap.get(username);
                VBox vbox= getChatMessagesVBox(tab);

                Text text = new Text();
                TextFlow textFlow = new TextFlow(text);
                textFlow.setPrefWidth(100);
                HBox hbox = new HBox(textFlow);
                if(chat.getChatMessages().getLast().getSender().equals(username)){
                    hbox.setAlignment(Pos.CENTER_LEFT);
                }else{ hbox.setAlignment(Pos.CENTER_RIGHT);}

                text.setText(chat.getChatMessages().getLast().getContent());

                vbox.getChildren().add(hbox);
                //vbox.getChildren().add(new Text(chat.getChatMessages().getLast().getContent()));
                //trovo altra chat
                System.out.println("zio pera");
            }

        });
    }
    private VBox getChatMessagesVBox(Tab chatTab) {
        VBox chatContainer = (VBox) chatTab.getContent();
        ScrollPane chatContent = (ScrollPane) chatContainer.getChildren().getFirst();
        return (VBox) chatContent.getContent();
    }

    private void createManuscriptGrids(MiniModel miniModel){

        for(Map.Entry<String, ClientManuscript> element :  miniModel.getManuscriptsMap().entrySet()){

            GridPane grid = new GridPane();

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMaxWidth(150);
            columnConstraints.setMinWidth(150);
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(100);
            rowConstraints.setMinHeight(100);

            for(int i = 0; i < 85; i++){
                grid.getColumnConstraints().add(columnConstraints);
                grid.getRowConstraints().add(rowConstraints);
            }

            grid.setHgap(-35);
            grid.setVgap(-40);

            ScrollPane newManuscriptScrollPane = new ScrollPane();

            newManuscriptScrollPane.setContent(grid);
            newManuscriptScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            newManuscriptScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            newManuscriptScrollPane.setFitToWidth(true);
            newManuscriptScrollPane.setFitToHeight(true);
            newManuscriptScrollPane.setHvalue(0.5);
            newManuscriptScrollPane.setVvalue(0.5);

            handleZoom(newManuscriptScrollPane, grid);

            manuscriptTabPane.getTabs().add(new Tab(element.getKey(), newManuscriptScrollPane));

        }
    }

    public void overwriteManuscript(MiniModel miniModel, String username) {

        Platform.runLater(() -> {

            boolean isMyManuscript = miniModel.getPlayer().getUsername().equals(username);

            ClientManuscript manuscript = miniModel.getManuscriptsMap().get(username);

            GridPane grid = new GridPane();

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMaxWidth(150);
            columnConstraints.setMinWidth(150);
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(100);
            rowConstraints.setMinHeight(100);

            for(int i = 0; i < 85; i++){
                grid.getColumnConstraints().add(columnConstraints);
                grid.getRowConstraints().add(rowConstraints);
            }

            grid.setHgap(-35);
            grid.setVgap(-40);

            Optional<Tab> userTab = manuscriptTabPane.getTabs().stream().filter(tab -> tab.getText().equals(username)).findFirst();

            // build manuscript
            if (isMyManuscript) {
                for (Placement placement : manuscript.getPlacements()) {
                    Face face = miniModel.getManuscript().getField()[placement.getX()][placement.getY()];

                    ImageView imageView = new ImageView();
                    imageView.setImage(new Image(face.getImagePath()));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);

                    ManuscriptCardData manuscriptCardData = new ManuscriptCardData(placement.getX(), placement.getY());
                    imageView.setUserData(manuscriptCardData);

                    grid.add(imageView, placement.getX(), placement.getY());

                    // playable positions
                    addValidPlacements(miniModel, placement, grid);
                }
            } else {
                for (Placement placement : manuscript.getPlacements()) {
                    ImageView imageView = new ImageView();
                    Face face = miniModel.getManuscriptsMap().get(username).getField()[placement.getX()][placement.getY()];
                    imageView.setImage(new Image(face.getImagePath()));
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);
                    imageView.toFront();
                    grid.add(imageView, placement.getX(), placement.getY());
                }
            }

            if (userTab.isPresent()) {
                ScrollPane scrollPane = (ScrollPane) userTab.get().getContent();
                scrollPane.setContent(grid);
                handleZoom(scrollPane, grid);
            }
        });

    }

    public void overwriteHand(MiniModel miniModel) {

        Platform.runLater(() -> {

            handCards.getChildren().clear();
            for (Card card : miniModel.getPlayer().getHand()) {
                ImageView newHandCard = new ImageView(new Image(card.getFront().getImagePath()));
                newHandCard.setFitHeight(100);
                newHandCard.setFitWidth(150);
                handleDragDetectedHand(newHandCard);
                handleOnClick(newHandCard);
                zoomCardOnHover(newHandCard, 1.3);
                HandCardData handCardData = new HandCardData(miniModel.getPlayer().getHand().indexOf(card), true);
                newHandCard.setUserData(handCardData);
                handCards.getChildren().add(newHandCard);
            }

        });

    }

    public void overwriteMarket(MiniModel miniModel) {

        Platform.runLater(() -> {

            HBox marketBox = this.marketResources;
            boolean isGold = false;
            boolean fromDeck;
            Image deckImage = new Image(miniModel.getMarket().getResourceDeck().getLast().getBack().getImagePath());

            marketResources.getChildren().clear();
            marketGolds.getChildren().clear();

            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 3; i++) {
                    if (i == 0) {
                        fromDeck = true;
                        ImageView marketRes = new ImageView(deckImage);
                        marketRes.setFitHeight(50);
                        marketRes.setFitWidth(75);
                        marketRes.setUserData(new MarketCardData(isGold, fromDeck, 0));
                        handleClickDetectedMarket(marketRes);
                        zoomCardOnHover(marketRes, 2.5);
                        marketBox.getChildren().add(marketRes);
                    } else {
                        fromDeck = false;
                        ImageView marketRes = new ImageView(new Image(miniModel.getMarket().getFaceUp(isGold)[i - 1].getFront().getImagePath()));
                        marketRes.setFitHeight(50);
                        marketRes.setFitWidth(75);
                        marketRes.setUserData(new MarketCardData(isGold, fromDeck, i - 1));
                        handleClickDetectedMarket(marketRes);
                        zoomCardOnHover(marketRes, 2.5);
                        marketBox.getChildren().add(marketRes);
                    }
                }
                marketBox = this.marketGolds;
                isGold = true;
                deckImage = new Image(miniModel.getMarket().getGoldDeck().getLast().getBack().getImagePath());
            }

        });

    }

    public void overwriteCounters(MiniModel miniModel) {

        Platform.runLater(() -> {

            counters.getChildren().clear();
            for (CornerSymbol cs : CornerSymbol.valuesList()) {
                if (cs.equals(CornerSymbol.BLACK) || cs.equals(CornerSymbol.EMPTY)) continue;
                TextField counter = new TextField(cs + " " + miniModel.getManuscript().getCounter(cs));
                counter.setEditable(false);
                counters.getChildren().add(counter);
            }

        });

    }

    private void addValidPlacements(MiniModel miniModel, Placement placement, GridPane grid){
        for(int i = -1; i <= 1; i = i + 2){
            for(int j = -1; j <= 1; j = j + 2){
                if(miniModel.getPlayer().getManuscript().isValidPlacement(placement.getX() + i, placement.getY() + j)){
                    ImageView imageView = new ImageView(new Image(getClass().getResource("/images/utility/validPlacement.png").toExternalForm()));
                    handleDropEventManuscript(imageView);
                    ManuscriptCardData manuscriptCardData = new ManuscriptCardData(placement.getX() + i, placement.getY() + j);
                    imageView.setUserData(manuscriptCardData);
                    imageView.toFront();
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);
                    grid.add(imageView, placement.getX() + i, placement.getY() + j);
                }
            }
        }
    }

   // public TextField getActionFeedback() {
      //  return actionFeedback;
    //}

}
