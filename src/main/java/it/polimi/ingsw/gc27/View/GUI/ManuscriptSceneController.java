package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.AddCardCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.DrawCardCommand;
import it.polimi.ingsw.gc27.Net.Commands.SendMessageCommand;
import it.polimi.ingsw.gc27.View.GUI.UserData.HandCardData;
import it.polimi.ingsw.gc27.View.GUI.UserData.ManuscriptCardData;
import it.polimi.ingsw.gc27.View.GUI.UserData.MarketCardData;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.rmi.RemoteException;


public class ManuscriptSceneController implements GenericController {


    @FXML
    public TextArea actionFeedback;
    @FXML
    private ScrollPane manuscriptScrollPane;
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
    private TabPane chat;

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

    public TextArea getActionFeedback() {
        return actionFeedback;
    }


    public void init() {

        MiniModel miniModel;
        try {
            miniModel = Gui.getInstance().getClient().getMiniModel();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        grid = new GridPane();
        grid.setGridLinesVisible(false);
        grid.setHgap(-35);
        grid.setVgap(-40);

        manuscriptScrollPane.setContent(grid);
        manuscriptScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        manuscriptScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        manuscriptScrollPane.setFitToWidth(true);
        manuscriptScrollPane.setFitToHeight(true);

        // make the grid scalable
        Scale scaleTransform = new Scale(1, 1, 0, 0);
        grid.getTransforms().add(scaleTransform);
        manuscriptScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
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

        // populate grid with imageViews
        boolean flag = true;
        for (int i = 1; i < Manuscript.FIELD_DIM-1; i++) {       // requires odd terminal value to work, especially for the j loop
            for (int j = 1; j < Manuscript.FIELD_DIM-1; j++) {
                if (flag) {

                    ImageView imageView = new ImageView();

                    ManuscriptCardData manuscriptCardData = new ManuscriptCardData(i, j);
                    imageView.setUserData(manuscriptCardData);

                    // set image for the center starter card
                    if (i == Manuscript.FIELD_DIM / 2 && j == Manuscript.FIELD_DIM / 2) {
                        imageView.setImage(new Image(miniModel.getPlayer().getManuscript().getStarterFace().getImagePath()));
                    }

                    // playable positions
                    if (miniModel.getPlayer().getManuscript().isValidPlacement(i, j)){
                        imageView.setImage(new Image(getClass().getResource("/images/utility/validPlacement2.png").toExternalForm()));
                        handleDropEventManuscript(imageView);
                        imageView.toFront();
                    }

                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);

                    grid.add(imageView, i, j);

                }
                flag = !flag;
            }
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
            Tab chatTab = new Tab();
            ScrollPane chatContent = new ScrollPane();
            TextField sendMessage = new TextField();

            VBox chatContainer = new VBox();

            chatContent.setPrefHeight(Region.USE_COMPUTED_SIZE);
            chatContent.setPrefWidth(Region.USE_COMPUTED_SIZE);
            chatContent.setFitToWidth(true);
            chatContent.setFitToHeight(true);
            VBox chatMessages = new VBox();
//            for (int j = 0; j < 20; j++) {
//                chatMessages.getChildren().add(new Text("Messaggio di prova" + j));
//            }

            HBox messageBox = new HBox();
            Button sendButton = new Button("Invia");
            sendMessage.setPromptText("Inserisci il tuo messaggio...");

            messageBox.getChildren().addAll(sendMessage, sendButton);
            messageBox.setSpacing(20);
            chatContent.setContent(chatMessages);

            sendButton.setOnAction(event -> {
                String message = sendMessage.getText();
                if (!message.isEmpty()) {
                    //chatMessages.getChildren().add(new Text(message));
                    sendChatMessage(miniModel.currentPlayer, message);
                    sendMessage.clear();
                }
            });

            // Set HBox growth for sendMessage
            HBox.setHgrow(sendMessage, Priority.ALWAYS);
            sendMessage.setMaxWidth(300);

            // Create a spacer
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            chatContainer.getChildren().addAll(chatContent, messageBox);


            chatTab.setText("Player " + i);
            //chatTab.setContent(chatContent);
            chatTab.setContent(chatContainer);
            chat.getTabs().add(chatTab);
        }

        // counters
        overwriteCounters(miniModel);

    }

    void sendChatMessage(String receiver, String content){
        try {
            Command command = new SendMessageCommand(Gui.getInstance().getClient().getMiniModel().getPlayer(), receiver, content);
            Gui.getInstance().getClient().sendCommand(command);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
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
            Image toAdd = new Image(imgView.getImage().getUrl(), 128*1.5, 128, false,false);

            //cb.putImage(imgView.getImage());

            cb.putImage(toAdd);
            db.setContent(cb);
            this.handCard = imgView;

            HandCardData handCardData = (HandCardData) imgView.getUserData();
            this.handCardIndex = handCardData.handIndex;
            this.isFront = handCardData.isFront;

            event.consume();
        });

        // DRAG DONE
        imgView.setOnDragDone(Event::consume);

    }

    void handleClickDetectedMarket(ImageView imgView) {

        // DRAG DETECTED
        imgView.setOnMouseClicked(event -> {
            this.marketCard = imgView;
            sendDrawCardCommand();
        });

    }

    void zoomCardOnHover(ImageView imgView, double factor) {

        double originalHeight = imgView.getFitHeight();
        double originalWidth = imgView.getFitWidth();

        imgView.setOnMouseEntered(event -> {
            imgView.setFitHeight(originalHeight * factor);
            imgView.setFitWidth(originalWidth * factor);
        });

        imgView.setOnMouseExited(event -> {
            imgView.setFitHeight(originalHeight);
            imgView.setFitWidth(originalWidth);
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

    public void overwriteHand(MiniModel miniModel) {

        Platform.runLater(() -> {

            handCards.getChildren().clear();
            for (Card card : miniModel.getPlayer().getHand()) {
                ImageView newHandCard = new ImageView(new Image(card.getFront().getImagePath()));
                newHandCard.setFitHeight(100);
                newHandCard.setFitWidth(150);
                handleDragDetectedHand(newHandCard);
                zoomCardOnHover(newHandCard, 1.3);
                // TODO gestire cambio faccia prima di giocare la carta
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

    public void overwriteCounters(MiniModel miniModel){

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

    public void setNewAvailablePositions() {

        Platform.runLater(() -> {
            MiniModel miniModel;
            try {
                miniModel = Gui.getInstance().getClient().getMiniModel();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            for (int i = -1; i <= 1; i = i + 2) {
                for (int j = -1; j <= 1; j = j + 2) {
                    if (miniModel.getPlayer().getManuscript().isValidPlacement(x + i, y + j)) {
                        ImageView imageView = getImageViewFromGrid(grid, x + i, y + j);
                        imageView.setImage(new Image(getClass().getResource("/images/utility/validPlacement2.png").toExternalForm()));
                        handleDropEventManuscript(imageView);
                        imageView.toFront();
                    }
                }
            }
        });

    }

    private ImageView getImageViewFromGrid(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            // Treat null values as 0
            if (rowIndex == null) {
                rowIndex = 0;
            }
            if (colIndex == null) {
                colIndex = 0;
            }
            if (rowIndex == row && colIndex == col && node instanceof ImageView) {
                return (ImageView) node;
            }
        }
        return null;
    }

}
