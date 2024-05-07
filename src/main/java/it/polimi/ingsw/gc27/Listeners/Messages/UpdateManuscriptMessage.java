package it.polimi.ingsw.gc27.Listeners.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;

public class UpdateManuscriptMessage extends Message{
    public UpdateManuscriptMessage(MiniModel miniModel){
        super(miniModel, "Something changed in your manuscript!");
    }
}
