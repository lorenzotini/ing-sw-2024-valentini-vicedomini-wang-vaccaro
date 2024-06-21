package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Map;
import java.util.*;


public class EndingSceneController implements GenericController{

    public Label winnerTitle;
    public Label winnerUsername;
    public Label secondUsername;
    public Label thirdUsername;
    public Label fourthUsername;
    public Label secondPoints;
    public Label thirdPoints;
    public Label fourthPoints;
    public Label highestScore;

    @FXML
    public void initialize(){
        secondPoints.setVisible(false);
        secondUsername.setVisible(false);
        thirdPoints.setVisible(false);
        thirdUsername.setVisible(false);
        fourthPoints.setVisible(false);
        fourthUsername.setVisible(false);
    }


    public void changeWinnersLabel(Map<String,Integer> scoreBoard){
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(scoreBoard.entrySet());

        // Sort the entries by value in descending order
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Create a new LinkedHashMap to maintain the sorted order
        Map<String, Integer> sortedScoreBoard = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedScoreBoard.put(entry.getKey(), entry.getValue());
        }
        int maxPoints = sortedScoreBoard.values().iterator().next();
        StringBuilder sb = new StringBuilder();


        Map.Entry<String,Integer> entry = sortedScoreBoard.entrySet().iterator().next();
        sb.append(entry.getKey());

        sortedScoreBoard.remove(entry.getKey(),entry.getValue());

        highestScore.setText("Highest score: " + maxPoints + " pts");

        boolean moreThanOneWinner = false;


        while(sortedScoreBoard.entrySet().iterator().hasNext()){
            entry = sortedScoreBoard.entrySet().iterator().next();
            if(entry.getValue().equals(maxPoints)){
                sb.append(" and ").append(entry.getKey());
                sortedScoreBoard.remove(entry.getKey(), entry.getValue());
                moreThanOneWinner = true;
            } else {
                break;
            }
        }

        if (moreThanOneWinner){
            winnerTitle.setText("The Winners are...");
        }
        winnerUsername.setText(sb.toString());

        if(sortedScoreBoard.entrySet().iterator().hasNext()){
            entry = sortedScoreBoard.entrySet().iterator().next();
            if(entry.getKey() != null) {
                secondUsername.setText(entry.getKey() + ":");
                secondUsername.setVisible(true);

                secondPoints.setText(entry.getValue().toString() + " pts");
                secondPoints.setVisible(true);
            }
            sortedScoreBoard.remove(entry.getKey(), entry.getValue());
        }
        if(sortedScoreBoard.entrySet().iterator().hasNext()){
            entry = sortedScoreBoard.entrySet().iterator().next();
            if(entry.getKey() != null) {
                thirdUsername.setText(entry.getKey() + ":");
                thirdUsername.setVisible(true);

                thirdPoints.setText(entry.getValue().toString() + " pts");
                thirdPoints.setVisible(true);
            }
            sortedScoreBoard.remove(entry.getKey(), entry.getValue());
        }
        if(sortedScoreBoard.entrySet().iterator().hasNext()){
            entry = sortedScoreBoard.entrySet().iterator().next();
            if(entry.getKey() != null) {
                fourthUsername.setText(entry.getKey() + ":");
                fourthUsername.setVisible(true);

                fourthPoints.setText(entry.getValue().toString() + " pts");
                fourthPoints.setVisible(true);
            }
            sortedScoreBoard.remove(entry.getKey(), entry.getValue());
        }

    }


    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void overwriteChat(ClientChat chat, MiniModel minimodel) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}
