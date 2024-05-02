package it.polimi.ingsw.gc27.Listeners;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<EmailMsgListeners> customers;

    public NotificationService() {
        this.customers = new ArrayList<>();
    }

    public void subscribe(EmailMsgListeners listener) {
        customers.add(listener);
    }
    public void unsubscribe(EmailMsgListeners listener) {
        customers.remove(listener);
    }

    public void updateAll() {
        customers.forEach(listener -> listener.update());
    }
}
