package it.polimi.ingsw.gc27.View.GUI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ScenePaths {

    STARTER("/fxml/StarterScene.fxml"), //scene number 0
    CHOSEGAME("/fxml/ChooseGameScene.fxml"), //scene number 1
    NEWGAME ("/fxml/NewGameScene.fxml"), //scene number 2.1
    JOINGAME ("/fxml/JoinGameScene.fxml"), //scene number 2.2
    LOGIN ("/fxml/LoginScene.fxml"), //scene number 3
    LOBBY ("/fxml/LobbyScene.fxml"), //scene number 5
    PLACESTARTER ("/fxml/PlaceStarterCardScene.fxml"), //scene number 5
    CHOOSEOBJ ("/fxml/ChooseObjectiveScene.fxml"), //scene number 5
    ERROR ("/fxml/ErrorScene.fxml"),
    MANUSCRIPT ("/fxml/ManuscriptScene.fxml"),
    ENDING ("/fxml/EndingScene.fxml");

    private final String value;

    ScenePaths(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static List<String> valuesList() {
        return Arrays.stream(ScenePaths.values())
                .map(ScenePaths::getValue)
                .collect(Collectors.toList());
    }
}