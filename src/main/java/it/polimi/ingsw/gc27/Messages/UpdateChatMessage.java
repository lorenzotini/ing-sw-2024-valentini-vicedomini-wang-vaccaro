package it.polimi.ingsw.gc27.Messages;

import it.polimi.ingsw.gc27.Model.ClientClass.ClientChat;
import it.polimi.ingsw.gc27.Model.Game.Chat;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.View;

import java.rmi.RemoteException;

/**
 * The UpdateChatMessage class represents a message updating the chat state
 * It extends the {@link Message} class
 */
public class UpdateChatMessage extends Message {

    /**
     * constructor matching super {@link Message}
     *
     * @param chat global chat message
     */
    public UpdateChatMessage(Chat chat) {
        super(new MiniModel(chat));
    }

    /**
     * constructor matching super {@link Message}
     *
     * @param chat     private chat message
     * @param player   player sender
     * @param receiver player receiver
     */
    public UpdateChatMessage(Chat chat, Player player, String receiver) {
        super(new MiniModel(chat, player, receiver));
    }

    /**
     * Reports the update to the specified VirtualView and View
     * This method updates the chat state on the client side and displays it on the associated View
     *
     * @param client The VirtualView to report the update to.
     * @param view   The View to report the update to.
     */
    @Override
    public void reportUpdate(VirtualView client, View view) {

        try {
            if (this.getMiniModel().getChats().getFirst().getChatters().size() == 1) {
                client.getMiniModel().getChats().addFirst(this.getMiniModel().getChats().getFirst());
                client.getMiniModel().getChats().remove(1);
                view.show(client.getMiniModel().getChats().getFirst());
            } else {
                ClientChat miniModelChat = this.getMiniModel().getChats().getFirst();
                ClientChat chat2 = client.getMiniModel().getChat(miniModelChat.getChatters());
                client.getMiniModel().getChats().remove(chat2);
                client.getMiniModel().getChats().add(this.getMiniModel().getChats().getFirst());
                view.show(client.getMiniModel().getChats().getLast());
            }
        } catch (RemoteException e) {
            throw new RuntimeException();
        }

    }
}
