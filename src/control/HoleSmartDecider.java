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
import model.binary_tree.Node;
import model.binary_tree.Tree;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class HoleSmartDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private static final List<String> winingMoveX = new ArrayList<>();
    private static final List<String> winingMoveO = new ArrayList<>();

    static {
        for (int i = 0; i < 8; i++) {
            winingMoveX.add((char) (i + 'A') + "1");
            winingMoveO.add((char) (i + 'A') + "8");
        }
    }

    private final View view;
    private final Tree tree;

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
        String from, to;

        while (!ok) {
            try {
                from = stage.isFirstPlayer() ? stage.getFirstFromPlay() : stage.findPawnFrom();
                int rowFrom = from.charAt(0) - 'A';
                int colFrom = from.charAt(1) - '1';

                if (!stage.isValidCoordinates(rowFrom, colFrom) || !stage.goodFromEntry(rowFrom, colFrom) || !stage.canMoveFrom(from)) {
                    if (stage.noOneCanMove(stage, board)) {
                        System.out.println("No one can move.");
                        model.stopStage();
                        return null;
                    }
                    System.out.println(model.getGameStage().getCurrentPlayerName() + " cannot move. Pass turn.");
                    ok = true;
                    continue;
                }

                setAllPossibleMove(board);
                setLoosingMove(stage, board, from);
                setWiningMove(stage, board);

                Node node = tree.getBestCoup();

                if (node.getPoint() == 0) {
                    to = getRandomCoup();
                } else {
                    to = node.getCoup();
                }

                int rowTo = to.charAt(0) - 'A';
                int colTo = to.charAt(1) - '1';

                stage.setLockedColor(stage, view, rowTo, colTo);
                stage.setFirstPlayer(false);

                Pawn pawn = (Pawn) board.getElement(colFrom, rowFrom);
                ActionList actions = ActionFactory.generateMoveWithinContainer(model, pawn, colTo, rowTo);
                actions.setDoEndOfTurn(true);

                stage.isWin();

                return actions;
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    public String getRandomCoup() {
        List<String> coups = tree.getAll0Point();
        if (coups.isEmpty()) {
            return null;
        }
        int randomIndex = loto.nextInt(coups.size());
        return coups.get(randomIndex);
    }

    private void setWiningMove(HoleStageModel stage, HoleBoard board) {
        boolean[][] reachableCells = board.getReachableCells();
        int winningSide = stage.getCurrentPlayerName().contains("X") ? 0 : 7;

        for (int i = 0; i < board.getNbCols(); i++) {
            if (reachableCells[winningSide][i]) {
                tree.add(50, "" + (char) (i + 'A') + (winningSide + 1));
            }
        }
    }

    private void setLoosingMove(HoleStageModel stage, HoleBoard board, String from) {
        MinimalBoard[][] minimalBoardBase = stage.createMinimalBoard(board);
        List<String> validMoveCurrentPlayer = getValidCurrentPlayerMove(board);
        String enemyName = stage.getCurrentPlayerName().contains("X") ? "Player O" : "Player X";

        int rowFrom = from.charAt(0) - 'A';
        int colFrom = from.charAt(1) - '1';

        for (String move : validMoveCurrentPlayer) {
            MinimalBoard[][] minimalBoard = cloneMinimalBoard(minimalBoardBase);
            int row = move.charAt(0) - 'A';
            int col = move.charAt(1) - '1';

            minimalBoard[col][row] = minimalBoardBase[colFrom][rowFrom];
            minimalBoard[colFrom][rowFrom] = new MinimalBoard('N', -1);

            String boardColorLock = stage.getBoardColor(stage, view, row, col);
            int[] coordPawnEnemy = findPawnFrom(minimalBoardBase, boardColorLock, enemyName);
            List<String> validMoveEnemyPlayer = stage.getValidCellsMove(minimalBoard, coordPawnEnemy[1], coordPawnEnemy[0], enemyName);

            for (String enemyMove : validMoveEnemyPlayer) {
                if (winingMoveX.contains(enemyMove) || winingMoveO.contains(enemyMove)) {
                    tree.add(-50, move);
                }
            }
        }
    }

    public int[] findPawnFrom(MinimalBoard[][] minimalBoardBase, String boardColorLock, String enemyPlayerName) {
        int[] coords = new int[2];
        char symboleToFound = enemyPlayerName.contains("X") ? 'X' : 'O';
        int idColor = ConsoleColor.getColorId(boardColorLock);

        for (int i = 0; i < minimalBoardBase.length; i++) {
            for (int j = 0; j < minimalBoardBase[i].length; j++) {
                if (minimalBoardBase[i][j].getSymbol() == symboleToFound && minimalBoardBase[i][j].getIdColor() == idColor) {
                    coords[0] = i;
                    coords[1] = j;
                    return coords;
                }
            }
        }

        return coords;
    }

    private MinimalBoard[][] cloneMinimalBoard(MinimalBoard[][] minimalBoardBase) {
        MinimalBoard[][] minimalBoard = new MinimalBoard[minimalBoardBase.length][minimalBoardBase[0].length];

        for (int i = 0; i < minimalBoardBase.length; i++) {
            for (int j = 0; j < minimalBoardBase[i].length; j++) {
                minimalBoard[i][j] = new MinimalBoard(minimalBoardBase[i][j].getSymbol(), minimalBoardBase[i][j].getIdColor());
            }
        }

        return minimalBoard;
    }

    private void setAllPossibleMove(HoleBoard board) {
        boolean[][] reachableCells = board.getReachableCells();

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (reachableCells[i][j]) {
                    tree.add(0, "" + (char) (j + 'A') + (i + 1));
                }
            }
        }
    }

    public List<String> getValidCurrentPlayerMove(HoleBoard board) {
        List<String> lst = new ArrayList<>();
        boolean[][] reachableCells = board.getReachableCells();

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (reachableCells[i][j]) {
                    lst.add("" + (char) (j + 'A') + (i + 1));
                }
            }
        }

        return lst;
    }
}
