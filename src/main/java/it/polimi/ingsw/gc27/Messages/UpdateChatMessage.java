package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.Game.Chat;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateChatMessage extends Message{

    public UpdateChatMessage(Chat chat){
        super(new MiniModel(chat));
    }
    public UpdateChatMessage(Chat chat, Player player, String receiver){
        super(new MiniModel(chat, player, receiver));
    }

    @Override
    public void reportUpdate(VirtualView client, View view)  {

        try{
        Chat chat2 = client.getMiniModel().getChat(this.getMiniModel().getChat().getFirst().getChatters());
//        if(chat2 == null) {
//            System.out.println("errore dei personaggi della chat");
//        }else{

        client.getMiniModel().getChat().remove(chat2);
        client.getMiniModel().getChat().add(this.getMiniModel().getChat().getFirst());
//        }
//        for(ChatMessage mess : this.getMiniModel().getChat().getFirst().getChat()){
//            System.out.println("Sender:"+mess.getSender() + "\n");
//            System.out.println("Receiver:"+mess.getReceiver() + "\n");
//            System.out.println(mess.getContent() + "\n");
//        }
          }catch(RemoteException e){

        }

    }
}
