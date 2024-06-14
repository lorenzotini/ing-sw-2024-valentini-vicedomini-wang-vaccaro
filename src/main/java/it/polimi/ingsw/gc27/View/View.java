package it.polimi.ingsw.gc27.View;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientBoard;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientManuscript;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientMarket;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;
import java.util.ArrayList;

public interface View {
    void run() throws IOException, InterruptedException;

    void setClient(VirtualView client);

    //the idea is that every object will be showed in his own way,
    //and when the Gui will be implemented will be the same
    //I decided to use more method and not only one that takes the miniModel (which include everything)
    //because in this way when the  method show is called you know what has been updated and there is no need to reprint all
    void showString(String phrase);

    void show(ArrayList<ResourceCard> hand);

    void show(ObjectiveCard objectiveCard);

    void show(ClientManuscript manuscript);

    void show(ClientBoard board);

    void show(ClientMarket market);

    void updateManuscriptOfOtherPlayer(ClientManuscript manuscript, String username);
    void show(Chat chat);



    String read();

    void okAck(String string);

    void koAck(String string);

}