package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.Game.Chat;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

public class UpdateChatMessage extends Message {

    public UpdateChatMessage(Chat chat) {
        super(new MiniModel(chat));
    }

    public UpdateChatMessage(Chat chat, Player player, String receiver) {
        super(new MiniModel(chat, player, receiver));
    }

    @Override
    public void reportUpdate(VirtualView client, View view) {

        try {
            if (this.getMiniModel().getChats().getFirst().getChatters().size() > 2) {
                client.getMiniModel().getChats().addFirst(this.getMiniModel().getChats().getFirst());
                client.getMiniModel().getChats().remove(1);
            } else {
                Chat miniModelChat = this.getMiniModel().getChats().getFirst();
                Chat chat2 = client.getMiniModel().getChat(miniModelChat.getChatters());
                client.getMiniModel().getChats().remove(chat2);
                client.getMiniModel().getChats().add(this.getMiniModel().getChats().getFirst());
            }
//        if(chat2 == null) {
//            System.out.println("errore dei personaggi della chat");
//        }else{
//        }
            for (ChatMessage mess : client.getMiniModel().getChats().getLast().getChatMessages()){
                System.out.println("Sender:" + mess.getSender().getUsername() + "\n");
                System.out.println("Receiver:" + mess.getReceiver().getUsername() + "\n");
                System.out.println(mess.getContent() + "\n");
            }
        } catch (RemoteException e) {
            throw new RuntimeException();
        }

    }
}
