package it.polimi.ingsw.gc27.Controller;

import it.polimi.ingsw.gc27.Messages.*;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Game.Board;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Model.ClientClass.MiniModel;
import it.polimi.ingsw.gc27.Model.States.*;

import java.io.Serializable;
import java.util.List;

/**
 * The TurnHandler class manages the flow and continuity of turns in the game.
 * It implements Serializable to allow for object serialization.
 */
public class TurnHandler implements Serializable {

    private final Game game;
    private boolean twentyPointsReached;
    private boolean lastRound;

    /**
     * constructor of the players' turn handler
     * @param game which game
     */
    public TurnHandler(Game game) {
        this.game = game;
        this.twentyPointsReached = false;
        this.lastRound = false;
    }

    /**
     * every player chooses the personal secret objective, then each notifies the turn handler using this method,
     * the last player to do so will set the first player to playing state and start the match
     */
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
                    Message updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(p,game.getBoard()));
                    this.game.notifyObservers(updatePlayerStateMessage);

                    Message yourTurnToPlayMessage = new YourTurnMessage(new MiniModel(p), "");
                    this.game.notifyObservers(yourTurnToPlayMessage);

                    for(Player otherPlayer: players) {
                        if( !otherPlayer.equals(p)){
                            String state = otherPlayer.getPlayerState().toString();
                            if(state.equalsIgnoreCase("WaitingState")){
                                WaitingState playerState = (WaitingState) otherPlayer.getPlayerState();
                                playerState.setCurrentPlayer(p);
                                UpdatePlayerStateMessage ok=new UpdatePlayerStateMessage(new MiniModel(otherPlayer));
                                this.game.notifyObservers(ok);
                            }
                        }
                    }

                    break;
                }else{
                    i++;
                }
            }while(i < players.size());
        }
    }

    /**
     * this method will be executed at the end of a player's turn, it will check the end game points threshold,
     * handle the last turn and handle the turn passing
     * @param player which player ended the turn
     * @throws InterruptedException exception
     */
    public void notifyEndOfTurnState(Player player) throws InterruptedException {

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
            WaitingState waitingState=new WaitingState(players.get(index), this);
            players.get(index).setPlayerState(waitingState);

            updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(player, game.getBoard()));
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
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index + i, players),game.getBoard()));
                this.game.notifyObservers(updatePlayerStateMessage);
                Message yourTurnToPlayMessage = new YourTurnMessage(new MiniModel(getNextOf(index+i, players)), "");
                this.game.notifyObservers(yourTurnToPlayMessage);
                // other players are updated about the current player
                for(Player otherPlayers: players){
                    if( !otherPlayers.equals(getNextOf(index, players))){
                        String state = otherPlayers.getPlayerState().toString();
                        if(state.equalsIgnoreCase("WaitingState")){
                            WaitingState playerState = (WaitingState) otherPlayers.getPlayerState();
                            playerState.setCurrentPlayer(getNextOf(index, players));
                            UpdatePlayerStateMessage ok=new UpdatePlayerStateMessage(new MiniModel(otherPlayers));
                            this.game.notifyObservers(ok);
                        }
                    }
                }

            } else { // the next player is connected

                getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index, players),game.getBoard()));
                this.game.notifyObservers(updatePlayerStateMessage);
                Message yourTurnToPlayMessage = new YourTurnMessage(new MiniModel(getNextOf(index,players)), "");
                this.game.notifyObservers(yourTurnToPlayMessage);
                // other players are updated about the current player
                for(Player otherPlayers: players){
                    if( !otherPlayers.equals(getNextOf(index, players))){
                        String state = otherPlayers.getPlayerState().toString();
                        if(state.equalsIgnoreCase("WaitingState")){
                            WaitingState playerState = (WaitingState) otherPlayers.getPlayerState();
                            playerState.setCurrentPlayer(getNextOf(index, players));
                            UpdatePlayerStateMessage ok=new UpdatePlayerStateMessage(new MiniModel(otherPlayers));
                            this.game.notifyObservers(ok);
                        }
                    }
                }


            }

            if (twentyPointsReached && game.getPlayers().getLast().equals(game.getPlayers().get(index))) {
                lastRound = true; // once someone gets 20 points, only if the round is finished you can trigger the last round
                Message lastRoundMessage = new StringMessage("It's the last turn!");
                this.game.notifyObservers(lastRoundMessage);
            }

        } else {

            players.get(index).setPlayerState(new EndingState(players.get(index), this));
            updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(player,game.getBoard()));
            this.game.notifyObservers(updatePlayerStateMessage);
            for(Player otherPlayers: players){
            }

            this.notifyCalculateObjectivePoints(players.get(index));

            if (getNextOf(index, players).isDisconnected()) { // the next player is disconnected --> find the next connected player

                int i = 0;
                while(getNextOf(index + i, players).isDisconnected()){
                    i++;
                }

                getNextOf(index + i, players).setPlayerState(new PlayingState(getNextOf(index + i, players), this));
                updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index + i, players),game.getBoard()));
                this.game.notifyObservers(updatePlayerStateMessage);
                Message yourTurnToPlayMessage = new YourTurnMessage(new MiniModel(getNextOf(index+i, players)), "");
                this.game.notifyObservers(yourTurnToPlayMessage);


            } else { // the next player is connected
                if(!(getNextOf(index, players).getPlayerState() instanceof EndingState)){
                    getNextOf(index, players).setPlayerState(new PlayingState(getNextOf(index, players), this));
                    updatePlayerStateMessage = new UpdatePlayerStateMessage(new MiniModel(getNextOf(index, players),game.getBoard()));
                    this.game.notifyObservers(updatePlayerStateMessage);
                    Message yourTurnToPlayMessage = new YourTurnMessage(new MiniModel(getNextOf(index,players)), "");
                    this.game.notifyObservers(yourTurnToPlayMessage);
                }
            }
        }
    }

    /**
     * this method is executed when every player is in EndingState
     * this method will calculate and add the points scored by Objective cards
     * then finally, will display to the user the winner/winners of this game
     * @param player which player
     */
    public void notifyCalculateObjectivePoints(Player player)  { // this method calculates the objective points and announces the winner/winners

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
                points = p.getSecretObjectives().getFirst().calculateObjectivePoints(p.getManuscript());
                game.addPoints(p, points); // adding secret obj points to the respective player
            }
            for (Player p: game.getPlayers()) {
                points = ((ObjectiveCard) game.getCommonObjective1()).calculateObjectivePoints(p.getManuscript());
                points += ((ObjectiveCard) game.getCommonObjective2()).calculateObjectivePoints(p.getManuscript());
                game.addPoints(p, points); // adding common obj points to the respective player
            }

            sendWinnersToClient();
            sendWinnersToClient();
        }
    }

    public void handleDisconnection(Player player, GameController gc) throws InterruptedException {

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
                updateObjectiveMessage = new UpdateObjectiveMessage(new MiniModel(player));
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
     * displays the winner/winners to the users, and the general scoreboard
     */
    public void sendWinnersToClient(){
        UpdateEndGameMessage winnerMessage = new UpdateEndGameMessage(new MiniModel(game.getBoard()));
        this.game.notifyObservers(winnerMessage);
    }

    /**
     * get the next player from the list of players
     * @param index index of the current player
     * @param players the list of players
     * @return the next player in line
     * @requires index < players.size()
     */
    private Player getNextOf(int index, List<Player> players) {
        if (index + 1 < players.size()) {
            return players.get(index + 1);
        } else {
            return players.get(index + 1 - players.size());
        }
    }

    /**
     * Gets the game which turns are handled by the TurnHandler
     * @return the game
     */
    public Game getGame() {
        return game;
    }
}