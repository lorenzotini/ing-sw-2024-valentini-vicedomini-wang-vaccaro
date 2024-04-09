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
        int index = 0;
        Face[][] field = manuscript.getField();

        xMax = manuscript.getxMax();
        xMin = manuscript.getxMin();
        yMax = manuscript.getyMax();
        yMin = manuscript.getyMin();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("   ");
        for(int z = xMin; z <= xMax; z++) {
            index = z;
            stringBuilder.append(z + " ");
            if (index <= 9){ // if the printed index is a 1 digit number you have to fill the gap with a space
                stringBuilder.append(" ");
            }
        }

        stringBuilder.append("\n");
        index = yMin;

        for(j = yMin; j<= yMax; j++) {

            stringBuilder.append(index);
            index++;
            if (index <= 9){
                stringBuilder.append("  ");
            } else {
                stringBuilder.append(" ");
            }

            for(i = xMin ; i<=xMax; i++) {
                if(field[i][j] != null) {
                    switch (field[i][j].getColour()) {
                        case PLANTKINGDOM :
                            stringBuilder.append(ColourControl.GREEN + "███" + ColourControl.RESET);
                            break;
                        case ANIMALKINGDOM :
                            stringBuilder.append(ColourControl.CYAN + "███" + ColourControl.RESET);
                            break;
                        case INSECTKINGDOM:
                            stringBuilder.append(ColourControl.PURPLE + "███" + ColourControl.RESET);
                            break;
                        case FUNGIKINGDOM:
                            stringBuilder.append(ColourControl.RED + "███" + ColourControl.RESET);
                            break;
                        case EMPTY:
                            stringBuilder.append(ColourControl.WHITE + "███" + ColourControl.RESET);
                        default:
                            break;
                    }
                } else {
                    stringBuilder.append("   ");
                }
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
    }

    public void zoom(Manuscript manuscript, int xCoordinate, int yCoordinate) {
        int xMax, xMin, yMax, yMin;
        Face[][] field = manuscript.getField();

        xMax = manuscript.getxMax();
        xMin = manuscript.getxMin();
        yMax = manuscript.getyMax();
        yMin = manuscript.getyMin();

        StringBuilder stringBuilder = new StringBuilder();

        // line 1
        stringBuilder.append("     ");

        if(field[xCoordinate-1][yCoordinate-1] != null){
            stringBuilder.append("│");
        } else {
            stringBuilder.append(" ");
        }

        stringBuilder.append("     ");

        if(field[xCoordinate+1][yCoordinate-1] != null){
            stringBuilder.append("│");
        } else {
            stringBuilder.append(" ");
        }

        stringBuilder.append("     ");
        stringBuilder.append("\n");


        //line 2
        stringBuilder.append(" ┌───");

        if(field[xCoordinate-1][yCoordinate-1] != null){
            stringBuilder.append("┼");
        } else if (!field[xCoordinate][yCoordinate].getCornerUL().isBlack()) {
            stringBuilder.append("┬");
        } else {
            stringBuilder.append("─");
        }

        stringBuilder.append("─────");

        if(field[xCoordinate+1][yCoordinate-1] != null){
            stringBuilder.append("┼");
        } else if (!field[xCoordinate][yCoordinate].getCornerUR().isBlack()) {
            stringBuilder.append("┬");
        } else {
            stringBuilder.append("─");
        }

        stringBuilder.append("───┐ ");
        stringBuilder.append("\n");


        //line 3
        stringBuilder.append(" │");
        String temp = "";
        if(!field[xCoordinate][yCoordinate].getCornerUL().isBlack() && !field[xCoordinate][yCoordinate].getCornerUL().isHidden()) {
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getCornerUL().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append(temp + "│");

        } else if(field[xCoordinate][yCoordinate].getCornerUL().isBlack()) {
            stringBuilder.append("    ");
        } else if(field[xCoordinate][yCoordinate].getCornerUL().isHidden()){
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate-1][yCoordinate-1].getCornerLR().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append(temp + "│");
        }

        stringBuilder.append("     ");

        if(!field[xCoordinate][yCoordinate].getCornerUR().isBlack() && !field[xCoordinate][yCoordinate].getCornerUR().isHidden()) {
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getCornerUR().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append("│" + temp);

        } else if(field[xCoordinate][yCoordinate].getCornerUR().isBlack()) {
            stringBuilder.append("    ");
        } else if(field[xCoordinate][yCoordinate].getCornerUR().isHidden()){
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate+1][yCoordinate-1].getCornerLL().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append("│" + temp);
        }

        stringBuilder.append("│ ");
        stringBuilder.append("\n");


        //line 4
        if(!field[xCoordinate][yCoordinate].getCornerUL().isBlack() && !field[xCoordinate][yCoordinate].getCornerUL().isHidden()
            && field[xCoordinate-1][yCoordinate-1] == null) {
            stringBuilder.append(" ├───┘");
        } else if(field[xCoordinate][yCoordinate].getCornerUL().isBlack()) {
            stringBuilder.append(" │    ");
        } else if(field[xCoordinate][yCoordinate].getCornerUL().isHidden() || field[xCoordinate-1][yCoordinate-1] != null){
            stringBuilder.append("─┼───┘");
        }

        stringBuilder.append("     ");

        if(!field[xCoordinate][yCoordinate].getCornerUR().isBlack() && !field[xCoordinate][yCoordinate].getCornerUR().isHidden()
            && field[xCoordinate+1][yCoordinate-1] == null) {
            stringBuilder.append("└───┤ ");
        } else if(field[xCoordinate][yCoordinate].getCornerUR().isBlack()) {
            stringBuilder.append("    │ ");
        } else if(field[xCoordinate][yCoordinate].getCornerUR().isHidden() || field[xCoordinate+1][yCoordinate-1] != null){
            stringBuilder.append("└───┼─");
        }
        stringBuilder.append("\n");


        //line 5
        stringBuilder.append(" │  ");
        if(field[xCoordinate][yCoordinate] instanceof BackFace){
            int size = 0;
            size = field[xCoordinate][yCoordinate].getPermanentResources().size();
            switch (size){
                case 1:
                    for(SymbolControl sc : SymbolControl.values()){
                        if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getPermanentResources().get(0).toCornerSymbol())){
                            temp = sc.getText();
                        }
                    }
                    stringBuilder.append("   " + temp + "   ");
                    break;
                case 2:
                    for(SymbolControl sc : SymbolControl.values()){
                        if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getPermanentResources().get(0).toCornerSymbol())){
                            temp = sc.getText();
                        }
                    }
                    stringBuilder.append(" " + temp + " ");
                    for(SymbolControl sc : SymbolControl.values()){
                        if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getPermanentResources().get(1).toCornerSymbol())){
                            temp = sc.getText();
                        }
                    }
                    stringBuilder.append(temp + " ");
                    break;
                case 3:
                    for(int i = 0; i<3; i++){
                        for(SymbolControl sc : SymbolControl.values()){
                            if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getPermanentResources().get(i).toCornerSymbol())){
                                temp = sc.getText();
                            }
                        }
                        stringBuilder.append(temp);
                    }
                    break;
                default:
                    break;
            }
        } else {
            stringBuilder.append("         ");
        }
        stringBuilder.append("  │ ");
        stringBuilder.append("\n");


        //line 6
        if(!field[xCoordinate][yCoordinate].getCornerLL().isBlack() && !field[xCoordinate][yCoordinate].getCornerLL().isHidden()
            && field[xCoordinate-1][yCoordinate+1] == null) {
            stringBuilder.append(" ├───┐");
        } else if(field[xCoordinate][yCoordinate].getCornerLL().isBlack()) {
            stringBuilder.append(" │    ");
        } else if(field[xCoordinate][yCoordinate].getCornerLL().isHidden() || field[xCoordinate-1][yCoordinate+1] != null){
            stringBuilder.append("─┼───┐");
        }

        stringBuilder.append("     ");

        if(!field[xCoordinate][yCoordinate].getCornerLR().isBlack() && !field[xCoordinate][yCoordinate].getCornerLR().isHidden()
            && field[xCoordinate+1][yCoordinate+1] == null) {
            stringBuilder.append("┌───┤ ");
        } else if(field[xCoordinate][yCoordinate].getCornerLR().isBlack()) {
            stringBuilder.append("    │ ");
        } else if(field[xCoordinate][yCoordinate].getCornerLR().isHidden() || field[xCoordinate+1][yCoordinate+1] != null){
            stringBuilder.append("┌───┼─");
        }
        stringBuilder.append("\n");


        //line 7
        stringBuilder.append(" │");
        if(!field[xCoordinate][yCoordinate].getCornerLL().isBlack() && !field[xCoordinate][yCoordinate].getCornerLL().isHidden()) {
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getCornerLL().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append(temp + "│");

        } else if(field[xCoordinate][yCoordinate].getCornerLL().isBlack()) {
            stringBuilder.append("    ");
        } else if(field[xCoordinate][yCoordinate].getCornerLL().isHidden()){
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate-1][yCoordinate+1].getCornerUR().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append(temp + "│");
        }

        stringBuilder.append("     ");

        if(!field[xCoordinate][yCoordinate].getCornerLR().isBlack() && !field[xCoordinate][yCoordinate].getCornerLR().isHidden()) {
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate][yCoordinate].getCornerLR().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append("│" + temp);

        } else if(field[xCoordinate][yCoordinate].getCornerLR().isBlack()) {
            stringBuilder.append("    ");
        } else if(field[xCoordinate][yCoordinate].getCornerLR().isHidden()){
            for(SymbolControl sc : SymbolControl.values()){
                if(sc.getSymbol().equals(field[xCoordinate+1][yCoordinate+1].getCornerUL().getSymbol())){
                    temp = sc.getText();
                }
            }
            stringBuilder.append("│" + temp);
        }

        stringBuilder.append("│ ");
        stringBuilder.append("\n");


        //line 8
        stringBuilder.append(" └───");

        if(field[xCoordinate-1][yCoordinate+1] != null){
            stringBuilder.append("┼");
        } else if (!field[xCoordinate][yCoordinate].getCornerLL().isBlack()) {
            stringBuilder.append("┴");
        } else {
            stringBuilder.append("─");
        }

        stringBuilder.append("─────");

        if(field[xCoordinate+1][yCoordinate+1] != null){
            stringBuilder.append("┼");
        } else if (!field[xCoordinate][yCoordinate].getCornerLR().isBlack()) {
            stringBuilder.append("┴");
        } else {
            stringBuilder.append("─");
        }

        stringBuilder.append("───┘ ");
        stringBuilder.append("\n");


        //line 9
        stringBuilder.append("     ");

        if(field[xCoordinate-1][yCoordinate+1] != null){
            stringBuilder.append("│");
        } else {
            stringBuilder.append(" ");
        }

        stringBuilder.append("     ");

        if(field[xCoordinate+1][yCoordinate+1] != null){
            stringBuilder.append("│");
        } else {
            stringBuilder.append(" ");
        }

        stringBuilder.append("     ");
        stringBuilder.append("\n");

        /*
                "┌───┼─────┼───┐\n" +
                "│   │     │   │\n" +
                "┼───┘     └───┼\n" +
                "│             │\n" +
                "┼───┐     ┌───┼\n" +
                "│   │     │   │\n" +
                "└───┼─────┼───┘");*/

        /*
                "┌───┬─────┬───┐\n" +
                "│   │     │   │\n" +
                "├───┘     └───┤\n" +
                "│             │\n" +
                "├───┐     ┌───┤\n" +
                "│   │     │   │\n" +
                "└───┴─────┴───┘");*/
        System.out.println(stringBuilder);
    }

    public void showCommandList() {

    }

    /*
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


        ViewCli cli = new ViewCli();

        //gameController.addStarterCard(p1, starterCard, starterCard.getFront());
        gameController.addCard(p1, resourceCard1, resourceCard1.getFront(), 43,43);
        gameController.addCard(p1, resourceCard2, resourceCard2.getFront(), 41,41);
        manuscript.setxMax(43);
        manuscript.setxMin(41);
        manuscript.setyMax(43);
        manuscript.setyMin(41);
        cli.showTitle();
        cli.showManuscript(manuscript);
        cli.zoom(manuscript, 42, 42);

    }
    */
}


