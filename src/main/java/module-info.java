module it.polimi.ingsw.gc27 {

    requires java.desktop;
    requires java.rmi;
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires javafx.media;
    requires org.controlsfx.controls;


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
    exports it.polimi.ingsw.gc27.View;
    exports it.polimi.ingsw.gc27.View.Gui;
    exports  it.polimi.ingsw.gc27.Messages;
    exports  it.polimi.ingsw.gc27.Net.Commands;
    opens it.polimi.ingsw.gc27.View;
    opens it.polimi.ingsw.gc27.View.Gui;
    exports it.polimi.ingsw.gc27.Model.ClientClass;
    opens it.polimi.ingsw.gc27.Model.ClientClass to javafx.fxml;
    exports it.polimi.ingsw.gc27.Model to java.rmi;
    exports it.polimi.ingsw.gc27.Utils;
    opens it.polimi.ingsw.gc27.Utils to javafx.fxml;
    exports it.polimi.ingsw.gc27.View.Gui.SceneController;
    opens it.polimi.ingsw.gc27.View.Gui.SceneController;
    exports it.polimi.ingsw.gc27.View.Tui;
    opens it.polimi.ingsw.gc27.View.Tui;

}