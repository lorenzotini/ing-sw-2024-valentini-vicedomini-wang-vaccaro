package it.polimi.ingsw.gc27.Model.ClientClass;

import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.ObjectiveCard;
import it.polimi.ingsw.gc27.Model.Card.ResourceCard;
import it.polimi.ingsw.gc27.Model.Card.StarterCard;
import it.polimi.ingsw.gc27.Model.Enumerations.PawnColour;
import it.polimi.ingsw.gc27.Model.Game.Manuscript;
import it.polimi.ingsw.gc27.Model.States.PlayerState;

import java.util.ArrayList;

public interface ClientPlayer {
    PlayerState getPlayerState();
    PawnColour getPawnColour();
    Manuscript getManuscript();
    ArrayList<ObjectiveCard> getSecretObjectives();
    String getUsername();
    ArrayList<ResourceCard> getHand();
    StarterCard getStarterCard();
}
