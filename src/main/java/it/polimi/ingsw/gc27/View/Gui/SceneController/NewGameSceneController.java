package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.View.Gui.Gui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;

import java.io.IOException;

//third.1 scene, will open after button "new game" (in ChooseGameNetScene) clicked,
//and number of players will be asked
public class NewGameSceneController extends GenericController{
    @FXML
    public CheckBox checkBox2;
    @FXML
    public CheckBox checkBox4;
    @FXML
    public CheckBox checkBox3;
    @FXML
    public Button go;
    public Button backButton;
    @FXML
    public CheckBox checkBox1;
    private String chosenButton;


    @FXML
    public void sendNumberOfPlayers() throws IOException {
        Gui.getInstance().stringFromSceneController("new");
        Gui.getInstance().stringFromSceneController(chosenButton);
        Gui.getInstance().switchScene( "/fxml/LoginScene.fxml");

    }
    private void handleOnKeyPress2(CheckBox checkBox2) {
        checkBox2.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendNumberOfPlayers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }
    private void handleOnKeyPress3(CheckBox checkBox3) {
        checkBox3.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendNumberOfPlayers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }
    private void handleOnKeyPress4(CheckBox checkBox4) {
        checkBox4.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    sendNumberOfPlayers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.consume();
            }
        });
    }

    public void chooseNumber(ActionEvent event) {
        if(event.getSource().equals(checkBox2)){
            handleOnKeyPress2(checkBox2);
            chosenButton="2";
            deselectOtherBoxes(checkBox2);
        }
        else if(event.getSource().equals(checkBox1)){
            chosenButton="1";
            deselectOtherBoxes(checkBox1);
        }
        else if(event.getSource().equals(checkBox3)){
            handleOnKeyPress3(checkBox3);
            chosenButton="3";
            deselectOtherBoxes(checkBox3);
        }
        else{
            deselectOtherBoxes(checkBox4);
            handleOnKeyPress4(checkBox4);
            chosenButton="4";
        }
    }

    public void deselectOtherBoxes(CheckBox checkBox){
        if(!checkBox.equals(checkBox1)){
            checkBox1.setSelected(false);
        }
        if(!checkBox.equals(checkBox2)){
            checkBox2.setSelected(false);
        }
        if(!checkBox.equals(checkBox3)){
            checkBox3.setSelected(false);
        }
        if(!checkBox.equals(checkBox4)){
            checkBox4.setSelected(false);
        }
    }

    //players can change their minds and go back to chooseGameScene
    public void previousScene(ActionEvent event) throws IOException {
        Gui.getInstance().switchScene("/fxml/ChooseGameScene.fxml");
    }

    @Override
    public void receiveOk(String ackType) { //shows the game id in the login scene
        LoginSceneController log= (LoginSceneController) Gui.getInstance().getControllerFromName("/fxml/LoginScene.fxml");
        log.getGameIDCreated().setVisible(true);
        log.getGameIDCreated().setText(ackType);
    }


}
