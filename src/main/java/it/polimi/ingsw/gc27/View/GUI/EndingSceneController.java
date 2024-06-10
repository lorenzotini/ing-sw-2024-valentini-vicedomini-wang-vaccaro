package it.polimi.ingsw.gc27.View.GUI;

import javafx.scene.control.Label;
import javafx.application.Platform;


public class EndingSceneController implements GenericController{

    public Label winnerTitle;
    public Label winnerUsername;
    public Label secondUsername;
    public Label thirdUsername;
    public Label forthUsername;
    public Label secondPoints;
    public Label thirdPoints;
    public Label forthPoints;







    @Override
    public void receiveOk(String ackType) {
        Platform.runLater(() -> {

        });
    }

    @Override
    public void receiveKo(String ackType) {

    }
}
