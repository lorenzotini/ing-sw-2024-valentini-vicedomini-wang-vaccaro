package it.polimi.ingsw.gc27.Model.Listener;

public interface ViewObservable { //virtual view, scene
    //viene modificata la view, onActionEvent fa la notify all'unico osservatore che è il GigaController
    //gli manda come messaggio il "comando" oppure il dato da inserire
}
