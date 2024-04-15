package it.polimi.ingsw.gc27.States;

import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Game.Game;

//aggiungere state e setState in Game

public abstract class State {
    private Game game;

    public State(Game game){
        this.game = game;
    }

    public abstract void drawCard();
    public abstract void addCard();

    public abstract void addCard(GameController gameController);

    public abstract void addStarterCard();
}
