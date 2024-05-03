package it.polimi.ingsw.gc27.Model.Listener;

import it.polimi.ingsw.gc27.Model.Game.Player;

import java.rmi.RemoteException;

public class ProvaListener implements Observer{
    private Player player;
    public ProvaListener(Player player){
        this.player=player;
        this.player.addObserver(this);

    }
    @Override
    public void update(Observable o)  {
        System.out.println("!!!!!!");
    }
}
