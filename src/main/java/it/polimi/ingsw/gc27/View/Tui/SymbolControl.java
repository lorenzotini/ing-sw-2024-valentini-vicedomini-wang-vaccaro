package it.polimi.ingsw.gc27.View.Tui;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;

// TODO non ha usages, eliminare?
public enum SymbolControl  {
    PLANT(ColourControl.GREEN + "PLT" + ColourControl.RESET, CornerSymbol.PLANT),
    INSECT(ColourControl.PURPLE + "INS" + ColourControl.RESET, CornerSymbol.INSECT),
    SHROOM(ColourControl.RED + "SHR" + ColourControl.RESET, CornerSymbol.FUNGI),
    ANIMAL(ColourControl.CYAN + "ANI" + ColourControl.RESET, CornerSymbol.ANIMAL),
    INKWELL(ColourControl.YELLOW + "INK" + ColourControl.RESET, CornerSymbol.INKWELL),
    QUILL(ColourControl.YELLOW + "QLL" + ColourControl.RESET, CornerSymbol.QUILL),
    MANUSCRIPT(ColourControl.YELLOW + "MNS" + ColourControl.RESET, CornerSymbol.MANUSCRIPT),
    EMPTY("   ", CornerSymbol.EMPTY);

    private final String text;
    private final CornerSymbol symbol;

    SymbolControl(String string, CornerSymbol symbol) {
        this.text = string;
        this.symbol = symbol;
    }

    public CornerSymbol getSymbol() {
        return symbol;
    }

    public String getText() {
        return text;
    }

}