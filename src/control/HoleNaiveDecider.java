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
                    if (stage.noOneCanMove(stage, board)) {
                        System.out.println("No one can move.");
                        model.stopStage();
                        return null;
                    }
                    System.out.println(model.getGameStage().getCurrentPlayerName() + " cannot move. Pass turn.");
                    ok = true;
                    continue;
                }

                setAllPossibleMove(board, rowFrom);

                List<Node> nodes = tree.getAll10Point();

                if (nodes.size() == 0) {
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
        int randomIndex = loto.nextInt(coups.size());
        return coups.get(randomIndex);
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
}
