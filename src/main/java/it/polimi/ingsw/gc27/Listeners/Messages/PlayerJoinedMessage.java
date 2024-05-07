package it.polimi.ingsw.gc27.Listeners.Messages;

public class PlayerJoinedMessage extends Message{

    public PlayerJoinedMessage(String username) {
        super(username+ " joined the game");
    }
}
