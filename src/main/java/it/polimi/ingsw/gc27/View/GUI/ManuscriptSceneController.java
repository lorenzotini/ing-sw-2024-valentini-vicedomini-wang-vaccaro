package it.polimi.ingsw.gc27.View.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;


public class ManuscriptSceneController implements GenericController{

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView handCard1;
    @FXML
    private ImageView handCard2;
    @FXML
    private ImageView handCard3;
    @FXML
    private ImageView manoProva;
    @FXML
    private ImageView manusProva;


    public void initialize(){

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        grid.setHgap(-35);
        grid.setVgap(-40);

        scrollPane.setContent(grid);

        // TODO sostituire gli estremi dei for con Manuscript.FIELD_DIM
        // populate grid with imageViews
        boolean flag = true;
        for(int i = 0; i < 11; i++){       // requires odd terminal value to work, especially for the j loop
            for (int j = 0; j < 11; j++) {
                if (flag) {

                    ImageView imageView = new ImageView();

                    // default image in order to make the imageView able to receive drag events
                    imageView.setImage(new Image("/images/cards/card1_back.png"));

                    // handle drag events
                    imageView.setOnDragOver(event -> {
                        Dragboard db = event.getDragboard();
                        if(db.hasImage() || db.hasFiles()){
                            event.acceptTransferModes(TransferMode.ANY);
                        }
                        event.consume();
                    });

                    imageView.setOnDragDropped(event -> {
                        Dragboard db = event.getDragboard();
                        if(db.hasImage()){
                            imageView.setImage(db.getImage());
                        }
                        event.consume();
                    });

                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);

                    grid.add(imageView, i, j);

                }
                flag = !flag;
            }
        }

    }

    @FXML
    void imageViewDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if(db.hasImage() || db.hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    @FXML
    void imageViewDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        if(db.hasImage()){
            manusProva.setImage(db.getImage());
            deleteImage();
        }
        event.consume();
    }

    @FXML
    void imageViewDragDetected(MouseEvent event){
        Dragboard db = manoProva.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putImage(manoProva.getImage());
        db.setContent(cb);
        event.consume();
    }

    void deleteImage(){
        manoProva.setImage(null);
    }

}


