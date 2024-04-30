package it.polimi.ingsw.gc27.Model.States.PlayerStates;

import it.polimi.ingsw.gc27.Controller.TurnHandler;
import it.polimi.ingsw.gc27.Model.Card.Face;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Game.Game;
import it.polimi.ingsw.gc27.Model.Game.Player;
import it.polimi.ingsw.gc27.Net.VirtualView;
import it.polimi.ingsw.gc27.View.MyCli;

import java.io.IOException;

public class InitializingState extends PlayerState{

    public InitializingState(Player player, TurnHandler turnHandler) {
        super(player, turnHandler);
    }

    @Override
    public void chooseObjectiveCard(Game game, int objectiveCardIndex) {
        System.out.println("place your starter card first and only then you can start playing\n");
    }

    @Override
    public void drawResourceCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("place your starter card first and only then you can start playing\n");
    }

    @Override
    public void drawGoldCard(Player player, boolean fromDeck, int faceUpCardIndex, Game game) {
        System.out.println("place your starter card first and only then you can start playing\n");
    }

    @Override
    public void addCard(Game game, ResourceCard resourceCard, Face face, int x, int y) {
        System.out.println("place your starter card first and only then you can start playing");
    }

    @Override
    public void askStarterCard(Game game, Player player, VirtualView client) throws IOException, InterruptedException {

        StarterCard starter = game.getStarterDeck().removeFirst();
        client.show(MyCli.showStarter(starter));
        client.show("Which side do you want to use? Front or back? ");
        String choice = client.read();
        do {
            if (choice.equalsIgnoreCase("front"))
                addStarterCard(game, starter, starter.getFront(), 42, 42);
            else if (choice.equalsIgnoreCase("back"))
                addStarterCard(game, starter, starter.getBack(), 42, 42);
        }while(!choice.equalsIgnoreCase("front") && !choice.equalsIgnoreCase("back"));

    }

    public void addStarterCard(Game game, StarterCard starterCard, Face face, int x, int y) {
        getPlayer().addCard(game, starterCard, face, 42, 42);
        getPlayer().setPlayerState(new WaitingState(getPlayer(), getTurnHandler()));
        // notifica il TurnHandler
        getTurnHandler().notifyInitializingState(getPlayer());
    }
}
