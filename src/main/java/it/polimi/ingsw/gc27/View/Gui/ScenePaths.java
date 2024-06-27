package it.polimi.ingsw.gc27.View.Gui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This enum contains the paths to the FXML files of the various scenes.
 */
public enum ScenePaths {

    /**
     * The paths to the FXML files of the various scenes.
     */
    STARTER("/fxml/StarterScene.fxml"), //scene number 0
    CHOOSEGAME("/fxml/ChooseGameScene.fxml"), //scene number 1
    NEWGAME ("/fxml/NewGameScene.fxml"), //scene number 2.1
    JOINGAME ("/fxml/JoinGameScene.fxml"), //scene number 2.2
    LOGIN ("/fxml/LoginScene.fxml"), //scene number 3
    LOBBY ("/fxml/LobbyScene.fxml"), //scene number 5
    PLACESTARTER ("/fxml/PlaceStarterCardScene.fxml"), //scene number 5
    CHOOSEOBJ ("/fxml/ChooseObjectiveScene.fxml"), //scene number 5
    MANUSCRIPT ("/fxml/ManuscriptScene.fxml"),
    ENDING ("/fxml/EndingScene.fxml");

    private final String value;

    ScenePaths(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the enum.
     *
     * @return The value of the enum.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a list of all the values of the enum.
     *
     * @return A list of all the values of the enum.
     */
    public static List<String> valuesList() {
        return Arrays.stream(ScenePaths.values())
                .map(ScenePaths::getValue)
                .collect(Collectors.toList());
    }
}