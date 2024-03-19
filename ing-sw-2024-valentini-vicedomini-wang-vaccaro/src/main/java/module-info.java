module it.polimi.ingsw.gc27 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens it.polimi.ingsw.gc27 to javafx.fxml;
    exports it.polimi.ingsw.gc27;
    exports it.polimi.ingsw.gc27.Deck;
    opens it.polimi.ingsw.gc27.Deck to javafx.fxml;
    exports it.polimi.ingsw.gc27.Card;
    opens it.polimi.ingsw.gc27.Card to javafx.fxml;
    exports it.polimi.ingsw.gc27.Game;
    opens it.polimi.ingsw.gc27.Game to javafx.fxml;
}