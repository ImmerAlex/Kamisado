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

public class HoleNaiveDecider extends Decider {

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

    public HoleNaiveDecider(Model model, Controller control, View view) {
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
                    if (noOneCanMove(stage, board)) {
                        System.out.println("No one can move.");
                        model.stopStage();
                        break;
                    }
                    System.out.println(model.getGameStage().getCurrentPlayerName() + " cannot move. Pass turn.");
                    ok = true;
                    continue;
                }

                setAllPossibleMove(board, rowFrom);

                List<Node> nodes = tree.getAll10Point();

                if (nodes == null || nodes.size() == 0) {
                    to = getRandomCoup();
                } else {
                    int index = loto.nextInt(nodes.size());
                    to = nodes.get(index).getCoup();
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
            } catch (Exception ignored) {}
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

    private MinimalBoard[][] createMinimalBoard(HoleBoard board) {
        MinimalBoard[][] minimalBoard = new MinimalBoard[board.getNbCols()][board.getNbRows()];

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                Pawn pawn = (Pawn) board.getElement(i, j);
                minimalBoard[i][j] = pawn != null ? new MinimalBoard(pawn.getSymbol(), pawn.getColor()) : new MinimalBoard('N', -1);
            }
        }

        return minimalBoard;
    }

    private void setAllPossibleMove(HoleBoard board, int rowFrom) {
        boolean[][] reachableCells = board.getReachableCells();

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (reachableCells[i][j] && j == rowFrom) {
                    tree.add(10, "" + (char) (j + 'A') + (i + 1));
                } else if (reachableCells[i][j] && j != rowFrom) {
                    tree.add(0, "" + (char) (j + 'A') + (i + 1));
                }
            }
        }
    }

    public List<String> getValidCellsMove(MinimalBoard[][] minimalBoard, int row, int col, String playerName) {
        List<String> lst = new ArrayList<>();
        int[][] directions = playerName.contains("X") ? new int[][]{{0, -1}, {1, -1}, {-1, -1}} : new int[][]{{0, 1}, {1, 1}, {-1, 1}};

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = row + dx, y = col + dy;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (minimalBoard[y][x].getSymbol() == 'N') {
                    lst.add("" + (char) (x + 'A') + (y + 1));
                } else {
                    break;
                }
                x += dx;
                y += dy;
            }
        }

        return lst;
    }

    public boolean noOneCanMove(HoleStageModel stage, HoleBoard board) {
        for (Pawn pawn : stage.getXPawns()) {
            if (canPawnMove(stage, board, pawn, "Player X")) return false;
        }

        for (Pawn pawn : stage.getOPawns()) {
            if (canPawnMove(stage, board, pawn, "Player O")) return false;
        }

        return true;
    }

    private boolean canPawnMove(HoleStageModel stage, HoleBoard board, Pawn pawn, String playerName) {
        int x = (int) pawn.getLocation().getX();
        int y = (int) pawn.getLocation().getY();
        int computeX = stage.reverseComputeX(x);
        int computeY = stage.reverseComputeY(y) + 1;

        Pawn tempPawn = (Pawn) board.getElement(computeY, computeX);
        if (tempPawn != null && ConsoleColor.getColorValue(tempPawn.getStringColor()).equals(stage.getLockedColor())) {
            List<String> validMoves = getValidCellsMove(createMinimalBoard(board), computeX, computeY, playerName);
            return !validMoves.isEmpty();
        }

        return false;
    }
}
