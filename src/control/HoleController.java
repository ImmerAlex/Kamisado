package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.ContainerElement;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.view.ConsoleColor;
import boardifier.view.View;
import model.HoleBoard;
import model.HoleStageModel;
import model.Pawn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HoleController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;
    String lockedColor;

    public HoleController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
    }

    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            playTurn();
            endOfTurn();
            update();
        }
        endGame();
    }

    private void playTurn() {
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            HoleDecider decider = new HoleDecider(model, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
            boolean ok = false;
            while (!ok) {
                try {
                    String lineFrom, lineTo;

                    if (firstPlayer) {
                        System.out.print(p.getName() + " from > ");
                        lineFrom = consoleIn.readLine();

                        System.out.print(p.getName() + " to > ");
                        lineTo = consoleIn.readLine();

                        ok = analyseAndPlay(lineFrom, lineTo);

                        if (ok) {
                            firstPlayer = false;
                        }
                    } else {
                        lineFrom = findPawnFrom();

                        if (!canMoveFrom(lineFrom)) {
                            System.out.println(model.getGameStage().getCurrentPlayerName() + " cannot move. Pass turn.");
                            ok = true;
                            continue;
                        }

                        System.out.print(p.getName() + " move " + lineFrom + " to > ");
                        lineTo = consoleIn.readLine();

                        ok = analyseAndPlay(lineFrom, lineTo);
                    }

                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }

                } catch (IOException ignored) {}
            }
        }
    }

    private boolean canMoveFrom(String lineFrom) {
        int rowFrom = lineFrom.charAt(0) - 'A';
        int colFrom = lineFrom.charAt(1) - '1';
        return hasReachableCells(model, rowFrom, colFrom);
    }

    private boolean hasReachableCells(Model model, int row, int col) {
        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();
        HoleBoard board = gameStage.getBoard();

        board.setValidCells(gameStage, row, col);
        boolean[][] reachableCells = board.getReachableCells();

        for (boolean[] cells : reachableCells) {
            for (boolean cell : cells) {
                if (cell) {
                    return true;
                }
            }
        }

        return false;
    }

    public void endOfTurn() {
        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        HoleStageModel stageModel = (HoleStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
    }

    private boolean analyseAndPlay(String from, String to) {
        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();

        int rowFrom = from.charAt(0) - 'A';
        int colFrom = from.charAt(1) - '1';

        if (!isValidCoordinates(rowFrom, colFrom)) return false;
        if (!goodFromEntry(gameStage, rowFrom, colFrom)) return false;

        int rowTo = to.charAt(0) - 'A';
        int colTo = to.charAt(1) - '1';

        if (!isValidCoordinates(rowTo, colTo)) return false;

        if (!gameStage.getBoard().canReachCell(to)) return false;

        setLockedColor(gameStage, rowTo, colTo);

        Pawn pawn = (Pawn) gameStage.getBoard().getElement(colFrom, rowFrom);

        ActionList actions = ActionFactory.generateMoveWithinContainer(model, pawn, colTo, rowTo);
        actions.setDoEndOfTurn(true);

        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();

        isWin(rowTo, colTo);

        return true;
    }

    private boolean isValidCoordinates(int row, int col) {
        return row >= 0 && row <= 8 && col >= 0 && col <= 8;
    }

    private boolean goodFromEntry(HoleStageModel gameStage, int row, int col) {
        String currentPlayer = gameStage.getCurrentPlayerName();
        GameElement element = gameStage.getBoard().getElement(col, row);

        if (element instanceof Pawn pawn) {
            if (pawn.getSymbol() == 'X' && (currentPlayer.equals("Player X") || currentPlayer.equals("Computer X"))) {
                return true;
            } else return pawn.getSymbol() == 'O' && (currentPlayer.equals("Player O") || currentPlayer.equals("Computer O"));
        }

        return false;
    }

    private String findPawnFrom() {
        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();
        ContainerElement board = gameStage.getBoard();

        for (int i = 0; i < board.getNbRows(); i++) {
            for (int j = 0; j < board.getNbCols(); j++) {
                GameElement element = board.getElement(j, i);
                if (element instanceof Pawn pawn) {
                    if (ConsoleColor.getColorValue(pawn.getStringColor()).equals(lockedColor) && goodFromEntry(gameStage, i, j)) {
                        return "" + (char) ('A' + i) + (char) ('1' + j);
                    }
                }
            }
        }

        return null;
    }

    public void isWin(int row, int col) {
        HoleStageModel gameStage = (HoleStageModel) model.getGameStage();
        HoleBoard board = gameStage.getBoard();

        GameElement element = board.getElement(col, row);

        if (element instanceof Pawn pawn) {
            if (pawn.getSymbol() == 'X' && col == 0) {
                model.setIdWinner(0);
                model.stopStage();
            } else if (pawn.getSymbol() == 'O' && col == 7) {
                model.setIdWinner(1);
                model.stopStage();
            }
        }
    }

    public void setLockedColor(HoleStageModel gameStage, int rowTo, int colTo) {
        int x = computeX(rowTo);
        int y = computeY(colTo) - 1;
        String value = view.getElementLook(gameStage.getBoard()).getShapePoint(x, y);
        lockedColor = ConsoleColor.getColorValue(value);
    }

    public int computeX(int row) {
        return 2 + (row * 8);
    }

    public int computeY(int col) {
        return 3 + (2 * col);
    }
}
