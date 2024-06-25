package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import it.polimi.ingsw.gc27.View.Gui.ScenePaths;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

//third.2 scene, will open after button "join game" (in ChooseGameNetScene) clicked,
//and gameID will be asked

public class JoinGameSceneController extends GenericController {
    @FXML
    public Label errorGameID;
    @FXML
    public TextField idTextField;
    @FXML
    public Button sendGameIdButton;
    private String tempId;

    @FXML
    public Button backButton;

    @FXML
    public void initialize() {
        backButton.setVisible(false);
        handleOnKeyPress(idTextField);
    }

    /**
     * allows to send the message in the chat by clicking the "enter" button on the keyboard
     *
     * @param textField
     */
    private void handleOnKeyPress(TextField textField) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendGameId();
                textField.clear();
                event.consume();
            }
        });
    }

    public void sendGameId() {
        Gui.getInstance().stringFromSceneController(idTextField.getText());
        tempId = idTextField.getText();
    }

    //players can change their minds and go back to chooseGameScene
    public void previousScene(MouseEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }

    @Override
    public void receiveOk(String ackType) {

        Platform.runLater(() -> {
            if (ackType.equals("validID")) {
                try {
                    // Cambia la scena qui
                    errorGameID.setVisible(false);
                    Gui.getInstance().switchScene("/fxml/LoginScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (ackType.equals("disconnectedPlayer")) {
                try {
                    LoginSceneController loginContr = (LoginSceneController) Gui.getInstance().getControllerFromName("/fxml/LoginScene.fxml");
                    loginContr.getGameIDCreated().setText("\nThis game has a disconnected player. Are you him? If so, please enter your username.");
                    loginContr.getGameIDCreated().setVisible(true);
                    loginContr.setGameId(tempId);
                    Gui.getInstance().switchScene("/fxml/LoginScene.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("\nMESSAGGIO NON TROVATO\n");
            }
        });
    }

    @Override
    public void receiveKo(String ackType) {
        Platform.runLater(() -> {
            backButton.setVisible(true);
            if (ackType.equals("invalidID")) {
                errorGameID.setText("THIS GAME ID DOES NOT EXIST! ASK YOUR FRIENDS AGAIN IF YOU WANT TO JOIN THEIR GAME");
                errorGameID.setVisible(true);
            } else if (ackType.equals("gameFull")) {
                errorGameID.setText("THIS GAME IS FULL! ");
                errorGameID.setVisible(true);
            } else if (ackType.equals("invalidFormatID")) {
                errorGameID.setText("INVALID FORMAT: PLEASE INSERT A NUMBER");
                errorGameID.setVisible(true);
            }
        });
    }
}
