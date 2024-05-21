package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Pawn;

public class PawnLook extends ElementLook {


    public PawnLook(GameElement element) {
        super(element, 3, 1);
        anchorType = ANCHOR_TOPLEFT;
    }

    protected void render() {
        Pawn pawn = (Pawn) element;

        String backgroundColor = switch (pawn.getColor()) {
            case 1 -> ConsoleColor.BROWN_BACKGROUND;
            case 2 -> ConsoleColor.GREEN_BACKGROUND;
            case 3 -> ConsoleColor.RED_BACKGROUND;
            case 4 -> ConsoleColor.YELLOW_BACKGROUND;
            case 5 -> ConsoleColor.PINK_BACKGROUND;
            case 6 -> ConsoleColor.MAGENTA_BACKGROUND;
            case 7 -> ConsoleColor.BLUE_BACKGROUND;
            case 8 -> ConsoleColor.ORANGE_BACKGROUND;
            default -> ConsoleColor.WHITE_BACKGROUND;
        };

        String symbolColor = pawn.getSymbol() == 'X' ? ConsoleColor.BLACK : ConsoleColor.WHITE;

        shape[0][0] = symbolColor + backgroundColor + " " + ConsoleColor.RESET;
        shape[0][1] = symbolColor + backgroundColor + pawn.getSymbol() + ConsoleColor.RESET;
        shape[0][2] = symbolColor + backgroundColor + " " + ConsoleColor.RESET;
    }
}
