package view;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.view.ClassicBoardLook;
import boardifier.view.ConsoleColor;

public class ColoredBoardLook extends ClassicBoardLook {
    private String[][] boardColors = initBoardColors();

    public ColoredBoardLook(int rowHeight, int colWidth, ContainerElement element, int depth, int borderWidth, boolean showCoords) {
        super(rowHeight, colWidth, element, depth, borderWidth, showCoords);
        this.boardColors = initBoardColors();
    }

    protected void render() {
        Logger.trace("called", this);
        // create & clear the viewport if needed
        setSize(getWidth(), getHeight());
        // clear the viewport => if there are more than inners looks to render (e.g. borders), must override this method
        clearShape();
        renderCoords();
        renderBorders();
        renderBackground();
        renderInners();
    }

    private String[][] initBoardColors() {
        return new String[][]{
                {ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.BROWN_BACKGROUND},
                {ConsoleColor.RED_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND},
                {ConsoleColor.GREEN_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BLUE_BACKGROUND},
                {ConsoleColor.PINK_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND},
                {ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.PINK_BACKGROUND},
                {ConsoleColor.BLUE_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.GREEN_BACKGROUND},
                {ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BROWN_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND, ConsoleColor.RED_BACKGROUND},
                {ConsoleColor.BROWN_BACKGROUND, ConsoleColor.GREEN_BACKGROUND, ConsoleColor.RED_BACKGROUND, ConsoleColor.YELLOW_BACKGROUND, ConsoleColor.PINK_BACKGROUND, ConsoleColor.MAGENTA_BACKGROUND, ConsoleColor.BLUE_BACKGROUND, ConsoleColor.ORANGE_BACKGROUND},
        };
    }

    public void renderBackground() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = (i * boardColors.length) / height;
                int x = (j * boardColors[0].length) / width;

                if ((i > 1 && i < height - 1) && (j > 1 && j < width - 1) && shape[i][j].equals(" ")) {
                    String color = boardColors[y][x];
                    shape[i][j] = color + shape[i][j] + ConsoleColor.RESET;
                }
            }
        }
    }
}
