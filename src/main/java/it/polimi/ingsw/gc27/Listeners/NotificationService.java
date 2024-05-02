package it.polimi.ingsw.gc27.Listeners;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<ModelListener> listeners;

    public NotificationService() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }
    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    public void updateAll() {
        listeners.forEach(listener -> listener.update());
    }
}
