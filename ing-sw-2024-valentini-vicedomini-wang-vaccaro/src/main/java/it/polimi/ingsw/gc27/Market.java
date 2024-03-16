package it.polimi.ingsw.gc27;

public class Market {
    private ResourceDeck resource1;
    private ResourceDeck resource2;
    private GoldDeck gold1;
    private GoldDeck gold2;
    private GoldDeck goldDeck;
    private ResourceDeck resourceDeck;

    public ResourceDeck getResource1() {
        return resource1;
    }

    public void setResource1(ResourceDeck resource1) {
        this.resource1 = resource1;
    }

    public ResourceDeck getResource2() {
        return resource2;
    }

    public void setResource2(ResourceDeck resource2) {
        this.resource2 = resource2;
    }

    public GoldDeck getGold1() {
        return gold1;
    }

    public void setGold1(GoldDeck gold1) {
        this.gold1 = gold1;
    }

    public GoldDeck getGold2() {
        return gold2;
    }

    public void setGold2(GoldDeck gold2) {
        this.gold2 = gold2;
    }

    public GoldDeck getGoldDeck() {
        return goldDeck;
    }

    public void setGoldDeck(GoldDeck goldDeck) {
        this.goldDeck = goldDeck;
    }

    public ResourceDeck getResourceDeck() {
        return resourceDeck;
    }

    public void setResourceDeck(ResourceDeck resourceDeck) {
        this.resourceDeck = resourceDeck;
    }

}
