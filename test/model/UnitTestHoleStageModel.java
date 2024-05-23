package model;
import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.TextElement;
import boardifier.model.action.ActionList;
import boardifier.view.ConsoleColor;
import boardifier.view.View;
import control.HoleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.util.List;


public class UnitTestHoleStageModel {
    Model model;
    HoleController holeController;
    Thread gameThread;

    @BeforeEach
    public void setUp() {
        model = new Model();
        model.addHumanPlayer("Player X");
        model.addHumanPlayer("Player O");

        StageFactory.registerModelAndView("KamisadoTest",
                "model.HoleStageModel",
                "view.HoleStageView");
        View holeView = new View(model);
        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");

        String simulatedInput = "A8\nA6";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try{
            gameThread = new Thread(() -> {
                try {
                    holeController.startGame();
                    holeController.stageLoop();
                } catch (GameException e) {
                    System.out.println("Cannot start the game. Abort!");
                }
            });
            gameThread.start();

            Thread.sleep(1000);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("Test setup was interrupted");
        }

    }

    @Test
    void test(){
        HoleStageModel stage = (HoleStageModel) model.getGameStage();
        Pawn pawn = (Pawn) stage.getBoard().getElement(7, 0);
        try {
            ActionList actions = ActionFactory.generateMoveWithinContainer(model, pawn, 0, 0);
            actions.setDoEndOfTurn(true);

            ActionPlayer play = new ActionPlayer(model, holeController, actions);

            play.start();
        } catch (Exception e) {
            model.stopStage();
            gameThread.interrupt();
            System.out.println("fin");
        }



    }
}
