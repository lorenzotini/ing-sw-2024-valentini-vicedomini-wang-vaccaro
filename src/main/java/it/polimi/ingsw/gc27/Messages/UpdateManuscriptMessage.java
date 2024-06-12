package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateManuscriptMessage extends Message {
    //this minimodel's class have player, manuscript and the string set,
    //player is the only one that has to receive the message

    public UpdateManuscriptMessage(MiniModel miniModel) {
        super(miniModel, "Something changed in your manuscript!");
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {
        try{
            System.out.println("reportUpdate called");
            client.getMiniModel().setPlayer(miniModel.getPlayer());
            client.getMiniModel().setManuscript(miniModel.getPlayer().getManuscript());
            client.getMiniModel().setManuscriptsMap(miniModel.getManuscriptsMap());
            view.showString(string);
            view.show(client.getMiniModel().getPlayer().getManuscript());
//            // update other players manuscripts in view
//            for(String username : client.getMiniModel().getOtherPlayersUsernames()){
//                if(client.getMiniModel().getManuscriptsMap().get(username) != null){
//                    view.updateManuscriptOfOtherPlayer(client.getMiniModel().getManuscriptsMap().get(username), username);
//                }
//            }
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

}
