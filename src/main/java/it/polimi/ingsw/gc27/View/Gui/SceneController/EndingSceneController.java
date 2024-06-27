package it.polimi.ingsw.gc27.View.Gui.SceneController;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Map;
import java.util.*;

/**
 * Controller for the ending scene displaying game results.
 */
public class EndingSceneController extends GenericController {

    /**
     * Label displaying the title for the winners section.
     */
    public Label winnerTitle;

    /**
     * Label displaying the username of the winner(s).
     */
    public Label winnerUsername;

    /**
     * Label displaying the username of the second place player.
     */
    public Label secondUsername;

    /**
     * Label displaying the username of the third place player.
     */
    public Label thirdUsername;

    /**
     * Label displaying the username of the fourth place player.
     */
    public Label fourthUsername;

    /**
     * Label displaying the points of the second place player.
     */
    public Label secondPoints;

    /**
     * Label displaying the points of the third place player.
     */
    public Label thirdPoints;

    /**
     * Label displaying the points of the fourth place player.
     */
    public Label fourthPoints;

    /**
     * Label displaying the highest score among the players.
     */
    public Label highestScore;

    /**
     * Initializes the controller by hiding labels for second, third, and fourth place players.
     */
    @FXML
    public void initialize() {
        secondPoints.setVisible(false);
        secondUsername.setVisible(false);
        thirdPoints.setVisible(false);
        thirdUsername.setVisible(false);
        fourthPoints.setVisible(false);
        fourthUsername.setVisible(false);
    }

    /**
     * Updates the labels to display the winners and their scores based on the provided score board.
     * It sorts the score board by score in descending order and displays the winners in the appropriate labels.
     *
     * @param scoreBoard a map containing player usernames as keys and their scores as values
     */
    public void changeWinnersLabel(Map<String, Integer> scoreBoard) {
        scoreBoard.remove(null);
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


        Map.Entry<String, Integer> entry = sortedScoreBoard.entrySet().iterator().next();
        sb.append(entry.getKey());

        sortedScoreBoard.remove(entry.getKey(), entry.getValue());

        highestScore.setText("Highest score: " + maxPoints + " pts");

        boolean moreThanOneWinner = false;


        while (sortedScoreBoard.entrySet().iterator().hasNext()) {
            entry = sortedScoreBoard.entrySet().iterator().next();
            if (entry.getValue().equals(maxPoints) && entry.getKey() != null) {
                sb.append(" and ").append(entry.getKey());
                sortedScoreBoard.remove(entry.getKey(), entry.getValue());
                moreThanOneWinner = true;
            } else {
                break;
            }
        }

        if (moreThanOneWinner) {
            winnerTitle.setText("The Winners are...");
        }
        winnerUsername.setText(sb.toString());

        if (sortedScoreBoard.entrySet().iterator().hasNext()) {
            entry = sortedScoreBoard.entrySet().iterator().next();
            if (entry.getKey() != null) {
                secondUsername.setText(entry.getKey() + ":");
                secondUsername.setVisible(true);

                secondPoints.setText(entry.getValue().toString() + " pts");
                secondPoints.setVisible(true);
            }
            sortedScoreBoard.remove(entry.getKey(), entry.getValue());
        }
        if (sortedScoreBoard.entrySet().iterator().hasNext()) {
            entry = sortedScoreBoard.entrySet().iterator().next();
            if (entry.getKey() != null) {
                thirdUsername.setText(entry.getKey() + ":");
                thirdUsername.setVisible(true);

                thirdPoints.setText(entry.getValue().toString() + " pts");
                thirdPoints.setVisible(true);
            }
            sortedScoreBoard.remove(entry.getKey(), entry.getValue());
        }
        if (sortedScoreBoard.entrySet().iterator().hasNext()) {
            entry = sortedScoreBoard.entrySet().iterator().next();
            if (entry.getKey() != null) {
                fourthUsername.setText(entry.getKey() + ":");
                fourthUsername.setVisible(true);

                fourthPoints.setText(entry.getValue().toString() + " pts");
                fourthPoints.setVisible(true);
            }
            sortedScoreBoard.remove(entry.getKey(), entry.getValue());
        }

    }

}
