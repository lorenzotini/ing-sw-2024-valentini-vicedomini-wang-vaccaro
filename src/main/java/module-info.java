module it.polimi.ingsw.gc27 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires json.simple;

    opens it.polimi.ingsw.gc27 to javafx.fxml;
    exports it.polimi.ingsw.gc27.Enumerations;
    exports it.polimi.ingsw.gc27;
    exports it.polimi.ingsw.gc27.Card;
    opens it.polimi.ingsw.gc27.Card to javafx.fxml;
    exports it.polimi.ingsw.gc27.Game;
    opens it.polimi.ingsw.gc27.Game to javafx.fxml;
    exports it.polimi.ingsw.gc27.Card.ObjectiveCard;
    opens it.polimi.ingsw.gc27.Card.ObjectiveCard to javafx.fxml;
    exports it.polimi.ingsw.gc27.Controller;
    opens it.polimi.ingsw.gc27.Controller to javafx.fxml;
}