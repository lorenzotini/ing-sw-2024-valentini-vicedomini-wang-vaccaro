package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.Message;
import it.polimi.ingsw.gc27.Messages.UpdateObjectiveMessage;
import it.polimi.ingsw.gc27.Messages.UpdatePlayerStateMessage;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
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
            int i = 0;

            do {


                Player p = players.get(i);
                if(!p.isDisconnected()) {
                    p.setPlayerState(new PlayingState(p, this));
                    Message updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(p));
                    this.game.notifyObservers(updatePlayerStateMessage);
                    break;
                }else{
                    i++;
                }
            }while(i < players.size());
            //TODO fai un check se bisogna fare qualcosa in caso in cui i sia uguale alla size o se deve esser gestito bene
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

            if (getNextOf(index, players).isDisconnected()) {  // the next player is disconnected --> skip him

                int i = 0;
                while(getNextOf(index + i, players).isDisconnected()){
                    i++;
                    if(index == players.size()){
                        //supendGame();
                        return ;
                    }
                }

                getNextOf(index + i, players).setPlayerState(new PlayingState(getNextOf(index + i, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index + i, players)));
                this.game.notifyObservers(updatePlayerStateMessage);

            } else { // the next player is connected

                getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index, players)));
                this.game.notifyObservers(updatePlayerStateMessage);

            }

            if (twentyPointsReached) {
                lastRound = true; // once someone gets 20 points, only if the round is finished you can trigger the last round
            }

        } else {

            player.setPlayerState(new EndingState(player, this));
            updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(player));
            this.game.notifyObservers(updatePlayerStateMessage);

            if (getNextOf(index, players).isDisconnected()) { // the next player is disconnected --> find the next connected player

                int i = 0;
                while(getNextOf(index + i, players).isDisconnected()){
                    i++;
                }

                getNextOf(index + i, players).setPlayerState(new PlayingState(getNextOf(index + i, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index + i, players)));
                this.game.notifyObservers(updatePlayerStateMessage);

            } else { // the next player is connected

                getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index, players)));
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

    public void handleDisconnection(Player player, GameController gc) {

        String stateClassName = player.getPlayerState().getClass().getSimpleName();
        Message updateObjectiveMessage;

        switch(stateClassName){

            case "InitializingState":
                // automatically choose the starter card for the player
                player.addCard(game, player.getStarterCard(), player.getStarterCard().getFront(), Manuscript.FIELD_DIM / 2, Manuscript.FIELD_DIM / 2);
                player.setPlayerState(new ChooseObjectiveState(player, this));
//                Message updateManuscriptMessage = new UpdateManuscriptMessage(new MiniModel(player, player.getManuscript()));
//                this.getGame().notifyObservers(updateManuscriptMessage);

                // automatically choose the objective card for the player
                player.getSecretObjectives().remove(1);
                player.setPlayerState(new WaitingState(player, this));
//              updateObjectiveMessage = new UpdateObjectiveMessage(new MiniModel(player, player.getSecretObjectives().getFirst()));
//              this.getGame().notifyObservers(updateObjectiveMessage);
                this.notifyChooseObjectiveState();

                break;

            case "ChooseObjectiveState":
                // automatically choose the objective card for the player
                player.getSecretObjectives().remove(1);
                player.setPlayerState(new WaitingState(player, this));
                updateObjectiveMessage = new UpdateObjectiveMessage(new MiniModel(player, player.getSecretObjectives().getFirst()));
                this.getGame().notifyObservers(updateObjectiveMessage);
                this.notifyChooseObjectiveState();
                break;

            case "PlayingState":
                // set the player to end of turn state
                player.setPlayerState(new EndOfTurnState(player, this));
                break;

            case "DrawingState":
                // automatically draw a card for the player
                gc.drawCard(player, true, true, 0);
                player.setPlayerState(new EndOfTurnState(player, this));
                break;

            case "WaitingState":
                // do nothing
                break;

            default:
                break;
        }
        if(game.isSuspended()){
            gc.suspendGame();
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
            return players.get(index + 1 - players.size());
        }
    }

}