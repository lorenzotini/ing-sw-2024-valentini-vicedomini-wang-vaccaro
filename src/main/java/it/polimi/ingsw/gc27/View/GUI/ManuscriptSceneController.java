package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.Collections;


public class ManuscriptSceneController implements GenericController {

    @FXML
    private ScrollPane scrollPane;
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


    public void initialize() {

        // shuffle decks
        ArrayList<StarterCard> deckStarter = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> deckResource = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ArrayList<GoldCard> deckGold = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> deckobjective = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        Collections.shuffle(deckStarter);
        Collections.shuffle(deckResource);
        Collections.shuffle(deckGold);
        Collections.shuffle(deckobjective);

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setHgap(-35);
        grid.setVgap(-40);

        scrollPane.setContent(grid);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // make the grid scalable
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

        // populate grid with imageViews
        boolean flag = true;
        for (int i = 0; i < Manuscript.FIELD_DIM; i++) {       // requires odd terminal value to work, especially for the j loop
            for (int j = 0; j < Manuscript.FIELD_DIM; j++) {
                if (flag) {

                    ImageView imageView = new ImageView();

                    // set image for the center starter card
                    if (i == Manuscript.FIELD_DIM / 2 && j == Manuscript.FIELD_DIM / 2) {
                        imageView.setImage(new Image(deckStarter.get(0).getFront().getImagePath()));
                    }

                    // playable positions
                    // TODO implement isValidPlacement
                    if ((i == Manuscript.FIELD_DIM / 2 - 1 || i == Manuscript.FIELD_DIM / 2 + 1) && (j == Manuscript.FIELD_DIM / 2 - 1 || j == Manuscript.FIELD_DIM / 2 + 1)) {
                        imageView.setImage(new Image(getClass().getResource("/images/utility/validPlacement.png").toExternalForm()));
                        // handle drag events
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
            ImageView handCard = new ImageView(new Image(deckGold.get(i).getFront().getImagePath()));
            handCard.setFitHeight(100);
            handCard.setFitWidth(150);
            handleDragDetected(handCard);
            zoomCardOnHover(handCard, 1.3);
            handCards.getChildren().add(handCard);
        }

        // market
        // TODO controllare che vengano caricate in ordine e non al contrario
        // TODO sembra che le carte non vengano tolte dal mazzo, capire perché
        StackPane resourceDeckStackPane = new StackPane();
        resourceDeckStackPane.setAlignment(Pos.TOP_CENTER);
        for(Card card : deckResource) {
            ImageView resourceDeckCard = new ImageView(new Image(card.getBack().getImagePath()));
            resourceDeckCard.setFitHeight(50);
            resourceDeckCard.setFitWidth(75);
            handleDragDetected(resourceDeckCard);
            zoomCardOnHover(resourceDeckCard, 2.5);
            resourceDeckStackPane.getChildren().add(resourceDeckCard);
        }
        marketResources.getChildren().add(resourceDeckStackPane);

        StackPane goldDeckStackPane = new StackPane();
        goldDeckStackPane.setAlignment(Pos.TOP_CENTER);
        for(Card card : deckGold) {
            ImageView goldDeckCard = new ImageView(new Image(card.getBack().getImagePath()));
            goldDeckCard.setFitHeight(50);
            goldDeckCard.setFitWidth(75);
            handleDragDetected(goldDeckCard);
            zoomCardOnHover(goldDeckCard, 2.5);
            goldDeckStackPane.getChildren().add(goldDeckCard);
        }
        marketGolds.getChildren().add(goldDeckStackPane);

        for (int i = 0; i < 2; i++) {
            // resources
            ImageView marketResource = new ImageView(new Image(deckResource.get(i).getFront().getImagePath()));
            marketResource.setFitHeight(50);
            marketResource.setFitWidth(75);
            handleDragDetected(marketResource);
            zoomCardOnHover(marketResource, 2.5);
            marketResources.getChildren().add(marketResource);
            // golds
            ImageView marketGold = new ImageView(new Image(deckGold.get(i).getFront().getImagePath()));
            marketGold.setFitHeight(50);
            marketGold.setFitWidth(75);
            handleDragDetected(marketGold);
            zoomCardOnHover(marketGold, 2.5);
            marketGolds.getChildren().add(marketGold);
        }

        // common objectives
        for (int i = 0; i < 2; i++) {
            ImageView commonObjective = new ImageView(new Image(deckobjective.get(i).getFront().getImagePath()));
            commonObjective.setFitHeight(100);
            commonObjective.setFitWidth(150);
            zoomCardOnHover(commonObjective, 1.2);
            commonObjectives.getChildren().add(commonObjective);
        }

        // counters
        for (int i = 0; i < 7; i++) {
            TextField counter = new TextField(String.valueOf(i + 1));
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


