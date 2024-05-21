package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hole main board represent the element where pawns are put when played
 * Thus, a simple ContainerElement with 3 rows and 3 column is needed.
 * Nevertheless, in order to "simplify" the work for the controller part,
 * this class also contains method to determine all the valid cells to put a
 * pawn with a given value.
 */
public class HoleBoard extends ContainerElement {
    public HoleBoard(int x, int y, int nbRows, int nbCols, GameStageModel gameStageModel) {
        // call the super-constructor to create a 3x3 grid, named "holeboard", and in x,y in space
        super("holeboard", x, y, nbRows, nbCols, gameStageModel);
    }

    public void setValidCells(HoleStageModel gameStage, int row, int col) {
        resetReachableCells(false);
        List<Point> valid = computeValidCells(gameStage, row, col);
        if (valid != null) {
            for (Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
    }

    public List<Point> computeValidCells(HoleStageModel gameStage, int row, int col) {
        List<Point> lst = new ArrayList<>();
        HoleBoard board = gameStage.getBoard();
        String currentPlayerName = gameStage.getCurrentPlayerName();
        int[][] directions;

        if (currentPlayerName.equals("Player X") || currentPlayerName.equals("Computer X")) {
            // Up, Up-Right, Up-Left
            directions = new int[][]{{0, -1}, {1, -1}, {-1, -1}};
        } else {
            // Down, Down-Right, Down-Left
            directions = new int[][]{{0, 1}, {-1, 1}, {1, 1}};
        }

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = row + dx, y = col + dy;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (board.getElement(y, x) == null) {
                    lst.add(new Point(x, y));
                } else {
                    break;
                }

                x += dx;
                y += dy;
            }
        }


        return lst;
    }

    @Override
    public boolean[][] getReachableCells() {
        return super.getReachableCells();
    }

    public void displayReachableCells(boolean[][] reachableCells) {
        System.out.println("[");
        for (boolean[] cells : reachableCells) {
            System.out.print("\t[");
            for (boolean cell : cells) {
                System.out.print(cell + ", ");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }
}
