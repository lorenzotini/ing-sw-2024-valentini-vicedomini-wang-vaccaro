package it.polimi.ingsw.gc27.Model;

import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.*;

import java.io.Serializable;
import java.util.ArrayList;

public class MiniModel implements Serializable {
    private final Manuscript manuscript;
    private final Board board;
    private final Market market;
    private final Player player;
    private final ArrayList<ResourceCard> hand;
    private final String currentPlayer;

    // used when update the players manuscript
    public MiniModel(Player player, Manuscript manuscript){
        this.manuscript = manuscript;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer = null;
    }

    // used when the board update, so when points are added to a player's score
    public MiniModel(Board board){
        this.manuscript = null;
        this.board = board;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    //used when the player's hand updates
    public MiniModel(Player player, ArrayList<ResourceCard> hand){
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = hand;
        this.currentPlayer = null;
    }

    //used when the market is being updated
    public MiniModel(Market market){
        this.manuscript = null;
        this.board = null;
        this.market = market;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    //
    public MiniModel(Game game){
        this.manuscript = null;
        this.board = game.getBoard();
        this.market = game.getMarket();
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    //used when you have to pass a message to a specific player
    public MiniModel(Player currentPlayer) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = currentPlayer.getUsername();
    }

    // not used
    public MiniModel(){
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

}

