package it.polimi.ingsw.gc27.Enumerations;

public enum CornerSymbol {
    EMPTY(" "),
    PLANTKINGDOM("P"),
    ANIMALKINGDOM("A"),
    INSECTKINGDOM("I"),
    FUNGIKINGDOM("F"),
    QUILL("q"),
    INKWELL("i"),
    MANUSCRIPT("m");
    private final String initials;

    CornerSymbol(String initials) {
        this.initials = initials;
    }

    public String toCliString() {
        return initials;
    }
}
