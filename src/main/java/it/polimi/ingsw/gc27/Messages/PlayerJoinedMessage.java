package it.polimi.ingsw.gc27.Messages;

public class PlayerJoinedMessage extends Message{

    public PlayerJoinedMessage(String username) {
        super(username+ " joined the game");
    }
}
