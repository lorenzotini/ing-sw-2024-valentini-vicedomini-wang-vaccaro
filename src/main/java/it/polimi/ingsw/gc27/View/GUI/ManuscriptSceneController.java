package it.polimi.ingsw.gc27.View.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
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

        scrollPane.setContent(grid);

//            FileInputStream inputstream;
//            try {
//                inputstream = new FileInputStream("C:\\Users\\loren\\Documents\\Studio\\ANNO_3\\Progetto_ing_soft\\ing-sw-2024-valentini-vicedomini-wang-vaccaro\\src\\main\\resources\\Images\\cards\\card5_front.png");
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            Image image = new Image(inputstream);

        boolean flag = true;
        for(int i = 0 ; i < 5 ; i++){
            for (int j = 0; j < 5; j++) {
                if (flag) {
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(300);
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

    @FXML
    void deleteImage(){
        manoProva.setImage(null);
    }

}


