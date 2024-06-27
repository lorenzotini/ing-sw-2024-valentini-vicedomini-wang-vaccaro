package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.ReconnectedPlayerMessage;
import it.polimi.ingsw.gc27.Model.ClientClass.ClientPlayer;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.PlayerListener;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;

/**
 * ReconnectPlayerCommand class represents a command to reconnect a disconnected player.
 * Implements the Command interface. {@link Command}
 */
public class ReconnectPlayerCommand implements Command{
    private final VirtualView client;
    private final Player player;

    /**
     * Constructs a ReconnectPlayerCommand with the specified client view and player
     * @param client the VirtualView associated with the player
     * @param player the player to be reconnected
     */
    public ReconnectPlayerCommand(VirtualView client, ClientPlayer player){
        this.client=client;
        this.player=(Player) player;
    }

    /**
     * Executes the ReconnectPlayerCommand to reconnect the player to the game.
     * Adds the player as an observer, updates their status, and sends a reconnection message to the client.
     * @param gc the GameController that controls the game
     */
    @Override
    public void execute(GameController gc)  {
        Game game = gc.getGame();

        game.addObserver(new PlayerListener(client, player));
        player.setDisconnected(false);

        // Update the client's miniModel with the player's data
        MiniModel miniModel = new MiniModel(player, game);
        Message message = new ReconnectedPlayerMessage(miniModel, "Welcome back!!");
        gc.getTurnHandler().handleReconnection(player);
        game.notifyObservers(message);

    }

    /**
     * Gets the name of the player who invoked this command
     * @return the name of the player
     */
    @Override
    public String getPlayerName() {
        return player.getUsername();
    }
}
