module it.polimi.ingsw.gc27 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires json.simple;
    requires java.rmi;

    opens it.polimi.ingsw.gc27 to javafx.fxml;
    exports it.polimi.ingsw.gc27.Model.Enumerations;
    exports it.polimi.ingsw.gc27;
    exports it.polimi.ingsw.gc27.Model.Card;
    opens it.polimi.ingsw.gc27.Model.Card to javafx.fxml;
    exports it.polimi.ingsw.gc27.Model.Game;
    opens it.polimi.ingsw.gc27.Model.Game to javafx.fxml;
    exports it.polimi.ingsw.gc27.Model.Card.ObjectiveCard;
    opens it.polimi.ingsw.gc27.Model.Card.ObjectiveCard to javafx.fxml;
    exports it.polimi.ingsw.gc27.Controller;
    opens it.polimi.ingsw.gc27.Controller to javafx.fxml;
    exports it.polimi.ingsw.gc27.Net;
    exports it.polimi.ingsw.gc27.Net.Rmi;
    opens it.polimi.ingsw.gc27.Model to javafx.fxml;
    exports  it.polimi.ingsw.gc27.Model;
    exports it.polimi.ingsw.gc27.Model.Listener to java.rmi;
    exports it.polimi.ingsw.gc27.View.GUI; //added for GUI
    exports it.polimi.ingsw.gc27.View;
    exports  it.polimi.ingsw.gc27.Messages;
    exports  it.polimi.ingsw.gc27.Net.Commands;
}