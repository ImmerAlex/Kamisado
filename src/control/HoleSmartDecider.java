package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.HoleBoard;
import model.HoleStageModel;
import model.Pawn;
import model.binary_tree.Tree;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class HoleSmartDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private View view;
    private Tree tree;
    private List<String> listOfPossibleMove;


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

                setAllPossibleMove(board, tree);

                getWiningMove(stage, board, tree);


                to = tree.getBestCoup();

                System.out.println("from : " + from + " to : " + to);
                tree.display();

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
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.

        stage.isWin();

        return actions;
    }

    private void getWiningMove(HoleStageModel stage, HoleBoard board, Tree tree) {
        boolean[][] reachableCells = board.getReachableCells();

        int winingSide = stage.getCurrentPlayerName().equals("Player X") || stage.getCurrentPlayerName().equals("Computer X") ? 0 : 7;

        for (int i = 0; i < board.getNbCols(); i++) {
            System.out.println("move to " + (char) (i + 'A') + (char) (winingSide + '1') + " : " + reachableCells[winingSide][i]);
            if (reachableCells[winingSide][i]) {
                tree.add(50, "" + (char) (i + 'A') + (char) (winingSide + '1'));
            }
        }
    }

    private String setLoosingMove(HoleStageModel stage, HoleBoard board, Tree tree) {
        char[][] minimalBoard = createMinimalBoard(board);
        displayCharBoard(minimalBoard);

        return null;
    }

    private char[][] createMinimalBoard(HoleBoard board) {
        char[][] minimalBoard = new char[board.getNbCols()][board.getNbRows()];

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (board.getElement(i, j) != null) {
                    minimalBoard[i][j] = ((Pawn) board.getElement(i, j)).getSymbol();
                } else {
                    minimalBoard[i][j] = ' ';
                }
            }
        }

        return minimalBoard;
    }

    private void setAllPossibleMove(HoleBoard board, Tree tree) {
        boolean[][] reachableCells = board.getReachableCells();

        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (reachableCells[j][i]) {
                    tree.add(0, "" + (char) (i + 'A') + (char) (j + '1'));
                }
            }
        }
    }








    private void displayCharBoard(char[][] board) {
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
