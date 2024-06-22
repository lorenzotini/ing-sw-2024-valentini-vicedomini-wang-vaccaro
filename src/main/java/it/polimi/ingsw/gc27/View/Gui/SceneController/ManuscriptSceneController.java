package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientBoard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientManuscript;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Placement;
import it.polimi.ingsw.gc27.Net.Commands.AddCardCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.DrawCardCommand;
import it.polimi.ingsw.gc27.Net.Commands.SendMessageCommand;
import it.polimi.ingsw.gc27.View.Gui.Point;
import it.polimi.ingsw.gc27.View.Gui.UserData.HandCardData;
import it.polimi.ingsw.gc27.View.Gui.UserData.ManuscriptCardData;
import it.polimi.ingsw.gc27.View.Gui.UserData.MarketCardData;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ManuscriptSceneController implements GenericController {

    @FXML
    public Pane feedbackPane;
    @FXML
    public TextFlow feedbackTextFlow;
    @FXML
    public Text actionFeedback;
    @FXML
    private TextFlow errorPane;
    @FXML
    private Text errorText;
    @FXML
    private Circle circleChat;
    @FXML
    private GridPane scoreBoard;
    @FXML
    private TabPane manuscriptTabPane;
    @FXML
    private HBox handCards;
    @FXML
    private VBox counters;
    @FXML
    private Pane marketArea;
    @FXML
    private HBox marketResources;
    @FXML
    private HBox marketGolds;
    @FXML
    private VBox commonObjectives;
    @FXML
    private ImageView secretObjective;
    @FXML
    private TabPane chatTabPane;
    @FXML
    private TitledPane chatTitledPane;
    private final HashMap<Integer, Point> position = new HashMap<Integer, Point>();

    private final int CARD_WIDTH = 150;
    private final int CARD_HEIGHT = 100;

    //TODO vedere se è meglio mettere gli attributi privati che ora sono pubblici

    // attributes to handle addCard invocation
    private int handCardIndex;
    private boolean isFront;
    private int x;
    private int y;
    private ImageView manuscriptCard;
    private ImageView handCard;

    // attributes to handle drawCard invocation
    private ImageView marketCard;

    //there is a private hashmap for all the scenes where the chat is displayed
    private final HashMap<String, Tab> chatTabHashMap = new HashMap<>();
    private final HashMap<PawnColour, Integer> pawnColourIntegerHashMap = new HashMap<>();
    private final HashMap<PawnColour, ImageView> pawnColourImageViewHashMap = new HashMap<>();
    private final int PAWN_DIM = 50;



    public void init() {
        circleChat.setVisible(false);

        MiniModel miniModel;
        do {
            try {
                miniModel = Gui.getInstance().getClient().getMiniModel();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } while (miniModel.getMarket() == null);

        // initialize manuscripts' grid panes
        createManuscriptGrids(miniModel);
        createBoardGrids(miniModel);
        createActionFeedback();
        creareErrorPane();

        // populate manuscripts
        for(Map.Entry<String, ClientManuscript> element :  miniModel.getManuscriptsMap().entrySet()){
            overwriteManuscript(miniModel, element.getKey(), true);
        }

        // populate hand with cards
        overwriteHand(miniModel);

        // market
        marketArea.toFront();
        overwriteMarket(miniModel);

        // common objectives
        for (int i = 0; i < 2; i++) {
            ImageView commonObjective = new ImageView(new Image(getClass().getResource(miniModel.getMarket().getCommonObjectives().get(i).getFront().getImagePath()).toExternalForm()));
            commonObjective.setFitHeight(70);
            commonObjective.setFitWidth(105);
            zoomCardOnHover(commonObjective, 1.4);
            commonObjectives.getChildren().add(commonObjective);
        }

        // secret Objective
        secretObjective.setImage(new Image(getClass().getResource(miniModel.getPlayer().getSecretObjectives().getFirst().getFront().getImagePath()).toExternalForm()));
        zoomCardOnHover(secretObjective, 1.3);

        // counters
        overwriteCounters(miniModel);

        try {
            fullChatAllocate();
        } catch (RemoteException e) {
            throw new RuntimeException();
        }


    }
    public void creareErrorPane(){
        Platform.runLater(()->{
            errorPane.setMaxWidth(200);
            errorPane.setTextAlignment(TextAlignment.RIGHT);
            errorPane.toFront();

        });
    }

    public void createActionFeedback(){
        Platform.runLater(()->{
            actionFeedback.setText("so fast!");
            actionFeedback.getStyleClass().add("labelActionFeedback");
            feedbackTextFlow.setMaxWidth(400);
            feedbackTextFlow.setTextAlignment(TextAlignment.RIGHT);
            feedbackTextFlow.toFront();

        });
    }
    public void fullChatAllocate() throws RemoteException {
        MiniModel miniModel = Gui.getInstance().getClient().getMiniModel();
        String myUsername = miniModel.getPlayer().getUsername();
        for (ClientChat chat : miniModel.getChats()) {
            Tab tab = chatTabHashMap.get(chat.getChatters().stream()
                    .filter(user -> !user.equals(myUsername))
                    .toList().getFirst());
            for (ChatMessage message : chat.getChatMessages()) {
                Gui.getInstance().addLastChatMessage(message, tab);
            }
        }
    }

    public void chatInitManuscript() {
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
                chatTabHashMap.put("global", chatTab);
            } else {
                String myusername = miniModel.getPlayer().getUsername();
                String username = miniModel.getChats().get(i).getChatters().stream()
                        .filter(user -> !user.equals(myusername))
                        .toList().getFirst();
                chatTab.setText(username);
                chatTabHashMap.put(username, chatTab);
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
            VBox.setVgrow(chatContainer, Priority.ALWAYS);
            ScrollPane chatContent = new ScrollPane();
            chatContent.setFitToHeight(true);
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

            // expand chatContent
            VBox.setVgrow(chatContent, Priority.ALWAYS);
            VBox.setVgrow(sendMessage, Priority.NEVER);

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

        chatTitledPane.setOnMouseClicked(event->{
            Platform.runLater(()->{
                chatTitledPane.toFront();
                circleChat.setVisible(false);
            });
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

    /**
     * allows to send the message in the chat by clicking the "send" button
     * @param button
     * @param textField
     */
    void handleOnActionChat(Button button, TextField textField) {
        button.setOnAction(event -> {
            sendChatMessage();
            textField.clear();

        });
    }
    /**
     * allows to send the message in the chat by clicking the "enter" button on the keyboard
     * @param textField
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

    void handleOnClick(ImageView imgView) {
        imgView.setOnMouseClicked(event -> {
            String oldUrl = imgView.getImage().getUrl();
            if (((HandCardData) imgView.getUserData()).isFront()) {
                imgView.setImage(new Image(oldUrl.replace("front", "back")));
            } else {
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
            System.out.println("CLICK MARKET" + event);
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

    private void sendAddCardCommand() {
        String username;
        try {
            username = Gui.getInstance().getClient().getUsername();
            Command command = new AddCardCommand(username, this.handCardIndex, this.isFront, this.x, this.y);
            Gui.getInstance().getClient().sendCommand(command);
        } catch (IOException e) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
            System.out.println("\nFEEDBACK "+feedback);
            actionFeedback.setText(feedback);
            feedbackTextFlow.setTextAlignment(TextAlignment.RIGHT);

        });
    }

    @Override
    public void receiveKo(String ackType) {
        Platform.runLater(()->{;
            errorPane.setTextAlignment(TextAlignment.CENTER);
            errorText.getStyleClass().add("labelError");
            errorText.setText(ackType);
        });

    }

    /**
     * method implemented from {@link GenericController},
     * invoked by Gui when a message is sent by a player in the chat
     * @param chat
     * @param miniModel
     */
    public void overwriteChat(ClientChat chat, MiniModel miniModel) {
        Platform.runLater(() -> {
            String username = chat.getChatters().stream()
                    .filter(user -> !user.equals(miniModel.getPlayer().getUsername()))
                    .toList().getFirst();
            Tab tab = chatTabHashMap.get(username);
            if(!chat.getChatMessages().getLast().getSender().equals(miniModel.getPlayer().getUsername())){
                circleChat.setVisible(true);
            }

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

    private void createManuscriptGrids(MiniModel miniModel) {

        for (Map.Entry<String, ClientManuscript> element : miniModel.getManuscriptsMap().entrySet()) {

            GridPane grid = new GridPane();

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMaxWidth(CARD_WIDTH);
            columnConstraints.setMinWidth(CARD_WIDTH);
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(CARD_HEIGHT);
            rowConstraints.setMinHeight(CARD_HEIGHT);

            for (int i = 0; i < 85; i++) {
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

    public void createBoardGrids(MiniModel miniModel) {
        Platform.runLater(() -> {
            initializePoints();
            setBoard(miniModel);
        });

    }

    public void overwriteManuscript(MiniModel miniModel, String username, boolean newScene) {

        Platform.runLater(() -> {

            boolean isMyManuscript = miniModel.getPlayer().getUsername().equals(username);

            ClientManuscript manuscript = miniModel.getManuscriptsMap().get(username);

            GridPane grid = new GridPane();

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMaxWidth(CARD_WIDTH);
            columnConstraints.setMinWidth(CARD_WIDTH);
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(CARD_HEIGHT);
            rowConstraints.setMinHeight(CARD_HEIGHT);

            for (int i = 0; i < 85; i++) {
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
                    imageView.setImage(new Image(getClass().getResource(face.getImagePath()).toExternalForm()));
                    imageView.setFitHeight(CARD_HEIGHT);
                    imageView.setFitWidth(CARD_WIDTH);

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
                    imageView.setImage(new Image(getClass().getResource(face.getImagePath()).toExternalForm()));
                    imageView.setFitHeight(CARD_HEIGHT);
                    imageView.setFitWidth(CARD_WIDTH);
                    imageView.toFront();
                    grid.add(imageView, placement.getX(), placement.getY());
                }
            }

            if (userTab.isPresent()) {
                ScrollPane scrollPane = (ScrollPane) userTab.get().getContent();
                scrollPane.setContent(grid);
                handleZoom(scrollPane, grid);
            }

            // make the player's manuscript as first tab visualized
            if(newScene){
                manuscriptTabPane.getSelectionModel().select(manuscriptTabPane.getTabs().stream().filter(tab -> tab.getText().equals(miniModel.getPlayer().getUsername())).findFirst().get());
            }

        });

    }

    public void overwriteHand(MiniModel miniModel) {

        Platform.runLater(() -> {

            handCards.getChildren().clear();
            for (Card card : miniModel.getPlayer().getHand()) {
                ImageView newHandCard = new ImageView(new Image(getClass().getResource(card.getFront().getImagePath()).toExternalForm()));
                newHandCard.setFitHeight(CARD_HEIGHT);
                newHandCard.setFitWidth(CARD_WIDTH);
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
            Image deckImage = new Image(getClass().getResource(miniModel.getMarket().getResourceDeck().getLast().getBack().getImagePath()).toExternalForm());

            marketResources.getChildren().clear();
            marketGolds.getChildren().clear();

            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 3; i++) {
                    if (i == 0) {
                        fromDeck = true;
                        ImageView marketRes = new ImageView(deckImage);
                        marketRes.setFitHeight(marketBox.getPrefHeight());
                        marketRes.setFitWidth(marketBox.getPrefWidth() / 3);
                        marketRes.setUserData(new MarketCardData(isGold, fromDeck, 0));
                        handleClickDetectedMarket(marketRes);
                        zoomCardOnHover(marketRes, 2);
                        marketBox.getChildren().add(marketRes);
                    } else {
                        fromDeck = false;
                        ImageView marketRes = new ImageView(new Image(getClass().getResource(miniModel.getMarket().getFaceUp(isGold)[i - 1].getFront().getImagePath()).toExternalForm()));
                        marketRes.setFitHeight(marketBox.getPrefHeight());
                        marketRes.setFitWidth(marketBox.getPrefWidth() / 3);
                        marketRes.setUserData(new MarketCardData(isGold, fromDeck, i - 1));
                        handleClickDetectedMarket(marketRes);
                        zoomCardOnHover(marketRes, 2);
                        marketBox.getChildren().add(marketRes);
                    }
                }
                marketBox = this.marketGolds;
                isGold = true;
                deckImage = new Image(getClass().getResource(miniModel.getMarket().getGoldDeck().getLast().getBack().getImagePath()).toExternalForm());
            }

        });

    }

    public void overwriteCounters(MiniModel miniModel) {

        Platform.runLater(() -> {

            counters.getChildren().clear();

            for (CornerSymbol cs : CornerSymbol.valuesList()) {
                if (cs.equals(CornerSymbol.BLACK) || cs.equals(CornerSymbol.EMPTY)) continue;
                Label counter = new Label( "  -  " + miniModel.getManuscript().getCounter(cs));
                counter.setPrefHeight(60);
                counter.setFont(Font.font("Agency FB", 30));
                counter.setFont(Font.font("Agency FB", 30));
                counters.getChildren().add(counter);
            }

        });

    }

    private void addValidPlacements(MiniModel miniModel, Placement placement, GridPane grid) {
        for (int i = -1; i <= 1; i = i + 2) {
            for (int j = -1; j <= 1; j = j + 2) {
                if (miniModel.getPlayer().getManuscript().isValidPlacement(placement.getX() + i, placement.getY() + j)) {
                    ImageView imageView = new ImageView(new Image(getClass().getResource("/Images/utility/validPlacement.png").toExternalForm()));
                    handleDropEventManuscript(imageView);
                    ManuscriptCardData manuscriptCardData = new ManuscriptCardData(placement.getX() + i, placement.getY() + j);
                    imageView.setUserData(manuscriptCardData);
                    imageView.toFront();
                    imageView.setFitHeight(CARD_HEIGHT);
                    imageView.setFitWidth(CARD_WIDTH);
                    grid.add(imageView, placement.getX() + i, placement.getY() + j);
                }
            }
        }
    }

    public void initializePoints(){
        position.put(0, new Point(3,60));
        position.put(1, new Point(7,60));
        position.put(2, new Point(10,60));

        position.put(6, new Point(1,54));
        position.put(5, new Point(6,54));
        position.put(4, new Point(8,54));
        position.put(3, new Point(12,54));

        position.put(7, new Point(1,48));
        position.put(8, new Point(6,48));
        position.put(9, new Point(8,48));
        position.put(10, new Point(12,48));

        position.put(14, new Point(1,42));
        position.put(13, new Point(6,42));
        position.put(12, new Point(8,42));
        position.put(11, new Point(12,42));

        position.put(15, new Point(1,36));
        position.put(16, new Point(6,36));
        position.put(17, new Point(8,36));
        position.put(18, new Point(12,36));

        position.put(19, new Point(12,30));
        position.put(20, new Point(7,25));
        position.put(21, new Point(1,30));
        position.put(22, new Point(1,20));

        position.put(23, new Point(1,11));
        position.put(24, new Point(4,6));
        position.put(25, new Point(4,5));
        position.put(26, new Point(9,6));
        position.put(27, new Point(12,11));
        position.put(28, new Point(12,20));
        position.put(29, new Point(7,15));

    }
    public void setBoard(MiniModel miniModel){
        ImageView img = new ImageView(new Image(getClass().getResource(miniModel.getPlayer().getPawnColour().getPathImage()).toExternalForm()));

        img.setFitWidth(PAWN_DIM);
        img.setFitHeight(PAWN_DIM);
        Point score = position.get(0);
        scoreBoard.add(img, score.getx(),score.gety());

        score.incrementCount();
        pawnColourIntegerHashMap.put(miniModel.getPlayer().getPawnColour(), 0);
        pawnColourImageViewHashMap.put(miniModel.getPlayer().getPawnColour(), img);

        for(String username : miniModel.getOtherPlayersUsernames()){
            ClientBoard board = miniModel.getBoard();
            img = new ImageView(new Image(getClass().getResource(board.getColourPlayermap().get(username).getPathImage()).toExternalForm()));
            img.setFitWidth(PAWN_DIM);
            img.setFitHeight(PAWN_DIM);
            pawnColourIntegerHashMap.put(board.getColourPlayermap().get(username), 0);
            scoreBoard.add(img, score.getx(), score.gety() - score.getCount());

            pawnColourImageViewHashMap.put(board.getColourPlayermap().get(username), img);
        }
    }
    public void updateBoard(ClientBoard board){
        MiniModel miniModel;
        try {
            miniModel = Gui.getInstance().getClient().getMiniModel();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        updatePawn(miniModel.getPlayer().getUsername(), miniModel);
        for(String user : miniModel.getOtherPlayersUsernames()){
            updatePawn(user, miniModel);

        }
    }

    public void updatePawn(String username, MiniModel miniModel){

        PawnColour colour = miniModel.getBoard().getColourPlayermap().get(username);
        int actualScore = miniModel.getBoard().getScoreBoard().get(username);
        int oldScore = pawnColourIntegerHashMap.get(colour);
        if(actualScore != oldScore){
            Point newPoint = position.get(actualScore);

            ImageView imgView = pawnColourImageViewHashMap.get(colour);
            ImageView newImage = new ImageView(new Image(getClass().getResource(colour.getPathImage()).toExternalForm()));
            newImage.setFitWidth(PAWN_DIM);
            newImage.setFitHeight(PAWN_DIM);
            pawnColourImageViewHashMap.remove(colour);
            pawnColourImageViewHashMap.put(colour, newImage);
            imgView.setImage(null);

            Platform.runLater(()->{
                scoreBoard.add(newImage, newPoint.getx(), newPoint.gety() - newPoint.getCount());

            });

            newPoint.incrementCount();
            pawnColourIntegerHashMap.remove(colour);
            pawnColourIntegerHashMap.put(colour, actualScore);

        }

    }

}
