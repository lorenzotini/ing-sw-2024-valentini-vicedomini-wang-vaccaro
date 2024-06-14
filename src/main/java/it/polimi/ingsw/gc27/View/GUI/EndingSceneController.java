package it.polimi.ingsw.gc27.View.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Map;


public class EndingSceneController implements GenericController{

    public Label winnerTitle;
    public Label winnerUsername;
    public Label secondUsername;
    public Label thirdUsername;
    public Label fourthUsername;
    public Label secondPoints;
    public Label thirdPoints;
    public Label fourthPoints;

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
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> points = new ArrayList<>();

        for(int i = 0; i<scoreBoard.size(); i++){
            int max = 0;
            String nameMax = null;

            for(Map.Entry<String,Integer> entry : scoreBoard.entrySet()){
                if(entry.getValue() >= max){
                    max = entry.getValue();
                    nameMax = entry.getKey();
                }
            }

            points.add(max);
            usernames.add(nameMax);

            scoreBoard.remove(nameMax);
        }

        boolean moreThanOneWinner = false;
        int maxPoints = points.getFirst();
        StringBuilder sb = new StringBuilder();

        // First cycle outside the while loop
        sb.append(usernames.getFirst());
        usernames.removeFirst();
        points.removeFirst();

        while(points.getFirst().equals(maxPoints)){
            sb.append(" and ").append(usernames.getFirst());
            usernames.removeFirst();
            points.removeFirst();
            moreThanOneWinner = true;
        }

        if (moreThanOneWinner){
            winnerTitle.setText("The Winners are...");
        }
        winnerUsername.setText(sb.toString());

        if(!usernames.isEmpty()){
            secondUsername.setText(usernames.getFirst());
            secondUsername.setVisible(true);
            usernames.removeFirst();
            secondPoints.setText(points.getFirst().toString());
            secondPoints.setVisible(true);
            points.removeFirst();
        }
        if(!usernames.isEmpty()){
            thirdUsername.setText(usernames.getFirst());
            thirdUsername.setVisible(true);
            usernames.removeFirst();
            thirdPoints.setText(points.getFirst().toString());
            thirdPoints.setVisible(true);
            points.removeFirst();
        }
        if(!usernames.isEmpty()){
            fourthUsername.setText(usernames.getFirst());
            fourthUsername.setVisible(true);
            usernames.removeFirst();
            fourthPoints.setText(points.getFirst().toString());
            fourthPoints.setVisible(true);
            points.removeFirst();
        }


    }

    @Override
    public void receiveOk(String ackType) {

    }

    @Override
    public void receiveKo(String ackType) {

    }
}
