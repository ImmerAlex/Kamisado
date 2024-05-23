package control;

import boardifier.control.Decider;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import boardifier.view.View;
import model.EntryFileContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTestAISelector {
    Model model;
    View holeView;
    HoleController holeController;
    AISelector aiSelector;

    @BeforeEach
    public void setup() {
        model = new Model();
        model.addComputerPlayer("Computer X");
        model.addComputerPlayer("Computer O");

        StageFactory.registerModelAndView("KamisadoTest", "model.HoleStageModel","view.HoleStageView");

        holeView = new View(model);
    }

    @Test
    void testGetDeciderSmart() {
        EntryFileContainer.addEntry("2");
        EntryFileContainer.addEntry("2");

        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");

        aiSelector = holeController.getAiSelector();

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (Exception e) {
            System.out.println("Cannot start the game. Abort!");
        }

        Decider decider = aiSelector.getDecider(0, holeController);
        assertNotNull(decider);
    }

    @Test
    void testGetDeciderNaive() {
        EntryFileContainer.addEntry("1");
        EntryFileContainer.addEntry("2");

        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");

        aiSelector = holeController.getAiSelector();

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (Exception e) {
            System.out.println("Cannot start the game. Abort!");
        }

        Decider decider = aiSelector.getDecider(1, holeController);
        assertNotNull(decider);
    }

    @Test
    void testGetDeciderInvalid() {
        EntryFileContainer.addEntry("2");
        EntryFileContainer.addEntry("2");

        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");

        aiSelector = holeController.getAiSelector();

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (Exception e) {
            System.out.println("Cannot start the game. Abort!");
        }

        Decider decider = aiSelector.getDecider(2, holeController);
        assertNull(decider);
    }
}
