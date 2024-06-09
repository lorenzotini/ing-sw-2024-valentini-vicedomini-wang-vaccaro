package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

public class KoMessage extends Message {

    public KoMessage(String string) {
        super(string);
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        view.koAck(this.string);
    }

}
