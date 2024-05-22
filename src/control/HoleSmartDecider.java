package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.view.ConsoleColor;
import boardifier.view.View;
import model.HoleBoard;
import model.HoleStageModel;
import model.MinimalBoard;
import model.Pawn;
import model.binary_tree.Tree;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class HoleSmartDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private View view;
    private Tree tree;
    private static final List<String> winingMoveX = new ArrayList<>();
    private static final List<String> winingMoveO = new ArrayList<>();

    static {
        for (int i = 0; i < 8; i++) {
            winingMoveX.add((char) (i + 'A') + "1");
            winingMoveO.add((char) (i + 'A') + "8");
        }
    }


    public HoleSmartDecider(Model model, Controller control, View view) {
        super(model, control);
        this.view = view;
        this.tree = new Tree();
    }

    @Override
    public ActionList decide() {
        HoleStageModel stage = (HoleStageModel) model.getGameStage();
        HoleBoard board = stage.getBoard();
        boolean ok = false;
        String from, to = null;
        int rowFrom = -1, colFrom = -1;

        while (!ok) {
            try {
                if (stage.isFirstPlayer()) {
                    from = stage.getFirstFromPlay();
                } else {
                    from = stage.findPawnFrom();
                }

                rowFrom = from.charAt(0) - 'A';
                colFrom = from.charAt(1) - '1';


                if (!stage.isValidCoordinates(rowFrom, colFrom)) throw new Exception();
                if (!stage.goodFromEntry(rowFrom, colFrom)) throw new Exception();
                if (!stage.canMoveFrom(from)) throw new Exception();

                setAllPossibleMove(board);

                setWiningMove(stage, board);

                setLoosingMove(stage, board);

                tree.display();

                to = tree.getBestCoup();

                ok = true;

            } catch (Exception e) {
                System.out.println("incorrect instruction. retry !");
            }
        }

        int rowTo = to.charAt(0) - 'A';
        int colTo = to.charAt(1) - '1';

        stage.setLockedColor(stage, view, rowTo, colTo);

        Pawn pawn = (Pawn) board.getElement(colFrom, rowFrom);

        ActionList actions = ActionFactory.generateMoveWithinContainer(model, pawn, colTo, rowTo);
        actions.setDoEndOfTurn(true);

        stage.isWin();

        return actions;
    }

    private void setWiningMove(HoleStageModel stage, HoleBoard board) {
        boolean[][] reachableCells = board.getReachableCells();

        int winingSide = stage.getCurrentPlayerName().equals("Player X") || stage.getCurrentPlayerName().equals("Computer X") ? 0 : 7;

        for (int i = 0; i < board.getNbCols(); i++) {
            if (reachableCells[winingSide][i]) {
                tree.add(50, "" + (char) (i + 'A') + (char) (winingSide + '1'));
            }
        }
    }

    private String setLoosingMove(HoleStageModel stage, HoleBoard board) {
        MinimalBoard[][] minimalBoardBase = createMinimalBoard(board);
        List<String> validMoveCurrentPlayer = getValidCurrentPlayerMove(board);
        String enemyName = stage.getCurrentPlayerName().equals("Player X") || stage.getCurrentPlayerName().equals("Computer X") ? "Player O" : "Player X";

        for (String move : validMoveCurrentPlayer) {
            MinimalBoard[][] minimalBoard = minimalBoardBase.clone();

            int row = move.charAt(0) - 'A';
            int col = move.charAt(1) - '1';

            String boardColorLock = stage.getBoardColor(stage, view, row, col);
            int[] coordPawnEnemy = findPawnFrom(minimalBoardBase, boardColorLock, enemyName);
            List<String> validMoveEnemyPlayer = getValideCellsMove(minimalBoard, coordPawnEnemy[1], coordPawnEnemy[0], enemyName);

            for (String enemyMove : validMoveEnemyPlayer) {
                if (winingMoveX.contains(enemyMove) || winingMoveO.contains(enemyMove)) {
                    tree.add(-50, move);
                }
            }
        }

        return null;
    }

    public int[] findPawnFrom(MinimalBoard[][] minimalBoardBase, String boardColorLock, String enemyPlayerName) {
        int[] coords = new int[2];
        char symboleToFound = enemyPlayerName.equals("Player X") || enemyPlayerName.equals("Computer X") ? 'X' : 'O';
        int idColor = ConsoleColor.getColorId(boardColorLock);

        for (int i = 0; i < minimalBoardBase.length; i++) {
            for (int j = 0; j < minimalBoardBase[i].length; j++) {
                if (minimalBoardBase[i][j].getSymbol() == symboleToFound && minimalBoardBase[i][j].getIdColor() == idColor) {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }

        return coords;
    }

    private MinimalBoard[][] createMinimalBoard(HoleBoard board) {
        MinimalBoard[][] minimalBoard = new MinimalBoard[board.getNbCols()][board.getNbRows()];

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                Pawn pawn = (Pawn) board.getElement(i, j);
                if (pawn != null) {
                    minimalBoard[i][j] = new MinimalBoard(pawn.getSymbol(), pawn.getColor());
                } else {
                    minimalBoard[i][j] = new MinimalBoard('N', -1);
                }
            }
        }

        return minimalBoard;
    }

    private void setAllPossibleMove(HoleBoard board) {
        boolean[][] reachableCells = board.getReachableCells();

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (reachableCells[i][j]) {
                    tree.add(0, "" + (char) (j + 'A') + (char) (i + '1'));
                }
            }
        }
    }

    public List<String> getValideCellsMove(MinimalBoard[][] minimalBoard, int row, int col, String playerName) {
        List<String> lst = new ArrayList<>();
        int[][] directions;


        if (playerName.equals("Player X") || playerName.equals("Computer X")) {
            // Up, Up-Right, Up-Left
            directions = new int[][]{{0, -1}, {1, -1}, {-1, -1}};
        } else {
            // Down, Down-Right, Down-Left
            directions = new int[][]{{1, 0}, {-1, 1}, {1, 1}};
        }

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = row + dx, y = col + dy;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (minimalBoard[y][x].getSymbol() == 'N') {
                    lst.add("" + (char) (x + 'A') + (char) (y + '1'));
                } else {
                    break;
                }

                x += dx;
                y += dy;
            }
        }


        return lst;
    }

    public List<String> getValidCurrentPlayerMove(HoleBoard board) {
        List<String> lst = new ArrayList<>();
        boolean[][] reachableCells = board.getReachableCells();

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (reachableCells[i][j]) {
                    lst.add("" + (char) (j + 'A') + (char) (i + '1'));
                }
            }
        }

        return lst;
    }






    private void displayCharBoard(MinimalBoard[][] board) {
        System.out.println("[");
        for (int i = 0; i < board.length; i++) {
            System.out.print("\t[");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + ", ");
            }
            System.out.println("]");
        }
        System.out.println("[");
    }

    private void displayReachableCells(boolean[][] reachableCells) {
        System.out.println("[");
        for (int i = 0; i < reachableCells.length; i++) {
            System.out.print("\t[");
            for (int j = 0; j < reachableCells[i].length; j++) {
                System.out.print(reachableCells[i][j] + ", ");
            }
            System.out.println("]");
        }
        System.out.println("[");
    }
}
