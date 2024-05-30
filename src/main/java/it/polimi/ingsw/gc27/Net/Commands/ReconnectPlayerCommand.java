package it.polimi.ingsw.gc27.Net.Commands;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.ReconnectedPlayerMessage;
import it.polimi.ingsw.gc27.Messages.UpdateStartOfGameMessage;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.Listener.PlayerListener;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Net.VirtualView;

import java.io.IOException;

public class ReconnectPlayerCommand implements Command{
    VirtualView client;
    Player player;

    public ReconnectPlayerCommand(VirtualView client, Player player){
        this.client=client;
        this.player=player;
    }
    @Override
    public void execute(GameController gc) throws IOException, InterruptedException {
        Game game = gc.getGame();
        game.addObserver(new PlayerListener(client, player));
        player.setDisconnected(false);

        // Update the client's miniModel with the player's data
        MiniModel miniModel = new MiniModel(player, game.getMarket(), game.getBoard());
        Message message = new ReconnectedPlayerMessage(miniModel, "Welcome back!!");
        game.notifyObservers(message);
    }

    @Override
    public String getPlayerName() {
        return null;
    }
}
