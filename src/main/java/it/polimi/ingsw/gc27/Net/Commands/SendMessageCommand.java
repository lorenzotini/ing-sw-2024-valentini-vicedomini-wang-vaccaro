package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientPlayer;
import it.polimi.ingsw.gc27.Model.Game.ChatMessage;
import it.polimi.ingsw.gc27.Model.Game.Player;

import java.io.IOException;

/**
 * SendMessageCommand class represents a command to send a chat message from one player to another.
 * Implements the Command interface. {@link Command}
 */
public class SendMessageCommand implements Command {
    private final String sender;
    private final String receiver;
    private final String content;

    /**
     * Constructs a SendMessageCommand with the specified sender, receiver, and content
     * @param p the player sending the message
     * @param receiver the receiver of the message
     * @param content the content of the message
     */
    public  SendMessageCommand(ClientPlayer p, String receiver, String content){
        this.sender = p.getUsername();
        this.receiver = receiver;
        this.content = content;
    }

    /**
     * Executes the SendMessageCommand to send the chat message
     * @param gc the GameController that controls the game
     */
    @Override
    public void execute(GameController gc) {
        gc.sendChatMessage(new ChatMessage(sender, receiver, this.content));
    }

    /**
     * Gets the name of the player who invoked this command
     * @return the name of the player
     */
    @Override
    public String getPlayerName() {
        return this.sender;
    }
}
