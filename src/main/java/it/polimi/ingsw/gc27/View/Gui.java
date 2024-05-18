package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Controller.GigaController;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public class Gui implements View{
    //la gui è observer, riceve le notify delle scene che vengono cambiate e manda i messaggi al controller di quello che
    //viene cambiato (Come mandare i messaggi al controller?)
    @Override
    public void run() throws IOException {

    }

    @Override
    public void showString(String phrase) {

    }

    @Override
    public void show(ArrayList<ResourceCard> hand) {

    }

    @Override
    public void show(Manuscript manuscript) {

    }

    @Override
    public void show(Board board) {

    }

    @Override
    public void show(Market market) {

    }

//    @Override
//    public void startTheGame() {
//
//    }
}
