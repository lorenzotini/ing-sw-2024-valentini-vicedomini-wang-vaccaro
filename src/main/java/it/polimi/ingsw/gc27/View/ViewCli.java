package it.polimi.ingsw.gc27.View;

import java.util.*;
import java.util.ArrayList;
import java.lang.*;


import it.polimi.ingsw.gc27.Card.Face;
import it.polimi.ingsw.gc27.Card.*;
import it.polimi.ingsw.gc27.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Console;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.JsonParser;
import it.polimi.ingsw.gc27.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.View.ColourControl;
import it.polimi.ingsw.gc27.Card.BackFace;
import it.polimi.ingsw.gc27.Card.FrontFace;
import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Game.*;

public class ViewCli implements View {
    private ArrayList<String> commandList;


    public void showTitle() {
        System.out.println("\n" +
                " ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                "██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                "██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                "██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                "╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                " ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n" +
                "                                                                                                                    \n");
    }
    public void showManuscript(Manuscript manuscript) {
        int xMax, xMin, yMax, yMin;
        int i = 0;
        int j = 0;
        Face[][] field = manuscript.getField();

        xMax = manuscript.getxMax();
        xMin = manuscript.getxMin();
        yMax = manuscript.getyMax();
        yMin = manuscript.getyMin();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");

        for(j = yMin; j<= yMax; j++) {
            for(i = xMin ; i<=xMax; i++) {
                if(field[i][j] != null) {
                    switch (field[i][j].getColour()) {
                        case PLANTKINGDOM :
                            stringBuilder.append(ColourControl.GREEN + "█" + ColourControl.RESET);
                            break;
                        case ANIMALKINGDOM :
                            stringBuilder.append(ColourControl.CYAN + "█" + ColourControl.RESET);
                            break;
                        case INSECTKINGDOM:
                            stringBuilder.append(ColourControl.PURPLE + "█" + ColourControl.RESET);
                            break;
                        case FUNGIKINGDOM:
                            stringBuilder.append(ColourControl.RED + "█" + ColourControl.RESET);
                            break;
                        case EMPTY:
                            stringBuilder.append(ColourControl.WHITE + "█" + ColourControl.RESET);
                        default:
                            break;
                    }
                } else {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
    }

    public void zoom() {

    }
    public void showCommandList() {

    }

    public static void main(String[] args) {
        //import parser
        ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
        ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
        ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);
        ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);

        Console console= new Console();
        List<Game> games = new ArrayList<>();
        console.setGames(games);


        //game 1
        GameController gameController=new GameController();
        Game game= new Game();
        gameController.setGame(game);
        game.setGameID(13);
        Board board=new Board();
        game.setBoard(board);
        List<Player> players=new ArrayList<>();
        game.setPlayers(players);

        //player 1 (manuscript)
        Player p1= new Player();
        players.add(p1);
        p1.setUsername("Olivia"); //1 player
        p1.setPawnColour(PawnColour.BLUE);
        board.setPointsBluePlayer(0);
        StarterCard starterCard= starterDeck.get(0);
        ResourceCard resourceCard1= resourceDeck.get(0);
        ResourceCard resourceCard2 = resourceDeck.get(9);
        ResourceCard goldCard = goldDeck.get(2);

        Manuscript manuscript=new Manuscript(starterCard.getBack());
        p1.setManuscript(manuscript);

        List<ResourceCard> hand=new ArrayList<>();
        p1.getHand().add(resourceCard1);
        p1.getHand().add(resourceCard2);
        p1.getHand().add(goldCard);



    }

}


