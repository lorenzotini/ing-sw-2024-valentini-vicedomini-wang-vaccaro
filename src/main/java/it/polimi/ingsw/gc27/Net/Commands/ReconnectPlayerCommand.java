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

public class ReconnectPlayerCommand implements Command{
    private VirtualView client;
    private Player player;

    public ReconnectPlayerCommand(VirtualView client, ClientPlayer player){
        this.client=client;
        this.player=(Player) player;
    }
    @Override
    public void execute(GameController gc)  {
        Game game = gc.getGame();


        game.addObserver(new PlayerListener(client, player));
        player.setDisconnected(false);


        // Update the client's miniModel with the player's data
        MiniModel miniModel = new MiniModel(player, game);
        Message message = new ReconnectedPlayerMessage(miniModel, "Welcome back!!");
        game.notifyObservers(message);
        gc.getTurnHandler().handleReconnection(player);
    }

    @Override
    public String getPlayerName() {
        return null;
    }
}
