package it.polimi.ingsw.gc27.Enumerations;

public enum PointsMultiplier {
    CORNER,
    QUILL,

    INKWELL,

    MANUSCRIPT;

    public CornerSymbol convertToCornerSymbol(){
        return switch (this) {
            case QUILL -> CornerSymbol.QUILL;
            case INKWELL-> CornerSymbol.INKWELL;
            case MANUSCRIPT -> CornerSymbol.MANUSCRIPT;
            case CORNER -> null;//solleva un eccezione
        };
        //aggiungere Ecception
    }


}
