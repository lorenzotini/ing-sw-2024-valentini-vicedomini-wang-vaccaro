package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Market;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public class Gui implements View{


    @Override
    public void run() throws IOException {
        //da qui inizia la partita vera e propria
    }

    @Override
    public void welcomePlayer(VirtualView client) throws IOException, InterruptedException {

    }

    @Override
    public void setClient(VirtualView client) {

    }

    @Override
    public void showString(String phrase) {

    }

    @Override
    public void show(ArrayList<ResourceCard> hand) {

    }

    @Override
    public void show(ObjectiveCard objectiveCard) {

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

    @Override
    public String read() {
        return "";
    }

}
