package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.HoleStageModel;
import model.Pawn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HoleController extends Controller {
    public static AISelector aiSelector;
    BufferedReader consoleIn;

    public HoleController(Model model, View view) {
        super(model, view);
        aiSelector = new AISelector(model, view);
        List<Player> players = model.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getType() == Player.COMPUTER) {
                aiSelector.selectAndSetAiType(i);
            }
        }
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
        HoleStageModel stage = (HoleStageModel) model.getGameStage();
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            Decider decider = aiSelector.getDecider(model.getIdPlayer(), this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            try {
                play.start();
            } catch (NullPointerException ignored) {}
        } else {
            boolean ok = false;
            while (!ok) {
                try {
                    String lineFrom, lineTo;

                    if (stage.isFirstPlayer()) {
                        System.out.print(p.getName() + " from > ");
                        lineFrom = consoleIn.readLine();

                        if (!stage.goodFromEntry(lineFrom)) {
                            System.out.println("Incorrect entry. Retry !");
                            continue;
                        }

                        if (!stage.canMoveFrom(lineFrom)) {
                            System.out.println(model.getGameStage().getCurrentPlayerName() + " cannot move. Pass turn.");
                            ok = true;
                            continue;
                        }

                        System.out.print(p.getName() + " to > ");
                        lineTo = consoleIn.readLine();

                        ok = analyseAndPlay(lineFrom, lineTo);

                        if (ok) {
                            stage.setFirstPlayer(false);
                        }
                    } else {
                        lineFrom = stage.findPawnFrom();

                        if (!stage.canMoveFrom(lineFrom)) {
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

                } catch (IOException ignored) {
                }
            }
        }
        stage.isWin();
    }

    public void endOfTurn() {
        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        HoleStageModel stageModel = (HoleStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
    }

    private boolean analyseAndPlay(String from, String to) {
        HoleStageModel stage = (HoleStageModel) model.getGameStage();

        if (from.length() != 2) return false;

        int rowFrom = from.charAt(0) - 'A';
        int colFrom = from.charAt(1) - '1';

        if (!stage.isValidCoordinates(rowFrom, colFrom)) return false;
        if (!stage.goodFromEntry(rowFrom, colFrom)) return false;

        if (to.length() != 2) return false;

        int rowTo = to.charAt(0) - 'A';
        int colTo = to.charAt(1) - '1';

        if (!stage.isValidCoordinates(rowTo, colTo)) return false;

        if (!stage.getBoard().canReachCell(to)) return false;

        stage.setLockedColor(stage, view, rowTo, colTo);

        Pawn pawn = (Pawn) stage.getBoard().getElement(colFrom, rowFrom);

        ActionList actions = ActionFactory.generateMoveWithinContainer(model, pawn, colTo, rowTo);
        actions.setDoEndOfTurn(true);

        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();

        return true;
    }
}
