package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Placement;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.Commands.AddCardCommand;
import it.polimi.ingsw.gc27.Net.Commands.Command;
import it.polimi.ingsw.gc27.Net.Commands.DrawCardCommand;
import it.polimi.ingsw.gc27.View.GUI.UserData.HandCardData;
import it.polimi.ingsw.gc27.View.GUI.UserData.ManuscriptCardData;
import it.polimi.ingsw.gc27.View.GUI.UserData.MarketCardData;
import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
        for(Map.Entry<String, Manuscript> element :  miniModel.getManuscriptsMap().entrySet()){
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
        for (int i = 0; i < 5; i++) {
            Tab chatTab = new Tab();
            ScrollPane chatContent = new ScrollPane();
            chatContent.setPrefHeight(Region.USE_COMPUTED_SIZE);
            chatContent.setPrefWidth(Region.USE_COMPUTED_SIZE);
            chatContent.setFitToWidth(true);
            chatContent.setFitToHeight(true);
            VBox chatMessages = new VBox();
            for (int j = 0; j < 20; j++) {
                chatMessages.getChildren().add(new Text("Messaggio di prova" + j));
            }
            chatContent.setContent(chatMessages);
            chatTab.setText("Player " + i);
            chatTab.setContent(chatContent);
            chat.getTabs().add(chatTab);
        }

        // counters
        overwriteCounters(miniModel);

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

    private void createManuscriptGrids(MiniModel miniModel){

        for(Map.Entry<String, Manuscript> element :  miniModel.getManuscriptsMap().entrySet()){

            GridPane grid = new GridPane();

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMaxWidth(150);
            columnConstraints.setMinWidth(150);
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(100);
            rowConstraints.setMinHeight(100);

            for(int i = 0; i < Manuscript.FIELD_DIM; i++){
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

            Manuscript manuscript = miniModel.getManuscriptsMap().get(username);

            GridPane grid = new GridPane();

            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setMaxWidth(150);
            columnConstraints.setMinWidth(150);
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMaxHeight(100);
            rowConstraints.setMinHeight(100);

            for(int i = 0; i < Manuscript.FIELD_DIM; i++){
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
                    ImageView imageView = new ImageView(new Image(getClass().getResource("/images/utility/validPlacement2.png").toExternalForm()));
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

    public TextField getActionFeedback() {
        return actionFeedback;
    }

}
