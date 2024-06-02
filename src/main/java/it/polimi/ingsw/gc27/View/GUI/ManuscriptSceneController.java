package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.JsonParser;
import it.polimi.ingsw.gc27.Model.Card.Card;
import it.polimi.ingsw.gc27.Model.Card.GoldCard;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;


public class ManuscriptSceneController implements GenericController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView handCard1;
    @FXML
    private ImageView handCard2;
    @FXML
    private ImageView handCard3;
    @FXML
    private HBox handCards;
    @FXML
    private VBox counters;
    @FXML
    private HBox marketResources;
    @FXML
    private HBox marketGolds;
    @FXML
    private ImageView resourceDeck;
    @FXML
    private ImageView resource1;
    @FXML
    private ImageView resource2;
    @FXML
    private ImageView goldDeck;
    @FXML
    private ImageView gold1;
    @FXML
    private ImageView gold2;



    public void initialize() {

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

//        grid.setPrefHeight(4000);
//        grid.setPrefWidth(8000);
//        grid.setMaxHeight(4000);
//        grid.setMaxWidth(8000);
//
//        RowConstraints rowC = new RowConstraints();
//        ColumnConstraints columnC = new ColumnConstraints();
//
//        rowC.setValignment(VPos.CENTER);
//        columnC.setHalignment(HPos.CENTER);
//
//        grid.getRowConstraints().add(rowC);
//        grid.getColumnConstraints().add(columnC);

        scrollPane.setContent(grid);

        // TODO sostituire gli estremi dei for con Manuscript.FIELD_DIM
        // populate grid with imageViews
        boolean flag = true;
        for (int i = 0; i < 11; i++) {       // requires odd terminal value to work, especially for the j loop
            for (int j = 0; j < 11; j++) {
                if (flag) {

                    ImageView imageView = new ImageView();

                    // default image in order to make the imageView able to receive drag events
                    imageView.setImage(new Image("/images/cards/card1_back.png"));

                    // handle drag events
                    handleDropEvent(imageView);

                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);

                    grid.add(imageView, i, j);

                }
                flag = !flag;
            }
        }


        // populate hand with cards
        Card c1 = deckGold.get(0);
        Card c2 = deckGold.get(1);
        Card c3 = deckGold.get(3);
        handCard1.setImage(new Image(c1.getFront().getImagePath()));
        handCard2.setImage(new Image(c2.getFront().getImagePath()));
        handCard3.setImage(new Image(c3.getFront().getImagePath()));
        handleDragDetected(handCard1);
        handleDragDetected(handCard2);
        handleDragDetected(handCard3);

        // counters
        for (int i = 0; i < 7; i++) {
            TextField counter = new TextField(String.valueOf(i + 1));
            counter.setEditable(false);
            counters.getChildren().add(counter);
        }

        // market
        Card goldDeckCard = deckGold.get(4);
        Card gold1card = deckGold.get(5);
        Card gold2card = deckGold.get(6);
        goldDeck.setImage(new Image(goldDeckCard.getFront().getImagePath()));
        gold1.setImage(new Image(gold1card.getFront().getImagePath()));
        gold2.setImage(new Image(gold2card.getFront().getImagePath()));
        handleDragDetected(goldDeck);
        handleDragDetected(gold1);
        handleDragDetected(gold2);

        Card resourceDeckCard = deckResource.get(4);
        Card resource1card = deckResource.get(5);
        Card resource2card = deckResource.get(6);
        resourceDeck.setImage(new Image(resourceDeckCard.getFront().getImagePath()));
        resource1.setImage(new Image(resource1card.getFront().getImagePath()));
        resource2.setImage(new Image(resource2card.getFront().getImagePath()));
        handleDragDetected(resourceDeck);
        handleDragDetected(resource1);
        handleDragDetected(resource2);

        //marketGolds.alignmentProperty().setValue(javafx.geometry.Pos.CENTER);

    }

    void handleDropEvent(ImageView imgView) {

        // DRAG OVER
        imgView.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage() || db.hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        // DRAG DROPPED
        imgView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasImage()) {
                imgView.setImage(db.getImage());
            }
            event.consume();
        });

    }

    void handleDragDetected(ImageView imgView) {

        // DRAG DETECTED
        imgView.setOnDragDetected(event -> {
            Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putImage(imgView.getImage());
            db.setContent(cb);
            event.consume();
        });

    }

    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}


