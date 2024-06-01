package it.polimi.ingsw.gc27.View.GUI;

import it.polimi.ingsw.gc27.View.Gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

//third.2 scene, will open after button "join game" (in ChooseGameNetScene) clicked,
//and gameID will be asked

public class JoinGameSceneController implements GenericController{
    private String m;

    public TextField idTextField;
    public Button sendGameIdButton;
    public Button backButton;

    public void sendGameId(ActionEvent event) throws IOException, InterruptedException {
        Gui.getInstance().stringFromSceneController(idTextField.getText());

//        m = Gui.getInstance().getMessagesReceived().take();
//
//        // the message received when the game id is not found
//        String gameNotFound = "\nGame not found. Please enter a valid game id or 'new' to start a new game";
//        // the message received when joining an already existing game
//        String joiningGame = "Joining game";
//
//
//        while (m.equals(gameNotFound)) { // continue the loop until a valid game id
//            System.out.println(m);
//            //visualizzo errore
//            m=Gui.getInstance().getMessagesReceived().take();
//        }
//        if (m.contains(joiningGame)) {
//            System.out.println(m);
//            // change the scene to LoginScene, where the player waits for the other players to join
//            Gui.getInstance().switchScene("/fxml/LoginScene.fxml" );
//        }

    }

    //players can change their minds and go back to chooseGameScene
    public void previousScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }//TODO: DOES NOT WORK PROPERLY, LOOK AT NOTE IN newGameSceneController TO KNOW WHY


    @Override
    public void receiveOk(String ackType) {
        Platform.runLater(() -> {
            try {
                // Cambia la scena qui
                Gui.getInstance().switchScene("/fxml/LoginScene.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void receiveKo(String ackType) {
        Platform.runLater(()->{
            try {
                Gui.getInstance().switchScene("/fxml/ErrorScene.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
