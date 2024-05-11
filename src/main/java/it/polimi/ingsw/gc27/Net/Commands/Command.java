package it.polimi.ingsw.gc27.Net.Commands;


import it.polimi.ingsw.gc27.Controller.GigaController;

import java.io.IOException;

public interface Command {
    void execute(GigaController gigaController) throws IOException, InterruptedException;
}
