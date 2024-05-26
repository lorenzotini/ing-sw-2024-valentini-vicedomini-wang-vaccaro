package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.UpdatePlayerStateMessage;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.MiniModel;
import it.polimi.ingsw.gc27.Model.States.*;

import java.io.Serializable;
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


    // TODO adesso il salto al player va a quello immediatamente successivo, ma andrebbe ciclato fino a trovarne uno connesso
    public void notifyChooseObjectiveState() {

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

    public void notifyEndOfTurnState(Player player) {

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

            if (!getNextOf(index, players).isDisconnected()) {  // the next player is connected

                getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index, players)));
                this.game.notifyObservers(updatePlayerStateMessage);

            } else { // the next player is disconnected --> skip him

                getNextOf(index + 1, players).setPlayerState(new PlayingState(getNextOf(index + 1, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index + 1, players)));
                this.game.notifyObservers(updatePlayerStateMessage);

            }

            if (twentyPointsReached) {
                lastRound = true; // once someone gets 20 points, only if the round is finished you can trigger the last round
            }

        } else {

            player.setPlayerState(new EndingState(player, this));
            updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(player));
            this.game.notifyObservers(updatePlayerStateMessage);

            if (!getNextOf(index, players).isDisconnected()) { // the next player is connected
                getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index, players)));
                this.game.notifyObservers(updatePlayerStateMessage);
            } else { // the next player is disconnected --> skip him
                getNextOf(index + 1, players).setPlayerState(new PlayingState(getNextOf(index + 1, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index + 1, players)));
                this.game.notifyObservers(updatePlayerStateMessage);
            }

        }

    }

    public void notifyCalculateObjectivePoints(Player player) {

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

        //TODO terminare la partita e notificare i giocatori e il vincitore

    }

    // TODO controllare che non ci siano problemi di concorrenza
//    public void revivePlayer(Player player){
//        player.setDisconnected(false);
//    }

    public void handleDisconnection(Player player, GameController gc) {

        PlayerState state = player.getPlayerState();

        if(state instanceof InitializingState || state instanceof ChooseObjectiveState){
            System.out.println("UCCIDERE LA PARTITAAAAA");
            //gc.endGame();
        } else if(state instanceof PlayingState) {
            player.setPlayerState(new EndOfTurnState(player, this));
        }

    }

    /**
     * @param index
     * @param players
     * @return
     * @requires index < players.size()
     */
    private Player getNextOf(int index, List<Player> players) {
        if (index + 1 < players.size()) {
            return players.get(index + 1);
        } else {
            return players.getFirst();
        }
    }

}