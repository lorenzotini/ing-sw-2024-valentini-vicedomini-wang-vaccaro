package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Game.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * MiniModel class represents a simplified model of the game state.
 * It includes the current player's state, the game board, market, manuscripts, chats, and other important information
 * needed to update the client's view of the game.
 */
public class MiniModel implements Serializable {

    private ClientManuscript manuscript;
    private ClientBoard board;
    private ClientMarket market;
    private ClientPlayer player;
    private ArrayList<ResourceCard> hand;
    public String currentPlayer;
    private ArrayList<String> otherPlayersUsernames = new ArrayList<>();
    final private ArrayList<ClientChat> chats = new ArrayList<>();
    private HashMap<String, ClientManuscript> manuscriptsMap = new HashMap<>();

    /**
     * Constructor for updating the player's manuscript.
     *
     * @param player     the player whose manuscript is being updated
     * @param manuscript the manuscript to update
     */
    public MiniModel(Player player, Manuscript manuscript) {
        this.manuscript = manuscript;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer = null;
        this.manuscriptsMap.put(player.getUsername(), manuscript);
    }

    /**
     * Constructor for updating the player's objective card
     *
     * @param player the player whose objective card is being updated
     */
    public MiniModel(Player player) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = player.getHand();
        this.currentPlayer = null;
    }

    /**
     * Constructor for updating the board when points are added to a player's score
     *
     * @param board the board to update
     */
    public MiniModel(Board board) {
        this.manuscript = null;
        this.board = board;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    /**
     * Constructor for updating the market
     *
     * @param market the market to update
     */
    public MiniModel(ClientMarket market) {
        this.manuscript = null;
        this.board = null;
        this.market = market;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    /**
     * Constructor for updating the player and the board
     *
     * @param player the player to update
     * @param board  the board to update
     */
    public MiniModel(Player player, Board board) {
        this.manuscript = null;
        this.board = board;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer = null;
    }

    /**
     * Constructor for initializing the model
     *
     * @param game the game to initialize
     */
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


    /**
     * Constructor for updating the player state
     *
     * @param player the player to update
     * @param game   the game to update
     */
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
        this.manuscriptsMap.putAll(game.getPlayers().stream()
                .collect(Collectors.toMap(Player::getUsername, Player::getManuscript)));
    }

    /**
     * Default constructor
     */
    public MiniModel() {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
    }

    /**
     * Constructor for updating the private chat between two players
     *
     * @param chat     the chat to update
     * @param player   the player to update
     * @param receiver the receiver of the chat
     */
    public MiniModel(Chat chat, Player player, String receiver) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = player;
        this.hand = null;
        this.currentPlayer = receiver;
        this.chats.add(chat);
    }

    /**
     * Constructor for updating the global chat
     *
     * @param chat the chat to update
     */
    public MiniModel(Chat chat) {
        this.manuscript = null;
        this.board = null;
        this.market = null;
        this.player = null;
        this.hand = null;
        this.currentPlayer = null;
        this.chats.add(chat);
    }

    /**
     * Gets the manuscripts map, which maps each player's username to their manuscript
     *
     * @return the manuscripts map
     */
    public synchronized HashMap<String, ClientManuscript> getManuscriptsMap() {
        return manuscriptsMap;
    }

    /**
     * Sets the manuscripts map
     *
     * @param manuscriptsMap the manuscripts map to set
     */
    public synchronized void setManuscriptsMap(HashMap<String, ClientManuscript> manuscriptsMap) {
        this.manuscriptsMap = manuscriptsMap;
    }

    /**
     * Gets the player
     *
     * @return the player
     */
    public synchronized ClientPlayer getPlayer() {
        return player;
    }

    /**
     * Gets the market
     *
     * @return the market
     */
    public synchronized ClientMarket getMarket() {
        return market;
    }

    /**
     * Gets the board
     *
     * @return the board
     */
    public synchronized ClientBoard getBoard() {
        return board;
    }

    /**
     * Gets the manuscript
     *
     * @return the manuscript
     */
    public synchronized ClientManuscript getManuscript() {
        return manuscript;
    }

    /**
     * Gets the hand of the player
     *
     * @return the hand of the player
     */
    public synchronized ArrayList<ResourceCard> getHand() {
        return hand;
    }

    /**
     * Sets the hand of the player
     *
     * @param hand the hand to set
     */
    public synchronized void setHand(ArrayList<ResourceCard> hand) {
        this.hand = hand;
    }

    /**
     * Sets the manuscript
     *
     * @param manuscript the manuscript to set
     */
    public synchronized void setManuscript(ClientManuscript manuscript) {
        this.manuscript = manuscript;
    }

    /**
     * Sets the market
     *
     * @param market the market to set
     */
    public synchronized void setMarket(ClientMarket market) {
        this.market = market;
    }

    /**
     * Sets the board
     *
     * @param board the board to set
     */
    public synchronized void setBoard(ClientBoard board) {
        this.board = board;
    }

    /**
     * Sets the player
     *
     * @param player the player to set
     */
    public synchronized void setPlayer(ClientPlayer player) {
        this.player = player;
    }

    /**
     * Copies the state from another MiniModel
     *
     * @param miniModel the MiniModel to copy from
     */
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

    /**
     * Gets the chat with the specified players in the chat
     *
     * @param chatters the list of players in the chat
     * @return the ClientChat if found, null otherwise
     */
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

    /**
     * Gets the usernames of other players
     *
     * @return the list of usernames of other players
     */
    public synchronized ArrayList<String> getOtherPlayersUsernames() {
        return otherPlayersUsernames;
    }

    /**
     * Checks if the specified username belongs to another player
     *
     * @param user the username to check
     * @return true if the username belongs to another player, false otherwise
     */
    public synchronized boolean checkOtherUsername(String user) {
        return otherPlayersUsernames.contains(user);
    }

    /**
     * Gets the list of chats
     *
     * @return the list of chats
     */
    public synchronized ArrayList<ClientChat> getChats() {
        return this.chats;
    }

    /**
     * Gets the chat with the specified player
     *
     * @param person the player to get the chat for
     * @return the ClientChat if found, null otherwise
     */
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
