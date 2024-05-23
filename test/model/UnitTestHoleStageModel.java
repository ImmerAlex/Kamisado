package model;

import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.HoleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UnitTestHoleStageModel {
    Model model;
    HoleController holeController;
    Thread gameThread;

    @BeforeEach
    public void setUp() {
        model = new Model();

        StageFactory.registerModelAndView("KamisadoTest",
                "model.HoleStageModel",
                "view.HoleStageView");
        View holeView = new View(model);
        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");

    }

    @Test
    void testIsWinPlayerX() {
        model.addHumanPlayer("Player X");
        model.addHumanPlayer("Player O");

        EntryFileContainer.addEntry("D8");
        EntryFileContainer.addEntry("D3");
        EntryFileContainer.addEntry("E2");
        EntryFileContainer.addEntry("G7");
        EntryFileContainer.addEntry("A7");
        EntryFileContainer.addEntry("F1");

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort!");
        }

        int winnerId = model.getIdWinner();
        String winnerName = model.getPlayers().get(winnerId).getName();
        assertEquals("Player X", winnerName);
    }

    @Test
    void testIsWinPlayerO() {
        model.addHumanPlayer("Player X");
        model.addHumanPlayer("Player O");

        EntryFileContainer.addEntry("G8");
        EntryFileContainer.addEntry("D5");
        EntryFileContainer.addEntry("H7");
        EntryFileContainer.addEntry("C5");
        EntryFileContainer.addEntry("G8");

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort!");
        }

        int winnerId = model.getIdWinner();
        String winnerName = model.getPlayers().get(winnerId).getName();
        assertEquals("Player O", winnerName);
    }

    @Test
    void testIsWinComputerX() {
        model.addHumanPlayer("Computer X");
        model.addHumanPlayer("Computer O");

        EntryFileContainer.addEntry("D8");
        EntryFileContainer.addEntry("D3");
        EntryFileContainer.addEntry("E2");
        EntryFileContainer.addEntry("G7");
        EntryFileContainer.addEntry("A7");
        EntryFileContainer.addEntry("F1");

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort!");
        }

        int winnerId = model.getIdWinner();
        String winnerName = model.getPlayers().get(winnerId).getName();
        assertEquals("Computer X", winnerName);
    }

    @Test
    void testIsWinComputerO() {
        model.addHumanPlayer("Computer X");
        model.addHumanPlayer("Computer O");

        EntryFileContainer.addEntry("G8");
        EntryFileContainer.addEntry("D5");
        EntryFileContainer.addEntry("H7");
        EntryFileContainer.addEntry("C5");
        EntryFileContainer.addEntry("G8");

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort!");
        }

        int winnerId = model.getIdWinner();
        String winnerName = model.getPlayers().get(winnerId).getName();
        assertEquals("Computer O", winnerName);
    }

    @Test
    void testIsValidCoordinates() {
        HoleStageModel stageModel = new HoleStageModel("KamisadoTest", model);
        assertTrue(stageModel.isValidCoordinates(0, 0));
        assertTrue(stageModel.isValidCoordinates(7, 7));
        assertFalse(stageModel.isValidCoordinates(9, 9));
        assertFalse(stageModel.isValidCoordinates(-1, -1));
        assertFalse(stageModel.isValidCoordinates(0, 9));
        assertFalse(stageModel.isValidCoordinates(9, 0));
    }
}
