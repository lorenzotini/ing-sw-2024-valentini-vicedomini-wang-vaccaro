package it.polimi.ingsw.gc27.Listeners;

public class Store {
    private final NotificationService notificationService;

    public Store(NotificationService notificationService) {
        this.notificationService = new NotificationService();
    }

    public void newItemPromotion(){
        notificationService.updateAll();
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }
}
