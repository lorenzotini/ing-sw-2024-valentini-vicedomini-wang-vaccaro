package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.View.Gui;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

import java.rmi.RemoteException;


public class ManuscriptSceneController implements GenericController {

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
//    @FXML
//    private ImageView scoreboard;


    public void init() {

        MiniModel miniModel;
        try {
            miniModel = Gui.getInstance().getClient().getMiniModel();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
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

                    // set image for the center starter card
                    if (i == Manuscript.FIELD_DIM / 2 && j == Manuscript.FIELD_DIM / 2) {
                        imageView.setImage(new Image(miniModel.getPlayer().getManuscript().getStarterFace().getImagePath()));
                    }

                    // playable positions
                    if (miniModel.getPlayer().getManuscript().isValidPlacement(i, j)){
                        imageView.setImage(new Image(getClass().getResource("/images/utility/validPlacement.png").toExternalForm()));
                        handleDropEvent(imageView);
                    }

                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);

                    grid.add(imageView, i, j);

                }
                flag = !flag;
            }
        }

        // populate hand with cards
        for (int i = 0; i < 3; i++) {
            ImageView handCard = new ImageView(new Image(miniModel.getPlayer().getHand().get(i).getFront().getImagePath()));
            handCard.setFitHeight(100);
            handCard.setFitWidth(150);
            handleDragDetected(handCard);
            zoomCardOnHover(handCard, 1.3);
            handCards.getChildren().add(handCard);
        }

        // market
        for (int i = 0; i < 3; i++) {
            if (i == 0){
                // resources
                ImageView marketResource = new ImageView(new Image(miniModel.getMarket().getResourceDeck().getLast().getBack().getImagePath()));
                marketResource.setFitHeight(50);
                marketResource.setFitWidth(75);
                handleDragDetected(marketResource);
                zoomCardOnHover(marketResource, 2.5);
                marketResources.getChildren().add(marketResource);
                // golds
                ImageView marketGold = new ImageView(new Image(miniModel.getMarket().getGoldDeck().getLast().getBack().getImagePath()));
                marketGold.setFitHeight(50);
                marketGold.setFitWidth(75);
                handleDragDetected(marketGold);
                zoomCardOnHover(marketGold, 2.5);
                marketGolds.getChildren().add(marketGold);
            } else {
                // resources
                ImageView marketResource = new ImageView(new Image(miniModel.getMarket().getFaceUp(false)[i-1].getFront().getImagePath()));
                marketResource.setFitHeight(50);
                marketResource.setFitWidth(75);
                handleDragDetected(marketResource);
                zoomCardOnHover(marketResource, 2.5);
                marketResources.getChildren().add(marketResource);
                // golds
                ImageView marketGold = new ImageView(new Image(miniModel.getMarket().getFaceUp(true)[i-1].getFront().getImagePath()));
                marketGold.setFitHeight(50);
                marketGold.setFitWidth(75);
                handleDragDetected(marketGold);
                zoomCardOnHover(marketGold, 2.5);
                marketGolds.getChildren().add(marketGold);
            }
        }

        // common objectives
        for (int i = 0; i < 2; i++) {
            ImageView commonObjective = new ImageView(new Image(miniModel.getMarket().getCommonObjectives().get(i).getFront().getImagePath()));
            commonObjective.setFitHeight(70);
            commonObjective.setFitWidth(105);
            zoomCardOnHover(commonObjective, 1.2);
            commonObjectives.getChildren().add(commonObjective);
        }

        // chat
        for(int i = 0; i < 5; i++){
            Tab chatTab = new Tab();
            ScrollPane chatContent = new ScrollPane();
            chatContent.setPrefHeight(Region.USE_COMPUTED_SIZE);
            chatContent.setPrefWidth(Region.USE_COMPUTED_SIZE);
            chatContent.setFitToWidth(true);
            chatContent.setFitToHeight(true);
            VBox chatMessages = new VBox();
            for(int j = 0; j < 20; j++){
                chatMessages.getChildren().add(new Text("Messaggio di prova" + j));
            }
            chatContent.setContent(chatMessages);
            chatTab.setText("Player " + i);
            chatTab.setContent(chatContent);
            chat.getTabs().add(chatTab);
        }

        // counters
        for (CornerSymbol cs : CornerSymbol.valuesList()) {
            if(cs.equals(CornerSymbol.BLACK) || cs.equals(CornerSymbol.EMPTY)) continue;
            TextField counter = new TextField(cs.toString() + " " + miniModel.getManuscript().getCounter(cs));
            counter.setEditable(false);
            counters.getChildren().add(counter);
        }

    }

    void handleDropEvent(ImageView imgView) {

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
                imgView.setImage(db.getImage());
                event.setDropCompleted(true);
            }
            event.consume();
        });

    }

    void handleDragDetected(ImageView imgView) {

        // DRAG DETECTED
        imgView.setOnDragDetected(event -> {
            Dragboard db = imgView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cb = new ClipboardContent();
            cb.putImage(imgView.getImage());
            db.setContent(cb);
            event.consume();
        });

        // DRAG DONE
        imgView.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                imgView.setImage(null);
            }
            event.consume();
        });

    }

    void zoomCardOnHover(ImageView imgView, double factor){

        double originalHeight = imgView.getFitHeight();
        double originalWidth = imgView.getFitWidth();

        imgView.setOnMouseEntered(event -> {
            imgView.setFitHeight(originalHeight*factor);
            imgView.setFitWidth(originalWidth*factor);
        });

        imgView.setOnMouseExited(event -> {
            imgView.setFitHeight(originalHeight);
            imgView.setFitWidth(originalWidth);
        });

    }

    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}


