package it.polimi.ingsw.gc27.Net.Commands;


import it.polimi.ingsw.gc27.Controller.GigaController;

import java.io.IOException;
import java.io.Serializable;

public interface Command extends Serializable {

    void execute(GigaController gigaController) throws IOException, InterruptedException;

}
