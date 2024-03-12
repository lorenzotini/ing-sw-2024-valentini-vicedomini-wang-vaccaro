package it.polimi.ingsw.gc27;

import Enumerations.BackSymbol;

import java.util.ArrayList;

public class StarterCard extends Card{
    private ArrayList<BackSymbol> permanentResources;
    public StarterCard(FrontFace front, BackFace back) {
        super(front, back);
    }
}
