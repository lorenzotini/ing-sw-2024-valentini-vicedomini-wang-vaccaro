package it.polimi.ingsw.gc27.View;
import it.polimi.ingsw.gc27.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.View.ColourControl;
import it.polimi.ingsw.gc27.Enumerations.CornerSymbol;


public enum SymbolControl {
    PLANT(ColourControl.GREEN + "PLT" + ColourControl.RESET, CornerSymbol.PLANTKINGDOM),
    INSECT(ColourControl.PURPLE + "INS" + ColourControl.RESET, CornerSymbol.INSECTKINGDOM),
    SHROOM(ColourControl.RED + "SHR" + ColourControl.RESET, CornerSymbol.FUNGIKINGDOM),
    ANIMAL(ColourControl.CYAN + "ANI" + ColourControl.RESET, CornerSymbol.ANIMALKINGDOM),
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