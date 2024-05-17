package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Pawn;

public class PawnLook extends ElementLook {


    public PawnLook(GameElement element) {
        // Pawn look is constituted of a single character, so shape size = 1x1
        super(element, 3, 1);
        anchorType = ANCHOR_TOPLEFT;
    }

//    private String[][] initBoardColors() {
//        return new String[][]{
//                {ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.BROWN_BACKGROUND},
//                {ConsoleColor.RED_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND},
//                {ConsoleColor.GREEN_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BLUE_BACKGROUND},
//                {ConsoleColor.PINK_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND},
//                {ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.PINK_BACKGROUND},
//                {ConsoleColor.BLUE_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.GREEN_BACKGROUND},
//                {ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.RED_BACKGROUND},
//                {ConsoleColor.BROWN_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND},
//        };
//    }

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

    private void displayShape(String[][] shape) {
        System.out.println("[");
        for(int i=0;i<height;i++) {
            System.out.print("[");
            for(int j=0;j<width;j++) {
                System.out.print(shape[i][j] + ", ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }


}
