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
    public void reportUpdate(VirtualView client, View view) throws RemoteException {

        Chat chat2 = client.getMiniModel().getChat(this.getMiniModel().chat.getFirst().getChatters());
        if(chat2 == null) {
            System.out.println("errore dei personaggi della chat");
        }else{
            client.getMiniModel().chat.remove(chat2);
            client.getMiniModel().chat.add(this.getMiniModel().chat.getFirst());
        }
        for(ChatMessage mess : this.getMiniModel().chat.getFirst().getChat()){
            System.out.println(mess.getContent() + "\n");
        }

    }
}
