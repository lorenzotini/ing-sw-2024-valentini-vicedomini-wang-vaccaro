package it.polimi.ingsw.gc27.Enumerations;

public enum PointsMultiplier {
    CORNER,
    QUILL,
    INKWELL,
    MANUSCRIPT,
    EMPTY;

    public CornerSymbol toCornerSymbol() {
        try{
            return switch (this) {
                case QUILL -> CornerSymbol.QUILL;
                case INKWELL-> CornerSymbol.INKWELL;
                case MANUSCRIPT -> CornerSymbol.MANUSCRIPT;
                default -> throw new IllegalArgumentException();
            };
        } catch (IllegalArgumentException e){
            System.err.println("You can't convert this points multiplier to a corner symbol: " + this.toString());
            e.printStackTrace();
            return null;
        }
    }

}
