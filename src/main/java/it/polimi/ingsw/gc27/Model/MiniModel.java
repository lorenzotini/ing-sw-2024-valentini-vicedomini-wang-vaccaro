package it.polimi.ingsw.gc27.Model;

import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.*;

import java.io.Serializable;
import java.util.ArrayList;

public class MiniModel implements Serializable {

    private Manuscript manuscript;
    private Board board;
    private Market market;
    private Player player;
    private ArrayList<ResourceCard> hand;
    public String currentPlayer;


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

    //associato ai messaggi di errore in cui currentPlayer è il giocatore da notificare che non ci sono stati cambiamenti
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

    public Player getPlayer() {
        return player;
    }

    public Market getMarket() {
        return market;
    }

    public Board getBoard() {
        return board;
    }

    public Manuscript getManuscript() {
        return manuscript;
    }

    public ArrayList<ResourceCard> getHand() {
        return hand;
    }
    public void setHand(ArrayList<ResourceCard> hand) {
        this.hand = hand;
    }

    public void setManuscript(Manuscript manuscript) {
        this.manuscript = manuscript;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void copy(MiniModel miniModel){
        this.manuscript = miniModel.manuscript ;
        this.board = miniModel.board;
        this.market = miniModel.market;
        this.player = miniModel.player;
        this.hand = miniModel.hand;
    }

}

