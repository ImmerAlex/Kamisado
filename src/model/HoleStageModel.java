package model;

import boardifier.model.*;
import boardifier.view.ConsoleColor;
import boardifier.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * HoleStageModel defines the model for the single stage in "The Hole". Indeed,
 * there are no levels in this game: a party starts and when it's done, the game is also done.
 * <p>
 * HoleStageModel must define all that is needed to manage a party : state variables and game elements.
 * In the present case, there are only 2 state variables that represent the number of pawns to play by each player.
 * It is used to detect the end of the party.
 * For game elements, it depends on what is chosen as a final UI design. For that demo, there are 12 elements used
 * to represent the state : the main board, 2 pots, 8 pawns, and a text for current player.
 * <p>
 * WARNING ! HoleStageModel DOES NOT create itself the game elements because it would prevent the possibility to mock
 * game element classes for unit testing purposes. This is why HoleStageModel just defines the game elements and the methods
 * to set this elements.
 * The instanciation of the elements is done by the HoleStageFactory, which uses the provided setters.
 * <p>
 * HoleStageModel must also contain methods to check/modify the game state when given events occur. This is the role of
 * setupCallbacks() method that defines a callback function that must be called when a pawn is put in a container.
 * This is done by calling onPutInContainer() method, with the callback function as a parameter. After that call, boardifier
 * will be able to call the callback function automatically when a pawn is put in a container.
 * NB1: callback functions MUST BE defined with a lambda expression (i.e. an arrow function).
 * NB2:  there are other methods to defines callbacks for other events (see onXXX methods in GameStageModel)
 * In "The Hole", everytime a pawn is put in the main board, we have to check if the party is ended and in this case, who is the winner.
 * This is the role of computePartyResult(), which is called by the callback function if there is no more pawn to play.
 */

public class HoleStageModel extends GameStageModel {
    // define stage game elements
    private HoleBoard board;
    private List<Pawn> XPawns;
    private List<Pawn> OPawns;
    private TextElement playerName;
    private boolean firstPlayer;
    private String lockedColor;

    public HoleStageModel(String name, Model model) {
        super(name, model);
        firstPlayer = true;
    }

    public boolean isValidCoordinates(int row, int col) {
        return row >= 0 && row <= 8 && col >= 0 && col <= 8;
    }

    public HoleBoard getBoard() {
        return board;
    }

    public void setBoard(HoleBoard board) {
        this.board = board;
    }

    public List<Pawn> getXPawns() {
        return XPawns;
    }

    public void setXPawns(List<Pawn> pawns) {
        XPawns = pawns;
        for (Pawn pawn : XPawns) {
            addElement(pawn);
        }
    }

    public List<Pawn> getOPawns() {
        return OPawns;
    }

    public void setOPawns(List<Pawn> pawns) {
        OPawns = pawns;
        for (Pawn pawn : OPawns) {
            addElement(pawn);
        }
    }

    public TextElement getPlayerName() {
        return playerName;
    }

    public void setPlayerName(TextElement text) {
        playerName = text;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new HoleStageFactory(this);
    }

    public String getFirstFromPlay() {
        List<String> froms = new ArrayList<>();

        for (int i = 0; i < board.getNbCols(); i++) {
            froms.add("A" + (char) ('1' + i));
        }

        return froms.get((int) (Math.random() * froms.size()));
    }

    public String findPawnFrom() {
        for (int i = 0; i < board.getNbRows(); i++) {
            for (int j = 0; j < board.getNbCols(); j++) {
                GameElement element = board.getElement(j, i);
                if (element instanceof Pawn pawn) {
                    if (ConsoleColor.getColorValue(pawn.getStringColor()).equals(lockedColor) && goodFromEntry(i, j)) {
                        return "" + (char) ('A' + i) + (char) ('1' + j);
                    }
                }
            }
        }

        return null;
    }

    public boolean goodFromEntry(int row, int col) {
        String currentPlayer = getCurrentPlayerName();
        GameElement element = getBoard().getElement(col, row);

        if (element instanceof Pawn pawn) {
            if (pawn.getSymbol() == 'X' && (currentPlayer.equals("Player X") || currentPlayer.equals("Computer X"))) {
                return true;
            } else
                return pawn.getSymbol() == 'O' && (currentPlayer.equals("Player O") || currentPlayer.equals("Computer O"));
        }

        return false;
    }

    public boolean canMoveFrom(String lineFrom) {
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

    public void setLockedColor(HoleStageModel gameStage, View view, int rowTo, int colTo) {
        int x = computeX(rowTo);
        int y = computeY(colTo) - 1;
        String value = view.getElementLook(gameStage.getBoard()).getShapePoint(x, y);
        lockedColor = ConsoleColor.getColorValue(value);
    }

    private int computeX(int row) {
        return 2 + (row * 8);
    }

    private int computeY(int col) {
        return 3 + (2 * col);
    }

    public void isWin(int row, int col) {
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

    public HoleStageModel copy() {
        HoleStageModel copy = new HoleStageModel(getName(), model);
        copy.setBoard(board.copy());
        copy.setXPawns(new ArrayList<>(XPawns));
        copy.setOPawns(new ArrayList<>(OPawns));
        copy.setPlayerName(playerName.copy());
        copy.setFirstPlayer(firstPlayer);
        copy.setLockedColor(lockedColor);
        return copy;
    }

    private void setLockedColor(String lockedColor) {
        this.lockedColor = lockedColor;
    }
}