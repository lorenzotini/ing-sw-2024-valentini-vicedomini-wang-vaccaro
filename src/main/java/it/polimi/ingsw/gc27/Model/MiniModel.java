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


    public MiniModel(Player player, Manuscript manuscript){
        this.manuscript = manuscript;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer = null;
    }
    public MiniModel(Board board){
        this.manuscript = null;
        this.board = board;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }
    public MiniModel(Player player, ArrayList<ResourceCard> hand){
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = hand;
        this.currentPlayer = null;
    }
    public MiniModel(Market market){
        this.manuscript = null;
        this.board = null;
        this.market = market;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }
    public MiniModel(Game game){
        this.manuscript = null;
        this.board = game.getBoard();
        this.market = game.getMarket();
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }
    public MiniModel(Player currentPlayer) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = currentPlayer.getUsername();
    }
    public MiniModel(){
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

}

