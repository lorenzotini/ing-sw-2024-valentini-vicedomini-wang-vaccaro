package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class MiniModel implements Serializable {

    private ClientManuscript manuscript;
    private ClientBoard board;
    private ClientMarket market;
    private ClientPlayer player;
    private ArrayList<ResourceCard> hand;
    public String currentPlayer;
    private ArrayList<String> otherPlayersUsernames = new ArrayList<>();
    final private ArrayList<ClientChat> chats = new ArrayList<>();
   // private ArrayList<Manuscript> manuscripts = new ArrayList<>();
    private HashMap<String, ClientManuscript> manuscriptsMap = new HashMap<>();

//    public MiniModel(ArrayList<Manuscript> manuscripts) {
//        this.manuscripts = manuscripts;
//    }

    // used when update the players manuscript
    public MiniModel(Player player, Manuscript manuscript) {
        this.manuscript = manuscript;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer = null;
        this.manuscriptsMap.put(player.getUsername(), manuscript);
    }

    // used when update the players objectiveCard
    public MiniModel(Player player) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = player.getHand();
        this.currentPlayer = null;
    }

    // used when the board update, so when points are added to a player's score
    public MiniModel(Board board) {
        this.manuscript = null;
        this.board = board;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    //used when the market is being updated
    public MiniModel(ClientMarket market ) {
        this.manuscript = null;
        this.board = null;
        this.market = market;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    public MiniModel(Player player, Board board) {
        this.manuscript = null;
        this.board = board;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer = null;
    }

    public MiniModel(Game game) {
        this.manuscript = null;
        this.board = game.getBoard();
        this.market = game.getMarket();
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
        this.manuscriptsMap.putAll(game.getPlayers().stream()
                .collect(Collectors.toMap(Player::getUsername, Player::getManuscript)));
    }


    // used to update player state
    public MiniModel(Player player, Game game) {
        this.manuscript = player.getManuscript();
        this.board = game.getBoard();
        this.market = game.getMarket();
        this.player = player;
        this.hand = player.getHand();
        this.currentPlayer = player.getUsername();
        this.chats.addAll(game.getChats(player));
        this.otherPlayersUsernames.addAll(game.getPlayers().stream()
                .map(Player::getUsername)
                .filter(username -> !username.equals(player.getUsername()))
                .collect(Collectors.toCollection(ArrayList<String>::new)));
        //this.manuscriptsMap.put(player.getUsername(), player.getManuscript());
        this.manuscriptsMap.putAll(game.getPlayers().stream()
                .collect(Collectors.toMap(Player::getUsername, Player::getManuscript)));
    }

    public MiniModel() {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    public MiniModel(Chat chat, Player player, String receiver) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer= receiver;
        this.chats.add(chat);
    }

    public MiniModel(Chat chat) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
        this.chats.add(chat);
    }

    public synchronized HashMap<String, ClientManuscript> getManuscriptsMap() {
        return manuscriptsMap;
    }

    public synchronized  void setManuscriptsMap(HashMap<String, ClientManuscript> manuscriptsMap) {
        this.manuscriptsMap = manuscriptsMap;
    }

    public synchronized ClientPlayer getPlayer() {
        return player;
    }

    public synchronized ClientMarket getMarket() {
        return market;
    }

    public synchronized ClientBoard getBoard() {
        return board;
    }

    public synchronized ClientManuscript getManuscript() {
        return manuscript;
    }

    public synchronized ArrayList<ResourceCard> getHand() {
        return hand;
    }

    public synchronized void setHand(ArrayList<ResourceCard> hand) {
        this.hand = hand;
    }

    public synchronized void setManuscript(ClientManuscript manuscript) {
        this.manuscript = manuscript;
    }

    public synchronized void setMarket(ClientMarket market) {
        this.market = market;
    }

    public synchronized void setBoard(ClientBoard board) {
        this.board = board;
    }

    public synchronized void setPlayer(ClientPlayer player) {
        this.player = player;
    }

    public synchronized void copy(MiniModel miniModel) {

        this.manuscript = miniModel.manuscript;
        this.board = miniModel.board;
        this.market = miniModel.market;
        this.player = miniModel.player;
        this.hand = miniModel.hand;
        this.chats.addAll(miniModel.chats);
        this.otherPlayersUsernames.addAll(miniModel.otherPlayersUsernames);
        this.manuscriptsMap.putAll(miniModel.manuscriptsMap);

    }

    public synchronized ClientChat getChat(ArrayList<String> chatters) {
        boolean flag;
        if (chatters.size() > 2) {
            return chats.getFirst();
        }
        for (ClientChat chat : this.chats) {
            if (chat.getChatters().size() < 3) {
                flag = true;
                for (String p : chatters) {
                    if (!(chat.contains(p))) {
                        flag = false;
                    }
                }
                if (flag) {
                    return chat;
                }
            }
        }
        return null;
    }

    public synchronized ArrayList<String> getOtherPlayersUsernames() {
        return otherPlayersUsernames;
    }


    public synchronized boolean checkOtherUsername(String user) { //verifichi che la stringa passata sia l'username di un altro giocatore
        return otherPlayersUsernames.contains(user);
    }

    public synchronized ArrayList<ClientChat> getChats() {
        return this.chats;
    }

    public synchronized ClientChat getChat(String person) {
        if (person.equalsIgnoreCase("global"))
            return chats.getFirst();
        for (ClientChat c : chats) {
            if (chats.getFirst() != c && c.getChatters().stream().toList().contains(person)) {
                return c;
            }
        }
        return null;
    }
}
