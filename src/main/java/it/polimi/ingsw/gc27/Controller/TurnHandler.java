package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.UpdatePlayerStateMessage;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Model.States.EndingState;
import it.polimi.ingsw.gc27.Model.States.PlayingState;
import it.polimi.ingsw.gc27.Model.States.WaitingState;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class TurnHandler implements Serializable {

    private Game game;
    private boolean twentyPointsReached;
    private boolean lastRound;

    //constructor
    public TurnHandler(Game game) {
        this.game = game;
        this.twentyPointsReached = false;
        this.lastRound = false;
    }

    public Game getGame() {
        return game;
    }

    public void notifyChooseObjectiveState() throws RemoteException {

        boolean everyoneReady = true;

        List<Player> players = game.getPlayers();

        for (Player p : players) {
            if (!(p.getPlayerState() instanceof WaitingState)) {
                everyoneReady = false;
                break;
            }
        }

        if (everyoneReady) {
            Player p = players.getFirst();
            p.setPlayerState(new PlayingState(p, this));
            Message updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(p));
            this.game.notifyObservers(updatePlayerStateMessage);
        }

    }

    public void notifyEndOfTurnState(Player player) throws RemoteException {

        List<Player> players = game.getPlayers();
        Board board = game.getBoard();
        Message updatePlayerStateMessage;

        // in case someone triggers the 20 points threshold
        if (board.getPointsBluePlayer() >= Board.END_GAME_THRESHOLD ||
                board.getPointsRedPlayer() >= Board.END_GAME_THRESHOLD ||
                board.getPointsYellowPlayer() >= Board.END_GAME_THRESHOLD ||
                board.getPointsGreenPlayer() >= Board.END_GAME_THRESHOLD) {
            this.twentyPointsReached = true;
        }

        // what happens if it's the last round or not
        int index = players.indexOf(player); // index of the player

        if (!lastRound) {

            // Set to waiting state the player that just finished his turn
            player.setPlayerState(new WaitingState(player, this));
            updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(player));
            this.game.notifyObservers(updatePlayerStateMessage);

            if (getNextOf(index, players) != null) {

                getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index, players)));
                this.game.notifyObservers(updatePlayerStateMessage);

            } else {

                players.getFirst().setPlayerState(new PlayingState(players.getFirst(), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(players.getFirst()));
                this.game.notifyObservers(updatePlayerStateMessage);

                if (twentyPointsReached) {
                    lastRound = true; // once someone gets 20 points, only if the round is finished you can trigger the last round
                }

            }

        } else {

            player.setPlayerState(new EndingState(player, this));
            updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(player));
            this.game.notifyObservers(updatePlayerStateMessage);

            if (getNextOf(index, players) != null) {
                getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
            }

        }

    }

    public void notifyCalculateObjectivePoints(Player player) throws RemoteException {

        // verify that every player is in the ending state
        int points;
        boolean everyPlayerEndingState = true;

        for (Player p : game.getPlayers()) {
            if (!(p.getPlayerState() instanceof EndingState)) {
                everyPlayerEndingState = false;
                break;
            }
        }

        // if everyone in ending state then start the objective points calculation
        if (everyPlayerEndingState) {
            for (Player p : game.getPlayers()) {
                points = p.getSecretObjectives().getFirst().calculateObjectivePoints(player.getManuscript());
                game.addPoints(player, points); // adding the points to the respective player
            }
        }

    }

    private Player getNextOf(int index, List<Player> players) {
        if (index + 1 < players.size()) {
            return players.get(index + 1);
        } else {
            return null;
        }
    }

}
